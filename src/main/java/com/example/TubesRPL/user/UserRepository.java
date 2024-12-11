package com.example.TubesRPL.user;

import java.util.*;

public interface UserRepository {
    List<User> findUser(String email, String password);
    void addUser(User user);
    List<User> findAll();
    List<User> findUserByName(String name);
    List<User> findAllDesc();
    List<User> findUserByRole(String role);
    void setUserInactive(Long idUser);
<<<<<<< HEAD
    List<User> findByNpm(String npm);
    List<User> findById(Long id);
    void save(User user);
=======
    List<User> findByNik(String nik);
    List<User> findByID (Long idUser);
>>>>>>> 1515fc8e896a6df47e8d0321e2f8edfb98e6162b
}