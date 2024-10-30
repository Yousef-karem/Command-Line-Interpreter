package cli.commands;

import cli.CommandLineInterpreter;
import org.junit.jupiter.api .*;


import static org.junit.jupiter.api.Assertions.*;

class PrintWorkDirectoryCommandTest {

    // To store the output printed by the command

    @BeforeEach
    void setUp() {
        CommandLineInterpreter.output="";
    }

    @AfterEach
    void tearDown() {
        CommandLineInterpreter.output="";
    }

    @Test
    void testPrintCurrentWorkingDirectory() {
        PrintWorkDirectoryCommand command = new PrintWorkDirectoryCommand();
        command.execute(new String[]{});


        // Verify the output matches the current working directory
        assertEquals(CommandLineInterpreter.output, System.getProperty("user.dir"));
    }

}