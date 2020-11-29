package com.gmail.visualbukkit.util;

import com.gmail.visualbukkit.VisualBukkit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class AutoSaver {

    private Timeline timeline;

    public void setTime(double minutes) {
        if (timeline != null) {
            timeline.stop();
        }
        if (minutes > 0) {
            timeline = new Timeline(new KeyFrame(Duration.minutes(minutes), e -> VisualBukkit.getInstance().save(false)));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        } else {
            timeline = null;
        }
    }
}
