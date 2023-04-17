package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.Errors;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private long counter = 1;

    @Override
    public User add(User user) {
        user.setId(counter++);
        if (user.getName() == null || user.getName().isBlank()) user.setName(user.getLogin());
        users.put(user.getId(), user);
        log.info("добавлен пользователь: " + user);
        return user;
    }

    @Override
    public User update(User user) {
        if (!users.containsKey(user.getId())) throw Errors.notFoundUser(user.getId());
        users.put(user.getId(), user);
        log.info("изменен пользователь: " + user);
        return user;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Optional<User> get(long id) {
        if (!users.containsKey(id)) return Optional.empty();
        return Optional.of(users.get(id));
    }
}
