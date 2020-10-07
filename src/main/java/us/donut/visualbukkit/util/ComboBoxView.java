package us.donut.visualbukkit.util;

import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import us.donut.visualbukkit.editor.ContextMenuManager;

public class ComboBoxView<T> extends CenteredHBox {

    private ComboBox<T> comboBox = new ComboBox<>();
    private ListView<T> listView = new ListView<>();
    private ContextMenu contextMenu = new ContextMenu();
    private Label arrowLabel = new Label("▾");
    private Label promptLabel;
    private StringBuilder stringBuilder;

    public ComboBoxView() {
        this("---");
    }

    public ComboBoxView(String promptText) {
        getStyleClass().addAll("combo-box-base", "combo-box-view");
        listView.setPrefHeight(200);
        listView.setPrefWidth(250);
        listView.setCellFactory(view -> {
            TextFieldListCell<T> cell = new TextFieldListCell<>();
            cell.setConverter(comboBox.getConverter());
            return cell;
        });
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                comboBox.setValue(newValue);
                ContextMenuManager.hide();
            }
        });

        arrowLabel.setOnKeyReleased(e -> {
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

        arrowLabel.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE) {
                e.consume();
            }
        });

        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> onSelection(newValue));
        contextMenu.getItems().add(new CustomMenuItem(listView, true));
        getChildren().addAll(promptLabel = new Label(promptText), arrowLabel);

        setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                stringBuilder = new StringBuilder();
                listView.getItems().setAll(comboBox.getItems());
                arrowLabel.requestFocus();
                Bounds bounds = localToScreen(getBoundsInLocal());
                ContextMenuManager.show(this, contextMenu, bounds.getMinX(), bounds.getMaxY());
                e.consume();
            }
        });
    }

    protected void onSelection(T value) {
        getChildren().set(0, value != null ? new Label(comboBox.getConverter().toString(value)) : promptLabel);
    }

    public ComboBox<T> getComboBox() {
        return comboBox;
    }

    public ListView<T> getListView() {
        return listView;
    }

    public ContextMenu getContextMenu() {
        return contextMenu;
    }

    public Label getPromptLabel() {
        return promptLabel;
    }

    public Label getArrowLabel() {
        return arrowLabel;
    }
}
