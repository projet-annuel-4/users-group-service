package fr.esgi.group.controller;


import fr.esgi.group.dto.group.CreateGroupRequest;
import fr.esgi.group.dto.group.GroupRequest;
import fr.esgi.group.dto.group.GroupResponse;
import fr.esgi.group.dto.user.UserResponse;
import fr.esgi.group.mapper.GroupMapper;
import fr.esgi.group.mapper.UserMapper;
import fr.esgi.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final GroupMapper groupMapper;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity createGroup(@RequestBody @Valid @NotNull CreateGroupRequest groupRequest) {
        var group = groupService.createGroup(groupRequest);
        URI location = URI.create(
                ServletUriComponentsBuilder.fromCurrentRequest().build().toUri() + "/" + group.getId());
        return ResponseEntity.created(location).body(group);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteGroupById(@PathVariable Long id) {
        groupService.deleteGroupById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> getGroupById(@PathVariable Long id) {
        var group = groupService.getGroupById(id);
        return ResponseEntity.ok(groupMapper.convertToResponse(group));
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getGroups() {
        var groups = groupService.getGroupAll();
        return ResponseEntity.ok(groups.stream().map(groupMapper::convertToResponse).collect(Collectors.toList()));
    }

    @GetMapping("/member/{email}")
    public ResponseEntity<List<GroupResponse>> getGroupsByMemberEmail(@PathVariable String email) {
        var groups = groupService.getGroupByUserEmail(email);
        return ResponseEntity.ok(groups.stream().map(groupMapper::convertToResponse).collect(Collectors.toList()));
    }

    @PatchMapping("/{id}/add-members")
    public ResponseEntity<GroupResponse> addMembers(@PathVariable Long id, @RequestBody @Valid @NotNull GroupRequest groupRequest) {
        var group = groupService.addMembers(id, groupMapper.convertToModel(groupRequest));
        return ResponseEntity.ok(groupMapper.convertToResponse(group));
    }

    @PatchMapping("/{id}/delete-members")
    public ResponseEntity<GroupResponse> deleteMembers(@PathVariable Long id, @RequestBody @Valid @NotNull GroupRequest groupRequest) {
        var group = groupService.deleteMembers(id, groupMapper.convertToModel(groupRequest));
        return ResponseEntity.ok(groupMapper.convertToResponse(group));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GroupResponse> updateGroupName(@PathVariable Long id, @RequestParam(value = "name", required = true) String name) {
        var group = groupService.updateGroupName(id, name);
        return ResponseEntity.ok(groupMapper.convertToResponse(group));
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<UserResponse>> getGroupMembers(@PathVariable Long id) {
        var members = groupService.getGroupMembers(id);
        return ResponseEntity.ok(members.stream().map(userMapper::convertToResponse).collect(Collectors.toList()));
    }
}
