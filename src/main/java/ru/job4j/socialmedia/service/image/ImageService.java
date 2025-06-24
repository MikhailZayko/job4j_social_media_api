package ru.job4j.socialmedia.service.image;

import ru.job4j.socialmedia.model.Image;

public interface ImageService {

    Image save(Image image);

    void deleteImageByPostId(Image image, Integer postId);
}
