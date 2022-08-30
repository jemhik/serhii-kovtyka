package com.epam.springproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "STATUS")
public class Status {

    @JsonIgnore
    @OneToMany(mappedBy = "status")
    @ToString.Exclude
    Set<User> users;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return Objects.equals(statusId, status.statusId) && Objects.equals(name, status.name) && Objects.equals(users, status.users);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
