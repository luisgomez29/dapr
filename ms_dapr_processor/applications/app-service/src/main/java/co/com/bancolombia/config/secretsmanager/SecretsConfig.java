package co.com.bancolombia.config.secretsmanager;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dapr.client.DaprClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Map;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class SecretsConfig {

    private final DaprClient daprClient;
    private static final ObjectMapper JSON_SERIALIZER = new ObjectMapper();

    private static final String SECRET_STORE_NAME = "aws-secrets";

    public <T> T getSecret(String secretName, Class<T> cls) {
        try {
            Map<String, String> secret = daprClient.getSecret(SECRET_STORE_NAME, secretName).block();

            var r = JSON_SERIALIZER.readValue(secret.get(secretName), cls);
            log.info("Secret was obtained successfully: {}", r);
            return r;
        } catch (JsonProcessingException e) {
            log.error("Error getting secret: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Bean
    @Profile("local")
    public RabbitMQConnectionProperties getSecretRabbit() {
        return getSecret("gestion-envio-local-secretrabbit-CNX", RabbitMQConnectionProperties.class);
    }

}
