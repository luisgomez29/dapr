package co.com.bancolombia.usecase.order;

import co.com.bancolombia.model.order.Order;
import co.com.bancolombia.model.order.gateways.OrderRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class OrderUseCase {

    private final OrderRepository orderRepository;

    public Mono<Order> getOrder(String id) {
        return orderRepository.getOrderById(id);
    }

    public Mono<Order> saveOrder(Order order) {
        return orderRepository.save(order);
    }

}
