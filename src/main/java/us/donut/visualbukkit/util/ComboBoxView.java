package us.donut.visualbukkit.util;

import javafx.collections.ListChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.HBox;

public class ComboBoxView<T> extends HBox {

    private ComboBox<T> comboBox = new ComboBox<>();
    private ContextMenu contextMenu = new ContextMenu();

    public ComboBoxView() {
        getStyleClass().addAll("combo-box-base", "combo-box-view");

        Label label = new Label();
        getChildren().addAll(label, new Label("▾"));

        ListView<T> listView = new ListView<>();
        listView.setCellFactory(view -> {
            TextFieldListCell<T> cell = new TextFieldListCell<>();
            cell.setConverter(comboBox.getConverter());
            return cell;
        });
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            comboBox.setValue(newValue);
            contextMenu.hide();
        });

        comboBox.getItems().addListener((ListChangeListener<T>) change -> listView.getItems().setAll(comboBox.getItems()));
        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> label.setText(comboBox.getConverter().toString(newValue)));

        contextMenu.getItems().add(new CustomMenuItem(listView, false));
        listView.setPrefHeight(250);
        listView.setPrefWidth(200);

        setOnMouseClicked(e -> {
            Bounds bounds = localToScreen(getBoundsInLocal());
            contextMenu.show(this, bounds.getMinX(), bounds.getMaxY());
        });
    }

    public ComboBox<T> getComboBox() {
        return comboBox;
    }

    public ContextMenu getContextMenu() {
        return contextMenu;
    }
}
