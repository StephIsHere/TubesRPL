package com.example.TubesRPL.user;

import java.util.List;

public interface UserRepository {
    List<User> findUser(String email, String password);
    void addUser(User user);
    List<User> findAll();
    List<User> findUserByName(String name);
    List<User> findAllDesc();
    List<User> findUserByRole(String role);
}