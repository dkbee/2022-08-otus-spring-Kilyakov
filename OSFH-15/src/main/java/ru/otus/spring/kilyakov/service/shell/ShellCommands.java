package ru.otus.spring.kilyakov.service.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@RequiredArgsConstructor
@ShellComponent
public class ShellCommands {

    @ShellMethod(value = "Buy beer", key = "-b beer")
    public void buyBeer() {
        System.out.println("Beer");
    }

}
