package ru.aneux.russvettestapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "API каталога товаров",
		version = "1.0",
		description = "Описание доступных методов API в соответствии с тестовым заданием от компании \"Русский свет\"",
		contact = @Contact(
				name = "Павел Тропинов",
				email = "pavel.tropinov@gmail.com")))
public class RusSvetTestApp {
	public static void main(String[] args) {
		SpringApplication.run(RusSvetTestApp.class, args);
	}
}
