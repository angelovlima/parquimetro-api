package com.fiap.parquimetro.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PlacaValidator implements ConstraintValidator<Placa, String> {

    private static final String PLACA_REGEX = "^[A-Z]{3}-[0-9]{4}$";
    private static final String PLACA_MERCOSUL_REGEX = "^[A-Z]{3}[0-9][A-Z][0-9]{2}$";

    @Override
    public void initialize(Placa constraintAnnotation) {
    }

    @Override
    public boolean isValid(String placa, ConstraintValidatorContext context) {
        if (placa == null) {
            return false;
        }
        return placa.matches(PLACA_REGEX) || placa.matches(PLACA_MERCOSUL_REGEX);
    }
}
