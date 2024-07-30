package com.fiap.parquimetro.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PlacaValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Placa {

    String message() default "Placa de veículo inválida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}