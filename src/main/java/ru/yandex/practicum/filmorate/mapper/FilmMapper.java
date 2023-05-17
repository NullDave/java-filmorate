package ru.yandex.practicum.filmorate.mapper;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FilmMapper {

    public static Film mapingFilm(ResultSet rs) throws SQLException {
        Film film = new Film();
        film.setId(rs.getLong("id"));
        film.setName(rs.getString("title"));
        film.setDescription(rs.getString("description"));
        film.setReleaseDate(rs.getDate("release_date").toLocalDate());
        film.setDuration(rs.getInt("duration"));

        Mpa mpaRating = new Mpa();
        mpaRating.setId(rs.getInt("rating_id"));
        mpaRating.setName(rs.getString("rating_name"));
        mpaRating.setDescription(rs.getString("rating_description"));
        film.setMpa(mpaRating);
        return film;
    }
}
