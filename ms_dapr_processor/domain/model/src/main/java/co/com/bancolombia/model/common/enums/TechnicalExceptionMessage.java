package co.com.bancolombia.model.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TechnicalExceptionMessage {

    INTERNAL_SERVER_ERROR("TE001", "Internal server error"),
    SECRET_EXCEPTION("TE002", "An error occurred while trying to get AWS secrets"),
    JSON_PROCESSING("TE002", "Error processing request for logs"),

    ORDER_FIND_ALL("TE008", "Error getting all orders"),
    ORDER_FIND_BY_ID("TE009", "Error getting order by id"),
    ORDER_SAVE("TE0010", "Error saving order"),
    ORDER_UPDATE("TE011", "Error updating order"),
    ORDER_DELETE("TE012", "Error deleting order");

    private final String code;
    private final String message;

}
