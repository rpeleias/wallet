package com.recargapay.wallet.web.exception;

import com.recargapay.wallet.exception.InsufficientBalanceException;
import com.recargapay.wallet.exception.WalletAlreadyExistsException;
import com.recargapay.wallet.exception.WalletNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class WalletExceptionHandler {

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<Object> handleWalletNotFoundException(WalletNotFoundException exception) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, exception.getMessage(), Collections.singletonList(exception.getMessage()));
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<Object> handleInsufficientBalanceExceptionn(InsufficientBalanceException exception) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, exception.getMessage(), Collections.singletonList(exception.getMessage()));
    }

    @ExceptionHandler(WalletAlreadyExistsException.class)
    public ResponseEntity<Object> handleWalletAlreadyExistsException(WalletAlreadyExistsException exception) {
        return buildResponseEntity(HttpStatus.CONFLICT, exception.getMessage(), Collections.singletonList(exception.getMessage()));
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus, String message, List<String> errors) {
        ApiError apiError = new ApiError(httpStatus.value(), httpStatus.getReasonPhrase(), LocalDateTime.now(), message, errors);
        return ResponseEntity.status(httpStatus).body(apiError);
    }
}
