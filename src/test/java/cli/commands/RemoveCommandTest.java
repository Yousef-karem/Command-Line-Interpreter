package cli.commands;

import cli.CommandLineInterpreter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import  org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RemoveCommandTest {
    private RemoveCommand rmCommand;

    @BeforeEach
    public void setUp() {
        rmCommand = new RemoveCommand();
        CommandLineInterpreter.output="";
    }

    @Test
    public void testRemoveFile() throws IOException {
        String fileName = "file.txt";
        new File(fileName).createNewFile();

        rmCommand.execute(new String[]{fileName});
        assertFalse(new File(fileName).exists());
    }

    @Test
    public void testRemovefiles() throws IOException {
        String file1 = "file1.txt";
        String file2 = "file2.txt";
        new File(file1).createNewFile();
        new File(file2).createNewFile();

        rmCommand.execute(new String[]{file1, file2});
        assertFalse(new File(file1).exists());
        assertFalse(new File(file2).exists());
    }

    @Test
    public void testRemoveDirRecursive() throws IOException {
        String dirName = "Dir";
        String fileName = "fileinDir.txt";
        new File(dirName).mkdir();
        new File(dirName + "/" + fileName).createNewFile();

        rmCommand.execute(new String[]{"-r", dirName});
        assertFalse(new File(dirName).exists());
        assertFalse(new File(dirName + "/" + fileName).exists());
    }

    @AfterEach
    public void clear() {
        new File("file.txt").delete();
        new File("file1.txt").delete();
        new File("file2.txt").delete();
        new File("Dir").delete();
        CommandLineInterpreter.output="";
    }

}