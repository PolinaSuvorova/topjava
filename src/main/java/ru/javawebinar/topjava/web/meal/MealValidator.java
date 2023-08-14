package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class MealValidator implements Validator {
    private static final String EXCEPTION_DUPLICATE_DATE_TIME = "exception.meal.duplicateDateTime";
    private final MealRepository repository;

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

            Meal meal = (Meal) target;
            id = meal.getId();
            localDateTime = meal.getDateTime();

        if (localDateTime != null) {
           if (repository.getBetweenHalfOpen(localDateTime, localDateTime.plusNanos(1000), userId)
                    .stream()
                    .filter(meal1 -> !(meal1.getId().equals(id))).findFirst().isPresent()) {
               errors.rejectValue("dateTime", EXCEPTION_DUPLICATE_DATE_TIME);
           }
        }
    }
}
