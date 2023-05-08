package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.mapper.EntityMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@Qualifier("filmDbStorage")
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;


    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film add(Film film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO films (title, description, release_date, duration, rating_id) VALUES (?, ?, ?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, Date.valueOf(film.getReleaseDate()));
            ps.setInt(4, film.getDuration());
            ps.setInt(5, film.getMpa().getId());
            return ps;
        }, keyHolder);

        long filmId = keyHolder.getKey().longValue();
        if (filmId != 0 && film.getGenres() != null) {
            for (Genre genre : film.getGenres())
                jdbcTemplate.update("INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)", filmId, genre.getId());
        }
        return get(filmId).get();
    }

    @Override
    public Film update(Film film) {
        String sql = "UPDATE films SET title=?, description=?, release_date=?, duration=?, rating_id=? WHERE id=?";

        jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId(), film.getId());

        jdbcTemplate.update("DELETE FROM film_genres WHERE film_id=?", film.getId());
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                jdbcTemplate.update("INSERT INTO film_genres(film_id, genre_id) VALUES(?,?)", film.getId(), genre.getId());
            }
        }
        return get(film.getId()).get();
    }

    @Override
    public List<Film> getAll() {
        String sql = "SELECT film.id, film.title, film.description, film.release_date, film.duration, " +
                "mpa.id AS rating_id, mpa.name AS rating_name, mpa.description AS rating_description " +
                "FROM films AS film " +
                "JOIN mpa_ratings AS mpa ON film.rating_id=mpa.id " +
                "GROUP BY film.id";
        List<Film> films = jdbcTemplate.query(sql, (rs, rowNum) -> EntityMapper.mapingFilm(rs));
        films.forEach(film -> film.setGenres(getGenresFilm(film.getId())));
        return films;
    }

    @Override
    public Optional<Film> get(long id) {
        String sql = "SELECT film.id, film.title, film.description, film.release_date, film.duration, " +
                "mpa.id AS rating_id, mpa.name AS rating_name, mpa.description AS rating_description " +
                "FROM films AS film " +
                "JOIN mpa_ratings AS mpa ON film.rating_id=mpa.id " +
                "WHERE film.id = ?" +
                "GROUP BY film.id";

        Optional<Film> film = jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> EntityMapper.mapingFilm(rs)).stream()
                .findFirst();
        film.ifPresent(value -> value.setGenres(getGenresFilm(id)));
        return film;
    }

    @Override
    public boolean contains(long id) {
        String sql = "SELECT COUNT(*) FROM films WHERE id = ?";
        return 0 < jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);
    }

    private Set<Genre> getGenresFilm(long id) {
        String sql = "SELECT id, name " +
                "FROM film_genres AS fg " +
                "LEFT JOIN genres AS genre ON genre.id=fg.genre_id " +
                "WHERE film_id = ? " +
                "GROUP BY id";
        return new HashSet<>(jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) -> EntityMapper.mappingGenre(rs)));
    }

}