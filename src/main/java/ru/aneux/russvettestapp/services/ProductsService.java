package ru.aneux.russvettestapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.aneux.russvettestapp.api.errorshandling.exceptions.BadContentTypeException;
import ru.aneux.russvettestapp.api.errorshandling.exceptions.CategoryNotFoundException;
import ru.aneux.russvettestapp.api.errorshandling.exceptions.ImageUploadException;
import ru.aneux.russvettestapp.api.errorshandling.exceptions.ProductNotFoundException;
import ru.aneux.russvettestapp.dto.product.ProductDTO;
import ru.aneux.russvettestapp.dto.product.ProductDetailedDTO;
import ru.aneux.russvettestapp.dto.product.ProductMapper;
import ru.aneux.russvettestapp.models.Image;
import ru.aneux.russvettestapp.models.Product;
import ru.aneux.russvettestapp.repositories.CategoriesRepository;
import ru.aneux.russvettestapp.repositories.ImageRepository;
import ru.aneux.russvettestapp.repositories.ProductsFilter;
import ru.aneux.russvettestapp.repositories.ProductsRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductsService {
	private final ProductsRepository productsRepository;
	private final ImageRepository imageRepository;
	private final CategoriesRepository categoriesRepository;
	private final ProductMapper mapper;

	@Autowired
	public ProductsService(ProductsRepository productsRepository, ImageRepository imageRepository,
			CategoriesRepository categoriesRepository, ProductMapper mapper) {
		this.productsRepository = productsRepository;
		this.imageRepository = imageRepository;
		this.categoriesRepository = categoriesRepository;
		this.mapper = mapper;
	}

	@Transactional(readOnly = true)
	public List<ProductDTO> getPaginated(int page, int perPage, ProductsFilter productsFilter) {
		return productsRepository.findAll(productsFilter, PageRequest.of(page, perPage)).getContent()
				.stream().map(mapper::toDTO).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public ProductDetailedDTO getOne(long id) {
		return mapper.toDetailedDTO(getProduct(id));
	}


	public void save(ProductDetailedDTO productDetailedDTO) {
		Product product = mapper.toEntity(productDetailedDTO);
		changeCategory(product, productDetailedDTO.getCategoryId());
		product.setAddedAt(LocalDateTime.now());
		productsRepository.save(product);
	}

	public void update(long id, ProductDetailedDTO productDetailedDTO) {
		Product product = getProduct(id);
		// Check if category should be changed
		long newCategoryId = productDetailedDTO.getCategoryId();
		if (product.getCategory().getId() != newCategoryId)
			changeCategory(product, newCategoryId);
		// Update rest fields
		mapper.updateOriginalProduct(productDetailedDTO, product);
	}

	public void delete(long id) {
		if (!productsRepository.existsById(id))
			throw new ProductNotFoundException(id);
		productsRepository.deleteById(id);
	}


	@Transactional(readOnly = true)
	public Image getImage(long id) {
		return getProduct(id).getImage();
	}

	public void uploadImage(long id, MultipartFile imageFile) {
		String requestContentType = imageFile.getContentType();
		if (!MediaType.IMAGE_JPEG_VALUE.equals(requestContentType) && !MediaType.IMAGE_PNG_VALUE.equals(requestContentType))
			throw new BadContentTypeException(requestContentType);

		try {
			Product product = getProduct(id);
			Image image = new Image(requestContentType, imageFile.getBytes(), imageFile.getSize());
			image.setProduct(product);
			product.setImage(image);
		} catch (IOException e) {
			throw new ImageUploadException(e);
		}
	}

	public void deleteImage(long id) {
		Product product = getProduct(id);
		Image image = product.getImage();
		if (image != null) {
			product.setImage(null);
			imageRepository.deleteById(image.getId());
		}
	}


	private Product getProduct(long id) {
		return productsRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
	}

	private void changeCategory(Product product, long newCategoryId) {
		product.setCategory(categoriesRepository.findById(newCategoryId)
				.orElseThrow(() -> new CategoryNotFoundException(newCategoryId)));
	}
}
