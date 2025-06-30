package ru.job4j.socialmedia.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.socialmedia.exception.NotFoundException;
import ru.job4j.socialmedia.model.Post;
import ru.job4j.socialmedia.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostSecurityService {

    private final PostRepository postRepository;

    public boolean isAuthor(int postId, String username) {
        Post post = postRepository.findByIdWithUser(postId)
                .orElseThrow(() -> new NotFoundException(Post.class, postId));
        return post.getUser().getUsername().equals(username);
    }
}
