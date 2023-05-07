package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Genre {
    private int id;
    private String name;

    public static Genre mappingGenre(ResultSet rs) throws SQLException {
        Genre genre = new Genre();
        genre.setId(rs.getInt("id"));
        genre.setName(rs.getString("name"));
        return genre;
    }
}
