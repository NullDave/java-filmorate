package ru.yandex.practicum.filmorate.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AfterMovieBirthdayValidator implements ConstraintValidator<AfterMovieBirthday, LocalDate> {
    private LocalDate movieBirthday = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (localDate != null) {
            if (localDate.isAfter(movieBirthday)) {
                return true;
            }
            if (localDate.isEqual(movieBirthday)) {
                return true;
            }
        }
        return false;
    }
}
