package cli.commands;

import org.junit.jupiter.api .*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PrintWorkDirectoryCommandTest {
    private final PrintStream originalOut = System.out;

    // To store the output printed by the command
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        // Restore the original System.out
        System.setOut(originalOut);
    }

    @Test
    void testPrintCurrentWorkingDirectory() {
        PrintWorkDirectoryCommand command = new PrintWorkDirectoryCommand();
        command.execute(new String[]{});

        String printedOutput = outputStream.toString().trim();

        // Verify the output matches the current working directory
        assertEquals(System.getProperty("user.dir"), printedOutput);
    }

}