package test.agni.server.dataguard;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import agni.server.dataguard.FileDataGuard;

import java.util.Date;

public class FileDataGuardTest {

    private FileDataGuard fileDataGuard;

    @Before
    public void setup() {
        fileDataGuard = new FileDataGuard("agni_test", "test", "");
    }

    @Test
    public void isCachedPassTest() {
        assertTrue(fileDataGuard.isCached("test/path.txt"));
    }

    @Test
    public void isCachedFailTest() {
        assertFalse(fileDataGuard.isCached("badtest/path.txt"));
    }

    @Test
    public void fileUploadDateTimeTest() {
        Date expectedDate = new Date(1995, 10, 7, 12, 34, 56);
        assertEquals(fileDataGuard.fileUploadDateTime("test/path.txt"), expectedDate);
    }

    @Test
    public void fileSizeTest() {
        long expectedFileSize = 363462352;
        assertEquals(fileDataGuard.fileSize("test/path.txt"), expectedFileSize);
    }

    @Test
    public void fileOwnerTest(){
        String expectedUser = "TestUser";
        assertEquals(fileDataGuard.fileOwner("test/path.txt"), expectedUser);
    }

    @Test
    public void cacheFileTest(){
        String expectedPath = "billy/bob.txt";
        String expectedOwner = "BillyBob";
        long expectedFileSize = 2349824234L;
        
        assertFalse(fileDataGuard.isCached(expectedPath));
        
        fileDataGuard.cacheFile(expectedPath, expectedOwner, expectedFileSize);
        
        assertTrue(fileDataGuard.isCached(expectedPath));
        assertEquals(fileDataGuard.fileSize(expectedPath), expectedFileSize);
        assertEquals(fileDataGuard.fileOwner(expectedPath), expectedOwner);
    }
}
