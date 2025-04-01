package co.com.bancolombia.model.common.exception;

import co.com.bancolombia.model.common.enums.BusinessExceptionMessage;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final BusinessExceptionMessage businessExceptionMessage;

    public BusinessException(BusinessExceptionMessage businessExceptionMessage) {
        super(businessExceptionMessage.getMessage());
        this.businessExceptionMessage = businessExceptionMessage;
    }
}
