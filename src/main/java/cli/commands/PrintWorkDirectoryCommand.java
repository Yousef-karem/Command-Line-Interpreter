package cli.commands;

public class PrintWorkDirectoryCommand implements command{
    public void execute(String[] args)
    {
        System.out.print(System.getProperty("user.dir"));
    }
}
