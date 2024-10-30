package cli.commands;

import cli.CommandLineInterpreter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListFilesCommandTest {
    private ListFilesCommand listFilesCommand;

    @BeforeEach
    void setUp() {
        listFilesCommand = new ListFilesCommand();
        CommandLineInterpreter.output = "";
    }

    @Test
    void testExecuteValidCommand_ls() {
        listFilesCommand.execute(new String[]{"ls"});
        assertFalse(CommandLineInterpreter.output.isEmpty(), "Output should not be empty for 'ls' command");
    }

    @Test
    void testExecuteValidCommand_ls_a() {
        listFilesCommand.execute(new String[]{"ls", "-a"});
        assertFalse(CommandLineInterpreter.output.isEmpty(), "Output should not be empty for 'ls -a' command");
    }

    @Test
    void testExecuteValidCommand_ls_r() {
        listFilesCommand.execute(new String[]{"ls", "-r"});
        assertFalse(CommandLineInterpreter.output.isEmpty(), "Output should not be empty for 'ls -r' command");
    }

    @Test
    void testExecuteValidCommand_ls_ar() {
        listFilesCommand.execute(new String[]{"ls", "-ar"});
        assertFalse(CommandLineInterpreter.output.isEmpty(), "Output should not be empty for 'ls -ar' command");
    }

    @Test
    void testExecuteValidCommand_ls_a_r() {
        listFilesCommand.execute(new String[]{"ls", "-a", "-r"});
        assertFalse(CommandLineInterpreter.output.isEmpty(), "Output should not be empty for 'ls -a -r' command");
    }

    @Test
    void testExecuteValidCommand_ls_ara_r() {
        listFilesCommand.execute(new String[]{"ls", "-ara", "-r"});
        assertFalse(CommandLineInterpreter.output.isEmpty(), "Output should not be empty for 'ls -ara -r' command");
    }

    @Test
    void testExecuteInvalidCommand_ls_dash_dash_r() {
        listFilesCommand.execute(new String[]{"ls", "--r"});
        assertTrue(CommandLineInterpreter.output.isEmpty(), "Output should be empty for invalid 'ls --r' command");
    }

    @Test
    void testExecuteInvalidCommand_ls_ars() {
        listFilesCommand.execute(new String[]{"ls", "-ars"});
        assertTrue(CommandLineInterpreter.output.isEmpty(), "Output should be empty for invalid 'ls -ars' command");
    }

    @Test
    void testExecuteInvalidCommand_ls_a_dash_r() {
        listFilesCommand.execute(new String[]{"ls", "-a-r"});
        assertTrue(CommandLineInterpreter.output.isEmpty(), "Output should be empty for invalid 'ls -a-r' command");
    }
}