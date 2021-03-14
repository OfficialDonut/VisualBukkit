package com.gmail.visualbukkit.gui;

import com.gmail.visualbukkit.SettingsManager;
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
            NotificationManager.displayException("Failed to load sound", e);
        }
    }

    public void play() {
        if (SettingsManager.getInstance().getSounds()) {
            audioClip.play();
        }
    }
}
