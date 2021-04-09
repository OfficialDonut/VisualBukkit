package com.gmail.visualbukkit.gui;

import com.gmail.visualbukkit.DataFile;
import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.*;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;

import java.util.*;

public class StatementSelector extends TabPane {

    private Map<String, VBox> categories = new HashMap<>();
    private Set<Statement> pinnedStatements = new TreeSet<>();
    private TextField searchField = new TextField();
    private TreeNode pinnedTree = new TreeNode(VisualBukkitApp.getString("label.pinned_blocks"));
    private VBox currentCategory;

    public StatementSelector(Set<Statement> statements) {
        Label categoryLabel = new Label();
        categoryLabel.setUnderline(true);
        VBox headerBox = new VBox(categoryLabel, new StyleableHBox(new Label(VisualBukkitApp.getString("label.search")), searchField), pinnedTree);
        headerBox.getStyleClass().add("statement-selector");

        VBox content = new VBox();
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (currentCategory != null) {
                filterSearch(currentCategory);
            }
        });

        getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
            if (newValue != null) {
                categoryLabel.setText(newValue.getText());
                content.getChildren().clear();
                content.getChildren().addAll(headerBox, new Separator(), currentCategory = categories.get(newValue.getText()));
                filterSearch(currentCategory);
                if (oldValue != null) {
                    oldValue.setContent(null);
                }
                newValue.setContent(scrollPane);
            }
        });

        Set<String> statementTitles = new HashSet<>();
        for (Statement statement : statements) {
            if (statementTitles.add(statement.getTitle())) {
                add(statement, VisualBukkitApp.getString("label.all"));
                if (statement.getCategory() != null) {
                    add(statement, statement.getCategory());
                }
            }
        }

        JSONArray pinnedArray = VisualBukkitApp.getInstance().getDataFile().getJson().optJSONArray("pinned-statements");
        if (pinnedArray != null) {
            for (Object obj : pinnedArray) {
                if (obj instanceof String) {
                    Statement statement = BlockRegistry.getStatement((String) obj);
                    if (statement != null) {
                        pinnedStatements.add(statement);
                    }
                }
            }
        }
        updatePinned();

        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        setSide(Side.LEFT);

        setOnDragOver(e -> {
            Object source = e.getGestureSource();
            if (source instanceof Statement.Block || source instanceof Expression.Block) {
                e.acceptTransferModes(TransferMode.ANY);
            }
            e.consume();
        });

        setOnDragDropped(e -> {
            UndoManager.run(e.getGestureSource() instanceof Statement.Block ?
                    ((Statement.Block) e.getGestureSource()).getPrevious().disconnect() :
                    ((Expression.Block) e.getGestureSource()).getExpressionParameter().clear());
            e.setDropCompleted(true);
            e.consume();
        });

        getSelectionModel().selectFirst();
    }

    public void savePinned(DataFile dataFile) {
        for (Statement statement : pinnedStatements) {
            dataFile.getJson().append("pinned-statements", statement.getID());
        }
    }

    private void updatePinned() {
        pinnedTree.clear();
        for (Statement statement : pinnedStatements) {
            StatementLabel label = new StatementLabel(statement);
            pinnedTree.add(label);
            MenuItem removeItem = new MenuItem(VisualBukkitApp.getString("context_menu.unpin"));
            label.setContextMenu(new ContextMenu(removeItem));
            removeItem.setOnAction(e -> {
                pinnedStatements.remove(statement);
                updatePinned();
            });
        }
    }

    private void createCategory(String category) {
        VBox labelBox = new VBox();
        labelBox.getStyleClass().add("statement-selector");
        categories.put(category, labelBox);
        int i = 0;
        for (; i < getTabs().size() && category.compareTo(getTabs().get(i).getText()) > 0; i++);
        getTabs().add(i, new Tab(category));
    }

    private void filterSearch(VBox labelBox) {
        for (Node node : labelBox.getChildren()) {
            StatementLabel label = (StatementLabel) node;
            boolean state = StringUtils.containsIgnoreCase(label.getText(), searchField.getText());
            label.setVisible(state);
            label.setManaged(state);
        }
    }

    private void add(Statement statement, String category) {
        StatementLabel label = new StatementLabel(statement);
        if (!categories.containsKey(category)) {
            createCategory(category);
        }
        categories.get(category).getChildren().add(label);
        MenuItem pinItem = new MenuItem(VisualBukkitApp.getString("context_menu.pin"));
        label.setContextMenu(new ContextMenu(pinItem));
        pinItem.setOnAction(e -> {
            pinnedStatements.add(statement);
            updatePinned();
        });
    }
}
