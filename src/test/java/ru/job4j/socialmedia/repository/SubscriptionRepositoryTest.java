package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.model.Subscription;
import ru.job4j.socialmedia.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class SubscriptionRepositoryTest {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    private User subscriber;

    private User target;

    @BeforeEach
    void setUp() {
        subscriptionRepository.deleteAll();
        userRepository.deleteAll();
        subscriber = new User();
        subscriber.setUsername("subscriber");
        subscriber.setEmail("subscriber@example.com");
        subscriber.setPassword("pass");
        userRepository.save(subscriber);
        target = new User();
        target.setUsername("targetUser");
        target.setEmail("target@example.com");
        target.setPassword("pass");
        userRepository.save(target);
    }

    @Test
    void whenSaveSubscriptionThenFindBySubscriberAndTarget() {
        Subscription subscription = new Subscription();
        subscription.setSubscriber(subscriber);
        subscription.setTarget(target);
        subscriptionRepository.save(subscription);
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        assertThat(subscriptions).hasSize(1);
        assertThat(subscriptions.getFirst()).extracting(
                s -> s.getSubscriber().getUsername(),
                s -> s.getTarget().getUsername()
        ).containsExactly("subscriber", "targetUser");
    }
}