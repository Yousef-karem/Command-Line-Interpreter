package cli.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
class MoveCommandTest {
    private MoveCommand mvCommand;
    @BeforeEach
    public  void setup(){
        mvCommand=new MoveCommand();
    }
    @Test
    public void testMvfile() throws IOException {
        String source="Sourcefile.txt",  destinationDir = "destinationDir";
        new File (source).createNewFile();
        new File(destinationDir).mkdir();
        mvCommand.execute(new String[]{source, destinationDir});
        assertFalse(new File(source).exists());
        assertTrue(new File(destinationDir, source).exists());
        new File(destinationDir, source).delete();
        new File(destinationDir).delete();
    }
    @Test
    public void testRename()throws IOException {
        String firstname="firstname.txt", newname="newname.txt";
        new File(firstname).createNewFile();
        mvCommand.execute(new String[]{firstname,newname});
        assertFalse(new File(firstname).exists());
        assertTrue(new  File(newname).exists());
        new File(newname).delete();}

    @Test
    public void testmvfiles() throws IOException{
        String source1="Sourcefile1.txt",source2="Sourcefile2.txt",  destinationDir = "destinationDir";
        new File(destinationDir).mkdir();
        new File(source1).createNewFile();
        new File(source2).createNewFile();
        mvCommand.execute(new String[]{source1,source2,destinationDir});
        assertFalse(new File(source1).exists());
        assertFalse(new File(source2).exists());
        assertTrue(new File(destinationDir, source1).exists());
        assertTrue(new File(destinationDir, source2).exists());
        new File(destinationDir, source1).delete();
        new File(destinationDir, source2).delete();
        new File(destinationDir).delete();
    }
    @AfterEach
    public void clear() {
        new File("Sourcefile.txt").delete();
        new File("destinationDir").delete();
        new File("firstname.txt").delete();
        new File("newname.txt").delete();
        new File("Sourcefile1.txt").delete();
        new File("Sourcefile2.txt").delete();
    }
}