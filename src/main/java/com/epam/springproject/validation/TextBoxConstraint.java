package com.epam.springproject.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TextBoxValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TextBoxConstraint {
    String message() default "Invalid text format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
