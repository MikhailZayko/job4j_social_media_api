package ru.job4j.socialmedia.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.socialmedia.dto.user.UserCreateDto;
import ru.job4j.socialmedia.dto.user.UserReadDto;
import ru.job4j.socialmedia.dto.user.UserUpdateDto;
import ru.job4j.socialmedia.service.user.UserService;

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
        return ResponseEntity.ok(userService.create(userCreateDto));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody UserUpdateDto userUpdateDto) {
        userService.update(userUpdateDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeById(@PathVariable Integer userId) {
        userService.deleteById(userId);
    }
}
