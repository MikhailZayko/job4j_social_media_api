package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.socialmedia.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
