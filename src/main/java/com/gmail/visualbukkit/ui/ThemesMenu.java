package com.gmail.visualbukkit.ui;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.google.common.io.MoreFiles;
import javafx.application.Application;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

public class ThemesMenu extends Menu {

    public ThemesMenu() {
        super(VisualBukkitApp.localizedText("menu.themes"));
        try {
            Map<String, String> themeMap = new TreeMap<>();
            themeMap.put("Dark", "css/dark.css");
            themeMap.put("Light", "css/light.css");
            Path themeDir = VisualBukkitApp.getDataDirectory().resolve("themes");
            Files.createDirectories(themeDir);
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(themeDir)) {
                for (Path path : stream) {
                    themeMap.put(MoreFiles.getNameWithoutExtension(path), path.toUri().toString());
                }
            }
            ToggleGroup toggleGroup = new ToggleGroup();
            for (Map.Entry<String, String> entry : themeMap.entrySet()) {
                RadioMenuItem menuItem = new RadioMenuItem(entry.getKey());
                toggleGroup.getToggles().add(menuItem);
                getItems().add(menuItem);
                menuItem.setOnAction(e -> {
                    Application.setUserAgentStylesheet(entry.getValue());
                    VisualBukkitApp.getData().put("theme", entry.getValue());
                });
                if (toggleGroup.getSelectedToggle() == null || entry.getValue().equals(VisualBukkitApp.getData().optString("theme"))) {
                    Application.setUserAgentStylesheet(entry.getValue());
                    menuItem.setSelected(true);
                }
            }
        } catch (IOException e) {
            VisualBukkitApp.displayException(e);
        }
    }
}
