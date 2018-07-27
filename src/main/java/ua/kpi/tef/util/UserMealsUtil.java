package ua.kpi.tef.util;

import ua.kpi.tef.model.UserMeal;
import ua.kpi.tef.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<UserMealWithExceed> newList = getFilteredWithExceeded(mealList, LocalTime.of(10, 0), LocalTime.of(12, 0), 2000);
        System.out.println(newList);
//        .toLocalDate();
//        .toLocalTime();

    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> newList = new ArrayList<>();
        Map<LocalDate, Integer> calorieMap = new HashMap<>();
        mealList.forEach(meal -> {
            fillMap(calorieMap, meal);
            fillList(startTime, endTime, newList, meal);
        });
        markExceeds(caloriesPerDay, newList, calorieMap);
        return newList;
    }

    private static void fillMap(Map<LocalDate, Integer> calorieMap, UserMeal meal) {
        LocalDate tempDate = meal.getDateTime().toLocalDate();
        if (calorieMap.containsKey(tempDate)) {
            int value = calorieMap.get(tempDate);
            calorieMap.replace(tempDate, value + meal.getCalories());
        } else {
            calorieMap.put(tempDate, meal.getCalories());
        }
    }

    private static void fillList(LocalTime startTime, LocalTime endTime, List<UserMealWithExceed> newList, UserMeal meal) {
        LocalTime mealTime = meal.getDateTime().toLocalTime();
        if (TimeUtil.isBetween(mealTime, startTime, endTime)) {
            UserMealWithExceed exceedMeal = new UserMealWithExceed(meal, false);
            newList.add(exceedMeal);
        }
    }

    private static void markExceeds(int caloriesPerDay, List<UserMealWithExceed> newList, Map<LocalDate, Integer> calorieMap) {
        newList.forEach(meal -> {
            if (calorieMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay) {
                meal.setExceed(true);
            }
        });
    }
}
