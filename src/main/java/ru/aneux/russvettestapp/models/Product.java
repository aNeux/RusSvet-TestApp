package ru.aneux.russvettestapp.models;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
public class Product {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	@NotNull(message = "Product name shouldn't be null")
	@Size(min = 1, max = 50, message = "Product name shouldn't be empty and contain no more than 50 characters")
	private String name;

	@Column(name = "description")
	@Size(min = 1, max = 200, message = "Product description shouldn't be empty and contain no more than 100 characters")
	private String description;

	@Column(name = "price")
	@DecimalMin(value = "0.0", message = "Product price couldn't be less than 0")
	@Digits(integer = 15, fraction = 2, message = "Product price could consist of 15 digits max with 2 ones in the decimal part")
	private BigDecimal price;

	@Column(name = "added_at")
	private LocalDateTime addedAt;

	@Column(name = "is_active")
	private boolean active;

	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "id")
	private Category category;

	@OneToOne(mappedBy = "product", cascade = CascadeType.PERSIST)
	private Image image;

	public Product() { }

	public Product(String name, String description, BigDecimal price, LocalDateTime addedAt, boolean active) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.addedAt = addedAt;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public LocalDateTime getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(LocalDateTime addedAt) {
		this.addedAt = addedAt;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
