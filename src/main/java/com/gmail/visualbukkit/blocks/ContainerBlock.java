package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.project.BuildInfo;
import javafx.css.PseudoClass;
import javafx.scene.Parent;
import javafx.scene.control.Separator;
import javafx.scene.input.TransferMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.StringJoiner;

public abstract class ContainerBlock extends StatementBlock {

    private static final PseudoClass NESTED_STYLE_CLASS = PseudoClass.getPseudoClass("nested");
    private final StatementHolder statementHolder = new StatementHolder();

    public ContainerBlock() {
        getStyleClass().add("container-block");

        Separator separator = new Separator();
        getChildren().addAll(separator, statementHolder);

        setOnDragOver(e -> {
            if (e.getGestureSource() instanceof StatementSource || e.getGestureSource() instanceof StatementBlock) {
                if (e.getY() < separator.getBoundsInParent().getMinY()) {
                    getStatementHolder().showConnector(this, false);
                } else {
                    if (2 * e.getY() > getHeight()) {
                        statementHolder.showLastConnector();
                    } else {
                        statementHolder.showFirstConnector();
                    }
                }
                e.acceptTransferModes(TransferMode.ANY);
                e.consume();
            }
        });

        setOnDragDropped(e -> {
            StatementBlock block = e.getGestureSource() instanceof StatementSource s ? s.getFactory().newBlock() : (StatementBlock) e.getGestureSource();
            StatementConnector.getCurrent().accept(block);
            e.setDropCompleted(true);
            e.consume();
        });
    }

    @Override
    public void updateState() {
        super.updateState();
        statementHolder.forEach(StatementBlock::updateState);
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
        for (StatementBlock block : statementHolder) {
            block.prepareBuild(buildInfo);
        }
    }

    public String generateChildrenJava() {
        StringJoiner joiner = new StringJoiner(System.lineSeparator());
        for (StatementBlock block : statementHolder) {
            joiner.add(block.generateJava());
        }
        return joiner.toString();
    }

    @Override
    public JSONObject serialize() {
        JSONObject json = super.serialize();
        for (StatementBlock block : statementHolder) {
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
                    StatementBlock block = BlockRegistry.getStatement(statementJson.optString("uid")).newBlock(statementJson);
                    statementHolder.addLast(block).execute();
                }
            }
        }
    }
}
