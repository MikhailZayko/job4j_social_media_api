package ru.job4j.socialmedia.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.socialmedia.dto.user.UserCreateDto;
import ru.job4j.socialmedia.dto.user.UserReadDto;
import ru.job4j.socialmedia.dto.user.UserUpdateDto;
import ru.job4j.socialmedia.service.user.UserService;

import java.net.URI;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserReadDto> get(@PathVariable Integer userId) {
        return userService.findById(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserReadDto> save(@RequestBody UserCreateDto userCreateDto) {
        UserReadDto createdUser = userService.create(userCreateDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdUser);
    }

    @PatchMapping
    public ResponseEntity<Void> update(@RequestBody UserUpdateDto userUpdateDto) {
        try {
            userService.update(userUpdateDto);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeById(@PathVariable Integer userId) {
        try {
            userService.deleteById(userId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
