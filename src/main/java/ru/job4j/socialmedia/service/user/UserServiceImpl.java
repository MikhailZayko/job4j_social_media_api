package ru.job4j.socialmedia.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmedia.dto.user.UserCreateDto;
import ru.job4j.socialmedia.dto.user.UserReadDto;
import ru.job4j.socialmedia.dto.user.UserUpdateDto;
import ru.job4j.socialmedia.exception.NotFoundException;
import ru.job4j.socialmedia.mapper.UserMapper;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public UserReadDto create(UserCreateDto userCreateDto) {
        User user = userMapper.toEntity(userCreateDto);
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    @Override
    public Optional<UserReadDto> findById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }

    @Override
    public void update(UserUpdateDto userUpdateDto) {
        User existing = userRepository.findById(userUpdateDto.getId())
                .orElseThrow(() -> new NotFoundException(User.class, userUpdateDto.getId()));
        userMapper.updateFromDto(userUpdateDto, existing);
        userRepository.save(existing);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(User.class, id));
        userRepository.deleteById(id);
    }
}
