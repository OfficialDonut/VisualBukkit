package com.gmail.visualbukkit.ui;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.google.common.base.Throwables;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LogWindow extends Stage {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone(ZoneId.systemDefault());
    private final Handler handler;

    public LogWindow() {
        TextArea textArea = new TextArea();
        textArea.getStyleClass().add("log-window");
        textArea.setEditable(false);
        textArea.setWrapText(true);

        initOwner(VisualBukkitApp.getPrimaryStage());
        setScene(new Scene(textArea));
        setMaximized(false);
        setFullScreen(false);

        handler = new Handler() {
            @Override
            public void publish(LogRecord record) {
                if (isLoggable(record)) {
                    if (Platform.isFxApplicationThread()) {
                        if (record.getMessage().matches("\\[[A-Z]+].+")) {
                            textArea.appendText(String.format("[%s] %s\n", FORMATTER.format(record.getInstant()), record.getMessage()));
                        } else {
                            textArea.appendText(String.format("[%s] [%s] %s\n", FORMATTER.format(record.getInstant()), record.getLevel().toString(), record.getMessage()));
                        }
                        if (record.getThrown() != null) {
                            textArea.appendText(Throwables.getStackTraceAsString(record.getThrown()) + "\n");
                        }
                    } else {
                        Platform.runLater(() -> publish(record));
                    }
                }
            }
            @Override
            public void flush() {}
            @Override
            public void close() {}
        };

        handler.setLevel(Level.INFO);
    }

    public Handler getHandler() {
        return handler;
    }
}
