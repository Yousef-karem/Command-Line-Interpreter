package cli.commands;

import cli.CommandLineInterpreter;

import java.io.*;

public class ShowFileContentCommand implements command {
    @Override
    public void execute(String[] args) {
        if (args.length < 1) {
            System.out.println("cat: missing argument");
            return;
        }
        boolean first=true;
        // Display multiple files
        for (String fileName : args) {
            if(first) {
                first = false;
                continue;
            }
            String outFile=displayFile(fileName);
            if(!CommandLineInterpreter.SuccessExecute)
            {
                CommandLineInterpreter.output=outFile;

                return;
            }
            CommandLineInterpreter.output+=outFile;
        }
        if(CommandLineInterpreter.output.endsWith("\n")) {
            CommandLineInterpreter.output = CommandLineInterpreter.output.substring(0,
                    CommandLineInterpreter.output.length() - 1);
        }
    }
    private String displayFile(String fileName) {
        File file = new File(fileName);
        String ret="";
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    ret=ret+line+"\n";
                }
                return ret;
            } catch (IOException e) {
                CommandLineInterpreter.SuccessExecute =false;
                return "cat: " + e.getMessage();
            }
        }
        else {
            CommandLineInterpreter.SuccessExecute =false;
            return "cat: no such file: " + fileName;
        }
    }

}

