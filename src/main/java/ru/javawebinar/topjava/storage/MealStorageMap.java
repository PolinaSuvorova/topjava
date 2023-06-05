package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MealStorageMap implements MealStorage {
    private Map<Integer, Meal> map = new HashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    public MealStorageMap() {
        for (Meal meal : MealsUtil.meals) {
            save(meal);
        }
    }

    @Override
    public Meal save(Meal meal) {
        Integer id;
        if (meal.isIdNull()) {
            id = counter.incrementAndGet();
            meal.setId(id);
        } else {
            id = meal.getId();
            if (get(id) == null) {
                return null;
            }
        }
        map.put(id, meal);
        return meal;
    }

    @Override
    public Meal get(int id) {
        return map.get(id);
    }

    @Override
    public void delete(int id) {
        map.remove(id);
    }
    @Override
    public  List<Meal> getAll() {
        Meal[] array = map.values().toArray(new Meal[0]);
        return Arrays.asList(Arrays.copyOfRange(array, 0, array.length));
    }
}
