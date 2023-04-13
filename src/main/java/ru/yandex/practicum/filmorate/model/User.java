package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.validation.Login;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    private int id;
    @Email
    private String email;
    @Login
    private String login;
    private String name;
    @Past
    private LocalDate birthday;

}
