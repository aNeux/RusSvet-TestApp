package ru.aneux.russvettestapp.dto.category;

import javax.validation.constraints.Size;

public class CategoryDetailedDTO extends CategoryDTO {
	@Size(min = 1, max = 100, message = "Category description shouldn't be empty and contain no more than 100 characters")
	private final String description;

	public CategoryDetailedDTO(Long id, String name, String description) {
		super(id, name);
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
