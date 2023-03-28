package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.IDUnknownException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


class UserControllerTest {
    private User user;
    private  UserController userController;

    @BeforeEach
    public void setData(){
        user = new User();
        user.setId(1);
        user.setName("Dave");
        user.setLogin("0xDave");
        user.setBirthday(LocalDate.of(2005,1,1));
        user.setEmail("simple@id.ru");

        userController = new UserController();
    }

    @Test
    public void NotCorrectID(){
        userController.create(user);
        user.setId(2);
        assertThrows(IDUnknownException.class,() -> userController.update(user));
    }

    @Test
    public void NotNameUser(){
        user.setName("");
        User nUser = userController.create(user);
        assertEquals(nUser.getName(), nUser.getLogin());
    }
}