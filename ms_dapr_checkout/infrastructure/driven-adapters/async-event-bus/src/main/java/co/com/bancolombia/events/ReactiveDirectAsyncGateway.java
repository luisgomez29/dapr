package co.com.bancolombia.events;

import co.com.bancolombia.model.order.Order;
import co.com.bancolombia.model.order.gateways.OrderRepository;
import io.dapr.client.DaprClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.logging.Level;

@Log
@Component
@RequiredArgsConstructor
public class ReactiveDirectAsyncGateway implements OrderRepository {
    public static final String PUBSUB_NAME = "orderpubsub"; // refer to the name of the pubsub type component
    public static final String TOPIC_NAME = "orders";
    private final DaprClient daprClient;


    @Override
    public Mono<Void> save(Order order) {
        log.log(Level.INFO, "Sending command: {0}: {1}", new String[]{TOPIC_NAME, order.toString()});
        return daprClient.publishEvent(PUBSUB_NAME, TOPIC_NAME, order);
    }

}
