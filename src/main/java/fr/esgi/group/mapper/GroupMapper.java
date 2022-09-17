package fr.esgi.group.mapper;


import fr.esgi.group.dto.group.CreateGroupRequest;
import fr.esgi.group.dto.group.GroupRequest;
import fr.esgi.group.dto.group.GroupResponse;
import fr.esgi.group.model.Group;
import fr.esgi.group.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class GroupMapper {

    private final ModelMapper modelMapper;

    public GroupResponse convertToResponse(Group group) {
        return modelMapper.map(group, GroupResponse.class);
    }

    public Group convertToModel(CreateGroupRequest groupRequest) {
        return Group.builder()
                .name(groupRequest.getName())
                .members(groupRequest.getMembers().stream().map(memberId-> User.builder().id(Long.parseLong(memberId)).build()).collect(Collectors.toSet()))
                .build();
    }

    public Group convertToModel(GroupRequest groupRequest) {
        return Group.builder()
                .id(groupRequest.getId())
                .name(groupRequest.getName())
                .members(groupRequest.getMembers().stream().map(memberId-> User.builder().id(Long.parseLong(memberId)).build()).collect(Collectors.toSet()))
                .build();
    }
}
