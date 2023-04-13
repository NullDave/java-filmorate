package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 1;

    @GetMapping("/films")
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @PostMapping("/films")
    public Film create(@Valid @RequestBody Film film) {
        film.setId(id++);
        films.put(film.getId(), film);
        log.info("добавлен фильм: " + film);
        return film;
    }

    @PutMapping("/films")
    public Film update(@Valid @RequestBody Film film) {
        if (!films.containsKey(film.getId())) throw new NotFoundException("неизвестный ID");
        films.put(film.getId(), film);
        log.info("изменён фильм: " + film);
        return film;
    }
}
