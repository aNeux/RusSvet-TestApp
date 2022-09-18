package ru.aneux.russvettestapp.api.errorshandling;

import java.time.LocalDateTime;

public class ErrorResponse {
	private final String message;
	private final LocalDateTime timestamp;

	public ErrorResponse(String message) {
		this.message = message;
		timestamp = LocalDateTime.now();
	}

	public String getMessage() {
		return message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}
}
