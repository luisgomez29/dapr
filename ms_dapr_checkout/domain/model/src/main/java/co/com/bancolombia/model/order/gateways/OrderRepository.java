package co.com.bancolombia.model.order.gateways;

import co.com.bancolombia.model.order.Order;
import reactor.core.publisher.Mono;

public interface OrderRepository {
    Mono<Void> save(Order order);
}
