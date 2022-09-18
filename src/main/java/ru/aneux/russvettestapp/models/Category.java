package ru.aneux.russvettestapp.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	@NotNull(message = "Category name shouldn't be null")
	@Size(min = 1, max = 50, message = "Category name shouldn't be empty and contain no more than 50 characters")
	private String name;

	@Column(name = "description")
	@Size(min = 1, max = 100, message = "Category description shouldn't be empty and contain no more than 100 characters")
	private String description;

	@OneToMany(mappedBy = "category")
	private List<Product> products;

	public Category() { }

	public Category(String name, String description) {
		this.name = name;
		this.description = description;
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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
