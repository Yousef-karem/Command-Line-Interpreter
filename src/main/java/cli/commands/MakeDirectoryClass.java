package cli.commands;

import java.io.File;

public class MakeDirectoryClass implements command {
    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: mkdir <directory>");
            return;
        }

        for (int i = 1; i < args.length; i++) {
            File dir = new File(System.getProperty("user.dir"), args[i]);
            if (dir.exists()) {
                System.out.println("mkdir: Directory already exists: " + args[i]);
            } else if (dir.mkdir()) {
                System.out.println("Created: " + dir.getAbsolutePath());
            } else {
                System.out.println("mkdir: Failed to create: " + args[i]);
            }
        }
    }
}