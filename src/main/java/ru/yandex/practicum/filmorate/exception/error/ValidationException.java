package ru.yandex.practicum.filmorate.exception.error;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
