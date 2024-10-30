package cli;
import cli.commands.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Arrays;

public class CommandLineInterpreter {
    public static boolean SuccessExecute=true;
    public static String output ="";
    private boolean running;
    Scanner scanner = new Scanner(System.in);
    public void start() throws IOException {
        running = true;
        Scanner scanner = new Scanner(System.in);
        while (running) {
            SuccessExecute=true;
            PrintWorkDirectoryCommand printWorkDirectoryCommand = new PrintWorkDirectoryCommand();
            String command[]={""};
            printWorkDirectoryCommand.execute(command);
            System.out.print(output);
            System.out.print("> ");
            output="";
            String input = scanner.nextLine();
            executeCommand(input);
        }
        scanner.close();
    }

    public void executeCommand(String input) throws IOException {

        String[] command = input.split(" ");
        String fileName = null;
        String commandForPip = null;
        boolean appendOperator = false;
        boolean replaceOperator = false;
        boolean PipeOperator = false;
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
            else if(command[command.length - 2].equals("|")) {
                PipeOperator = true;
                commandForPip = command[command.length - 1];
                command = Arrays.copyOf(command, command.length - 2);
            }

        }
        switch (command[0]) {
            case "help":
                HelpCommand helpCommand = new HelpCommand();
                output = helpCommand.execute();
                break;
            case "pwd":
                PrintWorkDirectoryCommand printWorkDirectoryCommand = new PrintWorkDirectoryCommand();
                printWorkDirectoryCommand.execute(command);
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
                ShowFileContentCommand showFileContentCommand = new ShowFileContentCommand();
                showFileContentCommand.execute(command);
                break;
            case "exit":
                stop();
                break;
            default:
                System.out.println("Command not recognized. Type 'help' for a list of commands.");
                break;

        }
        if(!SuccessExecute)
        {
            replaceOperator=false;
            appendOperator=false;
            PipeOperator=false;
        }
        if (replaceOperator) {
            ReplaceCommand replaceCommand = new ReplaceCommand();
            replaceCommand.execute( CommandLineInterpreter.output,fileName);
        }
        else if (appendOperator) {
            AppendCommand appendCommand = new AppendCommand();
            appendCommand.execute( CommandLineInterpreter.output,fileName);
        }
        else if(PipeOperator)
        {
            PipeCommand pipeCommand = new PipeCommand();
            pipeCommand.execute(new String[]{commandForPip});
        }
        else if(output!=null) {
            System.out.println(output);
        }
    }

    public void stop() {
        running = false;
    }
}
