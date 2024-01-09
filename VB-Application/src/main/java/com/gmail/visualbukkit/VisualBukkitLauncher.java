package com.gmail.visualbukkit;

import com.google.common.base.Throwables;
import javafx.application.Application;

import javax.swing.*;

public class VisualBukkitLauncher {

    public static void main(String[] args) {
        try {
            Application.launch(VisualBukkitApp.class);
        } catch (Throwable e) {
            JOptionPane.showMessageDialog(null, Throwables.getStackTraceAsString(e), "Failed to launch Visual Bukkit", JOptionPane.ERROR_MESSAGE);
        }
    }
}
