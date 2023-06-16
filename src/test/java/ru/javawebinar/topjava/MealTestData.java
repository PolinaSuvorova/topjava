package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int USER_ID = 100000;
    public static final int MEAL1_USER_ID = 100003;
    public static final int MEAL2_USER_ID = 100004;
    public static final int NOT_FOUND_MEAL_ID = 100010;
    public static final Meal MEAL1 = new Meal(MEAL1_USER_ID, LocalDateTime.of(2023, Month.JUNE, 16, 07, 10), "Завтрак", 1000);
    public static final Meal MEAL2 = new Meal(MEAL2_USER_ID, LocalDateTime.of(2023, Month.JUNE, 16, 13, 10), "Обед", 2000);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2023, Month.JUNE, 16, 19, 0), "Ужин", 1500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("Update Descr");
        updated.setCalories(1011);
        updated.setDateTime(LocalDateTime.of(2022, Month.JANUARY, 10, 15, 0));
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("registered", "roles").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
    }
}
