package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.OrderDTO;
import co.com.bancolombia.api.handlers.ValidatorHandler;
import co.com.bancolombia.api.mapper.OrderDTOMapper;
import co.com.bancolombia.model.common.exception.GeneralException;
import co.com.bancolombia.usecase.order.OrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static co.com.bancolombia.model.common.enums.GeneralExceptionMessage.INVALID_BODY_PARAMETER;

@Component
@RequiredArgsConstructor
public class Handler {
    private final OrderUseCase orderUseCase;
    private final ValidatorHandler validatorHandler;
    private final OrderDTOMapper mapper;

    public Mono<ServerResponse> getOrderById(ServerRequest serverRequest) {
        return orderUseCase.getOrder(1)
                .flatMap(user -> ServerResponse.ok().bodyValue(user));
    }

    public Mono<ServerResponse> saveOrder(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(OrderDTO.class)
                .switchIfEmpty(Mono.error(new GeneralException(INVALID_BODY_PARAMETER)))
                .doOnNext(validatorHandler::validateObject)
                .map(mapper::toEntity)
                .flatMap(orderUseCase::saveOrder)
                .flatMap(order -> ServerResponse.ok().bodyValue(order));
    }
}
