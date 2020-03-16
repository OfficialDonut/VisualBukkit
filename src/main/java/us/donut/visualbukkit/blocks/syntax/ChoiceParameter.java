package us.donut.visualbukkit.blocks.syntax;

import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.util.ComboBoxView;

import java.util.Collection;

public class ChoiceParameter extends ComboBoxView<String> implements BlockParameter {

    public ChoiceParameter(String... choices) {
        getComboBox().getItems().addAll(choices);
        getComboBox().setValue(choices[0]);
    }

    public ChoiceParameter(Collection<String> choices) {
        getComboBox().getItems().addAll(choices);
        getComboBox().setValue(getComboBox().getItems().get(0));
    }

    @Override
    public String toJava() {
        return getComboBox().getValue();
    }

    @Override
    public void unload(ConfigurationSection section) {
        if (getComboBox().getValue() != null) {
            section.set("selected-value", getComboBox().getValue());
        }
    }

    @Override
    public void load(ConfigurationSection section) {
        String selectedValue = section.getString("selected-value");
        if (selectedValue != null) {
            getComboBox().setValue(selectedValue);
        }
    }
}
