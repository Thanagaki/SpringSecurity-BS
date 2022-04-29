package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;


public interface UserService {

    List<User> getAllUsers();
    User getById(Long id);
    boolean saveUser(User user);
    void deleteById(Long id);
    void edit(User user);
    User findByUserName(String username);


}
