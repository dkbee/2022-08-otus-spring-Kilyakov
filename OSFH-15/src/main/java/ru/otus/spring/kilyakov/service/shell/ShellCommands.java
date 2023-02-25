package ru.otus.spring.kilyakov.service.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.kilyakov.domain.Cocktail;
import ru.otus.spring.kilyakov.domain.Drink;
import ru.otus.spring.kilyakov.domain.OrderItem;
import ru.otus.spring.kilyakov.service.BarService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@ShellComponent
public class ShellCommands {

    @Autowired
    private BarService barService;

    @ShellMethod(value = "Buy drink", key = "-b drink")
    public void buyBeer(String type, String name) {
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(name, type, Drink.class));
        Collection<Drink> drinks = barService.processDrink(orderItems);
        for (Drink drink :
                drinks) {
            if (drink == null) {
                System.out.println("Sorry, bro, there is no such drink");
            } else {
                System.out.println("Name: " + drink.getName() + ", volume: " + drink.getVolume() + ", alcohol: "
                        + drink.getAlcohol());
            }
        }
    }

    @ShellMethod(value = "Buy cocktail", key = "-b cocktail")
    public void buyCocktail(String name) {
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(name, Cocktail.class));
        Collection<Drink> drinks = barService.processDrink(orderItems);
        for (Drink drink :
                drinks) {
            if (drink == null) {
                System.out.println("Sorry, bro, there is no such drink");
            } else {
                var cocktail = (Cocktail) drink;
                System.out.println("Name: " + cocktail.getName() + ", volume: " + cocktail.getVolume() + ", alcohol: " + cocktail.getAlcohol());
                System.out.println("Ingredients: ");
                for (Drink ingredient :
                        cocktail.getIngredients()) {
                    System.out.println(ingredient.getType() + " " + ingredient.getName());

                }
            }
        }
    }

}
