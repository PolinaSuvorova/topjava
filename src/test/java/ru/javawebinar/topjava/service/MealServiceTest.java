package ru.javawebinar.topjava.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
//        "classpath:spring/spring-inmemory-test.xml"

})
//@ActiveProfiles(profiles = "classpath:spring/spring-app.xml")
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_MEAL_1_ID, USER_ID);
        assertMatch(meal, meal1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND_MEAL_ID, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL_1_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_1_ID, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_MEAL_ID, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> meals = service.getBetweenInclusive(meal2.getDate(), meal4.getDate(), USER_ID);
        MealTestData.assertMatch(meals, meal4, meal3, meal2);
    }

    @Test
    public void getWithoutFilter() {
        List<Meal> meals = service.getBetweenInclusive(null, null, USER_ID);
        MealTestData.assertMatch(meals, meal1, meal4, meal3, meal2);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        MealTestData.assertMatch(all, meal1, meal4, meal3, meal2);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        MealTestData.assertMatch(service.get(updated.getId(), USER_ID), getUpdated());
    }

    @Test
    public void updateNotFound() {
        Meal updated = getUpdated();
        updated.setId(NOT_FOUND_MEAL_ID);
        assertThrows(NotFoundException.class, () -> service.update(updated, USER_ID));
    }

    @Test
    public void updateMealOfOtherUser() {
        Meal updated = getUpdatedMeal2();
        assertThrows(NotFoundException.class, () -> service.update(updated, ADMIN_ID));
    }

    @Test
    public void getMealOfOtherUser() {
        assertThrows(NotFoundException.class, () -> service.get(meal1.getId(), ADMIN_ID));
    }

    @Test
    public void deleteMealOfOtherUser() {
        assertThrows(NotFoundException.class, () -> service.delete(meal1.getId(), ADMIN_ID));
    }

    @Test
    public void duplicateDateCreate() {
        Meal mealForCompareDate = service.get(meal1.getId(), USER_ID);
        Meal mealNewWithDublicateDate = getNew();
        mealNewWithDublicateDate.setDateTime(mealForCompareDate.getDateTime());
        assertThrows(DataAccessException.class, () -> service.create(mealNewWithDublicateDate, USER_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(MealTestData.getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }
}