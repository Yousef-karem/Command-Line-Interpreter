package cli.commands;

import cli.CommandLineInterpreter;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class RemoveDirectoryCommandTest {

    private String initialDir;

    @BeforeEach
    void setUp() {
        CommandLineInterpreter.output="";
        // Store the initial working directory
        initialDir = System.getProperty("user.dir");
    }

    @AfterEach
    void tearDown() {
        // Clean up any remaining directories after each test
        deleteDirectoryIfExists("testDir");
        deleteDirectoryIfExists("nonEmptyDir");
        CommandLineInterpreter.output="";
    }

    // Helper method to delete a directory if it exists
    private void deleteDirectoryIfExists(String dirName) {
        File dir = new File(initialDir, dirName);

        if (dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            if(files != null)
            {
                for (File file : files) {
                    assertTrue(file.delete(), "Failed to delete directory: " + file);
                }
            }
            assertTrue(dir.delete(), "Failed to delete directory: " + dirName);
        }
    }

    // create a directory
    private void createDirectory(String dirName) {
        File dir = new File(initialDir, dirName);
        assertTrue(dir.mkdir(), "Failed to create directory: " + dirName);
    }

    // create a non-empty directory
    private void createNonEmptyDirectory(String dirName) throws IOException {
        createDirectory(dirName);
        File file = new File(initialDir + "/" + dirName, "temp.txt");
        file.createNewFile();
    }

    @Test
    void testRemoveEmptyDirectory() {
        createDirectory("testDir");

        RemoveDirectoryCommand command = new RemoveDirectoryCommand();
        command.execute(new String[]{"rmdir", "testDir"});

        // Verify that the directory has been removed
        assertFalse(new File(initialDir, "testDir").exists(), "Directory 'testDir' should be removed");
    }

    @Test
    void testRemoveNonEmptyDirectory() throws IOException {
        createNonEmptyDirectory("nonEmptyDir");

        RemoveDirectoryCommand command = new RemoveDirectoryCommand();
        command.execute(new String[]{"rmdir", "nonEmptyDir"});

        // Verify that the directory still exists (since it's non-empty)
        assertTrue(new File(initialDir, "nonEmptyDir").exists(), "Directory 'nonEmptyDir' should not be removed");
    }

}
