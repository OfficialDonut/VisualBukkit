package com.gmail.visualbukkit.ui;

import com.gmail.visualbukkit.VisualBukkitApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.PopOver;

import java.util.Collection;
import java.util.Collections;

public class PopOverSelector<T> extends ComboBox<T> {

    private final ObservableList<T> itemList;

    public PopOverSelector() {
        this(Collections.emptyList());
    }

    public PopOverSelector(Collection<T> items) {
        itemList = FXCollections.observableArrayList(items);
        FilteredList<T> filteredItemList = new FilteredList<>(itemList);

        TextField searchField = new TextField();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filteredItemList.setPredicate(t -> StringUtils.containsIgnoreCase(t.toString(), newValue)));

        ListView<T> listView = new ListView<>(filteredItemList);
        PopOver popOver = new PopOver(new VBox(new HBox(new Label(VisualBukkitApp.localizedText("label.search")), searchField), listView));

        listView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<T> call(ListView<T> param) {
                ListCell<T> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(T item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item != null ? item.toString() : "");
                    }
                };
                cell.setOnMouseClicked(e -> {
                    popOver.hide();
                    setValue(cell.getItem());
                    requestFocus();
                });
                return cell;
            }
        });

        popOver.getStyleClass().add("pop-over-selector");
        popOver.setDetachable(false);
        popOver.setAnimated(false);
        popOver.setOnShowing(e -> {
            listView.getSelectionModel().clearSelection();
            searchField.clear();
        });

        addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            popOver.setArrowLocation(2 * e.getSceneY() > VisualBukkitApp.getPrimaryStage().getScene().getHeight() ? PopOver.ArrowLocation.BOTTOM_CENTER : PopOver.ArrowLocation.TOP_CENTER);
            popOver.show(this);
            searchField.requestFocus();
            e.consume();
        });
    }

    public ObservableList<T> getItemList() {
        return itemList;
    }
}
