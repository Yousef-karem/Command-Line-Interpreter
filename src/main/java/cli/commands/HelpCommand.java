package cli.commands;

public class HelpCommand {
    public String execute() {
        String ret = "";
        System.out.println("CLI Help - Available Commands:");
        ret = ret + "CLI Help - Available Commands:\n";
        System.out.println("-------------------------------------------------");
        ret += "-------------------------------------------------\n";
        System.out.println("pwd    : Prints the current working directory.");
        ret += "pwd    : Prints the current working directory.\n";
        System.out.println("cd     : Changes the current directory. Usage: cd <directory>");
        ret += "cd     : Changes the current directory. Usage: cd <directory>\n";
        System.out.println("ls     : Lists files and directories in the current directory. Options: -a (show hidden files), -r (reverse order)");
        ret += "ls     : Lists files and directories in the current directory. Options: -a (show hidden files), -r (reverse order)\n";
        System.out.println("mkdir  : Creates a new directory. Usage: mkdir <directory_name>");
        ret += "mkdir  : Creates a new directory. Usage: mkdir <directory_name>\n";
        System.out.println("rmdir  : Removes an empty directory. Usage: rmdir <directory_name>");
        ret += "rmdir  : Removes an empty directory. Usage: rmdir <directory_name>\n";
        System.out.println("touch  : Creates a new empty file. Usage: touch <file_name>");
        ret += "touch  : Creates a new empty file. Usage: touch <file_name>";
        System.out.println("mv     : Moves or renames a file or directory. Usage: mv <source> <destination>");
        ret += "mv     : Moves or renames a file or directory. Usage: mv <source> <destination>\n";
        System.out.println("rm     : Removes a file. Usage: rm <file_name>");
        ret += "rm     : Removes a file. Usage: rm <file_name>\n";
        System.out.println("cat    : Displays the contents of a file. Usage: cat <file_name>");
        ret += "cat    : Displays the contents of a file. Usage: cat <file_name>\n";
        System.out.println("-------------------------------------------------");
        ret += "-------------------------------------------------\n";
        System.out.println("Use these commands in the CLI to manage files and directories.");
        ret += "Use these commands in the CLI to manage files and directories.\n";
        return ret;
    }
}
