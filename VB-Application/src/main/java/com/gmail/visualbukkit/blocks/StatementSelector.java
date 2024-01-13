package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.project.UndoManager;
import com.gmail.visualbukkit.ui.IconButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.textfield.CustomTextField;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import java.util.Set;

public class StatementSelector extends VBox {

    private final ObservableList<StatementSource> statements = FXCollections.observableArrayList();

    public StatementSelector() {
        getStyleClass().add("statement-selector");

        FilteredList<StatementSource> filteredList = new FilteredList<>(statements);
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

    public void setStatements(Set<BlockFactory<StatementBlock>> statements) {
        this.statements.clear();
        for (BlockFactory<StatementBlock> factory : statements) {
            this.statements.add(new StatementSource(factory));
        }
    }
}
