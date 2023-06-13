package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
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
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        synchronized (repository) {
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                repository.put(meal.getId(), meal);
                return meal;
            } else {
                Meal mealInMem = repository.get(meal.getId());
                if (mealInMem == null || !checkUserId(mealInMem, meal.getUserId())) {
                    return null;
                }
            }
            // handle case: update, but not present in storage
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        synchronized (repository) {
            Meal meal = repository.get(id);
            if (meal == null) {
                return false;
            }
            if (!checkUserId(meal, userId)) {
                return false;
            }
            return repository.remove(id) != null;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal == null) {
            return null;
        }
        return checkUserId(meal, userId) ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream()
                .filter(meal -> checkUserId(meal, userId))
                .sorted(MEAL_COMPARATOR)
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAllByDate(int userId, LocalDate startDate, LocalDate endDate) {
        return repository.values().stream()
                .filter(meal -> checkUserId(meal, userId) && filterMealByDate(meal, startDate, endDate))
                .sorted(MEAL_COMPARATOR)
                .collect(Collectors.toList());
    }

    private boolean checkUserId(Meal meal, int userId) {
        return meal.getUserId() == userId;
    }

    private boolean filterMealByDate(Meal meal, LocalDate startDate, LocalDate endDate) {
        LocalDateTime lStartDate = startDate != null ? LocalDateTime.of(startDate, LocalTime.MIN) :
                LocalDateTime.MIN;
        LocalDateTime lEndDate = endDate != null ? LocalDateTime.of(endDate, LocalTime.MAX) :
                LocalDateTime.MAX;
        return DateTimeUtil.isBetweenDateTime(meal.getDateTime(), lStartDate, lEndDate);
    }
}

