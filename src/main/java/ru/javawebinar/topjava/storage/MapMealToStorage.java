package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.MealTo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapMealToStorage extends AbstractMealToStorage<Object> {
    protected Map<Integer, MealTo> map = new HashMap<>();

    @Override
    protected void doUpdate(Object searchKey, MealTo mealTo) {
        map.put((Integer) searchKey, mealTo);
    }

    @Override
    protected void doSave(MealTo mealTo, Object searchKey) {
        Integer id = mealTo.getId();
        map.put(id, mealTo);
    }

    @Override
    protected MealTo doGet(Object searchKey) {
        return map.get(searchKey);
    }

    @Override
    protected Object getSearchKey(Integer id) {
        return id;
    }

    @Override
    protected void doDelete(Object searchKey) {
        Integer id = (Integer) searchKey;
        map.remove(id);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected List<MealTo> getDataAsList() {
        MealTo[] array = map.values().toArray(new MealTo[0]);
        return Arrays.asList(Arrays.copyOfRange(array, 0, array.length));
    }

    @Override
    public int size() {
        return map.size();
    }

    protected boolean isExistKey(Object searchKey) {
        return map.containsKey(searchKey);
    }
}
