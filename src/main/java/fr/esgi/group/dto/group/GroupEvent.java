package fr.esgi.group.dto.group;

import fr.esgi.group.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupEvent {
     private Long id;
     private Long creatorId;
     private String name;
     private Set<UserResponse> members;
}
