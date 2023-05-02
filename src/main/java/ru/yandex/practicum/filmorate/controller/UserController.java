package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> findAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public User find(@PathVariable(name = "id") long userId) {
        return service.get(userId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable(name = "id") long userId) {
        return service.getFriends(userId);
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return service.add(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return service.update(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable(name = "id") long userId, @PathVariable(name = "friendId") long friendId) {
        return service.addFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User removeFriend(@PathVariable(name = "id") long userId, @PathVariable(name = "friendId") long friendId) {
        return service.removeFriend(userId, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> findAll(@PathVariable(name = "id") long userId, @PathVariable(name = "otherId") long otherId) {
        return service.getCommonFriends(userId, otherId);
    }

}
