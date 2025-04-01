package co.com.bancolombia.api.config;

import co.com.bancolombia.api.Handler;
import co.com.bancolombia.api.RouterRest;
import co.com.bancolombia.api.handlers.ValidatorHandler;
import co.com.bancolombia.api.mapper.OrderDTOMapper;
import co.com.bancolombia.model.order.Order;
import co.com.bancolombia.usecase.order.OrderUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {RouterRest.class, Handler.class})
@WebFluxTest
@Import({CorsConfig.class, SecurityHeadersConfig.class, ValidatorHandler.class})
class ConfigTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private OrderUseCase orderUseCase;

    @MockitoBean
    private OrderDTOMapper orderDTOMapper;

    private Order order;

    @BeforeEach
    void setUp() {
        order = Order.builder()
                .id("1")
                .productName("Product")
                .quantity(10)
                .price(120)
                .build();
    }

    @Test
    void corsConfigurationShouldAllowOrigins() {
        when(orderUseCase.getOrder(anyInt())).thenReturn(Mono.just(order));
        webTestClient.get()
                .uri("/api/orders")
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader().valueEquals("Content-Security-Policy",
                        "default-src 'self'; frame-ancestors 'self'; form-action 'self'")
                .expectHeader().valueEquals("Strict-Transport-Security", "max-age=31536000;")
                .expectHeader().valueEquals("X-Content-Type-Options", "nosniff")
                .expectHeader().valueEquals("Server", "")
                .expectHeader().valueEquals("Cache-Control", "no-store")
                .expectHeader().valueEquals("Pragma", "no-cache")
                .expectHeader().valueEquals("Referrer-Policy", "strict-origin-when-cross-origin");
    }

}