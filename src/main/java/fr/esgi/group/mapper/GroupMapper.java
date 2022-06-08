package fr.esgi.group.mapper;


import fr.esgi.group.dto.group.CreateGroupRequest;
import fr.esgi.group.dto.group.GroupRequest;
import fr.esgi.group.dto.group.GroupResponse;
import fr.esgi.group.model.Group;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class GroupMapper {

    private final ModelMapper modelMapper;

    public GroupResponse convertToResponse(Group group) {
        return modelMapper.map(group, GroupResponse.class);
    }

    public Group convertToModel(CreateGroupRequest group) {
        return modelMapper.map(group, Group.class);
    }

    public Group convertToModel(GroupRequest group) {
        return modelMapper.map(group, Group.class);
    }
}
