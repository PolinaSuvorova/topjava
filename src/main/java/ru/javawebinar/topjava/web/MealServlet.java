package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.InMemoryMealStorage;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.util.DateUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealStorage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new InMemoryMealStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }

        Integer id = getId(request);

        Meal meal;
        switch (action) {
            case "delete": {
                log.info("Start delete {}", id);
                storage.delete(id);
                response.sendRedirect("meals");
                return;
            }
            case "edit": {
                if (id == null) {
                    log.info("Start create");
                    meal = new Meal(
                            LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 0);
                } else {
                    meal = storage.get(id);
                    log.info("Start edit {}", meal.getId());
                }
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("editMeal.jsp").forward(request, response);
                return;
            }
            default:
                log.info("Show List");
                request.setAttribute("meals",
                        MealsUtil.filteredByStreams(storage.getAll(),
                                LocalTime.MIN,
                                LocalTime.MAX,
                                MealsUtil.CALORIES_PER_DAY));
                request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");

        Integer id = getId(request);
        Meal meal = new Meal(id,
                DateUtil.inputDateTime(request.getParameter("datetime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info("Save");
        storage.save(meal);
        response.sendRedirect("meals");
    }

    private Integer getId(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (id != null && !id.isEmpty() ) {
            return Integer.parseInt(id);
        }
        return null;
    }
}
