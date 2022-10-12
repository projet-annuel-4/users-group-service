package fr.esgi.group.mapper;


import fr.esgi.group.model.User;
import fr.esgi.group.dto.user.UserEvent;
import fr.esgi.group.dto.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserResponse convertToResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    public User convertToEntity(UserEvent user) {
        return modelMapper.map(user, User.class);
    }
}
