package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.socialmedia.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
}
