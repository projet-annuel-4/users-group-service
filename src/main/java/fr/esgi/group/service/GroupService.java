package fr.esgi.group.service;


import fr.esgi.group.bus.CreatedGroupProducer;
import fr.esgi.group.dto.group.CreateGroupRequest;
import fr.esgi.group.exception.BadRequestException;
import fr.esgi.group.exception.ResourceNotFoundException;
import fr.esgi.group.mapper.UserMapper;
import fr.esgi.group.model.Group;
import fr.esgi.group.model.User;
import fr.esgi.group.repository.GroupRepository;
import fr.esgi.group.socket.SocketModel;
import fr.esgi.group.socket.SocketType;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {
    private static final String NOTIFICATIONS_URL="/notifications/";

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final GroupRepository groupRepository;
    private final UserMapper userMapper;
    private final CreatedGroupProducer createdGroupProducer;
    private final UserService userService;

    public Group createGroup(CreateGroupRequest groupRequest) {
        var groupModel= new Group();
        if(groupRepository.findByName(groupRequest.getName()).isPresent()){
            throw new BadRequestException("Exist name of group.");
        }
        groupModel.setName(groupRequest.getName());
        groupModel.setMembers(groupRequest.getMembers().stream().map(memberId->userService.getUserById(Long.parseLong(memberId))).collect(Collectors.toSet()));
        var group= groupRepository.save(groupModel);
        createdGroupProducer.groupCreated(group);
        group.getMembers().forEach(member->{
            simpMessagingTemplate.convertAndSend("/notifications/" + member.getId(),
                    new SocketModel(SocketType.GROUP_CREATED, group.getName()));
                });
       return group;
    }

    public void deleteGroupById(Long id) {
        var group= groupRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Group","id",id.toString()));
        groupRepository.deleteById(id);
        group.getMembers().forEach(member->{
            simpMessagingTemplate.convertAndSend(NOTIFICATIONS_URL + member.getId(),
                    new SocketModel(SocketType.GROUP_DELETED, group.getName()));
        });

    }
    @Transactional
    public Set<Group> getGroupByUserEmail(String email) {
        var user = userService.getUserByEmail(email);
        var groups= groupRepository.findAll().stream().filter(group -> group.getMembers().contains(user)).collect(Collectors.toSet());
        return groups;
    }
    @Transactional
    public Group addMembers(Long id,Group groupModel) {
        var group = groupRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Group","id",id.toString()));
        group.getMembers().addAll(groupModel.getMembers());
        groupRepository.saveAndFlush(group);
        group.getMembers().forEach(member->{
            simpMessagingTemplate.convertAndSend(NOTIFICATIONS_URL + member.getId(),
                    new SocketModel(SocketType.GROUP_MEMBERS_ADDED, groupModel.getMembers().stream().map(userMapper::convertToResponse).collect(Collectors.toSet())));
        });
        return group;
    }

    public Group deleteMembers(Long id, Group groupModel) {
        var group = groupRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Group","id",id.toString()));
        group.getMembers().removeAll(groupModel.getMembers());
        groupRepository.saveAndFlush(group);
        group.getMembers().forEach(member->{
            simpMessagingTemplate.convertAndSend(NOTIFICATIONS_URL + member.getId(),
                    new SocketModel(SocketType.GROUP_MEMBERS_DELETED, groupModel.getMembers().stream().map(userMapper::convertToResponse).collect(Collectors.toSet())));
        });
        return group;
    }

    public Group updateGroupName(Long id, String name) {
        var group = groupRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Group","id",id.toString()));
        group.setName(name);
         groupRepository.saveAndFlush(group);
        group.getMembers().forEach(member->{
            simpMessagingTemplate.convertAndSend(NOTIFICATIONS_URL + member.getId(),
                    new SocketModel(SocketType.GROUP_NAME_UPDATED, group.getName()));
        });
        return group;
    }
    @Transactional
    public List<Group> getGroupAll() {
        return groupRepository.findAll();
    }
    public Group getGroupById(Long id) {
        return groupRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Group","id",id.toString()));
    }

    public Set<User> getGroupMembers(Long id) {
        var group = groupRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Group","id",id.toString()));
        return group.getMembers();
    }
}
