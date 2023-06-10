package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.util.Collection;
import java.util.List;

// TODO add userId
public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal save(Meal meal, Integer idUser);

    // false if meal does not belong to userId
    boolean delete(int id, Integer idUser);

    // null if meal does not belong to userId
    Meal get(int id, Integer idUser);

    // ORDERED dateTime desc
    Collection<Meal> getAll(Integer idUser);

}
