package ru.job4j.socialmedia.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.socialmedia.model.Image;

public interface ImageRepository extends ListCrudRepository<Image, Integer> {
}
