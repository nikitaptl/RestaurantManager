package ru.hse.restaurant.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hse.restaurant.data.entities.Dish;
import ru.hse.restaurant.data.entities.User;
import ru.hse.restaurant.data.repositories.DishRepository;

import java.math.BigDecimal;

public interface AdministratorController {
    @GetMapping("/addDish")
    String addDish(
            @RequestParam String name,
            @RequestParam BigDecimal price,
            @RequestParam int minutesToCook
    );

    @GetMapping("/deleteDish")
    String deleteDish(
            @RequestParam String name,
            @RequestParam BigDecimal price,
            @RequestParam int minutesToCook
    );
}

@RestController
@RequestMapping("/administrator")
class AdministratorControllerImpl implements AdministratorController {
    private final Logger Log = LoggerFactory.getLogger(ControllerImpl.class);
    @Autowired
    DishRepository dishRepository;
    @PersistenceContext
    private EntityManager entityManager;
    User currentAdministrator;

    @Override
    public String addDish(String name, BigDecimal price, int minutesToCook) {
        if(currentAdministrator == null) {
            return "В системе нет авторизованного администратора";
        }
        Dish newDish;
        try {
            var queryStr = "SELECT * FROM dishes WHERE name = '" + name + "' AND price = '"
                    + price + "' AND minutes_to_cook = '" + minutesToCook + "'";
            var query = entityManager.createNativeQuery(queryStr, Dish.class);
            var resultList = query.getResultList();

            if(!resultList.isEmpty()) {
                return "Блюдо с такими параметрами уже добавлено в меню";
            }

            newDish = new Dish(0, name, price, minutesToCook);
            dishRepository.save(newDish);

            return String.format("%s успешно добавлено в меню", newDish.ToString());
        }
        catch (Exception e) {
            return "Что-то пошло не так. Проверьте документацию к программе и попробуйте ещё раз!";
        }
    }

    @Override
    public String deleteDish(String name, BigDecimal price, int minutesToCook) {
        if(currentAdministrator == null) {
            return "В системе нет авторизованного администратора";
        }
        try {
            var queryStr = "SELECT * FROM dishes WHERE name = '" + name + "' AND price = '"
                    + price + "' AND minutes_to_cook = '" + minutesToCook + "'";
            var query = entityManager.createNativeQuery(queryStr, Dish.class);
            var resultList = query.getResultList();

            if(resultList.isEmpty()) {
                return "Блюда с такими параметрами нет в меню";
            }

            Dish dishDelete = (Dish)resultList.get(0);
            dishRepository.delete(dishDelete);

            return String.format("%s успешно удалено из меню", dishDelete.ToString());
        }
        catch (Exception e) {
            return "Что-то пошло не так. Проверьте документацию к программе и попробуйте ещё раз!";
        }
    }
}
