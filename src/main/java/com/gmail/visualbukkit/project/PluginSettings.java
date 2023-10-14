package com.gmail.visualbukkit.project;

import org.json.JSONObject;

public class PluginSettings {

    private String name;
    private String version;
    private String authors;
    private String description;
    private String website;
    private String dependencies;
    private String softDepend;
    private String loadBefore;
    private String permissions;
    private String prefix;

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setDependencies(String dependencies) {
        this.dependencies = dependencies;
    }

    public void setSoftDepend(String softDepend) {
        this.softDepend = softDepend;
    }

    public void setLoadBefore(String loadBefore) {
        this.loadBefore = loadBefore;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getAuthors() {
        return authors;
    }

    public String getDescription() {
        return description;
    }

    public String getWebsite() {
        return website;
    }

    public String getDependencies() {
        return dependencies;
    }

    public String getSoftDepend() {
        return softDepend;
    }

    public String getLoadBefore() {
        return loadBefore;
    }

    public String getPermissions() {
        return permissions;
    }

    public String getPrefix() {
        return prefix;
    }

    public JSONObject serialize() {
        return new JSONObject(this);
    }

    public void deserialize(JSONObject json) {
        name = json.optString("name");
        version = json.optString("version");
        authors = json.optString("authors");
        description = json.optString("description");
        website = json.optString("website");
        dependencies = json.optString("dependencies");
        softDepend = json.optString("softDepend");
        loadBefore = json.optString("loadBefore");
        permissions = json.optString("permissions");
        prefix = json.optString("prefix");
    }
}
