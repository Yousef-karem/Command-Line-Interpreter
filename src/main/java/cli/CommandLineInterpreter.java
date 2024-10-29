package cli;

import cli.commands.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;
import java.io.FileWriter;
public class CommandLineInterpreter {

    private boolean running;

    public void start() throws IOException {
        running = true;
        Scanner scanner = new Scanner(System.in);
        while (running) {
            PrintWorkDirectoryCommand printWorkDirectoryCommand = new PrintWorkDirectoryCommand();
            String command[]={""};
            printWorkDirectoryCommand.execute(command);
            System.out.print("> ");
            String input = scanner.nextLine();
            executeCommand(input);
        }
        scanner.close();
    }

    public void executeCommand(String input) throws IOException {
        String[] command = input.split(" ");
        String fileName = null;
        boolean appendOperator = false;
        boolean replaceOperator = false;
        if (command.length >= 3) {
            if (command[command.length - 2].equals(">")) {
                replaceOperator = true;
                fileName = command[command.length - 1];
                command = Arrays.copyOf(command, command.length - 2);
            } else if (command[command.length - 2].equals(">>")) {
                appendOperator = true;
                fileName = command[command.length - 1];
                command = Arrays.copyOf(command, command.length - 2);
            }
        }
        String output = "";
        switch (command[0]) {
            case "help":
                HelpCommand helpCommand = new HelpCommand();
                output = helpCommand.execute();
                break;
            case "pwd":
                PrintWorkDirectoryCommand printWorkDirectoryCommand = new PrintWorkDirectoryCommand();
                printWorkDirectoryCommand.execute(command);
                System.out.println();
                break;

            case "ls":
                ListFilesCommand listFilesCommand = new ListFilesCommand();
                listFilesCommand.execute(command);
                break;

            case "cd":
                ChangeDirectoryCommand changeDirectoryCommand = new ChangeDirectoryCommand();
                changeDirectoryCommand.execute(command);
                break;

            case "mkdir":
                MakeDirectoryClass mkdirCommand = new MakeDirectoryClass();
                mkdirCommand.execute(command);
                break;
            case "rmdir":
                //The directory should be empty before remove it
                RemoveDirectoryCommand removeDirectoryCommand = new RemoveDirectoryCommand();
                removeDirectoryCommand.execute(command);
                break;
            case "touch":
                CreateFileCommand createFileCommand = new CreateFileCommand();
                createFileCommand.execute(command);
            case "mv":
                MoveCommand moveCommand = new MoveCommand();
                moveCommand.execute(command);
                break;
            case "rm":
                RemoveCommand removeCommand = new RemoveCommand();
                removeCommand.execute(command);
                break;
            case "cat":
                ShowFileContentCommand showFileContentCommand = new ShowFileContentCommand();
                showFileContentCommand.execute(command);
                break;
            case "exit":
                stop();
            default:
                System.out.println("Command not recognized. Type 'help' for a list of commands.");
                break;

        }
        if (replaceOperator) {
            ReplaceCommand replaceCommand = new ReplaceCommand();
            replaceCommand.execute(output, fileName);
        }
        if (appendOperator) {
            AppendCommand appendCommand = new AppendCommand();
            appendCommand.execute(output, fileName);
        }
    }

    public void stop() {
        running = false;
    }
}
