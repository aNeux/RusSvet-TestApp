package ru.aneux.russvettestapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.aneux.russvettestapp.models.Product;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
}
