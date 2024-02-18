package com.gmail.visualbukkit.blocks.parameters;

import com.gmail.visualbukkit.blocks.BlockDefinition;
import com.gmail.visualbukkit.blocks.PluginComponentBlock;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.project.ProjectManager;
import javafx.scene.control.ComboBox;
import org.apache.commons.text.StringEscapeUtils;

public class PluginComponentParameter extends ComboBox<String> implements BlockParameter {

    public PluginComponentParameter(Class<? extends PluginComponentBlock> clazz) {
        setOnShowing(e -> getItems().setAll(ProjectManager.current().getPluginComponents(clazz.getAnnotation(BlockDefinition.class).id())));
    }

    @Override
    public String generateJava(BuildInfo buildInfo) {
        return '"' + StringEscapeUtils.escapeJava(getValue()) + '"';
    }

    @Override
    public Object serialize() {
        return getValue();
    }

    @Override
    public void deserialize(Object obj) {
        if (obj instanceof String s) {
            setValue(s);
        }
    }
}
