package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.validation.Login;

import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private long id;
    @Email
    private String email;
    @Login
    private String login;
    private String name;
    @Past
    private LocalDate birthday;
    private Set<Long> friendsId = new HashSet<>();

}
