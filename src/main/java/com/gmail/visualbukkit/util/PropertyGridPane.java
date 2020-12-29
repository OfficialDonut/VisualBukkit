package com.gmail.visualbukkit.util;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class PropertyGridPane extends GridPane {

    private int properties = 0;

    public PropertyGridPane() {
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(30);
        getColumnConstraints().add(columnConstraints);
    }

    public void addProperty(String property, String value) {
        addProperty(properties, property, value);
    }

    public void addProperty(int row, String property, String value) {
        addRow(row, createCell(property), createCell(value));
        properties++;
    }

    private VBox createCell(String string) {
        Label label = new Label(string);
        label.setPadding(new Insets(10));
        VBox vBox = new VBox(label);
        vBox.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
        return vBox;
    }
}
