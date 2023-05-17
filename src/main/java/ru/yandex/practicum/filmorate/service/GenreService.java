package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    private final GenreStorage genreStorage;

    @Autowired
    public GenreService(GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    public Genre get(int id) {
        Optional<Genre> genre = genreStorage.get(id);
        if (genre.isEmpty()) throw new NotFoundException(String.format("Не удалось найти Жанр с id =%d", id));
        return genre.get();
    }

    public List<Genre> getAll() {
        return genreStorage.getAll();
    }
}
