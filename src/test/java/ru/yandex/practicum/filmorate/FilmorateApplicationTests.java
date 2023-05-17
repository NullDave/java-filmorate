package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
    private final UserDbStorage userStorage;
    private final FilmDbStorage filmDbStorage;

    private User newUser;
    private Film newFilm;


    @Test
    public void testFindUserById() {
        newUser = new User();
        newUser.setId(1L);
        newUser.setName("Dave");
        newUser.setLogin("0xDave");
        newUser.setBirthday(LocalDate.of(2005, 1, 1));
        newUser.setEmail("simple@id.ru");
        userStorage.add(newUser);

        Optional<User> userOptional = userStorage.get(1);

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user -> {
                            assertThat(user).hasFieldOrPropertyWithValue("id", 1L);
                            assertThat(user).hasFieldOrPropertyWithValue("email", "simple@id.ru");
                            assertThat(user).hasFieldOrPropertyWithValue("login", "0xDave");
                        }
                );
    }


    @Test
    public void testFindFilmById() {
        newFilm = new Film();
        newFilm.setName("Clone");
        newFilm.setId(1L);
        newFilm.setDescription("Клон Бразильский хит");
        newFilm.setReleaseDate(LocalDate.of(2001, 1, 1));
        newFilm.setDuration(90000000);
        Mpa mpa = new Mpa();
        mpa.setId(1);
        newFilm.setMpa(mpa);
        filmDbStorage.add(newFilm);

        Optional<Film> filmOptional = filmDbStorage.get(1);

        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film -> {
                            assertThat(film).hasFieldOrPropertyWithValue("id", 1L);
                            assertThat(film).hasFieldOrPropertyWithValue("name", "Clone");
                            assertThat(film).hasFieldOrPropertyWithValue("description", "Клон Бразильский хит");
                        }
                );
    }


}
