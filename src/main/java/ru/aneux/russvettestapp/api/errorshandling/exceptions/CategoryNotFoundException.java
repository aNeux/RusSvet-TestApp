package ru.aneux.russvettestapp.api.errorshandling.exceptions;

public class CategoryNotFoundException extends RuntimeException {
	public CategoryNotFoundException(long id) {
		super("Category with id " + id + " wasn't found");
	}
}
