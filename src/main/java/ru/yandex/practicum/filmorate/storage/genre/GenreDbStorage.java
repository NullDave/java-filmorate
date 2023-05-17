package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Optional;

@Component
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Genre> get(int id) {
        String sql = "SELECT * FROM genres WHERE id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> GenreMapper.mappingGenre(rs)).stream().findFirst();
    }

    @Override
    public List<Genre> getAll() {
        String sql = "SELECT * FROM genres GROUP BY id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> GenreMapper.mappingGenre(rs));
    }

    @Override
    public boolean contains(int id) {
        String sql = "SELECT COUNT(*) FROM genres WHERE id = ?";
        return 0 < jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
    }


}
