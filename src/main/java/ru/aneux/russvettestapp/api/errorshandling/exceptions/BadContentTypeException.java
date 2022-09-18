package ru.aneux.russvettestapp.api.errorshandling.exceptions;

public class BadContentTypeException extends RuntimeException {
	public BadContentTypeException(String contentType) {
		super("Not suitable content type '" + contentType + "' has been sent");
	}
}
