package cli.commands;
import java.io.File;
public class ChangeDirectoryCommand  implements command{
    @Override
    public void execute(String[] args) {
        if (args.length != 2 ) {
            System.out.println("Usage: cd <directory>");
            return;
        }

        String path = args[1];
        String currDir=System.getProperty("user.dir");
        if(!path.startsWith("/"))
            currDir=currDir+"/";
        File newDir1 = new File(currDir, path);
        File newDir2 = new File(path);
        if (newDir1.exists() && newDir1.isDirectory()) {
            System.setProperty("user.dir", newDir1.getAbsolutePath());
        }
        else if(newDir2.exists() && newDir2.isDirectory())
        {
            System.setProperty("user.dir", newDir2.getAbsolutePath());
        }
        else {
            System.out.println("cd: No such directory: " + path);
        }
    }
}
