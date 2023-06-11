package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int idUser) {
        return repository.save(meal, idUser);
    }

    public void delete(int id, int idUser) {
        checkNotFoundWithId(repository.delete(id, idUser), id);
    }

    public Meal get(int id, int idUser) {
        return checkNotFoundWithId(repository.get(id, idUser), id);
    }

    public List<MealTo> getAllTo(int idUser, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        LocalDate lStartDate = LocalDate.MIN;
        LocalDate lEndDate;
        if (startDate != null && endDate == null) {
            lEndDate = lStartDate;
        } else if (endDate != null) {
            lEndDate = endDate;
        } else {
            lEndDate = LocalDate.MAX;
        }
        LocalTime lStartTime = LocalTime.MIN;
        LocalTime lEndTime = LocalTime.MAX;
        if (startTime != null && endTime == null) {
            lEndTime = lStartTime;
        } else if (endTime != null) {
            lEndTime = endTime;
        }

        List<Meal> list = repository.getAll(idUser).stream().filter(
                        meal -> meal.getDate().compareTo(lStartDate) >= 0 &&
                                meal.getDate().compareTo(lEndDate) < 0)
                .collect(Collectors.toList());
        return MealsUtil.getFilteredTos(list, caloriesPerDay, lStartTime, lEndTime);
    }

    public void update(Meal meal, int idUser) {
        checkNotFoundWithId(repository.save(meal, idUser), meal.getId());
    }
}