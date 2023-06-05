package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MapMealToStorage implements MealToStorage {
    private Map<Integer, Meal> map = new HashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    public MapMealToStorage() {
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
    public List<Meal> getAll() {
        Meal[] array = map.values().toArray(new Meal[0]);
        return Arrays.asList(Arrays.copyOfRange(array, 0, array.length));
    }
}
