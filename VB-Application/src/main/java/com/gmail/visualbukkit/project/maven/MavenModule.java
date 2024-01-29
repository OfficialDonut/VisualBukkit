package com.gmail.visualbukkit.project.maven;

import com.gmail.visualbukkit.project.PluginModule;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.json.JSONObject;

public abstract class MavenModule extends PluginModule {

    private final BooleanProperty userDefinedProperty = new SimpleBooleanProperty();

    public MavenModule(String id, String name, boolean userDefined) {
        super(id, name);
        userDefinedProperty.set(userDefined);
    }

    public abstract JSONObject serialize();

    public boolean isUserDefined() {
        return userDefinedProperty.get();
    }

    public BooleanProperty userDefinedPropertyProperty() {
        return userDefinedProperty;
    }

    @Override
    public int compareTo(PluginModule other) {
        if (other instanceof MavenModule m) {
            if (isUserDefined() && !m.isUserDefined()) {
                return -1;
            }
            if (!isUserDefined() && m.isUserDefined()) {
                return 1;
            }
            if (this instanceof MavenRepositoryModule && m instanceof MavenDependencyModule) {
                return -1;
            }
            if (this instanceof MavenDependencyModule && m instanceof MavenRepositoryModule) {
                return 1;
            }
        }
        return super.compareTo(other);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && o instanceof MavenModule m && isUserDefined() == m.isUserDefined();
    }
}
