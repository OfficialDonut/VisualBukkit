package com.gmail.visualbukkit;

import javafx.application.Application;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.swing.*;

public class VisualBukkitLauncher {

    public static void main(String[] args) {
        try {
            Application.launch(VisualBukkitApp.class);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, ExceptionUtils.getStackTrace(e), "Failed to launch Visual Bukkit", JOptionPane.ERROR_MESSAGE);
        }
    }
}
