package com.epam.springproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_id", referencedColumnName = "roleId")
    @JsonIgnore
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="status_id", referencedColumnName = "statusId")
    @JsonIgnore
    private Status status;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacher", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Course> courses;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<StudentCourse> studentCourses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(role, user.role) && Objects.equals(status, user.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, role, status);
    }
}
