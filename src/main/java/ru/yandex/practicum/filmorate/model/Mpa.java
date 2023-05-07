package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Mpa {
    private int id;
    private String name;
    private String description;

    public static Mpa mapingMpa(ResultSet rs) throws SQLException {
        Mpa mpaRating = new Mpa();
        mpaRating.setId(rs.getInt("id"));
        mpaRating.setName(rs.getString("name"));
        mpaRating.setDescription(rs.getString("description"));
        return mpaRating;
    }
}
