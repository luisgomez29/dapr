package co.com.bancolombia.model.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessExceptionMessage {

    ORDER_NOT_FOUND("BE001", "Order not found"),
    RATE_LIMIT_EXCEPTION("BE002", "Requests exceeded quota");

    private final String code;
    private final String message;
}
