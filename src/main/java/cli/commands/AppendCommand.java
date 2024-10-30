package cli.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AppendCommand {
    public void execute(String data, String fileName) throws IOException {
        File currentDirectory = new File(System.getProperty("user.dir"));
        File fileToAppend = new File(currentDirectory + "\\" + fileName);
        fileToAppend.createNewFile();  // Creates the file if it doesn't exist

        // Open FileWriter in append mode (true)
        FileWriter fileWriter = new FileWriter(fileToAppend, true);
        fileWriter.append(data);
        fileWriter.close();
    }
}
