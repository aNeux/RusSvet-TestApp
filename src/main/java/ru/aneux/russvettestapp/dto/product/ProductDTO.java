package ru.aneux.russvettestapp.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class ProductDTO {
	@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	private final Long id;

	@NotNull(message = "Product name shouldn't be null")
	@Size(min = 1, max = 50, message = "Product name shouldn't be empty and contain no more than 50 characters")
	private final String name;

	@DecimalMin(value = "0.0", message = "Product price couldn't be less than 0")
	@Digits(integer = 15, fraction = 2, message = "Product price could consist of 15 digits max with 2 ones in the decimal part")
	private final BigDecimal price;

	@NotNull(message = "Category ID shouldn't be null")
	@Min(value = 0, message = "Category ID should be greater than 0")
	private final Long categoryId;

	public ProductDTO(Long id, String name, BigDecimal price, Long categoryId) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.categoryId = categoryId;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Long getCategoryId() {
		return categoryId;
	}
}
