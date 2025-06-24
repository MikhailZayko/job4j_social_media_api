package ru.job4j.socialmedia.service.friendrequest;

import ru.job4j.socialmedia.model.FriendRequest;
import ru.job4j.socialmedia.model.User;

public interface FriendRequestService {

    FriendRequest sendRequest(User sender, User receiver);

    void acceptRequest(FriendRequest friendRequest);

    void declineRequest(FriendRequest friendRequest);

    void removeFriend(FriendRequest friendRequest, User user, User friend);
}
