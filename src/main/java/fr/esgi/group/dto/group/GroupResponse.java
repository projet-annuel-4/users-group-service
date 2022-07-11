package fr.esgi.group.dto.group;

import fr.esgi.group.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupResponse {
    @NotNull
     private Long id;
    @NotNull
     private String name;
     private Set<UserResponse> members;
}
