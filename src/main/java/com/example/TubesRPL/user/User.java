package com.example.TubesRPL.user;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class User {
    private Long idUser;
    private String nama;
    private String email;
    private String password;
    private String peran;
    private String npm;
    private Boolean status;

    public User() {}
}