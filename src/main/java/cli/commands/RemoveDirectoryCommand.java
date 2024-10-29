package cli.commands;

import java.io.File;
import java.util.Scanner;

public class RemoveDirectoryCommand implements command {
    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: rmdir <directory>");
            return;
        }
        for (int i = 1; i < args.length; i++) {
            File dir = new File(System.getProperty("user.dir"), args[i]);
            if (!dir.exists()) {
                System.out.println("rmdir: No such directory: " + args[i]);
            } else if (dir.list().length > 0) {
                System.out.println("rmdir: Directory not empty: " + args[i]);
            } else if (dir.delete()) {
                System.out.println("Removed: " + dir.getAbsolutePath());
            } else {
                System.out.println("rmdir: Failed to remove: " + args[i]);
            }
        }
    }
}