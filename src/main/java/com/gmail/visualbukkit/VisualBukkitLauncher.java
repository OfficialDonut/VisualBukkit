package com.gmail.visualbukkit;

import com.sun.javafx.application.LauncherImpl;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.swing.*;

public class VisualBukkitLauncher {

    public static void main(String[] args) {
        try {
            LauncherImpl.launchApplication(VisualBukkit.class, SplashScreenLoader.class, new String[0]);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, ExceptionUtils.getStackTrace(e), "Failed to launch Visual Bukkit", JOptionPane.ERROR_MESSAGE);
        }
    }
}
