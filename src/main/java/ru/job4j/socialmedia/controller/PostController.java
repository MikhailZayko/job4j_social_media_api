package ru.job4j.socialmedia.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.socialmedia.dto.post.PostCreateDto;
import ru.job4j.socialmedia.dto.post.PostReadDto;
import ru.job4j.socialmedia.dto.post.PostUpdateDto;
import ru.job4j.socialmedia.dto.userpost.UserPostsDto;
import ru.job4j.socialmedia.exception.ExceptionResponse;
import ru.job4j.socialmedia.service.post.PostService;
import ru.job4j.socialmedia.validation.ValidationErrorResponse;

import java.net.URI;
import java.util.List;

@Tag(name = "PostController", description = "PostController management APIs")
@Validated
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(
            summary = "Retrieve a Post by ID",
            description = "Get a post with full details",
            tags = {"Post"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post found",
                    content = @Content(schema = @Schema(implementation = PostReadDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Post not found",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @GetMapping("/{postId}")
    public ResponseEntity<PostReadDto> get(@PathVariable
                                           @NotNull(message = "ID must not be null")
                                           @Positive(message = "Resource number must be 1 or more")
                                           Integer postId) {
        return postService.findById(postId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create a new Post",
            description = "Create a new post with title and content",
            tags = {"Post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Post created",
                    content = @Content(schema = @Schema(implementation = PostReadDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<PostReadDto> save(@Valid @RequestBody PostCreateDto postCreateDto) {
        PostReadDto createdPost = postService.create(postCreateDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPost.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdPost);
    }

    @Operation(
            summary = "Update an existing Post",
            description = "Update post title and content",
            tags = {"Post"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Post not found",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PatchMapping
    public ResponseEntity<Void> update(@Valid @RequestBody PostUpdateDto postUpdateDto) {
        postService.update(postUpdateDto);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Delete a Post",
            description = "Delete a post by ID",
            tags = {"Post"})
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Post deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Post not found",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> removeById(@PathVariable
                                           @NotNull(message = "ID must not be null")
                                           @Positive(message = "Resource number must be 1 or more")
                                           Integer postId) {
        postService.deleteById(postId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Get users with their posts",
            description = "Retrieve multiple users with their associated posts",
            tags = {"Post", "User"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success",
                    content = @Content(schema = @Schema(implementation = UserPostsDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User(s) not found",
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @GetMapping("/usersWithPosts")
    public ResponseEntity<List<UserPostsDto>> getUsersWithPostsByUserIds(
            @RequestParam
            @NotEmpty(message = "The user ID list cannot be empty")
            List<@NotNull @Positive Integer> userIds) {
        return ResponseEntity.ok(postService.findUsersWithPostsByUserIds(userIds));
    }
}
