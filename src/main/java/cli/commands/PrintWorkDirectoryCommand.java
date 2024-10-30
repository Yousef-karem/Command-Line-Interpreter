package cli.commands;

import cli.CommandLineInterpreter;

public class PrintWorkDirectoryCommand implements command{
    public void execute(String[] args)
    {
        CommandLineInterpreter.output=System.getProperty("user.dir");
    }
}
