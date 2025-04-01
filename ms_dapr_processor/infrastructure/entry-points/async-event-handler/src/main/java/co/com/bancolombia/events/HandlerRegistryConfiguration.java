package co.com.bancolombia.events;

import co.com.bancolombia.model.order.Order;
import co.com.bancolombia.usecase.order.OrderUseCase;
import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequiredArgsConstructor
public class HandlerRegistryConfiguration {

    private final OrderUseCase orderUseCase;

    @Topic(name = "orders", pubsubName = "orderpubsub", deadLetterTopic = "orders-dlq")
    @PostMapping(path = "/orders", consumes = MediaType.ALL_VALUE)
    public Mono<ResponseEntity<String>> getCheckout(@RequestBody(required = false) CloudEvent<Order> cloudEvent) {
        return Mono.just(cloudEvent)
                .doOnNext(e -> log.info("Subscriber received: {}", cloudEvent.getData()))
                .filter(e -> e.getData().getQuantity() != 3)
                .flatMap(e -> orderUseCase.saveOrder(e.getData()))
                .map(e -> ResponseEntity.ok("SUCCESS"))
                .switchIfEmpty(Mono.just(ResponseEntity.internalServerError().body("ERROR")))
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body(e.getMessage())));
    }

    @Topic(name = "orders-dlq", pubsubName = "orderpubsub")
    @PostMapping(path = "/orders-dlq", consumes = MediaType.ALL_VALUE)
    public Mono<ResponseEntity<String>> getCheckoutDlq(@RequestBody(required = false) CloudEvent<Order> cloudEvent) {
        return Mono.just(cloudEvent)
                .doOnNext(e -> log.info("Subscriber DLQ received: {}", cloudEvent.getData()))
                .flatMap(e -> orderUseCase.saveOrder(e.getData()))
                .map(e -> ResponseEntity.ok("SUCCESS"))
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError().body(e.getMessage())));
    }

}
