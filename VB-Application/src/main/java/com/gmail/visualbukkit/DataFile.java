package com.gmail.visualbukkit;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DataFile {

    private Path path;
    private JSONObject json;

    public DataFile(Path path) throws IOException {
        this.path = path;
        if (Files.exists(path)) {
            try {
                json = new JSONObject(Files.readString(path));
            } catch (JSONException e) {
                json = new JSONObject();
            }
        } else {
            json = new JSONObject();
        }
    }

    public void save() throws IOException {
        if (Files.notExists(path)) {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        }
        Files.writeString(path, json.toString(2));
    }

    public void clear() {
        json = new JSONObject();
    }

    public Path getPath() {
        return path;
    }

    public JSONObject getJson() {
        return json;
    }
}
