package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, Integer idUser) {
        return repository.save(meal, idUser);
    }

    public void delete(int id, Integer idUser) {
        checkNotFoundWithId(repository.delete(id, idUser), id);
    }

    public Meal get(int id, Integer idUser) {
        return checkNotFoundWithId(repository.get(id, idUser), id);
    }

    public List getAll(Integer idUser) {
        return (List) repository.getAll(idUser);
    }

    public List<MealTo> getAllTo(Integer idUser, Integer caloriesPerDay) {
        return MealsUtil.getTos(
                repository.getAll(idUser),
                caloriesPerDay);
    }

    public void update(Meal meal, Integer idUser) {
        checkNotFoundWithId(repository.save(meal, idUser), meal.getId());
    }
}