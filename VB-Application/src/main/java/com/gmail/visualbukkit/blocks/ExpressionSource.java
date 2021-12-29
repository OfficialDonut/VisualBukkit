package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.project.ProjectManager;
import javafx.scene.SnapshotParameters;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;

public class ExpressionSource extends BlockSource<Expression> {

    public ExpressionSource(Expression expression) {
        super(expression);

        getStyleClass().add("expression-source");

        setOnDragDetected(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
                SnapshotParameters snapshotParameters = new SnapshotParameters();
                snapshotParameters.setFill(Color.TRANSPARENT);
                dragboard.setDragView(snapshot(snapshotParameters, null));
                ClipboardContent content = new ClipboardContent();
                content.putString("");
                dragboard.setContent(content);
                PluginComponent.Block block = ProjectManager.getCurrentProject().getCurrentPluginComponent();
                if (block != null) {
                    block.toggleExpressionParameters(true);
                }
                e.consume();
            }
        });

        setOnDragDone(e -> {
            PluginComponent.Block block = ProjectManager.getCurrentProject().getCurrentPluginComponent();
            if (block != null) {
                block.toggleExpressionParameters(false);
            }
            e.consume();
        });
    }
}
