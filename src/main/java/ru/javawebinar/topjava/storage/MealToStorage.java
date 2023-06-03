package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealToStorage {
    void clear();

    void update(Integer id, MealTo meal);

    void save(MealTo meal);

    MealTo get(Integer id);

    void delete(Integer id);

    List<MealTo> getAllSorted();

    int size();

}