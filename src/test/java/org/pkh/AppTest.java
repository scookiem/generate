package org.pkh;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple Generate.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void name() throws IOException {
        Path current = Paths.get(".");
        File currentFile = current.toFile();
        System.out.println(current.normalize() + ":" + currentFile.exists());
        Path parent = Paths.get("..");
        File parentFile = parent.toFile();
        System.out.print(parent.normalize() + ":" + parentFile.exists());
        Files.walk(parent, 2).forEach(c -> System.out.println(c.normalize()));
    }
}
