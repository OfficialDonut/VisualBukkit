package com.gmail.visualbukkit.ui;

import com.gmail.visualbukkit.VisualBukkitApp;
import javafx.scene.media.AudioClip;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public enum SoundManager {

    SNAP, WHOOSH;

    private AudioClip audioClip;

    SoundManager() {
        try {
            audioClip = new AudioClip(SoundManager.class.getResource("/sounds/" + name().toLowerCase() + ".wav").toURI().toURL().toString());
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (VisualBukkitApp.getSettingsManager().getSounds()) {
            audioClip.play();
        }
    }
}
