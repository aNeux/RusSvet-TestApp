package ru.aneux.russvettestapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aneux.russvettestapp.models.Category;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Long> {
}
