package cn.cs.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FilesTest {

    public static void main(String[] args) {
        Path dir = Paths.get("C:\\study\\workspace-openjdk");
        if (Files.isDirectory(dir)) {
            try (Stream<Path> stream = Files.list(dir)) {
                stream.filter(path -> path.toString().endsWith("*.java"))
                        .map(path -> path.getFileName().toString()).forEach(System.out::println);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
