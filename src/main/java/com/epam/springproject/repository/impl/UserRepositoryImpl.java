package com.epam.springproject.repository.impl;

import com.epam.springproject.model.User;
import com.epam.springproject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class UserRepositoryImpl implements UserRepository {

    private final List<User> list = new ArrayList<>();

    @Override
    public User getUser(String email) {
        log.info("UserRepository getUser with email " + email);
        return list.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User is not found!"));
    }

    @Override
    public User createUser(User user) {
        log.info("UserRepository createUser with email " + user.getEmail());
        list.add(user);
        return user;
    }

    @Override
    public User updateUser(String email, User user) {
        log.info("UserRepository updateUser with email " + email);
        boolean isDeleted = list.removeIf(u -> u.getEmail().equals(email));
        if (isDeleted) {
            list.add(user);
        } else {
            throw new RuntimeException("User is not found!");
        }
        return user;
    }

    @Override
    public void deleteUser(String email) {
        log.info("UserRepository deleteUser with email " + email);
        list.removeIf(user -> user.getEmail().equals(email));
    }

    @Override
    public List<User> listUsers() {
        log.info("UserRepository listUsers method");
        return new ArrayList<>(list);
    }
}