package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.List;
import java.util.Optional;

@Service
public class MpaService {
    private final MpaStorage mpaStorage;

    @Autowired
    public MpaService(MpaStorage mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public Mpa get(int id) {
        Optional<Mpa> mpa = mpaStorage.get(id);
        if (mpa.isEmpty()) throw new NotFoundException(String.format("Не удалось найти MPA рейтинг с id =%d", id));
        return mpa.get();
    }

    public List<Mpa> getAll() {
        return mpaStorage.getAll();
    }

}
