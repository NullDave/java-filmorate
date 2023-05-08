package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.validation.AfterMovieBirthday;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
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

}
