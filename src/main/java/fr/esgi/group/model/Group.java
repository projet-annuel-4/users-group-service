package fr.esgi.group.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groups_id_seq")
    @SequenceGenerator(name = "groups_id_seq", sequenceName = "groups_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    private Set<User> members= new HashSet<>();
}
