package ru.aneux.russvettestapp.api.errorshandling;

import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import ru.aneux.russvettestapp.api.errorshandling.exceptions.*;

@RestControllerAdvice
public class ErrorAdvice {
	@ExceptionHandler({ProductNotFoundException.class, CategoryNotFoundException.class})
	public ResponseEntity<ErrorResponse> handleNotFoundExceptions(RuntimeException e) {
		return createResponse(e, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ValidationException.class, BadContentTypeException.class, MaxUploadSizeExceededException.class})
	public ResponseEntity<ErrorResponse> handleBadRequestExceptions(RuntimeException e) {
		return createResponse(e, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ImageUploadException.class, PSQLException.class})
	public ResponseEntity<ErrorResponse> handleInternalErrorExceptions(Exception e) {
		return createResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}


	private ResponseEntity<ErrorResponse> createResponse(Exception e, HttpStatus status) {
		return new ResponseEntity<>(new ErrorResponse(e.getMessage()), status);
	}
}
