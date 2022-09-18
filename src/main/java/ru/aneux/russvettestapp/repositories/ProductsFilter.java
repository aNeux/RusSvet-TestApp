package ru.aneux.russvettestapp.repositories;

import org.springframework.data.jpa.domain.Specification;
import ru.aneux.russvettestapp.models.Product;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductsFilter implements Specification<Product> {
	private String name;
	private Long categoryId;
	private BigDecimal priceFrom, priceTo;

	@Override
	public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		if (name != null && !name.isEmpty())
			predicates.add(criteriaBuilder.equal(root.get("name"), "%" + name + "%"));
		if (categoryId != null)
			predicates.add(criteriaBuilder.equal(root.get("categoryId"), categoryId));
		if (priceFrom != null)
			predicates.add(criteriaBuilder.ge(root.get("price"), priceFrom));
		if (priceTo != null)
			predicates.add(criteriaBuilder.le(root.get("price"), priceTo));
		return predicates.size() == 0 ? null : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public BigDecimal getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(BigDecimal priceFrom) {
		this.priceFrom = priceFrom;
	}

	public BigDecimal getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(BigDecimal priceTo) {
		this.priceTo = priceTo;
	}
}
