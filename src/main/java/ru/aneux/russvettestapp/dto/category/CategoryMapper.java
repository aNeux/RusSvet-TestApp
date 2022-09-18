package ru.aneux.russvettestapp.dto.category;

import org.mapstruct.*;
import ru.aneux.russvettestapp.models.Category;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CategoryMapper {
	Category toEntity(CategoryDetailedDTO categoryDetailedDTO);

	CategoryDTO toDTO(Category category);
	CategoryDetailedDTO toDetailedDTO(Category category);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(source = "description", target = "description", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
	void updateOriginalCategory(CategoryDetailedDTO categoryDetailedDTO, @MappingTarget Category category);
}
