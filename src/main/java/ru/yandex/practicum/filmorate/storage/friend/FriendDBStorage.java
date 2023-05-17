package ru.yandex.practicum.filmorate.storage.friend;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mapper.UserMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Component
public class FriendDBStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;

    public FriendDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(long userId, long friendId) {
        jdbcTemplate.update("INSERT INTO friends(user_Id, friend_id) VALUES(?,?)", userId, friendId);

    }

    @Override
    public void confirm(long userId, long friendId) {
        jdbcTemplate.update("UPDATE friends SET friend_status = TRUE WHERE user_id = ? AND friend_id = ?", userId, friendId);
    }

    @Override
    public void remove(long userId, long friendId) {
        jdbcTemplate.update("DELETE FROM friends WHERE user_id = ? AND friend_id = ?", userId, friendId);
    }

    @Override
    public List<User> getAll(long userId) {
        String sql = "SELECT * " +
                "FROM users " +
                "INNER JOIN friends ON users.id = friends.friend_id " +
                "WHERE friends.user_id = ? " +
                "GROUP BY id";

        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> UserMapper.mapingUser(rs));
    }

    @Override
    public List<User> getCommon(long userId, long otherUserId) {
        String sql = "SELECT u.* " +
                "FROM users AS u " +
                "INNER JOIN friends AS f1 ON f1.friend_id = u.id AND f1.user_id = ? " +
                "INNER JOIN friends AS f2 ON f2.friend_id = u.id AND f2.user_id = ? " +
                "GROUP BY u.id";

        return jdbcTemplate.query(sql, new Object[]{userId, otherUserId}, (rs, rowNum) -> UserMapper.mapingUser(rs));

    }


}
