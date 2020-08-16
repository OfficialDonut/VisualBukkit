package us.donut.visualbukkit.blocks.syntax;

import javafx.event.Event;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import us.donut.visualbukkit.util.DataConfig;

public class InputParameter extends TextField implements BlockParameter {

    public InputParameter() {
        getStyleClass().add("input-parameter");
        prefColumnCountProperty().bind(textProperty().length().add(1));
        addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, e -> {
            Event.fireEvent(getParent(), e);
            e.consume();
        });
    }

    @Override
    public String toJava() {
        return getText();
    }

    @Override
    public void saveTo(DataConfig config) {
        config.set("value", getText());
    }

    @Override
    public void loadFrom(DataConfig config) {
        setText(config.getString("value", ""));
    }
}
