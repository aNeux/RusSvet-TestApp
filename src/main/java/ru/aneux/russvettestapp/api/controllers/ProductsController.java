package ru.aneux.russvettestapp.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.aneux.russvettestapp.api.errorshandling.exceptions.ValidationException;
import ru.aneux.russvettestapp.dto.category.CategoryDetailedDTO;
import ru.aneux.russvettestapp.dto.product.ProductDTO;
import ru.aneux.russvettestapp.dto.product.ProductDetailedDTO;
import ru.aneux.russvettestapp.models.Image;
import ru.aneux.russvettestapp.repositories.ProductsFilter;
import ru.aneux.russvettestapp.services.ProductsService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
	private final ProductsService productsService;

	@Autowired
	public ProductsController(ProductsService productsService) {
		this.productsService = productsService;
	}

	@Operation(summary = "Возвращает постраничный список всех продуктов в сокращенном виде")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductDTO.class))),
			@ApiResponse(responseCode = "500", description = "Произошла внутренняя ошибка сервера", content = @Content)
	})
	@GetMapping
	public List<ProductDTO> getProducts(@Parameter(description = "индекс текущей страницы (отсчет с 0)", required = true) @RequestParam("page") int page,
			@Parameter(description = "количество продуктов на одной странице", required = true) @RequestParam("perPage") int perPage,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Описание фильтра для поиска соответствующих категорий",
					content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductsFilter.class))})
			@RequestBody(required = false) ProductsFilter productsFilter) {
		return productsService.getPaginated(page, perPage, productsFilter);
	}

	@Operation(summary = "Возвращает подробную информацию о запрашиваемом продукте")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductDetailedDTO.class))),
			@ApiResponse(responseCode = "404", description = "Продукт не был найден", content = @Content),
			@ApiResponse(responseCode = "500", description = "Произошла внутренняя ошибка сервера", content = @Content)
	})
	@GetMapping("/{id}")
	public ProductDetailedDTO getOneProduct(@Parameter(description = "идентификатор запрашивамого продукта", required = true)
			@PathVariable("id") long id) {
		return productsService.getOne(id);
	}


	@Operation(summary = "Создает новый продукт")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Продукт был успешно создан", content = @Content),
			@ApiResponse(responseCode = "400", description = "Произошла ошибка валидации", content = @Content),
			@ApiResponse(responseCode = "500", description = "Произошла внутренняя ошибка сервера", content = @Content)
	})
	@PostMapping
	public ResponseEntity<HttpStatus> createProduct(@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "Полное описание создаваемого продукта (поле \"addedAt\" будет проигнорировано)", required = true,
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductDetailedDTO.class)))
			@RequestBody @Valid ProductDetailedDTO productDetailedDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw ValidationException.fromBindingResult(bindingResult);

		productsService.save(productDetailedDTO);
		return ResponseEntity.ok(HttpStatus.CREATED);
	}

	@Operation(summary = "Обновляет указанный продукт")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Обновление произведено успешно", content = @Content),
			@ApiResponse(responseCode = "400", description = "Произошла ошибка валидации", content = @Content),
			@ApiResponse(responseCode = "404", description = "Обновляемый продукт или соответствующая категория не были найдены", content = @Content),
			@ApiResponse(responseCode = "500", description = "Произошла внутренняя ошибка сервера", content = @Content)
	})
	@PutMapping("/{id}")
	public ResponseEntity<HttpStatus> updateProduct(@Parameter(description = "идентификатор обновляемого продукта", required = true)
			@PathVariable("id") long id, @io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "Полное описание продукта, включающее как измененные поля, так и все остальные (поле \"addedAt\" будет проигнорировано)",
			required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductDetailedDTO.class)))
			@RequestBody @Valid ProductDetailedDTO productDetailedDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw ValidationException.fromBindingResult(bindingResult);

		productsService.update(id, productDetailedDTO);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@Operation(summary = "Производит удаление указанного продукта")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Продукт был успешно удален", content = @Content),
			@ApiResponse(responseCode = "404", description = "Продукт с указанным идентификатором не был найден", content = @Content),
			@ApiResponse(responseCode = "500", description = "Произошла внутренняя ошибка сервера", content = @Content)
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@Parameter(description = "идентификатор удаляемого продукта", required = true)
			@PathVariable("id") long id) {
		productsService.delete(id);
		return ResponseEntity.ok(HttpStatus.OK);
	}


	@Operation(summary = "Возвращает изображение для указанного продукта")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					content = {@Content(mediaType = MediaType.IMAGE_PNG_VALUE), @Content(mediaType = MediaType.IMAGE_JPEG_VALUE)}),
			@ApiResponse(responseCode = "204", description = "Для указанного продукта изображение не установлено", content = @Content),
			@ApiResponse(responseCode = "404", description = "Продукт с указанным идентификатором не был найден", content = @Content),
			@ApiResponse(responseCode = "500", description = "Произошла внутренняя ошибка сервера", content = @Content)
	})
	@GetMapping("/{id}/image")
	public ResponseEntity<byte[]> getProductImage(@Parameter(description = "идентификатор продукта", required = true)
			@PathVariable("id") long id) {
		Image image = productsService.getImage(id);
		return image == null ? ResponseEntity.noContent().build()
				: ResponseEntity.ok().contentType(MediaType.valueOf(image.getContentType())).body(image.getData());
	}

	@Operation(summary = "Загружает и устанавливает изображение для указанного продукта")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Изображение было успешно добавлено", content = @Content),
			@ApiResponse(responseCode = "400", description = "Загружаемый ресурс имеет некорректный media type", content = @Content),
			@ApiResponse(responseCode = "404", description = "Продукт с указанным идентификатором не был найден", content = @Content),
			@ApiResponse(responseCode = "500", description = "Произошла внутренняя ошибка сервера", content = @Content)
	})
	@PostMapping("/{id}/image")
	public ResponseEntity<HttpStatus> uploadProductImage(@Parameter(description = "идентификатор продукта", required = true)
			@PathVariable("id") long id, @io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "Изображение, которое должно быть добавлено к указанному продукту", required = true,
			content = {@Content(mediaType = MediaType.IMAGE_PNG_VALUE), @Content(mediaType = MediaType.IMAGE_JPEG_VALUE)})
			@RequestParam("image") MultipartFile imageFile) {
		productsService.uploadImage(id, imageFile);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@Operation(summary = "Удаляет изображение для указанного продукта")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Изображение было успешно удалено", content = @Content),
			@ApiResponse(responseCode = "404", description = "Продукт с указанным идентификатором не был найден", content = @Content),
			@ApiResponse(responseCode = "500", description = "Произошла внутренняя ошибка сервера", content = @Content)
	})
	@DeleteMapping("/{id}/image")
	public ResponseEntity<HttpStatus> deleteProductImage(@Parameter(description = "идентификатор продукта", required = true)
			@PathVariable("id") long id) {
		productsService.deleteImage(id);
		return ResponseEntity.ok(HttpStatus.OK);
	}
}
