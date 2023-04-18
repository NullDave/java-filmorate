package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> getAll() {
        return userStorage.getAll();
    }

    public User add(User user) {
        return userStorage.add(user);
    }

    public User get(Long userId) {
        Optional<User> user = userStorage.get(userId);
        if (user.isEmpty())
            throw new NotFoundException(String.format("Не удалось найти пользователя с id =%d", userId));
        return user.get();
    }

    public List<User> getFriends(Long userId) {
        Optional<User> user = userStorage.get(userId);
        if (user.isEmpty())
            throw new NotFoundException(String.format("Не удалось найти пользователя с id =%d", userId));
        return user.get().getFriendsId().stream()
                .map(userStorage::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public User addFriend(Long userId, Long friendId) {
        Optional<User> user = userStorage.get(userId);
        if (user.isPresent()) {
            Optional<User> friendUser = userStorage.get(friendId);
            if (friendUser.isPresent()) {
                user.get().getFriendsId().add(friendId);
                friendUser.get().getFriendsId().add(userId);
                return user.get();
            }
            throw new NotFoundException(String.format("Не удалось найти пользователя с id =%d", friendId));
        }
        throw new NotFoundException(String.format("Не удалось найти пользователя с id =%d", userId));
    }

    public User removeFriend(Long userId, Long friendId) {
        Optional<User> user = userStorage.get(userId);
        if (user.isPresent()) {
            Optional<User> friendUser = userStorage.get(friendId);
            if (friendUser.isPresent()) {
                user.get().getFriendsId().remove(friendId);
                friendUser.get().getFriendsId().remove(userId);
                return user.get();
            }
            throw new NotFoundException(String.format("Не удалось найти пользователя с id =%d", friendId));
        }
        throw new NotFoundException(String.format("Не удалось найти пользователя с id =%d", userId));
    }

    public List<User> getCommonFriends(Long userId, Long otherUserId) {
        Optional<User> user = userStorage.get(userId);
        if (user.isPresent()) {
            Optional<User> otherUser = userStorage.get(otherUserId);
            if (otherUser.isPresent()) {
                Set<Long> common = new HashSet<>(user.get().getFriendsId());
                common.retainAll(otherUser.get().getFriendsId());
                return common.stream()
                        .map(userStorage::get)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());
            }
            throw new NotFoundException(String.format("Не удалось найти пользователя с id =%d", otherUserId));
        }
        throw new NotFoundException(String.format("Не удалось найти пользователя с id =%d", userId));
    }

}
