package com.gmail.visualbukkit;

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
import java.util.Map;
import java.util.TreeMap;

public class SettingsManager extends Menu {

    private static final SettingsManager instance = new SettingsManager();

    private final Map<String, String> themes = new TreeMap<>();
    private final StringProperty theme = new SimpleStringProperty();
    private final IntegerProperty fontSize = new SimpleIntegerProperty();
    private final BooleanProperty sounds = new SimpleBooleanProperty();
    private final IntegerProperty autosaveTime = new SimpleIntegerProperty();

    private SettingsManager() {
        super(VisualBukkitApp.getString("menu.settings"));
    }

    @SuppressWarnings("UnstableApiUsage")
    public void loadSettings(DataFile dataFile) throws IOException {
        Menu themeMenu = new Menu(VisualBukkitApp.getString("menu.theme"));
        Menu fontSizeMenu = new Menu(VisualBukkitApp.getString("menu.font_size"));
        Menu soundsMenu = new Menu(VisualBukkitApp.getString("menu.sounds"));
        Menu autosaveTimeMenu = new Menu(VisualBukkitApp.getString("menu.autosave"));
        getItems().setAll(themeMenu, fontSizeMenu, soundsMenu, autosaveTimeMenu);

        JSONObject json = dataFile.getJson();
        String selectedTheme = json.optString("settings.theme", "Dark");
        int selectedFontSize = json.optInt("settings.font-size", 14);
        int selectedAutosaveTime = json.optInt("settings.autosave", -1);

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

        Menu selectThemeMenu = new Menu(VisualBukkitApp.getString("menu.select_theme"));
        MenuItem openThemeDirItem = new MenuItem(VisualBukkitApp.getString("menu_item.theme_directory"));
        openThemeDirItem.setOnAction(e -> VisualBukkitApp.getInstance().openDirectory(themeDir));
        themeMenu.getItems().addAll(selectThemeMenu, openThemeDirItem);

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

        ToggleGroup soundsGroup = new ToggleGroup();
        RadioMenuItem enableSoundsItem = new RadioMenuItem(VisualBukkitApp.getString("menu_item.enable_sounds"));
        RadioMenuItem disableSoundsItem = new RadioMenuItem(VisualBukkitApp.getString("menu_item.disable_sounds"));
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
            RadioMenuItem timeItem = new RadioMenuItem(i == -1 ?
                    VisualBukkitApp.getString("menu_item.autosave_never") :
                    String.format(VisualBukkitApp.getString("menu_item.autosave_time"), i));
            timeItem.setOnAction(e -> autosaveTime.set(i));
            autosaveTimeGroup.getToggles().add(timeItem);
            autosaveTimeMenu.getItems().add(timeItem);
            if (getAutosaveTime() == 0 || i == selectedAutosaveTime) {
                autosaveTime.set(i);
                timeItem.setSelected(true);
            }
        }
    }

    public void saveSettings(DataFile dataFile) {
        JSONObject data = dataFile.getJson();
        data.put("settings.theme", getTheme());
        data.put("settings.font-size", getFontSize());
        data.put("settings.sounds", getSounds());
        data.put("settings.autosave", getAutosaveTime());
    }

    public void bindStyle(Parent parent) {
        theme.addListener((o, oldValue, newTheme) -> parent.getStylesheets().setAll("/themes/Base.css", themes.get(newTheme)));
        parent.getStylesheets().setAll("/themes/Base.css", themes.get(getTheme()));
        parent.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize));
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
}
