package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mapper.EntityMapper;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;
import java.util.Optional;

@Component
public class MpaDbStroge implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    public MpaDbStroge(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Mpa> get(int id) {
        String sql = "SELECT * FROM mpa_ratings WHERE id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> EntityMapper.mapingMpa(rs)).stream().findFirst();
    }


    @Override
    public List<Mpa> getAll() {
        String sql = "SELECT * FROM mpa_ratings GROUP BY id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> EntityMapper.mapingMpa(rs));
    }

    @Override
    public boolean contains(int id) {
        String sql = "SELECT COUNT(*) FROM mpa_ratings WHERE id = ?";
        return 0 < jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
    }

}
