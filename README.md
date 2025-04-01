## DAPR

Ejemplo de microservicios usando [DAPR](https://dapr.io/).

## Ejecutar

### Requisitos
- Docker
- [Dapr CLI](https://docs.dapr.io/getting-started/install-dapr-cli/)
- Java 17 o superior
- PostgreSQL
- RabbitMQ
- Redis

Generar el archivo jar para microservicio:

```bash
./gradlew clean build
```

Luego ejecutar el siguiente comando:

```bash
dapr run --app-id ms_dapr_checkout --app-port 8080 --dapr-http-port 3500 --resources-path ./dapr/components --config ./dapr/config/config.yaml -- java -jar -Dspring.profiles.active=local ./ms_dapr_checkout/applications/app-service/build/libs/ms_dapr_checkout.jar
```

En otra terminal ejecutar el siguiente comando:

```bash
dapr run --app-id ms_dapr_processor --app-port 8081 --dapr-http-port 3501 --resources-path ./dapr/components --config ./dapr/config/config.yaml -- java -jar -Dspring.profiles.active=local ./ms_dapr_processor/applications/app-service/build/libs/ms_dapr_processor.jar
```
