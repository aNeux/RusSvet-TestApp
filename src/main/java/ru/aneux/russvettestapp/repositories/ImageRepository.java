package ru.aneux.russvettestapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aneux.russvettestapp.models.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
