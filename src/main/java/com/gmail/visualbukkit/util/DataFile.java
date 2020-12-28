package com.gmail.visualbukkit.util;

import com.gmail.visualbukkit.gui.NotificationManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class DataFile {

    private Path path;
    private JSONObject json;

    public DataFile(Path path) {
        this.path = path;
        try {
            if (Files.exists(path)) {
                try {
                    json = new JSONObject(String.join("\n", Files.readAllLines(path, StandardCharsets.UTF_8)));
                } catch (JSONException e) {
                    json = new JSONObject();
                }
            } else {
                json = new JSONObject();
                Files.createDirectories(path.getParent());
                Files.createFile(path);
            }
        } catch (IOException e) {
            NotificationManager.displayException("Failed to load data file", e);
        }
    }

    public void save() throws IOException {
        Files.write(path, json.toString(2).getBytes(StandardCharsets.UTF_8));
    }

    public void clear() {
        json = new JSONObject();
    }

    public void setJson(JSONObject json) {
        this.json = json;
    }

    public Path getPath() {
        return path;
    }

    public JSONObject getJson() {
        return json;
    }
}
