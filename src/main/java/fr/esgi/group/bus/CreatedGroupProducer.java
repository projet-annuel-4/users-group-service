package fr.esgi.group.bus;

import fr.esgi.group.dto.group.GroupEvent;
import fr.esgi.group.mapper.UserMapper;
import fr.esgi.group.model.Group;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component("createdGroupProducer")
public class CreatedGroupProducer {
    static final String GROUP_CREATED_OUTPUT = "createdGroupProducer-out-0";
    private final StreamBridge streamBridge;
    private final UserMapper userMapper;

    public void groupCreated(Group group) {
        var groupEvent = GroupEvent.builder()
                .id(group.getId())
                .name(group.getName())
                .members(group.getMembers().stream().map(userMapper::convertToResponse).collect(Collectors.toSet()))
                .build();

        streamBridge.send(GROUP_CREATED_OUTPUT, groupEvent);

        log.info("New group with id '{}' and name '{}' sent to bus.", groupEvent.getId(), groupEvent.getName());
    }
}