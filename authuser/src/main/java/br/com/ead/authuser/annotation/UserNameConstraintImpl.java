package br.com.ead.authuser.annotation;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNameConstraintImpl implements ConstraintValidator<UserNameConstraint, String> {

	@Override
	public boolean isValid(String userName, ConstraintValidatorContext context) {
		if(Objects.isNull(userName) || userName.trim().isEmpty() || userName.contains(" ")) {
			return false;
		}
		return true;
	}



}
