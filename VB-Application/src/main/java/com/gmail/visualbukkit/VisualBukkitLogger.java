package com.gmail.visualbukkit;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class VisualBukkitLogger extends Stage {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm:ss a");

    private TextArea textArea = new TextArea();
    private PrintStream printStream = new PrintStream(new LogOutputStream(), true);

    public VisualBukkitLogger() {
        setTitle("Log");
        setScene(new Scene(textArea, 1000, 600));
        initOwner(VisualBukkitApp.getStage());
        textArea.getStyleClass().add("log-display");
        textArea.setEditable(false);
    }

    public void print(String str) {
        try {
            if (Platform.isFxApplicationThread()) {
                if (textArea.getText().isEmpty() || textArea.getText().endsWith("\n")) {
                    textArea.appendText("[" + LocalTime.now().format(TIME_FORMATTER) + "]  ");
                }
                textArea.appendText(str);
            } else {
                Platform.runLater(() -> print(str));
            }
        } catch (Exception ignored) {}
    }

    public void writeToFile(Path path) throws IOException {
        printStream.flush();
        Files.writeString(path, textArea.getText());
    }

    public TextArea getTextArea() {
        return textArea;
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
                print(new String(buffer));
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
