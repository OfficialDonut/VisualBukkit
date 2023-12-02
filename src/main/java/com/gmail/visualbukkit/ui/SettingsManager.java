package com.gmail.visualbukkit.ui;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.google.common.io.MoreFiles;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

public class SettingsManager {

    private static final StringProperty theme = new SimpleStringProperty();
    private static final IntegerProperty fontSize = new SimpleIntegerProperty();

    static {
        theme.addListener((observable, oldValue, newValue) -> {
            Application.setUserAgentStylesheet(newValue);
            VisualBukkitApp.getData().put("theme", newValue);
        });

        fontSize.addListener((observable, oldValue, newValue) -> {
            VisualBukkitApp.getPrimaryStage().getScene().getRoot().setStyle("-fx-font-size: " + newValue + ";");
            VisualBukkitApp.getData().put("font-size", newValue);
        });
    }

    public static Menu createThemesMenu() {
        Menu menu = new Menu(VisualBukkitApp.localizedText("menu.themes"));
        Map<String, String> themeMap = new TreeMap<>();
        themeMap.put("Dark", "css/dark.css");
        themeMap.put("Light", "css/light.css");
        try {
            Path themeDir = VisualBukkitApp.getDataDirectory().resolve("themes");
            Files.createDirectories(themeDir);
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(themeDir)) {
                for (Path path : stream) {
                    themeMap.put(MoreFiles.getNameWithoutExtension(path), path.toUri().toString());
                }
            }
        } catch (IOException e) {
            VisualBukkitApp.displayException(e);
        }
        ToggleGroup toggleGroup = new ToggleGroup();
        for (Map.Entry<String, String> entry : themeMap.entrySet()) {
            RadioMenuItem menuItem = new RadioMenuItem(entry.getKey());
            toggleGroup.getToggles().add(menuItem);
            menu.getItems().add(menuItem);
            menuItem.setOnAction(e -> theme.set(entry.getValue()));
            if (entry.getValue().equals(VisualBukkitApp.getData().optString("theme", "css/dark.css"))) {
                menuItem.setSelected(true);
                theme.set(entry.getValue());
            }
        }
        return menu;
    }

    public static Menu createFontSizeMenu() {
        Menu menu = new Menu(VisualBukkitApp.localizedText("menu.font_size"));
        ToggleGroup toggleGroup = new ToggleGroup();
        for (int size : new int[]{8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48}) {
            RadioMenuItem menuItem = new RadioMenuItem(String.valueOf(size));
            toggleGroup.getToggles().add(menuItem);
            menu.getItems().add(menuItem);
            menuItem.setOnAction(e -> fontSize.set(size));
            if (size == VisualBukkitApp.getData().optInt("font-size", 16)) {
                menuItem.setSelected(true);
                fontSize.set(size);
            }
        }
        return menu;
    }
}
