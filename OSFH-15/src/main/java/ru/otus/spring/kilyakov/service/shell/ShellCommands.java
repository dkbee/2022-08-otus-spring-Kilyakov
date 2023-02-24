package ru.otus.spring.kilyakov.service.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.kilyakov.domain.Beer;
import ru.otus.spring.kilyakov.domain.OrderItem;
import ru.otus.spring.kilyakov.domain.Vodka;
import ru.otus.spring.kilyakov.service.BarService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@ShellComponent
public class ShellCommands {

    @Autowired
    private BarService barService;

    @ShellMethod(value = "Buy beer", key = "-b beer")
    public void buyBeer(String name, int volume) {
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(name, volume, Beer.class));
        Collection<Beer> drinks = barService.processBeer(orderItems);
        for (Beer drink :
                drinks) {
            System.out.println("Name: " + drink.getName() + ", volume: " + drink.getVolume() + ", alcohol: "
                    + drink.getAlcohol() + ", gravity: " + drink.getOg() + ", bitterness: " + drink.getIbu());
        }
    }

    @ShellMethod(value = "Buy beer", key = "-b vodka")
    public void buyVodka(String name, int volume) {
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem(name, volume, Vodka.class));
        Collection<Vodka> drinks = barService.processVodka(orderItems);
        for (Vodka drink :
                drinks) {
            System.out.println("Name: " + drink.getName() + ", volume: " + drink.getVolume());
        }
    }

}
