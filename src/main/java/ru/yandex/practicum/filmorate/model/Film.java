package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.validation.AfterMovieBirthday;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private long id;
    @NotNull
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    @AfterMovieBirthday
    private LocalDate releaseDate;
    @Positive
    private int duration;
    private Set<Genre> genres = new HashSet<>();
    private Mpa mpa;
    private Set<Long> likes = new HashSet<>();

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
