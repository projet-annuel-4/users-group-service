package fr.esgi.group.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


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
    private Long creatorId;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_group",
            joinColumns = {
                    @JoinColumn(name = "group_id", foreignKey = @ForeignKey(name = "fk_group_users_id"))
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_group_id"))
            }
    )
    private Set<User> members= new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }
}
