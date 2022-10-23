package ru.otus.spring.kilyakov.shell.impl;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.kilyakov.service.TestStarter;
import ru.otus.spring.kilyakov.shell.ShellService;

@ShellComponent
public class ShellServiceImpl implements ShellService {

    private final TestStarter testStarter;
    private String userName;

    public ShellServiceImpl(TestStarter testStarter) {
        this.testStarter = testStarter;
    }

    @Override
    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "Any User") String userName) {
        this.userName = userName;
        return String.format("Добро пожаловать %s", userName);
    }

    @Override
    @ShellMethod(value = "Start test command", key = {"r", "run", "s", "start"})
    @ShellMethodAvailability(value = "isRunCommandAvailable")
    public void run() {
        testStarter.run();
    }

    private Availability isRunCommandAvailable() {
        return userName == null ? Availability.unavailable("Сначала залогиньтесь") : Availability.available();
    }
}