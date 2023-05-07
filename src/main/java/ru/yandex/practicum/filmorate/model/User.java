package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.validation.Login;

import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public static User mapingUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("username"));
        user.setLogin(rs.getString("login"));
        user.setEmail(rs.getString("email"));
        user.setBirthday(rs.getDate("birthday").toLocalDate());
        return user;
    }
}
