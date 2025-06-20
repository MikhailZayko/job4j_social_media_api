package ru.job4j.socialmedia.repository;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.socialmedia.model.FriendRequest;

public interface FriendRequestRepository extends ListCrudRepository<FriendRequest, Integer> {
}
