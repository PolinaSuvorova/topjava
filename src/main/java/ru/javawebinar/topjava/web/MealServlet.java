package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.exception.NotExistStorageException;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MapMealToStorage;
import ru.javawebinar.topjava.storage.MealToStorage;
import ru.javawebinar.topjava.util.DateUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Utils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealToStorage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MapMealToStorage();
        List<MealTo> mealsTo = MealsUtil.filteredByStreams(MealsUtil.MEALS,
                LocalTime.of(0, 1),
                LocalTime.of(23, 59),
                MealsUtil.CALORIES_PER_DAY);
        for (MealTo mealTo : mealsTo) {
            storage.save(mealTo);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/meals.jsp")
                    .forward(request, response);
            return;
        }
        Integer intId = Integer.parseInt(id);

        MealTo mealTo;
        switch (action) {
            case "delete": {
                storage.delete(intId);
                response.sendRedirect("meals");
                return;
            }
            case "view": {
                mealTo = storage.get(intId);
                break;
            }
            case "edit": {
                if (Utils.isEmptyInteger(intId)) {
                    mealTo = new MealTo(null, LocalDateTime.now(), "", 0, false);
                } else {
                    mealTo = storage.get(Integer.parseInt(id));
                }
                break;
            }
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("meal", mealTo);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/viewmeal.jsp" : "/WEB-INF/jsp/editmeal.jsp")
        ).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String status = request.getParameter("status");

        if (action != null && action.equals("back")) {
            response.sendRedirect("meals");
            return;
        }

        MealTo mealTo;
        String id = request.getParameter("id");

        LocalDateTime dateTime = DateUtil.inputDateTime(request.getParameter("datetime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        boolean isNew = false;
        try {
            mealTo = storage.get(Integer.parseInt(id));
        } catch (NotExistStorageException er) {
            mealTo = new MealTo(null, dateTime, description, calories, false);
            isNew = true;
        }

        mealTo.setDescription(description);
        mealTo.setDateTime(dateTime);
        mealTo.setCalories(calories);

        List<String> validate = validate(request);
        if (validate.isEmpty() && status.equals("save")) {
            if (isNew) {
                storage.save(mealTo);
            } else {
                storage.update(mealTo.getId(), mealTo);
            }
            response.sendRedirect("meals");
        } else {
            request.setAttribute("status", "");
            request.setAttribute("meal", mealTo);
            request.setAttribute("validate", validate);
            request.getRequestDispatcher("/WEB-INF/jsp/editmeal.jsp")
                    .forward(request, response);
        }
    }

    public List<String> validate(HttpServletRequest request) {
        String description = request.getParameter("description");
        List<String> violations = new ArrayList<>();
        if (Utils.isEmptyString(description)) {
            violations.add("Название обязательно для ввода");
        }
        LocalDateTime dateTime = DateUtil.inputDateTime(request.getParameter("datetime"));
        if (Utils.isEmptyDateTime(dateTime)) {
            violations.add("Дата обязательна для ввода");
        }
        return violations;
    }
}
