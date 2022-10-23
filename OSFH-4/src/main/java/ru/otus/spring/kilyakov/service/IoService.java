package ru.otus.spring.kilyakov.service;

public interface IoService {
    public int readDigit(int min, int max);

    public String readString();

    public void writeString(String message);
}
