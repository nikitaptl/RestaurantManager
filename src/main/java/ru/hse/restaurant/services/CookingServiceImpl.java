package ru.hse.restaurant.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hse.restaurant.data.entities.Dish;
import ru.hse.restaurant.data.entities.Ordering;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CookingServiceImpl implements CookingService {
    private final Logger Log = LoggerFactory.getLogger(CookingServiceImpl.class);
    public Ordering currentOrdering = null;
    private List<CompletableFuture<Void>> cookingThreads;
    public List<Dish> cookedDishes;
    public boolean isCanceled = false;
    @PersistenceContext
    private EntityManager entityManager;

    private CompletableFuture<Void> cookDish(Dish dish) {
        return CompletableFuture.runAsync(() -> {
            Log.info("Dish " + dish.getName() + " for " + currentOrdering.getUsername() + " is cooking...");
            try {
                Thread.sleep(dish.getMinutesToCook() * 1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            cookedDishes.add(dish);
            Log.info("Блюдо " + dish.getName() + " для " + currentOrdering.getUsername() + " было приготовлено!");
        });
    }

    @Override
    public String serveOrder() {
        List<Dish> dishes = GetDishesById(currentOrdering.getDishId());
        if (currentOrdering == null) {
            Log.info("Заказ для " + currentOrdering.getUsername() + " начал готовиться");
        }
        cookingThreads = new ArrayList<>();
        cookedDishes = new ArrayList<>();
        isCanceled = false;

        for (Dish dish : dishes) {
            CompletableFuture<Void> cookingFuture = cookDish(dish);
            cookingThreads.add(cookingFuture);
        }

        try {
            synchronized (cookingThreads) {
                CompletableFuture<Void>[] cookingThreadsArray = cookingThreads.toArray(new CompletableFuture[0]);
                CompletableFuture<Void> allOf = CompletableFuture.allOf(cookingThreadsArray);
                allOf.join();
            }
        } catch (Exception e) {
            cancelOrder();
            return null;
        }

        if (isCanceled) {
            Log.info("Заказ для " + currentOrdering.getUsername() + " был отменён");
            currentOrdering.setActive(false);
            return null;
        }

        List<Integer> dishId = new ArrayList<>();
        for (Dish dish : cookedDishes) {
            dishId.add(dish.getId());
            currentOrdering.setTotalPrice(currentOrdering.getTotalPrice().add(dish.getPrice()));
        }
        currentOrdering.setDishId(dishId);
        currentOrdering.setActive(false);

        Log.info(String.format("Заказ для %s был приготовлен", currentOrdering.getUsername()));
        return String.format("Заказ для %s был приготовлен", currentOrdering.getUsername());
    }

    @Override
    public String expandOrder(List<Integer> dishes) {
        Log.info("User " + currentOrdering.getUsername() + " is expanding his order");
        List<CompletableFuture<Void>> supplementCookingFutures = new ArrayList<>();

        for (Dish dish : GetDishesById(dishes)) {
            CompletableFuture<Void> supplementCookingFuture = cookDish(dish);
            supplementCookingFutures.add(supplementCookingFuture);
        }

        cookingThreads.addAll(supplementCookingFutures);
        return String.format("Заказ для %s был успешно расширен!", currentOrdering.getUsername());
    }

    @Override
    public void cancelOrder() {
        for (CompletableFuture<Void> cookingFuture : cookingThreads) {
            cookingFuture.cancel(true);
        }
        isCanceled = true;
    }

    public List<Dish> GetDishesById(List<Integer> dishId) {
        String queryStr = "SELECT * FROM dishes WHERE id IN (" +
                currentOrdering.getDishId().stream().map(Object::toString).collect(Collectors.joining(", ")) +
                ")";
        var query = entityManager.createNativeQuery(queryStr, Dish.class);

        List<Dish> resultList = new ArrayList<>();
        for (Object dish : query.getResultList()) {
            resultList.add((Dish) dish);
        }
        return resultList;
    }
}
