package co.com.bancolombia.usecase.order;

import co.com.bancolombia.model.order.Order;
import co.com.bancolombia.model.order.gateways.OrderRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class OrderUseCase {

    private final OrderRepository orderRepository;

    public Mono<Order> getOrder(int id) {
        return Mono.just(Order.builder().id("1").productName("Laptop").quantity(2).price(10).build());
    }

    public Mono<Order> saveOrder(Order order) {
        return orderRepository.save(order).thenReturn(order);
    }

}
