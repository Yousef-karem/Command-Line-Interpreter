package cli.commands;

import cli.CommandLineInterpreter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ListFilesCommandTest {

    private ListFilesCommand listFilesCommand;
    private String testDirectoryPath;

    @BeforeEach
    public void setup() throws IOException {
        listFilesCommand = new ListFilesCommand();
        testDirectoryPath = System.getProperty("user.dir") + "/testDir";

        // Create a test directory if it doesn't exist
        File testDir = new File(testDirectoryPath);
        if (!testDir.exists()) {
            assertTrue(testDir.mkdir(), "Failed to create test directory");
        }

        // Create test files (one hidden file)
        new File(testDirectoryPath + "/file1.txt").createNewFile();
        new File(testDirectoryPath + "/file2.txt").createNewFile();
        new File(testDirectoryPath + "/.hidden.txt").createNewFile();

        // Set the test directory as the current working directory
        System.setProperty("user.dir", testDirectoryPath);
        CommandLineInterpreter.output="";
    }

    @AfterEach
    public void tearDown() {
        // Delete all files inside the test directory
        File testDir = new File(testDirectoryPath);
        if (testDir.exists()) {
            Arrays.stream(testDir.listFiles()).forEach(File::delete);  // Delete all files
            assertTrue(testDir.delete(), "Failed to delete test directory");  // Delete the directory itself
        }

        // Reset the working directory to the original one
        System.setProperty("user.dir", System.getProperty("user.home"));
        CommandLineInterpreter.output="";
    }

    @Test
    public void testListFilesValidCommand() {
        String[] command = {"ls"};
        CommandLineInterpreter.output = "";
        CommandLineInterpreter.SuccessExecute = true;

        listFilesCommand.execute(command);

        // Collect only non-hidden files and sort them by name
        String expectedOutput = Arrays.stream(new File(testDirectoryPath).listFiles())
                .filter(file -> !file.getName().startsWith("."))  // Exclude hidden files
                .map(File::getName)
                .sorted()
                .collect(Collectors.joining("\n")) + "\n";

        assertTrue(CommandLineInterpreter.SuccessExecute);
        assertEquals(expectedOutput, CommandLineInterpreter.output);
    }

    @Test
    public void testListFilesShowAllFiles() {
        String[] command = {"ls", "-a"};
        CommandLineInterpreter.output = "";
        CommandLineInterpreter.SuccessExecute = true;

        listFilesCommand.execute(command);

        // Collect all files, including hidden ones, and sort by name
        String expectedOutput = Arrays.stream(new File(testDirectoryPath).listFiles())
                .map(File::getName)
                .sorted()
                .collect(Collectors.joining("\n")) + "\n";

        assertTrue(CommandLineInterpreter.SuccessExecute);
        assertEquals(expectedOutput, CommandLineInterpreter.output);
    }

    @Test
    public void testListFilesReversedOrder() {
        String[] command = {"ls", "-r"};
        CommandLineInterpreter.output = "";
        CommandLineInterpreter.SuccessExecute = true;

        listFilesCommand.execute(command);

        // Collect non-hidden files and sort them in reverse order
        String expectedOutput = Arrays.stream(new File(testDirectoryPath).listFiles())
                .filter(file -> !file.getName().startsWith("."))
                .map(File::getName)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.joining("\n")) + "\n";

        assertTrue(CommandLineInterpreter.SuccessExecute);
        assertEquals(expectedOutput, CommandLineInterpreter.output);
    }

    @Test
    public void testListFilesShowAllAndReversed() {
        String[] command = {"ls", "-a", "-r"};
        CommandLineInterpreter.output = "";
        CommandLineInterpreter.SuccessExecute = true;

        listFilesCommand.execute(command);

        // Collect all files and sort them in reverse order
        String expectedOutput = Arrays.stream(new File(testDirectoryPath).listFiles())
                .map(File::getName)
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.joining("\n")) + "\n";

        assertTrue(CommandLineInterpreter.SuccessExecute);
        assertEquals(expectedOutput, CommandLineInterpreter.output);
    }

    @Test
    public void testInvalidCommand() {
        String[] command = {"ls", "-z"};  // Invalid flag
        CommandLineInterpreter.output = "";
        CommandLineInterpreter.SuccessExecute = true;

        listFilesCommand.execute(command);

        assertFalse(CommandLineInterpreter.SuccessExecute);
        assertEquals("Invalid command", CommandLineInterpreter.output);
    }

    @Test
    public void testInvalidCommandWithoutDash() {
        String[] command = {"ls", "invalid"};  // Invalid argument
        CommandLineInterpreter.output = "";
        CommandLineInterpreter.SuccessExecute = true;

        listFilesCommand.execute(command);

        assertFalse(CommandLineInterpreter.SuccessExecute);
        assertEquals("Invalid command", CommandLineInterpreter.output);
    }
}
