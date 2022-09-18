package ru.aneux.russvettestapp.api.errorshandling.exceptions;

public class ProductNotFoundException extends RuntimeException {
	public ProductNotFoundException(long id) {
		super("Product with id " + id + " wasn't found");
	}
}
