package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.Errors;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> films = new HashMap<>();
    private long counter = 1;

    @Override
    public Film add(Film film) {
        film.setId(counter++);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) throw Errors.notFoundFilm(film.getId());
        films.put(film.getId(), film);
        log.info("изменён фильм: " + film);
        return film;
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Optional<Film> get(long id) {
        if (!films.containsKey(id)) return Optional.empty();
        return Optional.of(films.get(id));
    }


}
