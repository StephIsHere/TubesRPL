package com.example.TubesRPL.user;

import java.util.List;

public interface UserRepository {
    List<User> findUser (String email, String password);
}
