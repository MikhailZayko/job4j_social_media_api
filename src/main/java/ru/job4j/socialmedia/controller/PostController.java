package ru.job4j.socialmedia.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.socialmedia.dto.post.PostCreateDto;
import ru.job4j.socialmedia.dto.post.PostReadDto;
import ru.job4j.socialmedia.dto.post.PostUpdateDto;
import ru.job4j.socialmedia.dto.userpost.UserPostsDto;
import ru.job4j.socialmedia.service.post.PostService;

import java.net.URI;
import java.util.List;

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
        PostReadDto createdPost = postService.create(postCreateDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPost.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdPost);
    }

    @PatchMapping
    public ResponseEntity<Void> update(@RequestBody PostUpdateDto postUpdateDto) {
            postService.update(postUpdateDto);
            return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> removeById(@PathVariable Integer postId) {
            postService.deleteById(postId);
            return ResponseEntity.noContent().build();
    }

    @GetMapping("/usersWithPosts")
    public ResponseEntity<List<UserPostsDto>> getUsersWithPostsByUserIds(@RequestParam List<Integer> userIds) {
            return ResponseEntity.ok(postService.findUsersWithPostsByUserIds(userIds));
    }
}
