package com.fiap.parquimetro.api.exception;

public class PaymentMethodNotSelectedException extends RuntimeException {
    public PaymentMethodNotSelectedException(String message) {
        super(message);
    }
}
