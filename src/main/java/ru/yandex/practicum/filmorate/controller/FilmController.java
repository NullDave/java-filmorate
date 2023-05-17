package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(value = "/films")
public class FilmController {
    private final FilmService service;

    @Autowired
    public FilmController(FilmService service) {
        this.service = service;
    }

    @GetMapping
    public List<Film> findAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Film find(@Valid @Min(1) @PathVariable(name = "id") long filmId) {
        return service.get(filmId);
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return service.add(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return service.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addlike(@Valid @Min(1) @PathVariable(name = "id") long filmId,
                        @Valid @Min(1) @PathVariable(name = "userId") long userId) {
        service.addLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@Valid @Min(1) @PathVariable(name = "id") long filmId,
                           @Valid @Min(1) @PathVariable(name = "userId") long userId) {
        service.removeLike(filmId, userId);
    }

    @GetMapping("/popular")
    public List<Film> getTop(@RequestParam(value = "count", defaultValue = "10", required = false) int count) {
        return service.getTop(count);
    }
}
