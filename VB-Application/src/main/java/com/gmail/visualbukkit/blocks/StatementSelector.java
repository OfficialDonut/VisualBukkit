package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.project.UndoManager;
import com.gmail.visualbukkit.ui.ActionMenuItem;
import com.gmail.visualbukkit.ui.IconButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.textfield.CustomTextField;
import org.json.JSONArray;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.lineawesome.LineAwesomeSolid;

import java.util.HashSet;
import java.util.Set;

public class StatementSelector extends VBox {

    private final ObservableList<StatementSource> statements = FXCollections.observableArrayList();
    private final Set<String> pinnedStatements = new HashSet<>();

    public StatementSelector() {
        JSONArray json = VisualBukkitApp.getData().optJSONArray("pinned-statements");
        if (json != null) {
            for (Object obj : json) {
                if (obj instanceof String s) {
                    pinnedStatements.add(s);
                }
            }
        }

        FilteredList<StatementSource> filteredList = new FilteredList<>(statements.sorted((o1, o2) -> {
            String id1 = o1.getFactory().getBlockDefinition().id();
            String id2 = o2.getFactory().getBlockDefinition().id();
            if (pinnedStatements.contains(id1) && !pinnedStatements.contains(id2)) {
                return -1;
            }
            if (pinnedStatements.contains(id2) && !pinnedStatements.contains(id1)) {
                return 1;
            }
            return o1.getFactory().compareTo(o2.getFactory());
        }));

        getStyleClass().add("statement-selector");
        ListView<StatementSource> listView = new ListView<>(filteredList);
        listView.prefHeightProperty().bind(heightProperty());
        CustomTextField searchField = new CustomTextField();
        IconButton clearButton = new IconButton(FontAwesomeSolid.TIMES, e -> searchField.clear());
        searchField.textProperty().addListener((o, oldValue, newValue) -> filteredList.setPredicate(s -> StringUtils.containsIgnoreCase(s.getFactory().getBlockDefinition().name(), searchField.getText())));
        searchField.setRight(clearButton);

        getChildren().addAll(new VBox(new HBox(new Label(VisualBukkitApp.localizedText("label.search")), searchField), new Separator()), listView);

        setOnDragOver(e -> {
            if (e.getGestureSource() instanceof StatementBlock || e.getGestureSource() instanceof ExpressionBlock) {
                e.acceptTransferModes(TransferMode.ANY);
                e.consume();
            }
        });

        setOnDragDropped(e -> {
            UndoManager.current().execute(() -> ((Block) e.getGestureSource()).delete());
            e.setDropCompleted(true);
            e.consume();
        });
    }

    public void reloadStatements() {
        statements.clear();
        for (BlockFactory<StatementBlock> factory : BlockRegistry.getStatements()) {
            StatementSource statementSource = new StatementSource(factory);
            String id = factory.getBlockDefinition().id();
            if (pinnedStatements.contains(id)) {
                statementSource.setGraphic(new FontIcon(LineAwesomeSolid.THUMBTACK));
                statementSource.setContextMenu(new ContextMenu(new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.unpin"), e -> {
                    pinnedStatements.remove(id);
                    reloadStatements();
                    VisualBukkitApp.getData().put("pinned-statements", pinnedStatements);
                })));
            } else {
                statementSource.setContextMenu(new ContextMenu(new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.pin"), e -> {
                    pinnedStatements.add(id);
                    reloadStatements();
                    VisualBukkitApp.getData().append("pinned-statements", id);
                })));
            }
            statements.add(statementSource);
        }
    }
}
