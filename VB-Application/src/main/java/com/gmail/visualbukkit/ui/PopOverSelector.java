package com.gmail.visualbukkit.ui;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.PopOver;
import org.json.JSONArray;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.lineawesome.LineAwesomeSolid;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class PopOverSelector<T extends PopOverSelectable> extends ComboBox<T> {

    private static final Multimap<String, String> pinnedItems = HashMultimap.create();
    private final PopOver popOver;
    private final TextField searchField = new TextField();
    private final ObservableList<T> observableList = FXCollections.observableArrayList();
    private Function<T, Tooltip> cellTooltip = t -> null;
    private Consumer<T> selectAction = this::setValue;
    private Collection<? extends T> items;

    public PopOverSelector(String pinnedDataKey) {
        this(pinnedDataKey, Collections.emptyList());
    }

    public PopOverSelector(String pinnedItemsKey, Collection<? extends T> items) {
        this.items = items;

        JSONArray json = VisualBukkitApp.getData().optJSONArray(pinnedItemsKey);
        if (json != null) {
            for (Object obj : json) {
                if (obj instanceof String s) {
                    pinnedItems.put(pinnedItemsKey, s);
                }
            }
        }

        FilteredList<T> filteredItemList = new FilteredList<>(observableList.sorted((o1, o2) -> {
            String id1 = o1.getPinID();
            String id2 = o2.getPinID();
            if (pinnedItems.containsValue(id1) && !pinnedItems.containsValue(id2)) {
                return -1;
            }
            if (pinnedItems.containsValue(id2) && !pinnedItems.containsValue(id1)) {
                return 1;
            }
            return o1.toString().compareTo(o2.toString());
        }));

        getStyleClass().add("popover-selector");
        ListView<T> listView = new ListView<>(filteredItemList);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filteredItemList.setPredicate(t -> StringUtils.containsIgnoreCase(t.toString(), newValue)));
        VBox vBox = new VBox(new HBox(new Label(VisualBukkitApp.localizedText("label.search")), searchField), listView);
        vBox.prefWidthProperty().bind(widthProperty());
        popOver = new PopOver(vBox);
        popOver.setDetachable(false);
        popOver.setAnimated(false);
        popOver.setOnShowing(e -> {
            listView.getSelectionModel().clearSelection();
            searchField.clear();
        });

        listView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<T> call(ListView<T> param) {
                ListCell<T> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(T item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            String pinID = item.getPinID();
                            setTooltip(cellTooltip.apply(item));
                            if (pinnedItems.containsValue(pinID)) {
                                HBox hBox = new HBox(new FontIcon(LineAwesomeSolid.THUMBTACK));
                                hBox.getChildren().addAll(item.getDisplayNodes());
                                setGraphic(hBox);
                                setContextMenu(new ContextMenu(new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.unpin"), e -> {
                                    pinnedItems.remove(pinnedItemsKey, pinID);
                                    observableList.setAll(PopOverSelector.this.items);
                                    VisualBukkitApp.getData().put(pinnedItemsKey, pinnedItems.get(pinnedItemsKey));
                                })));
                            } else {
                                setGraphic(new HBox(item.getDisplayNodes()));
                                setContextMenu(new ContextMenu(new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.pin"), e -> {
                                    pinnedItems.put(pinnedItemsKey, pinID);
                                    observableList.setAll(PopOverSelector.this.items);
                                    VisualBukkitApp.getData().append(pinnedItemsKey, pinID);
                                })));
                            }
                        } else {
                            setTooltip(null);
                            setGraphic(null);
                        }
                    }
                };
                cell.setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        popOver.hide();
                        selectAction.accept(cell.getItem());
                    }
                });
                return cell;
            }
        });

        addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                if (popOver.isShowing()) {
                    popOver.hide();
                } else {
                    open();
                }
            }
            e.consume();
        });
    }

    public void open() {
        observableList.setAll(items);
        popOver.setArrowLocation(localToScene(getBoundsInLocal()).getCenterY() > VisualBukkitApp.getPrimaryStage().getScene().getHeight() * 0.65 ? PopOver.ArrowLocation.BOTTOM_CENTER : PopOver.ArrowLocation.TOP_CENTER);
        popOver.show(this);
        searchField.requestFocus();
    }

    public void setCellTooltip(Function<T, Tooltip> cellTooltip) {
        this.cellTooltip = cellTooltip;
    }

    public void setSelectAction(Consumer<T> selectAction) {
        this.selectAction = selectAction;
    }

    public void setItems(Collection<? extends T> items) {
        this.items = items;
    }

    public Collection<? extends T> getItemList() {
        return items;
    }
}
