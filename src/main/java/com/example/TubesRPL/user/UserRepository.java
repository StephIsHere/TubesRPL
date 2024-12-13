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
    List<User> findByNik(String nik);
    List<User> findByID (Long idUser);
    void updateUser(User user);
    boolean saveTtd (Long idUser, byte[] ttd);
    List<TandaTangan> getTtdByUserId (Long userId);
}