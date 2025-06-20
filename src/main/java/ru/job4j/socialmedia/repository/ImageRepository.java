package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.socialmedia.model.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
