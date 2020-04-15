package us.donut.visualbukkit.util;

import javafx.scene.control.Label;
import us.donut.visualbukkit.VisualBukkit;

public class TitleLabel extends Label {

    private VisualBukkit visualBukkit = VisualBukkit.getInstance();
    private double sizeFactor;

    public TitleLabel(String string, double sizeFactor) {
        super(string);
        this.sizeFactor = sizeFactor;
        updateFontSize();
        visualBukkit.getRootPane().styleProperty().addListener(((observable, oldValue, newValue) -> updateFontSize()));
    }

    public TitleLabel(String string, double sizeFactor, boolean underline) {
        this(string, sizeFactor);
        setUnderline(underline);
    }

    private void updateFontSize() {
        setStyle("-fx-font-size:" + (visualBukkit.getFontSize() * sizeFactor) + ";");
    }
}
