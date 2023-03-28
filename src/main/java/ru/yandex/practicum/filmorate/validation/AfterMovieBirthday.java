package ru.yandex.practicum.filmorate.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = AfterMovieBirthdayValidator.class)
@Documented
public @interface AfterMovieBirthday {
    String message() default "Дата должна быть ровна или меньше чем 28 декабря 1895 года.";

    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

}
