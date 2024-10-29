package cli.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class ChangeDirectoryCommandTest
{


    private String initialDir;

    @BeforeEach
    void setUp() {
        // Store the initial directory to restore it later
        initialDir = System.getProperty("user.dir");
    }

    @AfterEach
    void tearDown() {
        // Restore the initial directory after each test
        System.setProperty("user.dir", initialDir);
    }

    @Test
    void testChangeToValidRelativeDirectory() {
        // Create a test directory inside the current directory
        File testDir = new File(initialDir, "testDir");
        testDir.mkdir();

        // Execute the command with the relative path
        ChangeDirectoryCommand command = new ChangeDirectoryCommand();
        command.execute(new String[]{"cd", "testDir"});

        // Assert the directory change was successful
        assertEquals(testDir.getAbsolutePath(), System.getProperty("user.dir"));

        // Cleanup
        testDir.delete();
    }
    @Test
    void testChangeDirectoryStartWithSlash() {
        // Create a test directory inside the current directory
        File testDir = new File(initialDir, "\\testDir");
        testDir.mkdir();

        // Execute the command with the relative path
        ChangeDirectoryCommand command = new ChangeDirectoryCommand();
        command.execute(new String[]{"cd", "\\testDir"});

        // Assert the directory change was successful
        assertEquals(testDir.getAbsolutePath(), System.getProperty("user.dir"));

        // Cleanup
        testDir.delete();
    }

    @Test
    void testChangeToValidAbsoluteDirectory() {
        // Create a test directory with an absolute path
        File testDir = new File(initialDir, "testDir");
        testDir.mkdir();

        // Execute the command with the absolute path
        ChangeDirectoryCommand command = new ChangeDirectoryCommand();
        command.execute(new String[]{"cd", testDir.getAbsolutePath()});

        // Assert the directory change was successful
        assertEquals(testDir.getAbsolutePath(), System.getProperty("user.dir"));

        // Cleanup
        testDir.delete();
    }

    @Test
    void testChangeToInvalidDirectory() {
        // Execute the command with a non-existent directory
        ChangeDirectoryCommand command = new ChangeDirectoryCommand();
        command.execute(new String[]{"cd", "nonExistentDir"});

        // Assert that the current directory did not change
        assertEquals(initialDir, System.getProperty("user.dir"));
    }

    @Test
    void testUsageWithMissingArgument() {
        // Capture system output
        ChangeDirectoryCommand command = new ChangeDirectoryCommand();

        // Expect a usage message due to missing argument
        command.execute(new String[]{"cd"});

        // Assert the directory remains unchanged
        assertEquals(initialDir, System.getProperty("user.dir"));
    }
}