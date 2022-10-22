package ru.otus.spring.kilyakov.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.kilyakov.service.IoService;

import java.util.Scanner;

@Service
public class ConsoleServiceImpl implements IoService {

    private Scanner scanner = new Scanner(System.in);

    @Override
    public int readDigit(int min, int max) {
        while (true) {
            int choice;
            try {
                String innerText = scanner.nextLine();
                choice = Integer.parseInt(innerText);
            } catch (NumberFormatException ex) {
                writeString("You are entered not a digit, please try again");
                continue;
            }
            if (choice < min || choice > max) {
                writeString(String.format("You are entered wrong digit, please enter digit between %d and %d", min, max));
            } else {
                return choice;
            }
        }
    }

    @Override
    public String readString() {
        return scanner.nextLine();
    }

    @Override
    public void writeString(String message) {
        System.out.println(message);
    }
}
