package ru.otus.spring.kilyakov.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.kilyakov.domain.Cocktail;
import ru.otus.spring.kilyakov.domain.Drink;
import ru.otus.spring.kilyakov.domain.OrderItem;

import java.util.*;

@Service
public class PuzzlesBarService {
    private final Map<String, List<Drink>> cocktailRecipes = new HashMap<>();

    private final Map<String, List<Drink>> inStockHashMap = new HashMap<>();

    public PuzzlesBarService() {
        cocktailRecipes.put("yorsh", new ArrayList<>(List.of(new Drink("beer", 450),
                new Drink("vodka", 50))));
        cocktailRecipes.put("mojito", new ArrayList<>(List.of(new Drink("lemonade", 450),
                new Drink("rum", 60), new Drink("syrup", 30))));

        inStockHashMap.put("rum", new ArrayList<>(List.of(new Drink("bacardi", "rum", 40, 50))));
        inStockHashMap.put("syrup", new ArrayList<>(List.of(new Drink("monin", "syrup", 0, 500))));
        inStockHashMap.put("beer", new ArrayList<>(List.of(new Drink("zhigulevskoe", "beer", 4.5F, 500))));
        inStockHashMap.put("vodka", new ArrayList<>(List.of(new Drink("absolute", "Vodka", 40, 50))));
        inStockHashMap.put("lemonade", new ArrayList<>(List.of(new Drink("sprite", "lemonade", 0, 500))));
    }

    public Optional<Drink> pourDrink(OrderItem orderItem) {
        var drinks = inStockHashMap.get(orderItem.getDrinkType().toLowerCase());
        if (drinks == null) {
            return Optional.empty();
        }
        return drinks.stream().filter(drink -> drink.getName().equalsIgnoreCase(orderItem.getName())).findAny();
    }

    public Optional<Cocktail> pourCocktail(OrderItem orderItem) {
        var ingredients = cocktailRecipes.get(orderItem.getName().toLowerCase());
        if (ingredients == null) {
            return Optional.empty();
        }
        for (Drink ingredient : ingredients
        ) {
            var drinks = inStockHashMap.get(ingredient.getType());
            var foundDrink = drinks.stream()
                    .filter(drink -> drink.getType().equalsIgnoreCase(ingredient.getType()))
                    .findAny()
                    .orElse(null);
            if (foundDrink == null) {
                return Optional.empty();
            }
            ingredient.setName(foundDrink.getName());
            ingredient.setAlcohol(foundDrink.getAlcohol());
        }
        return ingredients.size() != 0 ? Optional.of(new Cocktail(orderItem.getName(), ingredients)) : Optional.empty();
    }

}
