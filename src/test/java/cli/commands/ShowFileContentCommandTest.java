package cli.commands;
import cli.CommandLineInterpreter;
import org.junit.jupiter.api.*;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class ShowFileContentCommandTest {
    private ShowFileContentCommand catCommand;

    @BeforeEach
    public void setUp() {
        catCommand = new ShowFileContentCommand();
        CommandLineInterpreter.output="";
    }

    @Test
    public void testDisplayFileContents() throws IOException {
        String fileName = "testFile.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Hello, World!");
        }
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        catCommand.execute(new String[]{"cat",fileName});
        assertEquals("Hello, World!", CommandLineInterpreter.output);
        new File(fileName).delete();
    }
    @Test
    public void testDisplayFilesContents() throws IOException {
        String fileName = "testFile.txt";
        String file2Name = "test2File.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Hello, World!");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file2Name))) {
            writer.write("Hello2, World!");
        }
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        catCommand.execute(new String[]{"cat",fileName,file2Name});
        assertEquals("Hello, World!\nHello2, World!", CommandLineInterpreter.output);
        new File(fileName).delete();
        new File(file2Name).delete();
    }




    @AfterEach
    public void clear() {
        CommandLineInterpreter.output="";
        String[] tempFiles = {"testFile.txt", "file1.txt", "file2.txt", "combined.txt", "source.txt", "target.txt", "createdFile.txt"};
        for (String fileName : tempFiles) {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
        }
    }}
