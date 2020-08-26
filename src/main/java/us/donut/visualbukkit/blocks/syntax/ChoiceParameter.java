package us.donut.visualbukkit.blocks.syntax;

import us.donut.visualbukkit.util.ComboBoxView;
import us.donut.visualbukkit.util.DataConfig;

import java.util.Collection;

public class ChoiceParameter extends ComboBoxView<String> implements BlockParameter {

    public ChoiceParameter(String... choices) {
        getStyleClass().add("choice-parameter");
        getComboBox().getItems().addAll(choices);
        getComboBox().setValue(choices[0]);
    }

    public ChoiceParameter(Collection<String> choices) {
        getStyleClass().add("choice-parameter");
        getComboBox().getItems().addAll(choices);
        getComboBox().setValue(getComboBox().getItems().get(0));
    }

    @Override
    public String toJava() {
        return getComboBox().getValue();
    }

    @Override
    public void saveTo(DataConfig config) {
        String value = getComboBox().getValue();
        if (value != null) {
            config.set("value", value);
        }
    }

    @Override
    public void loadFrom(DataConfig config) {
        String value = config.getString("value");
        if (value != null) {
            getComboBox().setValue(value);
        }
    }
}
