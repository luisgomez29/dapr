package co.com.bancolombia.api;

import co.com.bancolombia.api.handlers.ValidatorHandler;
import co.com.bancolombia.api.mapper.OrderDTOMapper;
import co.com.bancolombia.usecase.order.OrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    private final OrderUseCase orderUseCase;
    private final ValidatorHandler validatorHandler;
    private final OrderDTOMapper mapper;

    public Mono<ServerResponse> getOrderById(ServerRequest serverRequest) {
        return orderUseCase.getOrder(serverRequest.pathVariable("id"))
                .flatMap(user -> ServerResponse.ok().bodyValue(user));
    }

}
