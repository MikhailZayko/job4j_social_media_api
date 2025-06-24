package ru.job4j.socialmedia.service.subscription;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.socialmedia.model.Subscription;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.SubscriptionRepository;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public void subscribe(User subscriber, User target) {
        Subscription subscription = new Subscription();
        subscription.setSubscriber(subscriber);
        subscription.setTarget(target);
        subscriptionRepository.save(subscription);
    }

    @Override
    public void unsubscribe(User subscriber, User target) {
        subscriptionRepository.deleteBySubscriberIdAndTargetId(subscriber.getId(), target.getId());
    }
}
