package com.gmail.visualbukkit;

import com.google.common.base.Throwables;
import javafx.application.Application;

import javax.swing.*;

public class VisualBukkitLauncher {

    public static void main(String[] args) {
        try {
            System.setProperty("javafx.preloader", "com.gmail.visualbukkit.VisualBukkitPreloader");
            Application.launch(VisualBukkitApp.class);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, Throwables.getStackTraceAsString(e), "Failed to launch Visual Bukkit", JOptionPane.ERROR_MESSAGE);
        }
    }
}
