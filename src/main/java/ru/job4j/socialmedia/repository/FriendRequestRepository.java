package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.socialmedia.model.FriendRequest;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Integer> {
}
