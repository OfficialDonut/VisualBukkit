package us.donut.visualbukkit.blocks;

import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;

public class StatementLabel extends Label {

    private StatementDefinition<?> statement;
    private StatementBlock validationBlock;

    public StatementLabel(StatementDefinition<?> statement) {
        super(statement.getName());
        this.statement = statement;
        validationBlock = statement.createBlock();
        getStyleClass().add("statement-label");
        if (statement.getDescription() != null) {
            setTooltip(new Tooltip(statement.getDescription()));
        }
        setOnDragDetected(e -> {
            Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
            SnapshotParameters snapshotParameters = new SnapshotParameters();
            snapshotParameters.setFill(Color.TRANSPARENT);
            dragboard.setDragView(snapshot(snapshotParameters, null));
            ClipboardContent content = new ClipboardContent();
            content.putString("");
            dragboard.setContent(content);
            StatementBlock.DRAG_CACHE.clear();
            e.consume();
        });
    }

    public StatementDefinition<?> getStatement() {
        return statement;
    }

    public StatementBlock getValidationBlock() {
        return validationBlock;
    }
}
