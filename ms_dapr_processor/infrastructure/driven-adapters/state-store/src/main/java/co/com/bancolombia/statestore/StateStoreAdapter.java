package co.com.bancolombia.statestore;

import co.com.bancolombia.model.common.exception.TechnicalException;
import co.com.bancolombia.model.order.Order;
import co.com.bancolombia.model.order.gateways.OrderRepository;
import io.dapr.client.DaprClient;
import io.dapr.client.domain.State;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import static co.com.bancolombia.model.common.enums.TechnicalExceptionMessage.ORDER_FIND_BY_ID;
import static co.com.bancolombia.model.common.enums.TechnicalExceptionMessage.ORDER_SAVE;

@Log4j2
@Repository
@RequiredArgsConstructor
public class StateStoreAdapter implements OrderRepository {
    private static final String DAPR_STATE_STORE = "orderstore"; // refer to the name of the state store component

    private final DaprClient daprClient;

    @Override
    public Mono<Order> save(Order order) {
        log.info("Saving order with ID: {}", order.getId());
        return daprClient.saveState(DAPR_STATE_STORE, order.getId(), order)
                .thenReturn(order)
                .onErrorResume(e -> Mono.error(new TechnicalException(e.getMessage(), ORDER_SAVE)));
    }

    @Override
    public Mono<Order> getOrderById(String id) {
        return daprClient.getState(DAPR_STATE_STORE, id, Order.class)
                .doOnNext(e -> log.info("Getting order with ID: {}", e.getValue().getId()))
                .map(State::getValue)
                .switchIfEmpty(Mono.error(new TechnicalException(ORDER_FIND_BY_ID)));
    }
}
