package ru.job4j.socialmedia.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.socialmedia.dto.post.PostCreateDto;
import ru.job4j.socialmedia.dto.post.PostReadDto;
import ru.job4j.socialmedia.dto.post.PostUpdateDto;
import ru.job4j.socialmedia.service.post.PostService;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostReadDto> get(@PathVariable Integer postId) {
        return postService.findById(postId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PostReadDto> save(@RequestBody PostCreateDto postCreateDto) {
        return ResponseEntity.ok(postService.create(postCreateDto));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody PostUpdateDto postUpdateDto) {
        postService.update(postUpdateDto);
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeById(@PathVariable Integer postId) {
        postService.deleteById(postId);
    }
}
