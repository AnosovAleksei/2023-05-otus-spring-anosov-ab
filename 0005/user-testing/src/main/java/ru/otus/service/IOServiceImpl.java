package ru.otus.service;


import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;



public class IOServiceImpl implements IOService {

    private final PrintStream output;

    private final Scanner input;

    public IOServiceImpl(PrintStream outputStream, InputStream inputStream) {
        output = outputStream;
        input = new Scanner(inputStream);
    }

    @Override
    public void printLn(String line) {
        output.println(line);
    }

    @Override
    public String readLine() {
        return input.nextLine();
    }

}
