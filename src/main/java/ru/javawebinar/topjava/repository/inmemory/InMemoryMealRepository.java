package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Comparator<Meal> MEAL_COMPARATOR =
            Comparator.comparing(Meal::getDateTime).reversed();
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, meal.getIdUser()));
    }

    @Override
    public Meal save(Meal meal, Integer idUser) {
        synchronized (meal) {
            if (!meal.checkUserId(idUser)) {
                return null;
            }
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                repository.put(meal.getId(), meal);
                return meal;
            }
            // handle case: update, but not present in storage
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
    }

    @Override
    public boolean delete(int id, Integer idUser) {
        if (!repository.get(id).checkUserId(idUser)) {
            return false;
        }
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, Integer idUser) {
        Meal meal = repository.get(id);
        return meal.checkUserId(idUser) ? meal : null;
    }

    @Override
    public Collection<Meal> getAll(Integer idUser) {
        return repository.values().stream()
                .filter(meal -> meal.checkUserId(idUser))
                .sorted(MEAL_COMPARATOR)
                .collect(Collectors.toList());
    }
}

