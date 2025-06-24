package ru.job4j.socialmedia.service.friendrequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.model.FriendRequest;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.FriendRequestRepository;
import ru.job4j.socialmedia.service.subscription.SubscriptionService;

import static ru.job4j.socialmedia.model.enums.FriendRequestStatus.*;

@Service
@RequiredArgsConstructor
@Transactional
public class FriendRequestServiceImpl implements FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;

    private final SubscriptionService subscriptionService;

    @Override
    public FriendRequest sendRequest(User sender, User receiver) {
        FriendRequest request = new FriendRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setStatus(PENDING);
        friendRequestRepository.save(request);
        subscriptionService.subscribe(sender, receiver);
        return request;
    }

    @Override
    public void acceptRequest(FriendRequest friendRequest) {
        friendRequest.setStatus(ACCEPTED);
        friendRequestRepository.save(friendRequest);
        subscriptionService.subscribe(friendRequest.getReceiver(), friendRequest.getSender());
    }

    @Override
    public void declineRequest(FriendRequest friendRequest) {
        friendRequest.setStatus(DECLINED);
        friendRequestRepository.save(friendRequest);
    }

    @Override
    public void removeFriend(FriendRequest friendRequest, User user, User friend) {
        friendRequestRepository.delete(friendRequest);
        subscriptionService.unsubscribe(user, friend);
    }
}
