package us.donut.visualbukkit.blocks.syntax;

public class BinaryChoiceParameter extends ChoiceParameter {

    public BinaryChoiceParameter(String first, String second) {
        super(first, second);
    }

    public boolean isFirst() {
        return getComboBox().getSelectionModel().getSelectedIndex() == 0;
    }

    public boolean isSecond() {
        return !isFirst();
    }
}
