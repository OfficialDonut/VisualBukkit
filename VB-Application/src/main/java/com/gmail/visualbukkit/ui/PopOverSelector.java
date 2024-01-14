package com.gmail.visualbukkit.ui;

import com.gmail.visualbukkit.VisualBukkitApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.PopOver;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PopOverSelector<T> extends ComboBox<T> {

    private final PopOver popOver;
    private final ListView<T> listView = new ListView<>();
    private final TextField searchField = new TextField();
    private final ObservableList<T> itemList;
    private Consumer<T> selectAction = this::setValue;

    public PopOverSelector() {
        this(Collections.emptyList());
    }

    public PopOverSelector(Collection<T> items) {
        itemList = FXCollections.observableArrayList(items);
        FilteredList<T> filteredItemList = new FilteredList<>(itemList);
        listView.setItems(filteredItemList);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> filteredItemList.setPredicate(t -> StringUtils.containsIgnoreCase(t.toString(), newValue)));

        popOver = new PopOver(new VBox(new HBox(new Label(VisualBukkitApp.localizedText("label.search")), searchField), listView));
        popOver.getStyleClass().add("pop-over-selector");
        popOver.setDetachable(false);
        popOver.setAnimated(false);
        popOver.setOnShowing(e -> {
            listView.getSelectionModel().clearSelection();
            searchField.clear();
        });

        setCellSupplier(() -> new ListCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(item != null ? item.toString() : "");
            }
        });

        addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                open();
            }
            e.consume();
        });
    }

    public void open() {
        popOver.setArrowLocation(2 * localToScene(getBoundsInLocal()).getCenterY() > VisualBukkitApp.getPrimaryStage().getScene().getHeight() ? PopOver.ArrowLocation.BOTTOM_CENTER : PopOver.ArrowLocation.TOP_CENTER);
        popOver.show(this);
        searchField.requestFocus();
    }

    public void setCellSupplier(Supplier<ListCell<T>> supplier) {
        listView.setCellFactory(param -> {
            ListCell<T> cell = supplier.get();
            cell.setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    popOver.hide();
                    selectAction.accept(cell.getItem());
                }
            });
            return cell;
        });
    }

    public void setSelectAction(Consumer<T> selectAction) {
        this.selectAction = selectAction;
    }

    public ObservableList<T> getItemList() {
        return itemList;
    }
}
