package cli;

import cli.commands.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;
import java.io.FileWriter;
public class CommandLineInterpreter {

    private boolean running;
    Scanner scanner = new Scanner(System.in);
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
                for(int i=1;i< command.length;i++){
                    CreateFileCommand createFileCommand = new CreateFileCommand();
                    createFileCommand.execute(new String[]{command[i]});}
                break;
            case "mv":
                MoveCommand moveCommand = new MoveCommand();
                if (command.length < 3) {
                    System.out.println("mv: missing source or destination argument");
                } else {
                    String[] moveArgs = new String[command.length - 1];
                    for (int i = 1; i < command.length; i++) {
                        moveArgs[i - 1] = command[i];
                    }
                    moveCommand.execute(moveArgs);
                }
                break;
            case "rm":
                RemoveCommand removeCommand = new RemoveCommand();
                if (command.length < 2) {
                    System.out.println("rm: missing argument");
                } else {
                    String[] removeArgs = new String[command.length - 1];
                    for (int i = 1; i < command.length; i++) {
                        removeArgs[i - 1] = command[i];
                    }
                    removeCommand.execute(removeArgs);}
                break;
            case "cat":
                System.out.println("Enter the file names (separated by space) or 'exit' to stop:");
                String inp = scanner.nextLine();

                if (inp.equalsIgnoreCase("exit")) {
                    break;
                }
                String[] parts = inp.split(" ");
                ShowFileContentCommand showFileContentCommand = new ShowFileContentCommand();

                List<String> fileNames = new ArrayList<>();
                boolean isAppending = false;
                String targetFile = null;
                for (int i = 0; i < parts.length; i++) {
                    if (parts[i].equals(">")) {
                        if (i + 1 < parts.length) {
                            targetFile = parts[i + 1];
                            break;
                        } else {
                            System.out.println("cat: missing target file after '>'");
                            return;
                        }
                    } else if (parts[i].equals(">>")) {
                        if (i + 1 < parts.length) {
                            targetFile = parts[i + 1];
                            isAppending = true;
                            break;
                        } else {
                            System.out.println("cat: missing target file after '>>'");
                            return;
                        }
                    } else {
                        fileNames.add(parts[i]);
                    }
                }
                if (targetFile != null) {
                    if (fileNames.isEmpty() && !isAppending) {
                        showFileContentCommand.createFile(targetFile);
                    } else if (isAppending) {
                        String[] appendArgs = new String[fileNames.size() + 2];
                        for (int i = 0; i < fileNames.size(); i++) {
                            appendArgs[i] = fileNames.get(i);
                        }
                        appendArgs[fileNames.size()] = ">>";
                        appendArgs[appendArgs.length - 1] = targetFile;
                        showFileContentCommand.execute(appendArgs);
                    } else {
                        String[] overwriteArgs = new String[fileNames.size() + 1];
                        for (int i = 0; i < fileNames.size(); i++) {
                            overwriteArgs[i] = fileNames.get(i);
                        }
                        overwriteArgs[overwriteArgs.length - 1] = targetFile;
                        showFileContentCommand.execute(overwriteArgs);
                    }
                } else {
                    showFileContentCommand.execute(fileNames.toArray(new String[0]));
                }
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
