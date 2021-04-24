package com.tenniscourts.exceptions;

import org.apache.commons.lang3.BooleanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AdminValidator implements
        ConstraintValidator<AdminConstraint, String> {

    @Override
    public void initialize(AdminConstraint contactNumber) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return BooleanUtils.toBoolean(value);
    }

}
