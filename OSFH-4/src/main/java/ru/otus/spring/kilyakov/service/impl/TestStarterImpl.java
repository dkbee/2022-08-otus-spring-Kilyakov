package ru.otus.spring.kilyakov.service.impl;

import org.springframework.stereotype.Component;
import ru.otus.spring.kilyakov.config.property.ApplicationProperty;
import ru.otus.spring.kilyakov.service.IoService;
import ru.otus.spring.kilyakov.service.StudentTestingService;
import ru.otus.spring.kilyakov.service.TestStarter;

import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class TestStarterImpl implements TestStarter {

    private static final int START_TEST = 1;
    private static final int EXIT = 2;
    private static final int MIN_OPTION_MENU = 1;
    private static final int MAX_OPTION_MENU = 2;
    private final AtomicBoolean running;

    private final StudentTestingService studentTestingService;
    private final ApplicationProperty applicationProperty;

    private final IoService ioService;

    public TestStarterImpl(StudentTestingService studentTestingService,
                           ApplicationProperty applicationProperty, IoService ioService) {
        this.studentTestingService = studentTestingService;
        this.applicationProperty = applicationProperty;
        this.running = new AtomicBoolean(true);
        this.ioService = ioService;
    }

    @Override
    public void run() {
        greetUser();
        while (running.get()) {
            outputMainMenu();
            int menuCommand = ioService.readDigit(MIN_OPTION_MENU, MAX_OPTION_MENU);
            processMenuCommand(menuCommand);
        }
        running.set(true);
    }


    private void outputMainMenu() {
        ioService.writeString("Please make a choice:");
        ioService.writeString("1 - Start test");
        ioService.writeString("2 - Exit\n");
    }

    private void greetUser() {
        ioService.writeString("Please, enter your first name and last name:");
        String name = ioService.readString();
        ioService.writeString(MessageFormat.format(applicationProperty.getHelloOutput(), name));
    }

    private void processMenuCommand(int command) {
        if (command == START_TEST) {
            ioService.writeString("Test started...\n");
            studentTestingService.executeTest();
            ioService.writeString("Test completed.\n");
        }
        if (command == EXIT) {
            running.set(false);
            ioService.writeString("Program finished!");
        }
    }
}
