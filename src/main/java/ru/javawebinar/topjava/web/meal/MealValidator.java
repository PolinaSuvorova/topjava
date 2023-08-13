package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
public class MealValidator implements Validator {
    private final MealRepository repository;

    private static final String EXCEPTION_DUPLICATE_DATE_TIME = "exception.meal.duplicateDateTime";


    public MealValidator(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Meal.class.isAssignableFrom(clazz) || MealTo.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Integer id;
        LocalDateTime localDateTime;
        int userId = SecurityUtil.authUserId();

        if (MealTo.class.isAssignableFrom(target.getClass())) {
            MealTo mealTo = (MealTo) target;
            id = mealTo.getId();
            localDateTime = mealTo.getDateTime();
        } else {
            Meal meal = (Meal) target;
            id = meal.getId();
            localDateTime = meal.getDateTime();
        }

        if (localDateTime != null) {
            List<Meal> mealDb = repository.getBetweenHalfOpen(localDateTime, localDateTime.plusSeconds(1), userId);
            if (mealDb == null) {
                return;
            }
            List<Meal> mealsCheck = mealDb.stream().filter(meal -> !Objects.equals(meal.getId(), id)).toList();
            if (mealsCheck.size() == 0){
                return;
            }
            errors.rejectValue("dateTime", EXCEPTION_DUPLICATE_DATE_TIME);
        }
    }
}
