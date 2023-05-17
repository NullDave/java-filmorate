package ru.yandex.practicum.filmorate.storage.friend;


import ru.yandex.practicum.filmorate.model.User;

import java.util.List;


public interface FriendStorage {
    void add(long userId, long friendId);

    void confirm(long userId, long friendId);

    void remove(long userId, long friendId);

    List<User> getAll(long userId);

    List<User> getCommon(long userId, long otherUserId);
}
