package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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
    public User find(@Valid @Min(1) @PathVariable(name = "id") long userId) {
        return service.get(userId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@Valid @Min(1) @PathVariable(name = "id") long userId) {
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
    public void addFriend(@Valid @Min(1) @PathVariable(name = "id") long userId,
                          @Valid @Min(1) @PathVariable(name = "friendId") long friendId) {
        service.addFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@Valid @Min(1) @PathVariable(name = "id") long userId,
                             @Valid @Min(1) @PathVariable(name = "friendId") long friendId) {
        service.removeFriend(userId, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> findAll(@Valid @Min(1) @PathVariable(name = "id") long userId,
                              @Valid @Min(1) @PathVariable(name = "otherId") long otherId) {
        return service.getCommonFriends(userId, otherId);
    }

}
