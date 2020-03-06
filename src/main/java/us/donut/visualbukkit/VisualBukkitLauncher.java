package us.donut.visualbukkit;

import com.sun.javafx.application.LauncherImpl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VisualBukkitLauncher {

    public static void main(String[] args) {
        String javaVersion = System.getProperty("java.version");

        if (javaVersion.startsWith("1.8")) {
            LauncherImpl.launchApplication(VisualBukkit.class, SplashScreenLoader.class, new String[0]);
            return;
        }

        Path dir = Paths.get(System.getProperty("java.home"));
        while (!dir.getFileName().toString().equalsIgnoreCase("Java")) {
            dir = dir.getParent();
        }

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(dir)) {
            for (Path path : directoryStream) {
                if (path.getFileName().toString().contains("1.8")) {
                    Path bin = path.resolve("bin");
                    Path javaExe = bin.resolve("java.exe");
                    if (Files.notExists(javaExe)) {
                        javaExe = path.resolve("java");
                    }
                    String jarPath = VisualBukkitLauncher.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
                    if (jarPath.charAt(0) == '/') {
                        jarPath = jarPath.replaceFirst("/", "");
                    }
                    Runtime.getRuntime().exec("\"" + javaExe.toString() + "\" -jar \"" + jarPath + "\"");
                    break;
                }
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
