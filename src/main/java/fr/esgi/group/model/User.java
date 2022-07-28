package fr.esgi.group.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "users")
public class User {

    @Id
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String city;
    private String address;
    private String phoneNumber;
    private String postIndex;
    private String imgUrl;
    @ManyToMany(mappedBy = "members",fetch = FetchType.LAZY)
    Set<Group> groups = new HashSet<>();
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
}
