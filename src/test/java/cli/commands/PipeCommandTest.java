package cli.commands;

import cli.CommandLineInterpreter;
import cli.commands.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class PipeCommandTest {

    private final String testFileName = "testFile";
    private String initialDir;

    @BeforeEach
    public void setUp() throws Exception {
        CommandLineInterpreter.output="";
        initialDir = System.getProperty("user.dir");
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Delete the test file if it exists
        System.setProperty("user.dir", initialDir);
        File file = new File(System.getProperty("user.dir") + "\\" + testFileName);
        if (file.exists()) {
            Files.delete(file.toPath());
        }
        CommandLineInterpreter.output="";

    }

    @Test
    public void testPipeWithAndCd() throws IOException {
        File file = null;
        try {
            file = new File(System.getProperty("user.dir") + "/" + testFileName);
            if (!file.exists()) {
                boolean isCreated = file.createNewFile();
                assertTrue(isCreated, "File creation failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
            fail("An IOException occurred: " + e.getMessage());
        }
        CommandLineInterpreter.output = file.getAbsolutePath();


        PipeCommand pipeCommand = new PipeCommand();
        pipeCommand.execute(new String[]{"cd"});

        // Now verify if we are in the new directory
        assertEquals(CommandLineInterpreter.output, System.getProperty("user.dir"));
    }
}