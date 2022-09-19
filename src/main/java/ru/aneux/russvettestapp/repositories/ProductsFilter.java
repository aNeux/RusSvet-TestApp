package ru.aneux.russvettestapp.repositories;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.domain.Specification;
import ru.aneux.russvettestapp.api.errorshandling.exceptions.ValidationException;
import ru.aneux.russvettestapp.models.Product;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Hidden
public class ProductsFilter implements Specification<Product> {
	private String name;
	private Long categoryId;
	private BigDecimal priceFrom, priceTo;

	public static ProductsFilter of(String name, String categoryId, String priceFrom, String priceTo) {
		try {
			ProductsFilter filter = new ProductsFilter();
			filter.name = name;
			if (categoryId != null && !categoryId.isEmpty())
				filter.categoryId = Long.valueOf(categoryId);
			if (priceFrom != null && !priceFrom.isEmpty())
				filter.priceFrom = new BigDecimal(priceFrom);
			if (priceTo != null && !priceTo.isEmpty())
				filter.priceTo = new BigDecimal(priceTo);
			return filter;
		} catch (Exception e) {
			throw new ValidationException("Couldn't obtain filter clauses to search products: " + e.getMessage());
		}
	}


	@Override
	public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		if (name != null && !name.isEmpty())
			predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
		if (categoryId != null)
			predicates.add(criteriaBuilder.equal(root.get("category").get("id"), categoryId));
		if (priceFrom != null)
			predicates.add(criteriaBuilder.ge(root.get("price"), priceFrom));
		if (priceTo != null)
			predicates.add(criteriaBuilder.le(root.get("price"), priceTo));
		return predicates.size() == 0 ? null : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}
}
