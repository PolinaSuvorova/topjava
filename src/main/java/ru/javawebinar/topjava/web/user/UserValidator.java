package ru.javawebinar.topjava.web.user;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Component
public class UserValidator implements Validator {
    private static final String EXCEPTION_DUPLICATE_EMAIL = "exception.user.duplicateEmail";
    private final UserRepository repository;
    private final HttpServletRequest request;


    public UserValidator(UserRepository repository, HttpServletRequest request) {
        this.repository = repository;
        this.request = request;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz) || UserTo.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Integer id = 0;
        String email;
        String uri = request.getRequestURI();
        if (uri.contains("/profile")) {
            try {
                id = SecurityUtil.authUserId();
            } catch (NullPointerException e) {
                id = 0;
            }
        } else {
            String idStr = request.getParameter("id");
            if (StringUtils.hasText(idStr)) {
                id = Integer.valueOf(idStr);
            }
        }

        if (UserTo.class.isAssignableFrom(target.getClass())) {
            UserTo userTo = (UserTo) target;
            email = userTo.getEmail();
        } else {
            User user = (User) target;
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
