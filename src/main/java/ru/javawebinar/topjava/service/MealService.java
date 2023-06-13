package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<MealTo> getAllToByFilter(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        LocalDateTime lStartDate = startDate != null ? LocalDateTime.of(startDate, LocalTime.MIN) :
                LocalDateTime.MIN;
        LocalDateTime lEndDate = endDate != null ? LocalDateTime.of(endDate, LocalTime.MAX) :
                LocalDateTime.MAX;
        List<Meal> list = new ArrayList<>(repository.getAllByDate(userId, lStartDate, lEndDate));
        return MealsUtil.getFilteredTos(list, caloriesPerDay, startTime, endTime);
    }

    public List<MealTo> getAll(int userId) {
        return MealsUtil.getTos(repository.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public void update(Meal meal, int userId) {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }
}