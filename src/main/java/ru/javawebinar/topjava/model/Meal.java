package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal extends AbstractBaseEntity {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final Integer idUser;

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories, null);
    }

    public Meal(LocalDateTime dateTime, String description, int calories, Integer idUser) {
        this(null, dateTime, description, calories, idUser);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories, Integer idUser) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.idUser = idUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public boolean isNew() {
        return id == null;
    }

    public boolean checkUserId(Integer idUser) {
        return this.idUser.equals(idUser);
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
