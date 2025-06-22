package ru.job4j.socialmedia.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.socialmedia.model.FriendRequest;
import ru.job4j.socialmedia.model.Subscription;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.model.enums.FriendRequestStatus;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    private User user1;

    private User user2;

    private User user3;

    private User user4;

    @BeforeEach
    void setUp() {
        user1 = createUser("user1", "user1@example.com", "pass1");
        user2 = createUser("user2", "user2@example.com", "pass2");
        user3 = createUser("user3", "user3@example.com", "pass3");
        user4 = createUser("user4", "user4@example.com", "pass4");
    }

    private User createUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        return userRepository.save(user);
    }

    @Test
    void whenSaveUserThenFindById() {
        User user = createUser("testUser", "test@example.com", "password");
        Optional<User> foundUser = userRepository.findById(user.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("testUser");
    }

    @Test
    void whenFindAllUsers() {
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(4)
                .extracting(User::getUsername)
                .containsExactly("user1", "user2", "user3", "user4");
    }

    @Test
    void whenFindByLoginAndPasswordThenUserFound() {
        Optional<User> foundUser = userRepository.findByLoginAndPassword("user1", "pass1");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("user1@example.com");
    }

    @Test
    void whenFindByLoginAndPasswordWithWrongCredentialsThenEmpty() {
        Optional<User> foundUser = userRepository.findByLoginAndPassword("user1", "wrongpass");
        assertThat(foundUser).isEmpty();
    }

    @Test
    void whenFindSubscribersByUserIdThenReturnSubscribers() {
        createSubscription(user2, user1);
        createSubscription(user3, user1);
        List<User> subscribers = userRepository.findSubscribersByUserId(user1.getId());
        assertThat(subscribers)
                .hasSize(2)
                .extracting(User::getUsername)
                .containsExactlyInAnyOrder("user2", "user3");
    }

    @Test
    void whenFindFriendsByUserIdThenReturnFriends() {
        createFriendRequest(user1, user2, FriendRequestStatus.ACCEPTED);
        createFriendRequest(user3, user1, FriendRequestStatus.ACCEPTED);
        createFriendRequest(user1, user4, FriendRequestStatus.PENDING);
        createFriendRequest(user4, user1, FriendRequestStatus.DECLINED);
        List<User> friends = userRepository.findFriendsByUserId(user1.getId());
        assertThat(friends)
                .hasSize(2)
                .extracting(User::getUsername)
                .containsExactlyInAnyOrder("user2", "user3");
    }

    private void createSubscription(User subscriber, User target) {
        Subscription subscription = new Subscription();
        subscription.setSubscriber(subscriber);
        subscription.setTarget(target);
        entityManager.persist(subscription);
    }

    private void createFriendRequest(User sender, User receiver, FriendRequestStatus status) {
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        friendRequest.setStatus(status);
        entityManager.persist(friendRequest);
    }
}