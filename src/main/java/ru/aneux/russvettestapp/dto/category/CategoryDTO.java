package ru.aneux.russvettestapp.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CategoryDTO {
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private final Long id;

	@NotNull(message = "Category name shouldn't be null")
	@Size(min = 1, max = 50, message = "Category name shouldn't be empty and contain no more than 50 characters")
	private final String name;

	public CategoryDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
