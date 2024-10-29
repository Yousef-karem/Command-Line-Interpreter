package cli.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReplaceCommand {
    public void execute(String data, String fileName) throws IOException {
        File currentDirectory= new File(System.getProperty("user.dir"));
        File fileToReplace = new File(currentDirectory + "\\" + fileName);
        fileToReplace.createNewFile();
        FileWriter fileWriter = new FileWriter(fileToReplace);
        fileWriter.write(data);
        fileWriter.close();
    }
}
