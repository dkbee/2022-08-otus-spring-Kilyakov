package ru.otus.spring.kilyakov.service.impl;

import org.springframework.stereotype.Component;
import ru.otus.spring.kilyakov.config.property.ApplicationProperty;
import ru.otus.spring.kilyakov.service.StudentTestingService;
import ru.otus.spring.kilyakov.service.TestStarter;

import java.text.MessageFormat;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class TestStarterImpl implements TestStarter {

    private static final int START_TEST = 1;
    private static final int EXIT = 2;
    private final AtomicBoolean running;

    private final StudentTestingService studentTestingService;
    private final ApplicationProperty applicationProperty;

    public TestStarterImpl(StudentTestingService studentTestingService,
                           ApplicationProperty applicationProperty) {
        this.studentTestingService = studentTestingService;
        this.applicationProperty = applicationProperty;
        this.running = new AtomicBoolean(true);
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        greetUser(scanner);
        while (running.get()) {
            outputMainMenu();
            int menuCommand = readMenuCommand(scanner);
            processMenuCommand(menuCommand);
        }
    }

    private int readMenuCommand(Scanner scanner) {
        while (true) {
            int choice;
            try {
                String innerText = scanner.nextLine();
                choice = Integer.parseInt(innerText);
            } catch (NumberFormatException ex) {
                System.out.println("You are entered not a digit, please try again");
                continue;
            }
            if (choice < 1 || choice > 2) {
                System.out.println("Your answer do  not laying between 1 and 2, please try again");
                continue;
            }
            return choice;
        }
    }

    private void greetUser(Scanner scanner) {
        System.out.println("Please, enter your first name and last name:");
        String name = scanner.nextLine();
        System.out.println(MessageFormat.format(applicationProperty.getHelloOutput(), name));
    }

    public void outputMainMenu() {
        System.out.println("Please make a choice:");
        System.out.println("1 - Start test");
        System.out.println("2 - Exit\n");
    }

    private void processMenuCommand(int command) {
        if (command == START_TEST) {
            System.out.println("Test started...\n");
            studentTestingService.executeTest();
            System.out.println("Test completed.\n");
        }
        if (command == EXIT) {
            running.set(false);
            System.out.println("Program finished!");
        }
    }
}
