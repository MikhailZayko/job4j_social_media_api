package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmedia.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("""
            SELECT u FROM User u
            WHERE u.username = :login AND u.password = :password
            """)
    Optional<User> findByLoginAndPassword(@Param("login") String login, @Param("password") String password);

    @Query("""
            SELECT s.subscriber FROM Subscription s
            WHERE s.target.id = :userId
            """)
    List<User> findSubscribersByUserId(@Param("userId") Integer userId);

    @Query("""
                SELECT DISTINCT u FROM User u
                WHERE u.id IN (
                    SELECT fr.sender.id FROM FriendRequest fr
                    WHERE fr.receiver.id = :userId AND fr.status = 'ACCEPTED'
                    UNION ALL
                    SELECT fr.receiver.id FROM FriendRequest fr
                    WHERE fr.sender.id = :userId AND fr.status = 'ACCEPTED'
                )
            """)
    List<User> findFriendsByUserId(@Param("userId") Integer userId);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
