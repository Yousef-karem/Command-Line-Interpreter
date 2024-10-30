package cli.commands;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class PipeCommand implements command {

    @Override
    public void execute(String[] args) {

        // Execute the first command and capture its output
        String command1 = args[0];
        String command2 = args[2];

        String output = executeAndCaptureOutput(new String[]{command1});

        if (output != null) {
            if(ExecuteCommand(new String[]{command2,output})==0)
            {
                System.out.println("Failed to execute the Second command.");
            }

        } else {
            System.out.println("Failed to execute the first command.");
        }
    }

    private int ExecuteCommand(String command[]) {
        switch (command[0]) {
            case "pwd":
                PrintWorkDirectoryCommand printWorkDirectoryCommand = new PrintWorkDirectoryCommand();
                printWorkDirectoryCommand.execute(command);
                return 1;

            case "ls":
                ListFilesCommand listFilesCommand = new ListFilesCommand();
                listFilesCommand.execute(command);
                return 1;

            case "cd":
                ChangeDirectoryCommand changeDirectoryCommand = new ChangeDirectoryCommand();
                changeDirectoryCommand.execute(command);
                return 1;
            case "touch":
                CreateFileCommand createFileCommand = new CreateFileCommand();
                createFileCommand.execute(command);
                return 1;
            case "mv":
                MoveCommand moveCommand = new MoveCommand();
                moveCommand.execute(command);
                return 1;
            case "rm":
                RemoveCommand removeCommand = new RemoveCommand();
                removeCommand.execute(command);
                return 1;
            case "cat":
                ShowFileContentCommand showFileContentCommand = new ShowFileContentCommand();
                showFileContentCommand.execute(command);
                return 1;
            default:
                System.out.println("Command not recognized. Type 'help' for a list of commands.");
                return 0;
        }
    }

    private String executeAndCaptureOutput(String[] command) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        int ret = ExecuteCommand(command);
        System.setOut(originalOut);
        // Redirect System.out to capture output
        if (ret == 1) {
            return outputStream.toString().trim();
        } else {
            return null;
        }
    }

}
