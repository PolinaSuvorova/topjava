package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Comparator<Meal> MEAL_COMPARATOR =
            Comparator.comparing(Meal::getDateTime).reversed();
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            Map<Integer, Meal> meals = repository.compute(userId, (userId1, oldMealsUser) ->
                    (oldMealsUser == null ? new ConcurrentHashMap<>() : oldMealsUser));
            return meals.put(meal.getId(), meal);
        } else {
            Map<Integer, Meal> mealsUser = repository.get(userId);
            return mealsUser == null ? null : mealsUser.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> mealsUser = repository.get(userId);
        return mealsUser != null && mealsUser.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> mealsUser = repository.get(userId);
        return mealsUser == null ? null : mealsUser.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return filterByPredicate(userId, meal -> true);
    }

    @Override
    public List<Meal> getAllByDate(int userId, LocalDateTime startDate, LocalDateTime endDate) {
        return filterByPredicate(userId, meal -> DateTimeUtil.isBetweenDateTime(meal.getDateTime(), startDate, endDate));
    }

    public List<Meal> filterByPredicate(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> mealsUse = repository.get(userId);
        if (mealsUse == null) {
            return null;
        }
        return mealsUse.values().stream()
                .filter(filter)
                .sorted(MEAL_COMPARATOR)
                .collect(Collectors.toList());
    }
}

