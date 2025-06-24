package ru.job4j.socialmedia.service.post;

import ru.job4j.socialmedia.model.Image;
import ru.job4j.socialmedia.model.Post;

import java.util.List;

public interface PostService {

    Post createPostWithoutImages(Post post);

    Post createPostWithImages(Post post, List<Image> images);

    void updateTitleAndContent(Post post);

    void insertImage(Post post, Image image);

    void deleteImageByPost(Image image, Post post);

    void deleteById(Integer postId);
}
