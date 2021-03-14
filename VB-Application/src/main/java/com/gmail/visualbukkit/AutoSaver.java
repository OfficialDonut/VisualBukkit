package com.gmail.visualbukkit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;

public class AutoSaver {

    private static final AutoSaver instance = new AutoSaver();
    private Timeline timeline;

    private AutoSaver() {
        SettingsManager.getInstance().autosaveTimeProperty().addListener((o, oldValue, newValue) -> setTime(newValue != null ? newValue.intValue() : -1));
    }

    public void setTime(int minutes) {
        if (timeline != null) {
            timeline.stop();
        }
        if (minutes > 0) {
            timeline = new Timeline(new KeyFrame(Duration.minutes(minutes), e -> {
                try {
                    VisualBukkitApp.getInstance().save();
                } catch (IOException ignored) {}
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        } else {
            timeline = null;
        }
    }

    public static AutoSaver getInstance() {
        return instance;
    }
}
