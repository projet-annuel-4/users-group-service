package fr.esgi.group.dto.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateGroupRequest {
    @NotNull
    private String name;
    @NotNull
    private Long creatorId;
    @NotEmpty(message = "Input members list cannot be empty.")
    private Set<String> members;
}
