package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.ui.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.*;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;

import java.util.*;

public class BlockSelector extends StyleableVBox {

    private ObservableList<BlockSource<?>> blocks = FXCollections.observableArrayList();
    private FilteredList<BlockSource<?>> filteredBlocks = new FilteredList<>(blocks);
    private Set<String> favoriteBlocks = new HashSet<>();

    private TextField searchTitleField = new TextField();
    private TextField searchTagField = new TextField();
    private CheckBox pluginComponentCheckBox = new CheckBox(LanguageManager.get("check_box.plugin_components"));
    private CheckBox statementCheckBox = new CheckBox(LanguageManager.get("check_box.statements"));
    private CheckBox expressionCheckBox = new CheckBox(LanguageManager.get("check_box.expressions"));
    private CheckBox pinnedCheckBox = new CheckBox(LanguageManager.get("check_box.favorite"));

    public BlockSelector() {
        Label title = new Label(LanguageManager.get("label.block_selector"));
        title.setUnderline(true);

        ListView<BlockSource<?>> listView = new ListView<>(filteredBlocks);
        listView.prefHeightProperty().bind(heightProperty());

        pluginComponentCheckBox.setSelected(true);
        statementCheckBox.setSelected(true);
        expressionCheckBox.setSelected(true);

        searchTitleField.textProperty().addListener((o, oldValue, newValue) -> updateFiltered());
        searchTagField.textProperty().addListener((o, oldValue, newValue) -> updateFiltered());
        pluginComponentCheckBox.setOnAction(e -> updateFiltered());
        statementCheckBox.setOnAction(e -> updateFiltered());
        expressionCheckBox.setOnAction(e -> updateFiltered());
        pinnedCheckBox.setOnAction(e -> updateFiltered());

        StyleableGridPane gridPane = new StyleableGridPane();
        gridPane.addRow(0, new Label(LanguageManager.get("label.search_title")), searchTitleField);
        gridPane.addRow(1, new Label(LanguageManager.get("label.search_tag")), searchTagField);

        getStyleClass().add("block-selector");
        getChildren().addAll(new StyleableVBox(title, gridPane, pluginComponentCheckBox, statementCheckBox, expressionCheckBox, pinnedCheckBox), new Separator(), listView);
    }

    public void setBlocks(Set<BlockDefinition> definitions) {
        blocks.clear();
        favoriteBlocks.clear();

        JSONArray pinnedArray = VisualBukkitApp.getData().optJSONArray("favorite-blocks");
        if (pinnedArray != null) {
            for (Object obj : pinnedArray) {
                if (obj instanceof String) {
                    favoriteBlocks.add((String) obj);
                }
            }
        }

        for (BlockDefinition def : definitions) {
            BlockSource<?> block = def.createSource();
            blocks.add(block);
            MenuItem favoriteItem = new MenuItem(LanguageManager.get("context_menu.favorite"));
            MenuItem unfavoriteItem = new MenuItem(LanguageManager.get("context_menu.unfavorite"));
            favoriteItem.setDisable(favoriteBlocks.contains(block.getBlockDefinition().getID()));
            favoriteItem.setOnAction(e -> {
                favoriteBlocks.add(block.getBlockDefinition().getID());
                updateFavorite();
                updateFiltered();
                favoriteItem.setDisable(true);
            });
            unfavoriteItem.setOnAction(e -> {
                favoriteBlocks.remove(block.getBlockDefinition().getID());
                updateFavorite();
                updateFiltered();
                favoriteItem.setDisable(false);
            });
            unfavoriteItem.disableProperty().bind(favoriteItem.disableProperty().not());
            block.setContextMenu(new ContextMenu(favoriteItem, unfavoriteItem));
        }
    }

    private void updateFiltered() {
        filteredBlocks.setPredicate(null);
        filteredBlocks.setPredicate(block ->
                (pluginComponentCheckBox.isSelected() || !(block instanceof PluginComponentSource)) &&
                (statementCheckBox.isSelected() || !(block instanceof StatementSource)) &&
                (expressionCheckBox.isSelected() || !(block instanceof ExpressionSource)) &&
                (!pinnedCheckBox.isSelected() || favoriteBlocks.contains(block.getBlockDefinition().getID())) &&
                StringUtils.containsIgnoreCase(block.getBlockDefinition().getTitle(), searchTitleField.getText()) &&
                StringUtils.containsIgnoreCase(block.getBlockDefinition().getTag(), searchTagField.getText()));
    }

    private void updateFavorite() {
        VisualBukkitApp.getData().remove("favorite-blocks");
        for (String block : favoriteBlocks) {
            VisualBukkitApp.getData().append("favorite-blocks", block);
        }
    }
}
