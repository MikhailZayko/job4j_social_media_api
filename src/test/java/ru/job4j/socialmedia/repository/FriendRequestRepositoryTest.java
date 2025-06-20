package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.model.FriendRequest;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.model.enums.FriendRequestStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class FriendRequestRepositoryTest {

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private UserRepository userRepository;

    private User sender;

    private User receiver;

    @BeforeEach
    void setUp() {
        friendRequestRepository.deleteAll();
        userRepository.deleteAll();
        sender = new User();
        sender.setUsername("sender");
        sender.setEmail("sender@example.com");
        sender.setPassword("pass");
        userRepository.save(sender);
        receiver = new User();
        receiver.setUsername("receiver");
        receiver.setEmail("receiver@example.com");
        receiver.setPassword("pass");
        userRepository.save(receiver);
    }

    @Test
    void whenSaveFriendRequestThenCheckStatus() {
        FriendRequest request = new FriendRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setStatus(FriendRequestStatus.PENDING);
        friendRequestRepository.save(request);
        List<FriendRequest> requests = friendRequestRepository.findAll();
        assertThat(requests).hasSize(1);
        assertThat(requests.getFirst().getStatus()).isEqualTo(FriendRequestStatus.PENDING);
        assertThat(requests.getFirst().getSender().getUsername()).isEqualTo("sender");
    }
}