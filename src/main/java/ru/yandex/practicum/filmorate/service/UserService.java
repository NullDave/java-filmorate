package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friend.FriendStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserStorage userStorage;
    private final FriendStorage friendStorage;

    @Autowired
    public UserService(@Qualifier("UserDbStorage") UserStorage userStorage, FriendStorage friendStorage) {
        this.userStorage = userStorage;
        this.friendStorage = friendStorage;
    }

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public User add(User user) {
        if (user.getName() == null || user.getName().isBlank())
            user.setName(user.getLogin());
        return userStorage.add(user);
    }

    public User get(long userId) {
        Optional<User> user = userStorage.get(userId);
        if (user.isEmpty())
            throw new NotFoundException(String.format("Не удалось найти пользователя с id =%d", userId));
        return user.get();
    }

    public List<User> getFriends(long userId) {
        if (userStorage.contains(userId))
            return friendStorage.getAll(userId);
        throw new NotFoundException(String.format("Не удалось найти пользователя с id =%d", userId));
    }

    public User update(User user) {
        if (userStorage.contains(user.getId()))
            return userStorage.update(user);
        throw new NotFoundException(String.format("Не удалось найти пользователя с id =%d", user.getId()));
    }

    public void addFriend(long userId, long friendId) {
        if (!userStorage.contains(userId))
            throw new NotFoundException(String.format("Не удалось найти пользователя с id =%d", userId));
        if (!userStorage.contains(friendId))
            throw new NotFoundException(String.format("Не удалось найти пользователя с id =%d", friendId));
        friendStorage.add(userId, friendId);

    }

    public void removeFriend(long userId, long friendId) {
        if (!userStorage.contains(userId))
            throw new NotFoundException(String.format("Не удалось найти пользователя с id =%d", userId));
        if (!userStorage.contains(friendId))
            throw new NotFoundException(String.format("Не удалось найти пользователя с id =%d", friendId));
        friendStorage.remove(userId, friendId);

    }

    public List<User> getCommonFriends(long userId, long otherUserId) {
        if (userStorage.contains(userId)) {
            if (userStorage.contains(otherUserId)) {
                return friendStorage.getCommon(userId, otherUserId);
            }
            throw new NotFoundException(String.format("Не удалось найти пользователя с id =%d", otherUserId));
        }
        throw new NotFoundException(String.format("Не удалось найти пользователя с id =%d", userId));
    }

}
