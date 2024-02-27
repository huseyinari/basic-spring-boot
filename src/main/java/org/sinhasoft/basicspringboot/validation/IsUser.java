package org.sinhasoft.basicspringboot.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UserValidation.class)
@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsUser {
    String message() default "Hatalı User";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
