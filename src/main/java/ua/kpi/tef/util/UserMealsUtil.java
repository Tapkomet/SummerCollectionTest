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
        fillMap(mealList, calorieMap);
        fillNewList(mealList, startTime, endTime, caloriesPerDay, newList, calorieMap);
        return newList;
    }

    private static void fillMap(List<UserMeal> mealList, Map<LocalDate, Integer> calorieMap) {
        mealList.forEach(meal -> {
            LocalDate tempDate = meal.getDateTime().toLocalDate();
            int value = calorieMap.getOrDefault(tempDate, 0);
            calorieMap.merge(tempDate, meal.getCalories(), Math::addExact);
        });
    }

    private static void fillNewList(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay, List<UserMealWithExceed> newList, Map<LocalDate, Integer> calorieMap) {
        mealList.forEach(meal -> {
            LocalTime mealTime = meal.getDateTime().toLocalTime();
            if (TimeUtil.isBetween(mealTime, startTime, endTime)) {
                UserMealWithExceed exceedMeal = new UserMealWithExceed(meal,
                        calorieMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay);
                newList.add(exceedMeal);
            }
        });
    }
}
