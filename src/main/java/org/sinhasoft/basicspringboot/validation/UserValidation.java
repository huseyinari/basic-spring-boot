package org.sinhasoft.basicspringboot.validation;

import org.sinhasoft.basicspringboot.dto.UserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserValidation implements ConstraintValidator<IsUser, UserDTO> {
    @Override
    public void initialize(IsUser constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserDTO user, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext
                .buildConstraintViolationWithTemplate("xx alanı hatalı").addConstraintViolation()
                .buildConstraintViolationWithTemplate("yy alanı hatalı").addConstraintViolation()
                .buildConstraintViolationWithTemplate("zz alanı hatalı").addConstraintViolation();

        return false;
    }
}
