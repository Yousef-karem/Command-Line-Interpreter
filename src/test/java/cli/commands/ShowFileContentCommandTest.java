package cli.commands;
import org.junit.jupiter.api.*;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class ShowFileContentCommandTest {
    private ShowFileContentCommand catCommand;

    @BeforeEach
    public void setUp() {
        catCommand = new ShowFileContentCommand();
    }

    @Test
    public void testDisplayFileContents() throws IOException {
        String fileName = "testFile.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Hello, World!");
        }

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        catCommand.execute(new String[]{fileName});
        assertEquals("Hello, World!", outContent.toString().trim());
        new File(fileName).delete();
    }

    @Test
    public void testConcatenateFiles() throws IOException {
        String file1 = "file1.txt";
        String file2 = "file2.txt";
        String combinedFile = "combined.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file1))) {
            writer.write("Hello from file 1.");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file2))) {
            writer.write("Hello from file 2.");
        }

        catCommand.execute(new String[]{file1, file2, ">", combinedFile});

        // read the content of combined file
        String content = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(combinedFile)));

        content = content.replace("\r\n", "\n");
        String expectedContent = "Hello from file 1.\nHello from file 2.\n";
        expectedContent = expectedContent.replace("\r\n", "\n");

        assertEquals(expectedContent, content);
        new File(file1).delete();
        new File(file2).delete();
        new File(combinedFile).delete();
    }


    @Test
    public void testAppendToFile() throws IOException {
        String file1 = "source.txt";
        String file2 = "target.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file1))) {
            writer.write("This is source.");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file2))) {
            writer.write("Existing content.");
        }

        catCommand.execute(new String[]{file1, ">>", file2});
        String content = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(file2)));
        content = content.replace("\r\n", "\n");
        String expectedContent = "Existing content.\nThis is source.\n";
        expectedContent = expectedContent.replace("\r\n", "\n");

        assertEquals(expectedContent, content);

        new File(file1).delete();
        new File(file2).delete();
    }




    @Test
    public void testCreateFileUsingRedirect(){
        String newFile = "createdFile.txt";
        catCommand.execute(new String[]{"cat", ">", newFile});
        assertTrue(new File(newFile).exists());
        new File(newFile).delete();
    }


    @AfterEach
    public void clear() {
        String[] tempFiles = {"testFile.txt", "file1.txt", "file2.txt", "combined.txt", "source.txt", "target.txt", "createdFile.txt"};
        for (String fileName : tempFiles) {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
        }
    }}
