package cli.commands;

import cli.CommandLineInterpreter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class CreateFileCommandTest {
    private CreateFileCommand createcommand;
    @BeforeEach
    public void setUp() {
        createcommand = new CreateFileCommand();
        CommandLineInterpreter.output="";
    }
    @Test
    public void createFile(){
        String fileName = "file.txt";
        createcommand.execute(new String[]{fileName});
        assertTrue(new File(fileName).exists());
        new File(fileName).delete();
    }
    @Test
    public  void createFiles(){
        String[] fileNames = {"file1.txt", "file2.txt"};
        createcommand.execute(fileNames);
        for (String fileName : fileNames) {
            assertTrue(new File(fileName).exists());
            new File(fileName).delete();
        }

    }
    @Test
    public void updateexistfiles(){
        String[] fileNames = {"file1.txt", "file2.txt"};
        createcommand.execute(fileNames); //create files

        createcommand.execute(fileNames); //update files

        for (String fileName : fileNames) {
            assertTrue(new File(fileName).exists());
            new File(fileName).delete();
        }
    }
    @AfterEach
    public void clear() {
        for (String fileName : new String[]{"file1.txt", "file2.txt"}) {
            new File(fileName).delete();
        }
        CommandLineInterpreter.output="";
    }

}