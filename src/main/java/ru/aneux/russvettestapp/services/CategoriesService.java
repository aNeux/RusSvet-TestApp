package ru.aneux.russvettestapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aneux.russvettestapp.api.errorshandling.exceptions.CategoryNotFoundException;
import ru.aneux.russvettestapp.dto.category.CategoryDTO;
import ru.aneux.russvettestapp.dto.category.CategoryDetailedDTO;
import ru.aneux.russvettestapp.dto.category.CategoryMapper;
import ru.aneux.russvettestapp.models.Category;
import ru.aneux.russvettestapp.repositories.CategoriesRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoriesService {
	private final CategoriesRepository categoriesRepository;
	private final CategoryMapper mapper;

	@Autowired
	public CategoriesService(CategoriesRepository categoriesRepository, CategoryMapper mapper) {
		this.categoriesRepository = categoriesRepository;
		this.mapper = mapper;
	}

	@Transactional(readOnly = true)
	public List<CategoryDTO> getAll() {
		return categoriesRepository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public CategoryDetailedDTO getOne(long id) {
		return mapper.toDetailedDTO(categoriesRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id)));
	}


	public void save(CategoryDetailedDTO categoryDetailedDTO) {
		categoriesRepository.save(mapper.toEntity(categoryDetailedDTO));
	}

	public void update(long id, CategoryDetailedDTO categoryDetailedDTO) {
		mapper.updateOriginalCategory(categoryDetailedDTO,
				categoriesRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id)));
	}

	public void delete(long id) {
		Category category = categoriesRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
		category.getProducts().forEach(product -> {
			product.setCategory(null);
			product.setActive(false);
		});
		categoriesRepository.delete(category);
	}
}
