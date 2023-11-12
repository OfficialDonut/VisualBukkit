package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.project.CopyPasteManager;
import com.gmail.visualbukkit.project.UndoManager;
import com.gmail.visualbukkit.ui.ActionMenuItem;
import javafx.css.PseudoClass;
import javafx.scene.Parent;
import javafx.scene.control.Separator;
import javafx.scene.input.TransferMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.StringJoiner;

public abstract class ContainerBlock extends StatementBlock {

    private static final PseudoClass NESTED_STYLE_CLASS = PseudoClass.getPseudoClass("nested");
    private final StatementHolder childStatementHolder = new StatementHolder();

    public ContainerBlock() {
        getStyleClass().remove("statement-block");
        getStyleClass().add("container-block");
        getChildren().addAll(new Separator(), childStatementHolder);

        ActionMenuItem pasteInsideItem = new ActionMenuItem(VisualBukkitApp.localizedText("context_menu.paste_inside"), e -> UndoManager.current().execute(() -> childStatementHolder.addFirst(CopyPasteManager.pasteStatement())));
        pasteInsideItem.disableProperty().bind(CopyPasteManager.statementCopiedProperty().not());
        getContextMenu().getItems().add(pasteInsideItem);

        setOnDragOver(e -> {
            if (e.getGestureSource() instanceof StatementSource || e.getGestureSource() instanceof StatementBlock) {
                double distanceToTop = e.getY() - getBoundsInLocal().getMinY();
                double distanceToBottom = getBoundsInLocal().getMaxY() - e.getY();
                double distanceToHolderTop = Math.abs(e.getY() - childStatementHolder.getBoundsInParent().getMinY());
                double distanceToHolderBottom = Math.abs(e.getY() - childStatementHolder.getBoundsInParent().getMaxY());
                if (distanceToTop < distanceToBottom && distanceToTop < distanceToHolderTop && distanceToTop < distanceToHolderBottom) {
                    getParentStatementHolder().showConnector(this, false);
                } else if (distanceToBottom < distanceToHolderTop && distanceToBottom < distanceToHolderBottom) {
                    getParentStatementHolder().showConnector(this, true);
                } else if (distanceToHolderTop < distanceToHolderBottom) {
                    childStatementHolder.showFirstConnector();
                } else {
                    childStatementHolder.showLastConnector();
                }
                e.acceptTransferModes(TransferMode.ANY);
                e.consume();
            }
        });

        setOnDragDropped(e -> {
            StatementBlock block = e.getGestureSource() instanceof StatementSource s ? s.getFactory().newBlock() : (StatementBlock) e.getGestureSource();
            StatementConnector.current().accept(block);
            e.setDropCompleted(true);
            e.consume();
        });
    }

    @Override
    public void updateState() {
        super.updateState();
        childStatementHolder.forEach(StatementBlock::updateState);
        int level = 0;
        Parent parent = getParent();
        while (parent != null) {
            if (parent instanceof ContainerBlock) {
                level++;
            }
            parent = parent.getParent();
        }
        pseudoClassStateChanged(NESTED_STYLE_CLASS, level % 2 == 1);
    }

    @Override
    public void prepareBuild(BuildInfo buildInfo) {
        super.prepareBuild(buildInfo);
        for (StatementBlock block : childStatementHolder) {
            block.prepareBuild(buildInfo);
        }
    }

    public String generateChildrenJava() {
        StringJoiner joiner = new StringJoiner(System.lineSeparator());
        for (StatementBlock block : childStatementHolder) {
            joiner.add(block.generateJava());
        }
        return joiner.toString();
    }

    @Override
    public JSONObject serialize() {
        JSONObject json = super.serialize();
        for (StatementBlock block : childStatementHolder) {
            json.append("statements", block.serialize());
        }
        return json;
    }

    @Override
    public void deserialize(JSONObject json) {
        super.deserialize(json);
        JSONArray statements = json.optJSONArray("statements");
        if (statements != null) {
            for (Object obj : statements) {
                if (obj instanceof JSONObject statementJson) {
                    childStatementHolder.addLast(BlockRegistry.newStatement(statementJson));
                }
            }
        }
    }

    public StatementHolder getChildStatementHolder() {
        return childStatementHolder;
    }
}
