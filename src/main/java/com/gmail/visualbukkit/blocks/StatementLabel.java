package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkit;
import com.gmail.visualbukkit.gui.ElementInspector;
import com.gmail.visualbukkit.util.PropertyGridPane;
import javafx.geometry.Insets;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class StatementLabel extends Label implements ElementInspector.Inspectable {

    private StatementDefinition<?> statement;

    public StatementLabel(StatementDefinition<?> statement) {
        super(statement.getName());
        this.statement = statement;

        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        setBackground(new Background(new BackgroundFill(statement.getBlockColor(), CornerRadii.EMPTY, Insets.EMPTY)));
        setPadding(new Insets(3));
        setTextFill(Color.BLACK);

        setOnDragDetected(e -> {
            Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
            SnapshotParameters snapshotParameters = new SnapshotParameters();
            snapshotParameters.setFill(Color.TRANSPARENT);
            dragboard.setDragView(snapshot(snapshotParameters, null));
            ClipboardContent content = new ClipboardContent();
            content.putString("");
            dragboard.setContent(content);
            e.consume();
        });

        setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                VisualBukkit.getInstance().getElementInspector().inspect(this);
                e.consume();
            }
        });
    }

    @Override
    public Pane createInspectorPane() {
        PropertyGridPane gridPane = new PropertyGridPane();
        gridPane.addProperty(0, "Name", statement.getName());
        gridPane.addProperty(1, "Description", statement.getDescription());
        return gridPane;
    }

    @Override
    public void highlight() {
        setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2)),
                new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
    }

    @Override
    public void unhighlight() {
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
    }

    public StatementDefinition<?> getStatement() {
        return statement;
    }
}
