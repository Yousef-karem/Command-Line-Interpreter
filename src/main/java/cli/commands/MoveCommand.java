package cli.commands;
import java.io.File;
import java.util.Scanner;

public class MoveCommand implements command {
    private Scanner scanner = new Scanner(System.in);

    public void execute(String[] files) {
        if (files.length < 2) {
            System.out.println("mv: invalid number of arguments");
            return;
        }

        String destinationPath = files[files.length - 1];
        File destination = new File(destinationPath);
        boolean isDestinationDir= destination.isDirectory();

        for (int i = 0; i < files.length - 1; i++) {
            File source = new File(files[i]);
            if (source.exists()) {
                if (isDestinationDir) {
                    File newFile = new File(destination, source.getName());
                    handleFileMove(source, newFile,false);
                } else {
                    handleFileMove(source, destination,true);
                }
            } else {
                System.out.println("mv: no such file or directory: " + source.getPath());
            }
        }
    }

    private void handleFileMove(File source, File destination, boolean isRename) {
        if (destination.exists()) {
            System.out.println("Destination file already exists: " + destination.getPath());
            System.out.print("Do you want to overwrite it? (y/n): ");
            String response = scanner.nextLine();
            if (!response.equalsIgnoreCase("y")) {
                return;
            }
            if (!destination.delete()) {
                System.out.println("Failed to delete existing file: " + destination.getPath());
                return;
            }
        }

        if (source.renameTo(destination)) {
            if (isRename) {
                System.out.println("Renamed: " + source.getName() + " to " + destination.getName());
            } else {
                System.out.println("Moved: " + source.getName() + " to " + destination.getPath());
            }
        } else {
            System.out.println("mv: failed to move " + source.getName() + " to " + destination.getPath());
        }
    }

}
