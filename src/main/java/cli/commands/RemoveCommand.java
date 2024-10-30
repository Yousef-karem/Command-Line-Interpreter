package cli.commands;

import java.io.File;

public class RemoveCommand implements command{
    public void execute(String[] files){
        boolean recursive = false;
        if (files.length < 1) {
            System.out.println("rm: missing argument");
            return;
        }
        if (files[0].equals("-r")) {
            recursive = true;
            files = removefirst(files);
        }
        for(String f:files){
            File file=new File(f);
            if(file.exists()){
                if(file.isDirectory()&&recursive){
                    removeDir(file);
                    System.out.println("Removed directory and its contents: " + f);
                } else if (file.isFile()) {
                    if (file.delete()) {
                        System.out.println("Removed: " + f);
                    } else {
                        System.out.println("rm: failed to remove " + f);
                    }
                }
                else {
                    System.out.println("rm: " + f + " is a directory.can use -r to remove.");
                }
            }
            else {
                System.out.println("rm: no such file or directory: " + f);
            }
        }
    }
    private String[] removefirst(String[] input) {
        String[] newArgs = new String[input.length - 1];
        for (int i=1;i<input.length; i++) {
            newArgs[i-1] = input[i];
        }
        return newArgs;
    }
    private void removeDir(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    removeDir(file); // recursive for directories
                } else {
                    file.delete();
                }

            }
        }
        directory.delete();
    }

}

