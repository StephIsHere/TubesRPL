package com.example.TubesRPL.user;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class User {
    private int idUser;
    private String email;
    private String password;
    private String nama;
    private String peran;
    private String npm;
}
 