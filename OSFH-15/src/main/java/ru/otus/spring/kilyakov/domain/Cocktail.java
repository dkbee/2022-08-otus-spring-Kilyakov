package ru.otus.spring.kilyakov.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Cocktail extends Drink {
    private List<Drink> ingredients;

    public Cocktail() {

    }

    public Cocktail(String name, List<Drink> ingredients) {
        super(name, "Cocktail",0, 0);
        this.ingredients = ingredients;
        int commonVolume = ingredients.stream().map(Drink::getVolume).mapToInt(Integer::intValue).sum();
        double alcoholVolume = ingredients.stream().map(drink -> drink.getVolume() * drink.getAlcohol() / 100)
                .mapToDouble(Double::doubleValue).sum();
        double alcohol = alcoholVolume / commonVolume * 100;
        setAlcohol(alcohol);
        setVolume(commonVolume);
    }
}