package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.ui.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class StatementSelector extends StyleableVBox {

    protected TextField searchField = new TextField();
    protected Set<Statement> pinnedBlocks = new TreeSet<>();
    protected TreeNode pinnedTree = new TreeNode(LanguageManager.get("label.pinned_blocks"));
    protected VBox labelBox = new StyleableVBox();

    public StatementSelector() {
        Label title = new Label(LanguageManager.get("label.statement_selector"));
        title.setUnderline(true);

        ScrollPane scrollPane = new ScrollPane(labelBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        getStyleClass().add("statement-selector");
        getChildren().addAll(new StyleableVBox(title, new StyleableHBox(new Label(LanguageManager.get("label.search")), searchField), pinnedTree), new Separator(), scrollPane);

        searchField.textProperty().addListener((o, oldValue, newValue) -> {
            for (Node node : labelBox.getChildren()) {
                StatementLabel label = (StatementLabel) node;
                boolean state = StringUtils.containsIgnoreCase(label.getText(), searchField.getText());
                label.setVisible(state);
                label.setManaged(state);
            }
        });

        setOnDragOver(e -> {
            Object source = e.getGestureSource();
            if (source instanceof Statement.Block || source instanceof Expression.Block) {
                e.acceptTransferModes(TransferMode.ANY);
            }
            e.consume();
        });

        setOnDragDropped(e -> {
            UndoManager.run(e.getGestureSource() instanceof Statement.Block ?
                    ((Statement.Block) e.getGestureSource()).getStatementHolder().removeStack((Statement.Block) e.getGestureSource()) :
                    ((Expression.Block) e.getGestureSource()).getExpressionParameter().clear());
            e.setDropCompleted(true);
            e.consume();
        });
    }

    public void loadPinned() {
        JSONArray pinnedArray = VisualBukkitApp.getData().optJSONArray("pinned-blocks");
        if (pinnedArray != null) {
            for (Object obj : pinnedArray) {
                if (obj instanceof String) {
                    Statement statement = BlockRegistry.getStatement((String) obj);
                    if (statement != null) {
                        pinnedBlocks.add(statement);
                    }
                }
            }
        }
        updatePinned();
    }

    public void setStatements(Set<Statement> statements) {
        labelBox.getChildren().clear();
        Set<String> titles = new HashSet<>();
        for (Statement statement : statements) {
            if (titles.add(statement.getTitle())) {
                StatementLabel label = new StatementLabel(statement);
                labelBox.getChildren().add(label);
                label.setContextMenu(new ContextMenu(new ActionMenuItem(LanguageManager.get("context_menu.pin"), e -> {
                    pinnedBlocks.add(statement);
                    updatePinned();
                })));
            }
        }
    }

    private void updatePinned() {
        pinnedTree.clear();
        VisualBukkitApp.getData().remove("pinned-blocks");
        for (Statement statement : pinnedBlocks) {
            VisualBukkitApp.getData().append("pinned-blocks", statement.getID());
            StatementLabel label = new StatementLabel(statement);
            pinnedTree.add(label);
            label.setContextMenu(new ContextMenu(new ActionMenuItem(LanguageManager.get("context_menu.unpin"), e -> {
                pinnedBlocks.remove(statement);
                updatePinned();
            })));
        }
    }
}
