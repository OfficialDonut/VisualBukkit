package com.gmail.visualbukkit.gui;

import com.gmail.visualbukkit.DataFile;
import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.*;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class StatementSelector extends TabPane {

    private Map<String, VBox> categories = new HashMap<>();
    private Set<Statement> favoriteStatements = new TreeSet<>();
    private TextField searchField = new TextField();
    private TreeNode favoriteTree = new TreeNode(VisualBukkitApp.getString("label.favorite_blocks"));

    public StatementSelector(Set<Statement> statements) {
        Label categoryLabel = new Label();
        categoryLabel.setUnderline(true);
        VBox headerBox = new VBox(categoryLabel, new StyleableHBox(new Label(VisualBukkitApp.getString("label.search")), searchField), favoriteTree);
        headerBox.getStyleClass().add("statement-selector");

        VBox content = new VBox();
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
            if (newValue != null) {
                categoryLabel.setText(newValue.getText());
                content.getChildren().clear();
                content.getChildren().addAll(headerBox, new Separator(), categories.get(newValue.getText()));
                if (oldValue != null) {
                    oldValue.setContent(null);
                }
                newValue.setContent(scrollPane);
            }
        });

        String allCategory = VisualBukkitApp.getString("label.all");
        createCategory(allCategory);

        for (Statement statement : statements) {
            add(statement, allCategory);
            if (statement.getCategory() != null) {
                add(statement, statement.getCategory());
            }
        }

        JSONArray favoriteArray = VisualBukkitApp.getInstance().getDataFile().getJson().optJSONArray("favorite-statements");
        if (favoriteArray != null) {
            for (Object obj : favoriteArray) {
                if (obj instanceof String) {
                    Statement statement = BlockRegistry.getStatement((String) obj);
                    if (statement != null) {
                        favoriteStatements.add(statement);
                    }
                }
            }
        }
        updateFavorites();

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

    public void saveFavorites(DataFile dataFile) {
        for (Statement statement : favoriteStatements) {
            dataFile.getJson().append("favorite-statements", statement.getID());
        }
    }

    private void updateFavorites() {
        favoriteTree.clear();
        for (Statement statement : favoriteStatements) {
            StatementLabel label = new StatementLabel(statement);
            favoriteTree.add(label);
            MenuItem removeItem = new MenuItem(VisualBukkitApp.getString("context_menu.remove_favorite"));
            label.setContextMenu(new ContextMenu(removeItem));
            removeItem.setOnAction(e -> {
                favoriteStatements.remove(statement);
                updateFavorites();
            });
        }
    }

    private void createCategory(String category) {
        VBox labelBox = new VBox();
        labelBox.getStyleClass().add("statement-selector");
        categories.put(category, labelBox);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String search = searchField.getText().toLowerCase();
            for (Node node : labelBox.getChildren()) {
                if (node instanceof StatementLabel) {
                    StatementLabel label = (StatementLabel) node;
                    boolean state = label.getText().toLowerCase().contains(search);
                    label.setVisible(state);
                    label.setManaged(state);
                }
            }
        });

        int i = 0;
        for (; i < getTabs().size() && category.compareTo(getTabs().get(i).getText()) > 0; i++);
        getTabs().add(i, new Tab(category));
    }

    private void add(Statement statement, String category) {
        StatementLabel label = new StatementLabel(statement);
        if (!categories.containsKey(category)) {
            createCategory(category);
        }
        categories.get(category).getChildren().add(label);
        MenuItem favoriteItem = new MenuItem(VisualBukkitApp.getString("context_menu.favorite"));
        label.setContextMenu(new ContextMenu(favoriteItem));
        favoriteItem.setOnAction(e -> {
            favoriteStatements.add(statement);
            updateFavorites();
        });
    }
}
