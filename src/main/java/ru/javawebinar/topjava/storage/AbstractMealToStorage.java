package ru.javawebinar.topjava.storage;


import ru.javawebinar.topjava.exception.ExistStorageException;
import ru.javawebinar.topjava.exception.NotExistStorageException;
import ru.javawebinar.topjava.exception.StorageException;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public abstract class AbstractMealToStorage<SK> implements MealToStorage {
    private static final Comparator<MealTo> MEALTO_COMPARATOR =
            Comparator.comparing(MealTo::getDateTime).thenComparing(MealTo::getId);
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void update(Integer id, MealTo meal) {
        SK searchKey = getExistingSearchKey(id);
        doUpdate(searchKey, meal);
        refreshExcessField(meal.getDateTime());
    }

    @Override
    public void save(MealTo meal) {
        Integer id = meal.getId();
        MealTo mealLocal;
        if (id == null) {
            id = counter.incrementAndGet();
        }
        mealLocal = new MealTo(
                id,
                meal.getDateTime(),
                meal.getDescription(),
                meal.getCalories(), meal.getExcess());
        SK searchKey = getNotExistingSearchKey(id);
        doSave(mealLocal, searchKey);
        refreshExcessField(meal.getDateTime());
    }

    @Override
    public MealTo get(Integer id) {
        SK searchKey = getExistingSearchKey(id);
        return doGet(searchKey);
    }

    @Override
    public void delete(Integer id) {
        SK searchKey = getExistingSearchKey(id);
        LocalDateTime dateTime = doGet(searchKey).getDateTime();
        doDelete(searchKey);
        refreshExcessField(dateTime);
    }

    private SK getExistingSearchKey(Integer id) {
        SK searchKey = getSearchKey(id);
        if (!isExistKey(searchKey)) {
            throw new NotExistStorageException(id);
        }
        return searchKey;
    }

    private SK getNotExistingSearchKey(Integer id) {
        SK searchKey = getSearchKey(id);
        if (searchKey != null && isExistKey(searchKey)) {
            throw new ExistStorageException(id);
        }
        return searchKey;
    }

    @Override
    public List<MealTo> getAllSorted() {
        List<MealTo> list = getDataAsList();
        list.sort(MEALTO_COMPARATOR);
        return list;
    }

    public void refreshExcessField(LocalDateTime dateTime) {
        LocalDate date = dateTime.toLocalDate();
        List<MealTo> mealForRefresh = getDataAsList().stream()
                .filter(meal -> meal.getDate().equals(date))
                .collect(Collectors.toList());

        Map<LocalDate, Integer> caloriesSumByDate = mealForRefresh.stream()
                .collect(
                        Collectors.groupingBy(MealTo::getDate,
                                Collectors.summingInt(MealTo::getCalories)));

        Integer caloriesSum = caloriesSumByDate.get(date);

        boolean excess = caloriesSum > MealsUtil.CALORIES_PER_DAY;

        for (MealTo meal : mealForRefresh) {
            if (meal.getExcess() != excess) {
                meal.setExcess(excess);
                update(meal.getId(), meal);
            }
        }
    }

    protected abstract List<MealTo> getDataAsList();

    protected abstract boolean isExistKey(SK searchKey);

    protected abstract void doUpdate(SK searchKey, MealTo resume);

    protected abstract void doSave(MealTo resume, SK searchKey) throws StorageException;

    protected abstract MealTo doGet(SK searchKey);

    protected abstract SK getSearchKey(Integer id);

    protected abstract void doDelete(SK searchKey);

}