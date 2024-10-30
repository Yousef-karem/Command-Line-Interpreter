package cli.commands;

import cli.CommandLineInterpreter;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class AppendCommandTest {

    private AppendCommand appendCommand;
    private final String testFileName = "testFile.txt";

    @BeforeEach
    void setUp() {
        CommandLineInterpreter.output="";
        appendCommand = new AppendCommand();
    }

    @AfterEach
    void tearDown() throws IOException {
        CommandLineInterpreter.output="";
        // Delete the test file if it exists
        File file = new File(System.getProperty("user.dir") + "\\" + testFileName);
        if (file.exists()) {
            Files.delete(file.toPath());
        }
    }

    @Test
    void testExecuteCreatesNewFileWithContent() throws IOException {
        String data = "Hello, World!";

        // Execute the append command
        appendCommand.execute(data, testFileName);

        // Verify the file exists
        File file = new File(System.getProperty("user.dir") + "\\" + testFileName);
        assertTrue(file.exists(), "File should be created");

        // Verify the content is written to the file
        String actualContent = Files.readString(file.toPath());
        assertEquals(data, actualContent, "File content should match the input data");
    }

    @Test
    void testExecuteAppendsContentToExistingFile() throws IOException {
        String initialData = "First line.\n";
        String newData = "Second line.\n";

        // Create a file with initial content
        Files.writeString(Path.of(testFileName), initialData);

        // Execute the append command with new content
        appendCommand.execute(newData, testFileName);

        // Verify the content has been appended
        String expectedContent = initialData + newData;
        String actualContent = Files.readString(Path.of(testFileName));
        assertEquals(expectedContent, actualContent, "File content should be appended correctly");
    }

    @Test
    void testExecuteThrowsIOExceptionForInvalidFileName() {
        String invalidFileName = "invalidDir/testFile.txt";  // Invalid path

        // Assert that an IOException is thrown
        assertThrows(IOException.class, () -> {
            appendCommand.execute("Some data", invalidFileName);
        });
    }
}
