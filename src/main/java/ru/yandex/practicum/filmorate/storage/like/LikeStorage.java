package ru.yandex.practicum.filmorate.storage.like;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikeStorage {
    void add(long filmId, long userId);

    void remove(long filmId, long userId);

    List<Film> getTop(int count);
}
