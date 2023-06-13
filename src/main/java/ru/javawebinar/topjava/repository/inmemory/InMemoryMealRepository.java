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
        MealsUtil.meals.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            Map<Integer, Meal> mealForUser = repository.get(userId);
            if (mealForUser == null) {
                mealForUser = new ConcurrentHashMap<>();
            }
            mealForUser.put(meal.getId(), meal);
            repository.put(userId, mealForUser);
            return meal;
        } else {
                Map<Integer, Meal> mealsForUser = repository.get(userId);
                if (mealsForUser == null) {
                    return null;
                }
                Meal mealInMem = mealsForUser.get(meal.getId());
                meal.setUserId(userId);
                if (mealInMem == null || !checkUserId(mealInMem, meal.getUserId())) {
                    return null;
                }
                Meal saveMeal = mealsForUser.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
                if (saveMeal == null) {
                    return null;
                }
                repository.computeIfPresent(userId, (userId1, oldMapMeal) -> mealsForUser);
                return saveMeal;
            }
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(userId).get(id);
        if (meal == null || !checkUserId(meal, userId)) {
            return false;
        }
        return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.get(userId).get(id);
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
        return repository.get(userId).values().stream()
                .filter(meal -> checkUserId(meal, userId))
                .filter(filter)
                .sorted(MEAL_COMPARATOR)
                .collect(Collectors.toList());
    }

    private boolean checkUserId(Meal meal, int userId) {
        return meal.getUserId() == userId;
    }
}

