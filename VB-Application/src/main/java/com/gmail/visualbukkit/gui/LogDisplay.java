package com.gmail.visualbukkit.gui;

import com.gmail.visualbukkit.SettingsManager;
import com.gmail.visualbukkit.VisualBukkitApp;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class LogDisplay extends Stage {

    private TextArea textArea = new TextArea();
    private PrintStream printStream = new PrintStream(new LogOutputStream(), true);

    public LogDisplay() {
        textArea.getStyleClass().add("log-display");
        textArea.setEditable(false);
        SettingsManager.getInstance().bindStyle(textArea);
        initOwner(VisualBukkitApp.getInstance().getPrimaryStage());
        setTitle(VisualBukkitApp.getString("window.log"));
        setScene(new Scene(textArea, 1000, 600));
    }

    public void print(String string) {
        textArea.appendText(string);
    }

    public void writeToFile(Path path) throws IOException  {
        printStream.flush();
        if (Files.notExists(path)) {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        }
        Files.writeString(path, textArea.getText());
    }

    public PrintStream getPrintStream() {
        return printStream;
    }

    private class LogOutputStream extends OutputStream {

        private char[] buffer = new char[128];
        private byte index = 0;

        @Override
        public synchronized void write(int b) {
            buffer[index++] = (char) b;
            if (index < 0) {
                flush();
            }
        }

        @Override
        public synchronized void flush() {
            if (index != 0) {
                if (Platform.isFxApplicationThread()) {
                    print(new String(buffer));
                } else {
                    char[] bufferClone = buffer.clone();
                    Platform.runLater(() -> print(new String(bufferClone)));
                }
                buffer = new char[128];
                index = 0;
            }
        }

        @Override
        public void close() {
            flush();
        }
    }
}
