package ru.aneux.russvettestapp.dto.product;

import org.mapstruct.*;
import ru.aneux.russvettestapp.models.Product;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ProductMapper {
	Product toEntity(ProductDetailedDTO productDetailedDTO);

	@Mapping(source = "category.id", target = "categoryId")
	ProductDTO toDTO(Product product);
	@Mapping(source = "category.id", target = "categoryId")
	ProductDetailedDTO toDetailedDTO(Product product);

	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	@Mapping(source = "description", target = "description", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
	@Mapping(target = "addedAt", ignore = true)
	void updateOriginalProduct(ProductDetailedDTO productDetailedDTO, @MappingTarget Product product);
}
