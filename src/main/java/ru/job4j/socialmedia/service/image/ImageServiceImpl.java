package ru.job4j.socialmedia.service.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.socialmedia.model.Image;
import ru.job4j.socialmedia.repository.ImageRepository;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public void deleteImageByPostId(Image image, Integer postId) {
        imageRepository.deleteImageByPostId(image.getId(), postId);
    }
}
