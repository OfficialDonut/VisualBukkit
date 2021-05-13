package com.gmail.visualbukkit;

import com.gmail.visualbukkit.gui.NotificationManager;
import com.google.common.io.MoreFiles;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class SettingsManager extends Menu {

    private static final SettingsManager instance = new SettingsManager();

    private Menu themeMenu = new Menu("Theme");
    private Map<String, String> themes = new TreeMap<>();
    private final StringProperty theme = new SimpleStringProperty();
    private final IntegerProperty fontSize = new SimpleIntegerProperty();
    private final BooleanProperty sounds = new SimpleBooleanProperty();
    private final IntegerProperty autosaveTime = new SimpleIntegerProperty();
    private final StringProperty lang = new SimpleStringProperty();

    private SettingsManager() {
        super("Settings");
    }

    public void loadSettings(DataFile dataFile) throws IOException {
        Menu fontSizeMenu = new Menu("Font Size");
        Menu soundsMenu = new Menu("Sounds");
        Menu autosaveTimeMenu = new Menu("Auto-save");
        Menu langMenu = new Menu("Language");
        getItems().setAll(themeMenu, fontSizeMenu, soundsMenu, autosaveTimeMenu, langMenu);

        JSONObject json = dataFile.getJson();
        String selectedLang = json.optString("settings.lang", "System Default");
        int selectedFontSize = json.optInt("settings.font-size", 14);
        int selectedAutosaveTime = json.optInt("settings.autosave", -1);
        reloadThemes(json.optString("settings.theme", "Dark"));

        ToggleGroup soundsGroup = new ToggleGroup();
        RadioMenuItem enableSoundsItem = new RadioMenuItem("Enable");
        RadioMenuItem disableSoundsItem = new RadioMenuItem("Disable");
        enableSoundsItem.setOnAction(e -> sounds.set(true));
        disableSoundsItem.setOnAction(e -> sounds.set(false));
        for (RadioMenuItem soundItem : new RadioMenuItem[]{enableSoundsItem, disableSoundsItem}) {
            soundsGroup.getToggles().add(soundItem);
            soundsMenu.getItems().add(soundItem);
        }
        sounds.set(json.optBoolean("settings.sounds", true));
        (getSounds() ? enableSoundsItem : disableSoundsItem).setSelected(true);

        ToggleGroup fontSizeGroup = new ToggleGroup();
        for (int i : new int[]{8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36}) {
            RadioMenuItem fontSizeItem = new RadioMenuItem(String.valueOf(i));
            fontSizeItem.setOnAction(e -> fontSize.set(i));
            fontSizeGroup.getToggles().add(fontSizeItem);
            fontSizeMenu.getItems().add(fontSizeItem);
            if (getFontSize() == 0 || i == selectedFontSize) {
                fontSize.set(i);
                fontSizeItem.setSelected(true);
            }
        }

        ToggleGroup autosaveTimeGroup = new ToggleGroup();
        for (int i : new int[]{5, 10, 15, 20, 25, 30, -1}) {
            RadioMenuItem timeItem = new RadioMenuItem(i == -1 ? "Never" : (i + " minutes"));
            timeItem.setOnAction(e -> autosaveTime.set(i));
            autosaveTimeGroup.getToggles().add(timeItem);
            autosaveTimeMenu.getItems().add(timeItem);
            if (getAutosaveTime() == 0 || i == selectedAutosaveTime) {
                autosaveTime.set(i);
                timeItem.setSelected(true);
            }
        }

        ToggleGroup langGroup = new ToggleGroup();
        for (String s : new String[]{"System Default", "en-US","zh-CN"}) {
            RadioMenuItem langItem = new RadioMenuItem(s);
            langItem.setOnAction(e -> {
                lang.set(s);
                NotificationManager.displayMessage(VisualBukkitApp.getString("message.language_change.title"), VisualBukkitApp.getString("message.language_change.content"));
            });
            langGroup.getToggles().add(langItem);
            langMenu.getItems().add(langItem);
            if (getLang() == null || s.equals(selectedLang)) {
                lang.set(s);
                langItem.setSelected(true);
                if (!s.equals("System Default")) {
                    Locale.setDefault(Locale.forLanguageTag(s));
                }
            }
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    private void reloadThemes(String selectedTheme) throws IOException {
        theme.set(null);
        themes.clear();
        themeMenu.getItems().clear();

        themes.put("Dark", "/themes/Dark.css");
        themes.put("Light", "/themes/Light.css");

        Path themeDir = VisualBukkitApp.getInstance().getDataDir().resolve("Themes");
        if (Files.exists(themeDir)) {
            try (DirectoryStream<Path> pathStream = Files.newDirectoryStream(themeDir, p -> p.toString().endsWith(".css"))) {
                for (Path path : pathStream) {
                    themes.put(MoreFiles.getNameWithoutExtension(path), path.toUri().toString());
                }
            }
        }

        Menu selectThemeMenu = new Menu("Select");
        MenuItem openThemeDirItem = new MenuItem("Open Folder");
        MenuItem reloadThemesItem = new MenuItem("Reload");
        openThemeDirItem.setOnAction(e -> VisualBukkitApp.getInstance().openDirectory(themeDir));
        reloadThemesItem.setOnAction(e -> {
            try {
                reloadThemes(getTheme());
            } catch (Exception ex) {
                NotificationManager.displayException("Failed to reload themes", ex);
            }
        });
        themeMenu.getItems().addAll(selectThemeMenu, openThemeDirItem, reloadThemesItem);

        ToggleGroup themeGroup = new ToggleGroup();
        for (String s : themes.keySet()) {
            RadioMenuItem themeItem = new RadioMenuItem(s);
            themeItem.setOnAction(e -> theme.set(s));
            themeGroup.getToggles().add(themeItem);
            selectThemeMenu.getItems().add(themeItem);
            if (getTheme() == null || s.equals(selectedTheme)) {
                theme.set(s);
                themeItem.setSelected(true);
            }
        }
    }

    public void bindStyle(Parent parent) {
        parent.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize));
        theme.addListener((o, oldValue, newTheme) -> {
            parent.getStylesheets().clear();
            parent.getStylesheets().add("/themes/Base.css");
            if (newTheme != null && themes.containsKey(newTheme)) {
                parent.getStylesheets().add(themes.get(newTheme));
            }
        });
        parent.getStylesheets().clear();
        parent.getStylesheets().add("/themes/Base.css");
        if (getTheme() != null && themes.containsKey(getTheme())) {
            parent.getStylesheets().add(themes.get(getTheme()));
        }
    }

    public void saveSettings(DataFile dataFile) {
        JSONObject data = dataFile.getJson();
        data.put("settings.theme", getTheme());
        data.put("settings.font-size", getFontSize());
        data.put("settings.sounds", getSounds());
        data.put("settings.autosave", getAutosaveTime());
        data.put("settings.lang", getLang());
    }

    public static SettingsManager getInstance() {
        return instance;
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

    public String getLang() {
        return lang.get();
    }

    public ReadOnlyStringProperty themeProperty() {
        return theme;
    }

    public ReadOnlyIntegerProperty fontSizeProperty() {
        return fontSize;
    }

    public ReadOnlyBooleanProperty soundsProperty() {
        return sounds;
    }

    public ReadOnlyIntegerProperty autosaveTimeProperty() {
        return autosaveTime;
    }

    public StringProperty langProperty() {
        return lang;
    }
}
