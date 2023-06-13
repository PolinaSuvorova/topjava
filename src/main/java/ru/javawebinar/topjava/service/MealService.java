package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    public void delete(int id, int idUser) {
        checkNotFoundWithId(repository.delete(id, idUser), id);
    }

    public Meal get(int id, int idUser) {
        return checkNotFoundWithId(repository.get(id, idUser), id);
    }

    public List<MealTo> getAllTo(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<Meal> list = new ArrayList<>(repository.getAllByDate(userId, startDate, endDate));
        return MealsUtil.getFilteredTos(list, caloriesPerDay, startTime, endTime);
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public void update(Meal meal) {
        checkNotFoundWithId(repository.save(meal), meal.getId());
    }
}