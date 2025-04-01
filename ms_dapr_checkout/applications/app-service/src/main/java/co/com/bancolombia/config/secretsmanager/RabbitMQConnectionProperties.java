package co.com.bancolombia.config.secretsmanager;

public record RabbitMQConnectionProperties(
        String virtualhost,
        String hostname,
        String password,
        String username,
        boolean ssl,
        Integer port) {
}