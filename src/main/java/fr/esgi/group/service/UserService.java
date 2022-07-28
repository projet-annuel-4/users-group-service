package fr.esgi.group.service;

import fr.esgi.group.model.User;
import fr.esgi.group.dto.user.UserEvent;
import fr.esgi.group.dto.user.UserResponse;
import fr.esgi.group.exception.ResourceNotFoundException;
import fr.esgi.group.mapper.UserMapper;
import fr.esgi.group.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User createUser(UserEvent userEvent) {
        var user = userMapper.convertToEntity(userEvent);
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        var user = userRepository.findById(id);
        return user.orElseThrow(()->new ResourceNotFoundException("User", "id", String.valueOf(id)));
    }

    public User getUserByEmail(String email) {
        var user = userRepository.findByEmail(email);
        return user.orElseThrow(()->new ResourceNotFoundException("User", "email", email));
    }
}
