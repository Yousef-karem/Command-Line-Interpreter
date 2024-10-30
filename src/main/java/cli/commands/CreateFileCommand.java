package cli.commands;
import java.io.File;
import java.io.IOException;

public class CreateFileCommand implements command{
    public void execute(String[] files)
    {
        if (files.length < 1) {
            System.out.println("Error! touch:no files name to create!");
            return;}
        for (String fileName : files) {
            File file = new File(fileName);
            try {
                if (file.createNewFile()) {
                    System.out.println("File created: " + fileName);
                } else {
                    boolean updated = file.setLastModified(System.currentTimeMillis());//if file already exists update last modified time
                    if (updated) {
                        System.out.println("File updated: " + fileName);
                    } else {
                        System.out.println("Failed to update file: " + fileName);
                    }
                }

            } catch (IOException e) {
                System.out.println("touch: " + e.getMessage());
            }
        }

    }

}
