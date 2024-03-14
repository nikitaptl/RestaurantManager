package ru.hse.restaurant.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.hse.restaurant.data.entities.Dish;
import ru.hse.restaurant.data.entities.User;
import ru.hse.restaurant.data.repositories.UserRepository;

import java.util.Objects;
import java.util.UUID;

public interface Controller {
    @GetMapping("/register")
    String createUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String userType
    );

    @GetMapping("/logIn")
    String logIn(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String userType
    );

    @GetMapping("/logOut")
    String logOut(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String userType
    );

    @GetMapping("/menu")
    Object menu();

    @GetMapping("/currentUser")
    Object currentUser();
}

@RestController
class ControllerImpl implements Controller {
    private final org.slf4j.Logger Log = LoggerFactory.getLogger(ControllerImpl.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdministratorControllerImpl administratorController;
    @Autowired
    private ClientControllerImpl clientController;
    @PersistenceContext
    private EntityManager entityManager;
    private User currentUser;

    @Override
    public String createUser(String username, String password, String userType) {
        if (!Objects.equals(userType, "administrator") && !Objects.equals(userType, "client")) {
            return "Вы ввели неправильный тип пользователя";
        }
        if (currentUser != null) {
            return "В системе уже есть вошедший пользователь";
        }

        String queryStr = "SELECT * FROM users WHERE username = '" + username + "' AND user_type = '" + userType + "'";
        var query = entityManager.createNativeQuery(queryStr, User.class);

        if (!query.getResultList().isEmpty()) {
            return "Пользователь с таким именем уже существует";
        }

        User user = new User(UUID.randomUUID(), username, password, userType);
        userRepository.save(user);

        Log.info(String.format("%s зарегистрировался и вошёл в систему", user.ToString()));
        currentUser = user;

        if (Objects.equals(currentUser.getUser_type(), "administrator")) {
            administratorController.currentAdministrator = currentUser;
        } else {
            clientController.currentClient = currentUser;
        }

        return "Вы успешно зарегистрировались и вошли в аккаунт!";
    }

    @Override
    public String logIn(String username, String password, String userType) {
        if (!Objects.equals(userType, "administrator") && !Objects.equals(userType, "client")) {
            return "Вы ввели неправильный тип пол   ьзователя";
        }
        if (currentUser != null) {
            return "На данный момент уже есть вошедший пользователь.";
        }
        String queryStr = "SELECT * FROM users WHERE username = '" + username + "' AND password = '"
                + password + "' AND user_type = '" + userType + "'";
        var query = entityManager.createNativeQuery(queryStr, User.class);

        var resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return "Пользователя с таким именем и паролем не существует";
        }

        User user = (User) resultList.get(0);
        Log.info(String.format("%s вошёл в систему", user.ToString()));
        currentUser = user;

        if (Objects.equals(currentUser.getUser_type(), "administrator")) {
            administratorController.currentAdministrator = currentUser;
        } else {
            clientController.currentClient = currentUser;
        }

        return String.format("Пользователь %s успешно вошёл в систему", username);
    }

    @Override
    public String logOut(String username, String password, String userType) {
        if (!Objects.equals(userType, "administrator") && !Objects.equals(userType, "client")) {
            return "Вы ввели неправильный тип пол   ьзователя";
        }
        if (currentUser == null) {
            return "На данный момент нет вошедших в систему пользователей.";
        }
        String queryStr = "SELECT * FROM users WHERE username = '" + username + "' AND password = '"
                + password + "' AND user_type = '" + userType + "'";
        var query = entityManager.createNativeQuery(queryStr, User.class);

        var resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return "Пользователя с таким именем и паролем не существует";
        }
        if (!Objects.equals(username, currentUser.getUsername())) {
            return String.format("Пользователь %s не является вошедшим в систему", username);
        }

        Log.info(String.format("%s вышел из системы", currentUser.ToString()));

        if (Objects.equals(currentUser.getUser_type(), "administrator")) {
            administratorController.currentAdministrator = null;
        } else {
            clientController.currentClient = null;
        }

        currentUser = null;
        return String.format("Пользователь %s успешно вышел из системы", username);
    }

    @Override
    public Object menu() {
        String queryStr = "SELECT * FROM dishes";
        var query = entityManager.createNativeQuery(queryStr, Dish.class);
        var resultList = query.getResultList();

        if (resultList.isEmpty()) {
            return "На данный момент меню пустое.";
        }
        StringBuilder result = new StringBuilder("");
        for (var dish : resultList) {
            result.append(((Dish) dish).ToString());
            result.append("<br>");
        }
        return result;
    }

    @Override
    public Object currentUser() {
        if (currentUser == null) {
            return "В системе нет авторизанного пользователя";
        }
        return currentUser.ToString();
    }
}
