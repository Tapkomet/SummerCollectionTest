package ua.kpi.tef.model;

import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class UserMealWithExceed {
    private LocalDateTime dateTime;

    private String description;

    private int calories;

    private boolean exceed;

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }

    public UserMealWithExceed(UserMeal userMeal, boolean exceed) {
        this.dateTime = userMeal.getDateTime();
        this.description = userMeal.getDescription();
        this.calories = userMeal.getCalories();
        this.exceed = exceed;
    }

    public String toString() {
        return this.dateTime + " " + this.description + " " + this.calories + " " + this.exceed;
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

    public boolean getExceed() {
        return exceed;
    }

    public void setDateTime(LocalDateTime dateTime) {this.dateTime = dateTime;};

    public void setDescription(String description) {this.description = description;};

    public void setCalories(int calories) {this.calories = calories;};

    public void setExceed(boolean exceed) {this.exceed = exceed;};
}
