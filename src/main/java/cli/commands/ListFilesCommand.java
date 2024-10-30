package cli.commands;
import cli.CommandLineInterpreter;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
public class ListFilesCommand {
    public void execute(String[] command) {
        File currentDirectory= new File(System.getProperty("user.dir"));
        boolean isReversed = false;
        boolean showAllFiles = false;
        boolean valid = true;
        for (int i = 1; i < command.length; i++) {
            if (command[i].charAt(0) == '-') {
                for (int j = 1; j < command[i].length(); j++) {
                    if (command[i].charAt(j) == 'r')
                        isReversed = true;
                    else if (command[i].charAt(j) == 'a')
                        showAllFiles = true;
                    else {
                        valid = false;
                        break;
                    }
                }
            } else {
                valid = false;
                break;
            }
        }
        String ret = "";
        if (!valid) {
            CommandLineInterpreter.SuccessExecute=false;
            CommandLineInterpreter.output="";
            return;
        }
        File[] files = currentDirectory.listFiles();
        Arrays.sort(files);
        if (isReversed)
            Collections.reverse(Arrays.asList(files));
        for (File file : files) {
            if (showAllFiles || file.getName().charAt(0) != '.') {
                CommandLineInterpreter.output+= file.getName()+"\n";
            }
        }
    }
}
