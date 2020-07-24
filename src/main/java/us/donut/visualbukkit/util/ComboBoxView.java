package us.donut.visualbukkit.util;

import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

public class ComboBoxView<T> extends HBox {

    private ComboBox<T> comboBox = new ComboBox<>();
    private ContextMenu contextMenu = new ContextMenu();
    private StringBuilder stringBuilder;

    public ComboBoxView() {
        getStyleClass().addAll("combo-box-base", "combo-box-view");

        ListView<T> listView = new ListView<>();
        listView.setCellFactory(view -> {
            TextFieldListCell<T> cell = new TextFieldListCell<>();
            cell.setConverter(comboBox.getConverter());
            return cell;
        });
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                comboBox.setValue(newValue);
                contextMenu.hide();
            }
        });

        Label label = new Label();
        label.setOnKeyReleased(e -> {
            if (contextMenu.isShowing()) {
                if (e.getCode() == KeyCode.BACK_SPACE && stringBuilder.length() > 0) {
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                } else {
                    stringBuilder.append(e.getText());
                }
                String search = stringBuilder.toString();
                listView.getItems().setAll(comboBox.getItems());
                listView.getItems().removeIf(o -> !comboBox.getConverter().toString(o).toLowerCase().contains(search));
            }
        });
        getChildren().addAll(label, new Label("▾"));

        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> label.setText(comboBox.getConverter().toString(newValue)));

        contextMenu.getItems().add(new CustomMenuItem(listView, false));
        listView.setPrefHeight(250);
        listView.setPrefWidth(200);

        setOnMouseClicked(e -> {
            stringBuilder = new StringBuilder();
            listView.getItems().setAll(comboBox.getItems());
            label.requestFocus();
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
