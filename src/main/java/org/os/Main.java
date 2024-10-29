package org.os;

import cli.CommandLineInterpreter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        CommandLineInterpreter cli = new CommandLineInterpreter();
        cli.start();
    }
}