package cli;

import cli.commands.*;

import java.io.File;
import java.util.Scanner;

public class CommandLineInterpreter {

    private static File currentDirectory = new File(System.getProperty("user.dir"));

    private boolean running;

    public void start() {
        running = true;
        Scanner scanner = new Scanner(System.in);
        while (running) {
            System.out.print(currentDirectory + "> ");
            String input = scanner.nextLine();
            executeCommand(input);
        }
        scanner.close();
    }

    public void executeCommand(String input) {
        String[] command = input.split(" ");
        switch (command[0]) {
            case "help":
                System.out.println("Available commands:");
                System.out.println(" - exit: Terminate the CLI.");
                System.out.println(" - help: Display available commands and their usage.");
                System.out.println(" - pwd: Display the current directory.");
                System.out.println(" - ls: List files and directories.");
                System.out.println(" - cd <directory>: Change the current directory.");
                break;
            case "pwd":
                PrintWorkDirectoryCommand printWorkDirectoryCommand = new PrintWorkDirectoryCommand();
                printWorkDirectoryCommand.execute();
                break;

            case "ls":
                ListFilesCommand listFilesCommand = new ListFilesCommand();
                listFilesCommand.execute();
                break;

            case "cd":
                ChangeDirectoryCommand changeDirectoryCommand = new ChangeDirectoryCommand();
                changeDirectoryCommand.execute();
                break;

            case "mkdir":
                MakeDirectoryClass mkdirCommand = new MakeDirectoryClass();
                mkdirCommand.execute();
                break;
            case "rmdir":
                //The directory should be empty before remove it
                RemoveDirectoryCommand removeDirectoryCommand = new RemoveDirectoryCommand();
                removeDirectoryCommand.execute();
                break;
            case "touch":
                CreateFileCommand createFileCommand = new CreateFileCommand();
                createFileCommand.execute();
            case "mv":
                MoveCommand moveCommand = new MoveCommand();
                moveCommand.execute();
                break;
            case "rm":
                RemoveCommand removeCommand = new RemoveCommand();
                removeCommand.execute();
                break;
            case "cat":
                ShowFileContentCommand showFileContentCommand = new ShowFileContentCommand();
                showFileContentCommand.execute();
                break;
            case "exit":
                stop();
            default:
                System.out.println("Command not recognized. Type 'help' for a list of commands.");
                break;

        }
    }

    public void stop() {
        running = false;
    }
}