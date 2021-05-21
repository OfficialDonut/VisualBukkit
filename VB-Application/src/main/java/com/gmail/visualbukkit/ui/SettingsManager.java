package com.gmail.visualbukkit.ui;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.google.common.io.MoreFiles;
import javafx.beans.property.*;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SettingsManager {

    private final static List<String> LANGUAGES = List.of("System Default", "en-US", "zh-CN");
    private final static List<Integer> FONT_SIZES = List.of(8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48);
    private final static List<Integer> AUTOSAVE_TIMES = List.of(5, 10, 15, 20, 25, 30, -1);

    private StringProperty language = new SimpleStringProperty();
    private StringProperty theme = new SimpleStringProperty();
    private IntegerProperty fontSize = new SimpleIntegerProperty();
    private BooleanProperty sounds = new SimpleBooleanProperty();
    private IntegerProperty autosaveTime = new SimpleIntegerProperty();
    private Menu themeMenu;

    public SettingsManager() {
        language.addListener((o, oldValue, newValue) -> VisualBukkitApp.getData().put("settings.lang", newValue));
        theme.addListener((o, oldValue, newValue) -> VisualBukkitApp.getData().put("settings.theme", newValue));
        fontSize.addListener((o, oldValue, newValue) -> VisualBukkitApp.getData().put("settings.font-size", newValue));
        sounds.addListener((o, oldValue, newValue) -> VisualBukkitApp.getData().put("settings.sounds", newValue));
        autosaveTime.addListener((o, oldValue, newValue) -> {
            VisualBukkitApp.getData().put("settings.autosave", newValue);
            VisualBukkitApp.setAutosaveTime(newValue != null ? newValue.intValue() : -1);
        });

        String lang = VisualBukkitApp.getData().optString("settings.lang");
        language.set(LANGUAGES.contains(lang) ? lang : "System Default");
        if (!language.get().equals("System Default")) {
            Locale.setDefault(Locale.forLanguageTag(lang));
        }

        int size = VisualBukkitApp.getData().optInt("settings.font-size");
        fontSize.set(FONT_SIZES.contains(size) ? size : 14);

        int time = VisualBukkitApp.getData().optInt("settings.autosave");
        autosaveTime.set(AUTOSAVE_TIMES.contains(time) ? time : -1);

        sounds.set(VisualBukkitApp.getData().optBoolean("settings.sounds", true));
    }

    public Menu createMenu() {
        Menu fontSizeMenu = new Menu(LanguageManager.get("menu.font_size"));
        Menu soundsMenu = new Menu(LanguageManager.get("menu.sounds"));
        Menu autosaveTimeMenu = new Menu(LanguageManager.get("menu.autosave"));
        Menu langMenu = new Menu(LanguageManager.get("menu.language"));

        themeMenu = new Menu(LanguageManager.get("menu.theme"));
        reloadThemes();

        ToggleGroup soundsGroup = new ToggleGroup();
        RadioMenuItem enableSoundsItem = new RadioMenuItem(LanguageManager.get("menu_item.enable_sounds"));
        RadioMenuItem disableSoundsItem = new RadioMenuItem(LanguageManager.get("menu_item.disable_sounds"));
        enableSoundsItem.setOnAction(e -> sounds.set(true));
        disableSoundsItem.setOnAction(e -> sounds.set(false));
        (getSounds() ? enableSoundsItem : disableSoundsItem).setSelected(true);
        for (RadioMenuItem soundItem : new RadioMenuItem[]{enableSoundsItem, disableSoundsItem}) {
            soundsGroup.getToggles().add(soundItem);
            soundsMenu.getItems().add(soundItem);
        }

        ToggleGroup fontSizeGroup = new ToggleGroup();
        for (int i : FONT_SIZES) {
            RadioMenuItem fontSizeItem = new RadioMenuItem(String.valueOf(i));
            fontSizeItem.setOnAction(e -> fontSize.set(i));
            fontSizeGroup.getToggles().add(fontSizeItem);
            fontSizeMenu.getItems().add(fontSizeItem);
            if (i == getFontSize()) {
                fontSizeItem.setSelected(true);
            }
        }

        ToggleGroup autosaveTimeGroup = new ToggleGroup();
        for (int i : AUTOSAVE_TIMES) {
            RadioMenuItem timeItem = new RadioMenuItem(i == -1 ? "Never" : (i + " minutes"));
            timeItem.setOnAction(e -> autosaveTime.set(i));
            autosaveTimeGroup.getToggles().add(timeItem);
            autosaveTimeMenu.getItems().add(timeItem);
            if (i == getAutosaveTime()) {
                timeItem.setSelected(true);
            }
        }

        ToggleGroup langGroup = new ToggleGroup();
        for (String s : LANGUAGES) {
            RadioMenuItem langItem = new RadioMenuItem(s);
            langItem.setOnAction(e -> {
                language.set(s);
                NotificationManager.displayMessage(LanguageManager.get("message.language_change.title"), LanguageManager.get("message.language_change.content"));
            });
            langGroup.getToggles().add(langItem);
            langMenu.getItems().add(langItem);
            if (s.equals(language.get())) {
                langItem.setSelected(true);
            }
        }

        return new Menu(LanguageManager.get("menu.settings"), null, themeMenu, fontSizeMenu, soundsMenu, autosaveTimeMenu, langMenu);
    }

    @SuppressWarnings("UnstableApiUsage")
    public void reloadThemes() {
        Map<String, String> themeMap = new HashMap<>();
        themeMap.put("/themes/Dark.css", "Dark");
        themeMap.put("/themes/Light.css", "Light");

        Path themeDir = VisualBukkitApp.getDataDir().resolve("Themes");
        if (Files.exists(themeDir)) {
            try (DirectoryStream<Path> pathStream = Files.newDirectoryStream(themeDir, p -> p.toString().endsWith(".css"))) {
                for (Path path : pathStream) {
                    themeMap.put(MoreFiles.getNameWithoutExtension(path), path.toUri().toString());
                }
            } catch (IOException ignored) {}
        }

        String selectedTheme = VisualBukkitApp.getData().optString("settings.theme");
        theme.set(themeMap.containsKey(selectedTheme) ? selectedTheme : "/themes/Dark.css");

        Menu selectThemeMenu = new Menu(LanguageManager.get("menu.select_theme"));
        themeMenu.getItems().setAll(selectThemeMenu,
                new ActionMenuItem(LanguageManager.get("menu_item.open_theme_dir"), e -> VisualBukkitApp.openDirectory(themeDir)),
                new ActionMenuItem(LanguageManager.get("menu_item.reload_themes"), e -> reloadThemes()));

        ToggleGroup themeGroup = new ToggleGroup();
        for (Map.Entry<String, String> entry : themeMap.entrySet()) {
            RadioMenuItem themeItem = new RadioMenuItem(entry.getValue());
            themeItem.setOnAction(e -> theme.set(entry.getKey()));
            themeGroup.getToggles().add(themeItem);
            selectThemeMenu.getItems().add(themeItem);
            if (entry.getKey().equals(getTheme())) {
                themeItem.setSelected(true);
            }
        }
    }

    public void bindStyle(Parent parent) {
        theme.addListener((o, oldValue, newValue) -> style(parent));
        fontSize.addListener((o, oldValue, newValue) -> style(parent));
        style(parent);
    }

    public void style(Parent parent) {
        parent.setStyle("-fx-font-size: " + getFontSize() + ";");
        parent.getStylesheets().clear();
        parent.getStylesheets().add("/themes/Base.css");
        String theme = getTheme();
        if (theme != null) {
            parent.getStylesheets().add(theme);
        }
    }

    public String getTheme() {
        return theme.get();
    }

    public int getFontSize() {
        return fontSize.get();
    }

    public boolean getSounds() {
        return sounds.get();
    }

    public int getAutosaveTime() {
        return autosaveTime.get();
    }
}
