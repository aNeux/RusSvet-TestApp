package ru.aneux.russvettestapp.api.errorshandling.exceptions;

public class ImageUploadException extends RuntimeException {
	public ImageUploadException(Throwable cause) {
		super("Error occurred while uploading an image: " + cause.getMessage());
	}
}
