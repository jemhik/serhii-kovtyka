package com.epam.springproject.repository;

import com.epam.springproject.model.User;

import java.util.List;

public interface UserRepository {

    User getUser(String email);

    User createUser(User user);

    User updateUser(String email, User user);

    void deleteUser(String email);

    List<User> listUsers();
}
