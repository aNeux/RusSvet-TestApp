package ru.aneux.russvettestapp.dto.product;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDetailedDTO extends ProductDTO {
	@Size(min = 1, max = 200, message = "Product description shouldn't be empty and contain no more than 100 characters")
	private final String description;

	private final LocalDateTime addedAt;

	@NotNull(message = "Activity status should be specified manually")
	private final Boolean active;

	public ProductDetailedDTO(Long id, String name, BigDecimal price, Long categoryId, String description,
			LocalDateTime addedAt, boolean active) {
		super(id, name, price, categoryId);
		this.description = description;
		this.addedAt = addedAt;
		this.active = active;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getAddedAt() {
		return addedAt;
	}

	public boolean isActive() {
		return active;
	}
}
