package com.gmail.visualbukkit.gui;

import com.gmail.visualbukkit.VisualBukkit;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.StatementDefinition;
import com.gmail.visualbukkit.blocks.StatementLabel;
import com.gmail.visualbukkit.util.CenteredHBox;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BlockSelector extends TabPane {

    private Map<String, CategoryTab> categoryTabs = new HashMap<>();

    public BlockSelector() {
        setSide(Side.LEFT);
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        setOnDragOver(e -> {
            Object source = e.getGestureSource();
            if (source instanceof StatementBlock) {
                e.acceptTransferModes(TransferMode.ANY);
            }
            e.consume();
        });

        setOnDragDropped(e -> {
            UndoManager.capture();
            ((StatementBlock) e.getGestureSource()).disconnect();
            e.setDropCompleted(true);
            e.consume();
        });

        setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                VisualBukkit.getInstance().getElementInspector().uninspect();
                e.consume();
            }
        });
    }

    public void add(StatementDefinition<?> statement) {
        for (String category : statement.getCategories()) {
            CategoryTab tab = categoryTabs.computeIfAbsent(category, this::createTab);
            tab.add(new StatementLabel(statement));
        }
    }

    private CategoryTab createTab(String category) {
        CategoryTab tab = new CategoryTab(category);
        int i = 0;
        while (i < getTabs().size() && tab.getText().compareTo(getTabs().get(i).getText()) > 0) {
            i++;
        }
        getTabs().add(i, tab);
        return tab;
    }

    private static class CategoryTab extends Tab {

        private VBox labelBox = new VBox(10);
        private Set<StatementLabel> labels = new HashSet<>();

        public CategoryTab(String category) {
            super(category);

            Label titleLabel = new Label(category);
            titleLabel.setUnderline(true);

            TextField searchField = new TextField();
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                String search = searchField.getText().toLowerCase();
                for (StatementLabel label : labels) {
                    boolean state = label.getText().toLowerCase().contains(search);
                    label.setVisible(state);
                    label.setManaged(state);
                }
            });

            ScrollPane scrollPane = new ScrollPane(labelBox);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);

            VBox filterBox = new VBox(10, titleLabel, new CenteredHBox(5, new Label("Search:"), searchField));

            VBox content = new VBox(15, filterBox, new Separator(), scrollPane);
            content.setPadding(new Insets(10));
            setContent(content);
        }

        private void add(StatementLabel label) {
            labels.add(label);
            int i = 0;
            while (i < labelBox.getChildren().size() && label.getText().compareTo(((StatementLabel) labelBox.getChildren().get(i)).getText()) > 0) {
                i++;
            }
            labelBox.getChildren().add(i, label);
        }
    }
}
