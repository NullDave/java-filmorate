package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.Optional;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final LikeStorage likeStorage;
    private final UserStorage userStorage;
    private final MpaStorage mpaStorage;
    private final GenreStorage genreStorage;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                       LikeStorage likeStorage,
                       @Qualifier("UserDbStorage") UserStorage userStorage,
                       MpaStorage mpaStorage,
                       GenreStorage genreStorage) {

        this.filmStorage = filmStorage;
        this.likeStorage = likeStorage;
        this.userStorage = userStorage;
        this.mpaStorage = mpaStorage;
        this.genreStorage = genreStorage;
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }

    public Film get(Long id) {
        Optional<Film> film = filmStorage.get(id);
        if (film.isEmpty()) throw new NotFoundException(String.format("Не удалось найти фильм с id =%d", id));
        return film.get();
    }

    public Film add(Film film) {
        if (validationFilm(film))
            return filmStorage.add(film);
        return null;
    }

    public Film update(Film film) {
        if (filmStorage.contains(film.getId()) && validationFilm(film))
            return filmStorage.update(film);
        throw new NotFoundException(String.format("Не удалось найти фильм с id =%d", film.getId()));
    }

    public void addLike(long filmId, long userId) {
        if (!filmStorage.contains(filmId))
            throw new NotFoundException(String.format("Не удалось найти фильм с id =%d", filmId));
        if (!userStorage.contains(userId))
            throw new NotFoundException(String.format("Не удалось найти пользователя с id =%d", userId));
        likeStorage.add(filmId, userId);
    }

    public void removeLike(long filmId, long userId) {
        if (!filmStorage.contains(filmId))
            throw new NotFoundException(String.format("Не удалось найти фильм с id =%d", filmId));
        if (!userStorage.contains(userId))
            throw new NotFoundException(String.format("Не удалось найти пользователя с id =%d", userId));
        likeStorage.remove(filmId, userId);
    }

    public List<Film> getTop(int count) {
        return likeStorage.getTop(count);
    }

    private boolean validationFilm(Film film) {
        film.getGenres().forEach(genre -> {
            if (!genreStorage.contains(genre.getId())) {
                throw new NotFoundException(String.format("Не удалось найти Жанр с id =%d", genre.getId()));
            }
        });
        if (!mpaStorage.contains(film.getMpa().getId()))
            throw new NotFoundException(String.format("Не удалось найти MPA рейтинг с id =%d", film.getMpa().getId()));
        return true;
    }
}
