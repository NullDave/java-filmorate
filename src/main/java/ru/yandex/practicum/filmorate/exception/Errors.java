package ru.yandex.practicum.filmorate.exception;

import ru.yandex.practicum.filmorate.exception.error.NotFoundException;

public class Errors {
    public static NotFoundException notFoundFilm(long id) {
        return new NotFoundException(String.format("Не удалось найти фильм с id =%d", id));
    }

    public static NotFoundException notFoundUser(long id) {
        return new NotFoundException(String.format("Не удалось найти пользователя с id =%d", id));
    }
}
