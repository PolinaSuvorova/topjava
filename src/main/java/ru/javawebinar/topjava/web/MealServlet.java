package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
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
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealToStorage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MapMealToStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        Integer id = getParamInteger("id", request);

        Meal meal;
        switch (action) {
            case "delete": {
                storage.delete(id);
                response.sendRedirect("meals");
                log.info("Start delete {}", id);
                return;
            }
            case "edit": {
                if (id == null) {
                    log.info("Start create");
                    meal = new Meal(null,
                            LocalDateTime.of(LocalDateTime.now().getYear(),
                                    LocalDateTime.now().getMonth(),
                                    LocalDateTime.now().getDayOfMonth(),
                                    LocalDateTime.now().getHour(),
                                    LocalDateTime.now().getMinute()),
                            "", 0);
                } else {
                    meal = storage.get(id);
                    log.info("Start edit {}", meal.getId());
                }
                break;
            }
            default:
                log.info("Show List");
                request.setAttribute("meals",
                        MealsUtil.filteredByStreams((List<Meal>) storage.getAll(),
                                LocalTime.MIN,
                                LocalTime.MAX,
                                MealsUtil.CALORIES_PER_DAY));
                request.getRequestDispatcher("meals.jsp")
                        .forward(request, response);
                return;
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("editMeal.jsp")
                .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if (action != null && action.equals("back")) {
            response.sendRedirect("meals");
            log.info("Back");
            return;
        }

        Integer calories = getParamInteger("calories", request);
        if (calories == null) {
            calories = 0;
        }

        Integer id = getParamInteger("id", request);
        Meal meal = new Meal(id,
                DateUtil.inputDateTime(request.getParameter("datetime")),
                request.getParameter("description"),
                calories);

        String status = request.getParameter("status");
        if (status.equals("save")) {
            log.info("Save");
            storage.save(meal);
            response.sendRedirect("meals");
        } else {
            log.info("Show edit screen { }", meal);
            request.setAttribute("status", "");
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("editMeal.jsp")
                    .forward(request, response);
        }
    }


    private Integer getParamInteger(String nameParam, HttpServletRequest request) {
        String id = request.getParameter(nameParam);
        if (!Utils.isEmptyString(id) && !id.equals("null")) {
            return Integer.parseInt(id);
        }
        return null;
    }
}
