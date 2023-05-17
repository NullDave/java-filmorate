package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
@Qualifier("UserDbStorage")
@Slf4j
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public User add(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO users (username, login, email, birthday) VALUES (?, ?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, user.getName());
            ps.setString(2, user.getLogin());
            ps.setString(3, user.getEmail());
            ps.setDate(4, Date.valueOf(user.getBirthday()));
            return ps;
        }, keyHolder);

        long userId = keyHolder.getKey().longValue();
        return get(userId).get();
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE users SET username=?, login=?, email=?, birthday=? WHERE id=?";

        jdbcTemplate.update(sql, user.getName(), user.getLogin(), user.getEmail(),
                user.getBirthday(), user.getId());

        return get(user.getId()).get();
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM users;";

        return jdbcTemplate.query(sql, (rs, rowNum) -> UserMapper.mapingUser(rs));
    }

    @Override
    public Optional<User> get(long id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        return jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> UserMapper.mapingUser(rs)).stream().findFirst();
    }

    @Override
    public boolean contains(long id) {
        String sql = "SELECT COUNT(*) FROM users WHERE id=?";
        return 0 < jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
    }

}
