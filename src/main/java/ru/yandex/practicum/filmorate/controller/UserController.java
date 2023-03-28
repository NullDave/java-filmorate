package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IDUnknownException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class UserController {

    private final Map<Integer, User> users = new HashMap<>();
    private int id = 1;

    @GetMapping("/users")
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @PostMapping("/users")
    public User create(@Valid @RequestBody User user) {
        user.setId(id++);
        if (user.getName() == null || user.getName().isBlank()) user.setName(user.getLogin());
        users.put(user.getId(), user);
        log.info("добавлен пользователь: " + user);
        return user;
    }

    @PutMapping("/users")
    public User update(@Valid @RequestBody User user) {
        if (user.getId() == 0 || !users.containsKey(user.getId())) throw new IDUnknownException("неизвестный ID");
        users.put(user.getId(), user);
        log.info("изменен пользователь: " + user);
        return user;
    }
}
