package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.Errors;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public Film get(Long id) {
        Optional<Film> film = filmStorage.get(id);
        if (film.isEmpty()) throw Errors.notFoundFilm(id);
        return film.get();
    }

    public Film add(Film film) {
        return filmStorage.add(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public Film addLike(long filmId, long userid) {
        Optional<Film> film = filmStorage.get(filmId);
        if (film.isPresent()) {
            if (userStorage.get(userid).isPresent()) {
                film.get().getLikes().add(userid);
                return film.get();
            }
            throw Errors.notFoundUser(userid);
        }
        throw Errors.notFoundFilm(filmId);
    }

    public Film removeLike(long filmId, long userid) {
        Optional<Film> film = filmStorage.get(filmId);
        if (film.isPresent()) {
            if (userStorage.get(userid).isPresent()) {
                film.get().getLikes().remove(userid);
                return film.get();
            }
            throw Errors.notFoundUser(userid);
        }
        throw Errors.notFoundFilm(filmId);
    }

    public List<Film> getTop(int count) {
        return filmStorage.getAll()
                .stream()
                .sorted((film1, film2) -> film2.getLikes().size() - film1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }
}
