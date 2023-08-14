package ru.javawebinar.topjava.web.user;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.UserTo;

import java.util.Objects;

@Component
public class UserValidator implements Validator {
    private static final String EXCEPTION_DUPLICATE_EMAIL = "exception.user.duplicateEmail";
    private final UserRepository repository;


    public UserValidator(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz) || UserTo.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Integer id;
        String email;
        if (UserTo.class.isAssignableFrom(target.getClass())) {
            UserTo userTo = (UserTo) target;
            id = userTo.getId();
            email = userTo.getEmail();
        } else {
            User user = (User) target;
            id = user.getId();
            email = user.getEmail();
        }
        if (StringUtils.hasText(email)) {
            User userDb = repository.getByEmail(email);
            if (userDb == null || Objects.equals(userDb.getId(), id)) {
                return;
            }
            errors.rejectValue("email", EXCEPTION_DUPLICATE_EMAIL);
        }
    }
}
