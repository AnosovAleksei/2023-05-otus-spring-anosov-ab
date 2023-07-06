package ru.otus.service;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;


@Service
public class IOServiceImpl implements IOService {


    private final PrintStream output;

    private final Scanner input;

    public IOServiceImpl(PrintStream outputStream, InputStream inputStream) {
        output = outputStream;
        input = new Scanner(inputStream);
    }

    public IOServiceImpl() {
        output = System.out;
        input = new Scanner(System.in);
    }

    @Override
    public void printLn(String line) {
        output.println(line);
    }

    @Override
    public String readLine(String line) {
        output.println(line);
        return input.nextLine();
    }

}
