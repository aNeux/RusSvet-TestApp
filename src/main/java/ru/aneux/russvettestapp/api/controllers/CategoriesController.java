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
import ru.aneux.russvettestapp.api.errorshandling.exceptions.ValidationException;
import ru.aneux.russvettestapp.dto.category.CategoryDTO;
import ru.aneux.russvettestapp.dto.category.CategoryDetailedDTO;
import ru.aneux.russvettestapp.services.CategoriesService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {
	private final CategoriesService categoriesService;

	@Autowired
	public CategoriesController(CategoriesService categoriesService) {
		this.categoriesService = categoriesService;
	}

	@Operation(summary = "Возвращает список всех категорий в сокращенном виде")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CategoryDTO.class))),
			@ApiResponse(responseCode = "500", description = "Произошла внутренняя ошибка сервера", content = @Content)
	})
	@GetMapping
	public List<CategoryDTO> getAllCategories() {
		return categoriesService.getAll();
	}

	@Operation(summary = "Возвращает подробную информацию о запрошенной категории")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CategoryDetailedDTO.class))),
			@ApiResponse(responseCode = "404", description = "Категория не была найдена", content = @Content),
			@ApiResponse(responseCode = "500", description = "Произошла внутренняя ошибка сервера", content = @Content)
	})
	@GetMapping("/{id}")
	public CategoryDetailedDTO getCategory(@Parameter(description = "идентификатор запрашиваемой категории", required = true)
			@PathVariable("id") long id) {
		return categoriesService.getOne(id);
	}


	@Operation(summary = "Создает новую категорию")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Категория была успешно создана", content = @Content),
			@ApiResponse(responseCode = "400", description = "Произошла ошибка валидации", content = @Content),
			@ApiResponse(responseCode = "500", description = "Произошла внутренняя ошибка сервера", content = @Content)
	})
	@PostMapping
	public ResponseEntity<HttpStatus> createCategory(@io.swagger.v3.oas.annotations.parameters.RequestBody(
			description = "Полное описание создаваемой категории", required = true,
			content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CategoryDetailedDTO.class)))
			@RequestBody @Valid CategoryDetailedDTO categoryDetailedDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw ValidationException.fromBindingResult(bindingResult);

		categoriesService.save(categoryDetailedDTO);
		return ResponseEntity.ok(HttpStatus.CREATED);
	}

	@Operation(summary = "Обновляет указанную категорию")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Обновление произведено успешно", content = @Content),
			@ApiResponse(responseCode = "400", description = "Произошла ошибка валидации", content = @Content),
			@ApiResponse(responseCode = "404", description = "Обновляемая категория не была найдена", content = @Content),
			@ApiResponse(responseCode = "500", description = "Произошла внутренняя ошибка сервера", content = @Content)
	})
	@PutMapping("/{id}")
	public ResponseEntity<HttpStatus> updateCategory(@Parameter(description = "идентификатор обновляемой категории", required = true)
			@PathVariable("id") long id, @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Полное описание категории, включающее как измененные поля, так и все остальные", required = true,
					content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CategoryDetailedDTO.class)))
			@RequestBody @Valid CategoryDetailedDTO categoryDetailedDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw ValidationException.fromBindingResult(bindingResult);

		categoriesService.update(id, categoryDetailedDTO);
		return ResponseEntity.ok(HttpStatus.OK);
	}

	@Operation(summary = "Производит удаление указанной категории")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Категория была успешно удалена", content = @Content),
			@ApiResponse(responseCode = "404", description = "Категория с указанным идентификатором не была найдена", content = @Content),
			@ApiResponse(responseCode = "500", description = "Произошла внутренняя ошибка сервера", content = @Content)
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteCategory(@Parameter(description = "идентификатор удаляемой категории", required = true)
			@PathVariable("id") long id) {
		categoriesService.delete(id);
		return ResponseEntity.ok(HttpStatus.OK);
	}
}
