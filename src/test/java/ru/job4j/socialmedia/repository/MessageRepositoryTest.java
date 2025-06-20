package ru.job4j.socialmedia.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.model.Message;
import ru.job4j.socialmedia.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    private User sender;

    private User receiver;

    @BeforeEach
    void setUp() {
        messageRepository.deleteAll();
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
    void whenSaveMessageThenFindContent() {
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent("Hello!");
        messageRepository.save(message);
        Message foundMessage = messageRepository.findById(message.getId()).orElseThrow();
        assertThat(foundMessage.getContent()).isEqualTo("Hello!");
        assertThat(foundMessage.getSender().getUsername()).isEqualTo("sender");
    }
}