package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_MEAL_1_ID = START_SEQ + 3;
    public static final int USER_MEAL_2_ID = USER_MEAL_1_ID + 1;
    public static final int USER_MEAL_3_ID = USER_MEAL_2_ID + 1;

    public static final int USER_MEAL_4_ID = USER_MEAL_3_ID + 1;
    public static final int ADMIN_MEAL_1_ID = USER_MEAL_3_ID + 2;
    public static final int NOT_FOUND_MEAL_ID = ADMIN_MEAL_1_ID + 5;
    public static final Meal meal1 = new Meal(USER_MEAL_1_ID, LocalDateTime.of(2023, Month.JUNE, 17, 0, 0), "Ночной завтрак", 1000);
    public static final Meal meal2 = new Meal(USER_MEAL_2_ID, LocalDateTime.of(2023, Month.JUNE, 15, 7, 0), "Завтрак", 1000);
    public static final Meal meal3 = new Meal(USER_MEAL_3_ID, LocalDateTime.of(2023, Month.JUNE, 16, 7, 10), "Завтрак", 1000);
    public static final Meal meal4 = new Meal(USER_MEAL_4_ID, LocalDateTime.of(2023, Month.JUNE, 16, 13, 10), "Обед", 2000);

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2023, Month.JUNE, 22, 22, 0), "Ужин", 1500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(meal1);
        updated.setDescription("Update Descr");
        updated.setCalories(1011);
        updated.setDateTime(LocalDateTime.of(2022, Month.JANUARY, 10, 15, 0));
        return updated;
    }

    public static Meal getUpdatedMeal2() {
        Meal updated = new Meal(meal2);
        updated.setDescription("Update Descr");
        updated.setCalories(1011);
        updated.setDateTime(LocalDateTime.of(2024, Month.JANUARY, 10, 15, 0));
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields("registered", "roles").isEqualTo(expected);
    }
}
