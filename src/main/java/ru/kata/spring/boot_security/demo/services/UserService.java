package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    User getUserByName(String username);

    List<User> getAllUsers();

    User getUserById(Long id);

    void save(User user);

    void update(Long id, User updateUser);

    void deleteUser(Long id);

    List<Role> getAllRoles();

}