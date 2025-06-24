package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.socialmedia.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Subscription s WHERE s.subscriber.id = :subscriberId AND s.target.id = :targetId")
    void deleteBySubscriberIdAndTargetId(
            @Param("subscriberId") Integer subscriberId,
            @Param("targetId") Integer targetId
    );
}
