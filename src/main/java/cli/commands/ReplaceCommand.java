package cli.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReplaceCommand {
    public void execute(String data, File currentDirectory, String fileName) throws IOException {
        File fileToReplace = new File(currentDirectory + "\\" + fileName);
        fileToReplace.createNewFile();
        FileWriter fileWriter = new FileWriter(fileToReplace);
        fileWriter.write(data);
        fileWriter.close();
    }
}
