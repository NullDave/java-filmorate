package ru.yandex.practicum.filmorate.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmControllerTest {
    private Film film;
    private FilmController filmController;

    @BeforeEach
    public void setData() {
        film = new Film();
        film.setId(1);
        film.setName("Clone");
        film.setDescription("Клон Бразильский хит");
        film.setReleaseDate(LocalDate.of(2001, 1, 1));
        film.setDuration(90000000);

        filmController = new FilmController();
    }

    @Test
    public void notCorrectID() {
        filmController.create(film);
        film.setId(2);
        assertThrows(NotFoundException.class, () -> filmController.update(film));
    }
}