package cli.commands;
import cli.CommandLineInterpreter;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ReplaceCommandTest {

    private ReplaceCommand replaceCommand;
    private final String testFileName = "testFile.txt";

    @BeforeEach
    void setUp() {
        CommandLineInterpreter.output="";
        replaceCommand = new ReplaceCommand();
    }

    @AfterEach
    void tearDown() throws IOException {
        // Clean up the test file if it exists
        File file = new File(System.getProperty("user.dir") + "\\" + testFileName);
        if (file.exists()) {
            Files.delete(file.toPath());
        }
        CommandLineInterpreter.output ="";
    }

    @Test
    void testExecuteWritesContentToFile() throws IOException {
        String expectedContent = "Hello, World!";
        replaceCommand.execute(expectedContent, testFileName);
        File file = new File(System.getProperty("user.dir") + "\\" + testFileName);
        assertTrue(file.exists(), "File should be created");

        // Verify the content of the file
        String actualContent = Files.readString(file.toPath());
        assertEquals(expectedContent, actualContent, "Content should match");
    }

    @Test
    void testExecuteOverwritesExistingFileContent() throws IOException {
        String initialContent ="";
        String newContent = "New Content";
        // Create a file with initial content
        Files.writeString(Path.of(testFileName), initialContent);

        replaceCommand.execute(newContent, testFileName);

        // Verify the content has been overwritten
        String CurrentContent = Files.readString(Path.of(testFileName));
        assertEquals(newContent, CurrentContent, "File content should be overwritten");
    }

    @Test
    void testExecuteThrowsIOExceptionForInvalidFileName() {
        String invalidFileName = "invalidDir/testFile.txt";  // Invalid path

        // Assert that an IOException is thrown
        assertThrows(IOException.class, () -> {
            replaceCommand.execute("Data", invalidFileName);
        });
    }
}
