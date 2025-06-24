package ru.job4j.socialmedia.service.subscription;

import ru.job4j.socialmedia.model.User;

public interface SubscriptionService {

    void subscribe(User subscriber, User target);

    void unsubscribe(User subscriber, User target);
}
