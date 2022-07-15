package com.epam.springproject.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TextBoxValidator implements ConstraintValidator<TextBoxConstraint, String> {
    @Override
    public boolean isValid(String text, ConstraintValidatorContext constraintValidatorContext) {
        return text != null && text.matches("^[a-zA-Z ]+[1-9]*");
    }
}
