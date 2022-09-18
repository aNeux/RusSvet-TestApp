package ru.aneux.russvettestapp.api.errorshandling.exceptions;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ValidationException extends RuntimeException {
	public ValidationException(String message) {
		super(message);
	}

	public static ValidationException fromBindingResult(BindingResult bindingResult) {
		StringBuilder builder = new StringBuilder();
		for (FieldError error : bindingResult.getFieldErrors())
			builder.append(error.getDefaultMessage()).append(";");
		builder.setLength(builder.length() - 1);
		return new ValidationException(builder.toString());
	}
}
