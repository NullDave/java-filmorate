package ru.yandex.practicum.filmorate.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.error.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

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

        filmController = new FilmController(new FilmService(new InMemoryFilmStorage(), new InMemoryUserStorage()));
    }

    @Test
    public void notCorrectID() {
        filmController.create(film);
        film.setId(2);
        assertThrows(NotFoundException.class, () -> filmController.update(film));
    }
}