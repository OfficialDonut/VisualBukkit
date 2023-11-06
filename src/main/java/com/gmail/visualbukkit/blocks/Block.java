package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.project.BuildInfo;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public sealed abstract class Block extends VBox permits PluginComponentBlock, StatementBlock, ExpressionBlock {

    private final HBox header = new HBox();
    private GridPane parameterGrid;
    protected List<BlockParameter> parameters;

    public Block() {
        getStyleClass().add("block");
        Label blockTitle = new Label(getDefinition().name());
        blockTitle.getStyleClass().add("block-title");
        header.getStyleClass().add("block-header");
        header.getChildren().add(blockTitle);
        getChildren().add(header);
    }

    public void addToHeader(Node node) {
        if (header.getChildren().size() == 1) {
            HBox spacer = new HBox();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            header.getChildren().add(spacer);
        }
        header.getChildren().add(node);
    }

    public <T extends Node & BlockParameter> void addParameter(String label, T parameter) {
        addParameter(label, null, parameter);
    }

    public <T extends Node & BlockParameter> void addParameter(String labelText, String tooltip, T parameter) {
        if (parameters == null) {
            parameters = new ArrayList<>();
            getChildren().add(1, parameterGrid = new GridPane());
            parameterGrid.getStyleClass().add("parameter-grid");
        }
        Label label = new Label(labelText + ": ");
        if (tooltip != null) {
            label.setTooltip(new Tooltip(tooltip));
        }
        parameters.add(parameter);
        parameterGrid.addRow(parameterGrid.getRowCount(), label, parameter);
    }

    public void removeParameters(int index) {
        parameterGrid.getChildren().removeIf(n -> GridPane.getRowIndex(n) >= index);
        while (index < parameters.size()) {
            parameters.remove(index);
        }
    }

    public String arg(int i) {
        return parameters.get(i).generateJava();
    }

    public void updateState() {
        if (parameters != null) {
            parameters.forEach(BlockParameter::updateState);
        }
    }

    public void prepareBuild(BuildInfo buildInfo) {
        if (parameters != null) {
            for (BlockParameter parameter : parameters) {
                parameter.prepareBuild(buildInfo);
            }
        }
    }

    public JSONObject serialize() {
        JSONObject json = new JSONObject();
        json.put("uid", getDefinition().uid());
        if (parameters != null) {
            for (BlockParameter parameter : parameters) {
                json.append("params", parameter.serialize());
            }
        }
        return json;
    }

    public void deserialize(JSONObject json) {
        JSONArray parameterJson = json.optJSONArray("params");
        if (parameterJson != null && parameters != null) {
            for (int i = 0; i < Math.min(parameters.size(), parameterJson.length()); i++) {
                parameters.get(i).deserialize(parameterJson.get(i));
            }
        }
    }

    public BlockDefinition getDefinition() {
        return getClass().getAnnotation(BlockDefinition.class);
    }
}
