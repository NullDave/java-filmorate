package ru.yandex.practicum.filmorate.storage.like;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Component
public class LikeDbStorage implements LikeStorage {
    private final JdbcTemplate jdbcTemplate;

    public LikeDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(long filmId, long userId) {
        jdbcTemplate.update("INSERT INTO film_likes(film_id, user_id) VALUES(?,?)", filmId, userId);

    }

    @Override
    public void remove(long filmId, long userId) {
        jdbcTemplate.update("DELETE FROM film_likes WHERE film_id = ? AND user_id = ?", filmId, userId);
    }

    @Override
    public List<Film> getTop(int count) {
        String sql = "SELECT film.id, film.title, film.description, film.release_date, film.duration, " +
                "mpa.id AS rating_id, mpa.name AS rating_name, mpa.description AS rating_description, " +
                "genre.id AS genre_id, genre.name AS genre_name, " +
                "FROM films AS film " +
                "JOIN mpa_ratings AS mpa ON film.rating_id=mpa.id " +
                "LEFT JOIN film_genres AS fg ON film.id=fg.film_id " +
                "LEFT JOIN genres AS genre ON fg.genre_id=genre.id " +
                "LEFT JOIN film_likes ON film.id = film_likes.film_id " +
                "GROUP BY film.id, genre.id, mpa.id " +
                "ORDER BY COUNT(film_likes.user_id) DESC " +
                "LIMIT ?";
        return jdbcTemplate.query(sql, new Object[]{count}, (rs, rowNum) -> Film.mapingFilm(rs));
    }
}
