package cli.commands;

import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MakeDirectoryClassTest {

    private String initialDir;

    @BeforeEach
    void setUp() {
        // Store the initial working directory
        initialDir = System.getProperty("user.dir");
    }

    @AfterEach
    void tearDown() {
        // Delete any created directories
        deleteDirectoryIfExists("testDir");
        deleteDirectoryIfExists("anotherDir");
    }

    // delete a directory if it exists
    private void deleteDirectoryIfExists(String dirName) {
        File dir = new File(initialDir, dirName);
        if (dir.exists()) {
            dir.delete();
        }
    }

    @Test
    void testCreateSingleDirectory() {
        MakeDirectoryClass command = new MakeDirectoryClass();
        command.execute(new String[]{"mkdir", "testDir"});

        // Assert the directory was created
        File testDir = new File(initialDir, "testDir");
        assertTrue(testDir.exists() && testDir.isDirectory(), "Directory 'testDir' should exist");
    }

    @Test
    void testCreateMultipleDirectories() {
        MakeDirectoryClass command = new MakeDirectoryClass();
        command.execute(new String[]{"mkdir", "testDir", "anotherDir"});

        // Assert both directories were created
        assertTrue(new File(initialDir, "testDir").exists(), "Directory 'testDir' should exist");
        assertTrue(new File(initialDir, "anotherDir").exists(), "Directory 'anotherDir' should exist");
    }

    @Test
    void testCreateExistingDirectory() {
        // First create the directory
        File testDir = new File(initialDir, "testDir");
        assertTrue(testDir.mkdir(), "Failed to create directory 'testDir' before the test");

        MakeDirectoryClass command = new MakeDirectoryClass();
        command.execute(new String[]{"mkdir", "testDir"});

        // Assert the directory still exists
        assertTrue(testDir.exists(), "Directory 'testDir' should still exist");
    }

    @Test
    void testUsageWithMissingArguments() {
        // Execute the command with missing arguments
        MakeDirectoryClass command = new MakeDirectoryClass();
        command.execute(new String[]{"mkdir"});

        // Assert that no directories were created
        assertFalse(new File(initialDir, "mkdir").exists(), "No directory should be created");
    }
}