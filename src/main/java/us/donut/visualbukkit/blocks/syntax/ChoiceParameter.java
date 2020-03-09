package us.donut.visualbukkit.blocks.syntax;

import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.util.ResizingComboBox;

import java.util.Collection;

public class ChoiceParameter extends ResizingComboBox<String> implements BlockParameter {

    public ChoiceParameter(String... choices) {
        getItems().addAll(choices);
        setValue(choices[0]);
    }

    public ChoiceParameter(Collection<String> choices) {
        getItems().addAll(choices);
        setValue(getItems().get(0));
    }

    @Override
    public String toJava() {
        return getValue();
    }

    @Override
    public void unload(ConfigurationSection section) {
        if (getValue() != null) {
            section.set("selected-value", getValue());
        }
    }

    @Override
    public void load(ConfigurationSection section) {
        String selectedValue = section.getString("selected-value");
        if (selectedValue != null) {
            setValue(selectedValue);
        }
    }
}
