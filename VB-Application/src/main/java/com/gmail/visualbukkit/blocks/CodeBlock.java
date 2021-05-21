package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.definitions.CompEventListener;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.ui.*;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class CodeBlock extends StyleableVBox {

    private static final PseudoClass INVALID_STYLE_CLASS = PseudoClass.getPseudoClass("invalid");
    private static final PseudoClass SELECTED_STYLE_CLASS = PseudoClass.getPseudoClass("selected");

    private BlockDefinition definition;
    private List<BlockParameter> parameters;
    private HBox header = new StyleableHBox();
    private VBox body = new StyleableVBox();
    private ContextMenu contextMenu = new ContextMenu();
    private String invalidReason;

    public CodeBlock(BlockDefinition definition) {
        this.definition = definition;
        parameters = new ArrayList<>(definition.getParameterNames() != null ? definition.getParameterNames().length : 0);

        header.getStyleClass().add("code-block-header");
        body.getStyleClass().add("code-block");
        body.getChildren().add(header);
        getChildren().add(body);

        body.setOnContextMenuRequested(e -> {
            contextMenu.show(VisualBukkitApp.getScene().getWindow(), e.getScreenX(), e.getScreenY());
            VisualBukkitApp.getScene().getWindow().requestFocus();
            e.consume();
        });

        IconButton infoButton = new IconButton("help", null, null);
        infoButton.setOnAction(e -> {
            PopOver popOver = new PopOver(new Label(definition.getDescription() != null ? definition.getDescription() : LanguageManager.get("tooltip.no_description")));
            popOver.getStyleClass().add("block-info-popover");
            popOver.setAnimated(false);
            popOver.show(infoButton);
        });

        addToHeader(new Label(definition.getTitle()));
        addToHeader(infoButton);

        SelectionManager.register(this);
    }

    public void addToHeader(Node node) {
        if (header.getChildren().size() == 1) {
            header.getChildren().add(new SpacerRegion());
        }
        header.getChildren().add(node);
        addHeaderParameter(node);
    }

    private void addHeaderParameter(Node node) {
        if (node instanceof BlockParameter) {
            parameters.add((BlockParameter) node);
        }
        if (node instanceof Parent) {
            for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
                addHeaderParameter(child);
            }
        }
    }

    public void addParameterLines(String[] parameterNames, BlockParameter[] parameters) {
        for (int i = 0; i < parameters.length; i++) {
            addParameterLine(parameterNames[i], parameters[i]);
        }
    }

    public HBox addParameterLine(String name, BlockParameter parameter) {
        HBox hBox = new StyleableHBox(new Label(name + ":"), (Node) parameter);
        body.getChildren().add(hBox);
        parameters.add(parameter);
        int maxLen = name.length() + 1;
        for (Node node : body.getChildren()) {
            if (node instanceof HBox && !node.equals(header)) {
                Label label = (Label) ((HBox) node).getChildren().get(0);
                if (label.getText().length() > maxLen) {
                    maxLen = label.getText().length();
                } else {
                    label.setText(String.format("%-" + maxLen + "s", label.getText()));
                }
            }
        }
        return hBox;
    }

    public void select() {
        body.pseudoClassStateChanged(SELECTED_STYLE_CLASS, true);
        if (invalidReason != null) {
            NotificationManager.displayError(LanguageManager.get("error.invalid_block.title"), invalidReason);
        }
    }

    public void unselect() {
        body.pseudoClassStateChanged(SELECTED_STYLE_CLASS, false);
    }

    public abstract void handleSelectedAction(KeyEvent e);

    public void setValid() {
        body.pseudoClassStateChanged(INVALID_STYLE_CLASS, false);
        invalidReason = null;
    }

    public void setInvalid(String reason) {
        body.pseudoClassStateChanged(INVALID_STYLE_CLASS, true);
        invalidReason = reason;
    }

    protected boolean checkForPluginComponent(String componentID) {
        PluginComponent.Block block = getPluginComponentBlock();
        if (block == null || !block.getDefinition().getID().equals(componentID)) {
            setInvalid(String.format(LanguageManager.get("error.invalid_block_parent"), BlockRegistry.getPluginComponent(componentID).getTitle()));
            return false;
        }
        return true;
    }

    protected boolean checkForEvent(ClassInfo event) {
        PluginComponent.Block block = getPluginComponentBlock();
        if (!(block instanceof CompEventListener.EventBlock) || !event.equals(((CompEventListener.EventBlock) block).getEvent())) {
            setInvalid(String.format(LanguageManager.get("error.invalid_block_parent"), event.getDisplayClassName()));
            return false;
        }
        return true;
    }

    protected boolean checkForContainer(String containerID) {
        Parent parent = getParent();
        while (parent != null) {
            if (parent instanceof Statement.Block && ((Statement.Block) parent).getDefinition().getID().equals(containerID)) {
                return true;
            }
            parent = parent.getParent();
        }
        setInvalid(String.format(LanguageManager.get("error.invalid_block_parent"), BlockRegistry.getStatement(containerID)));
        return false;
    }

    public void update() {
        setValid();
        for (BlockParameter parameter : parameters) {
            parameter.update();
        }
    }

    public void prepareBuild(BuildContext buildContext) {
        for (BlockParameter parameter : parameters) {
            parameter.prepareBuild(buildContext);
        }
    }

    public JSONObject serialize() {
        JSONObject json = new JSONObject();
        json.put("=", definition.getID());
        for (BlockParameter parameter : parameters) {
            json.append("parameters", parameter.serialize());
        }
        return json;
    }

    public void deserialize(JSONObject json) {
        JSONArray parameterArray = json.optJSONArray("parameters");
        if (parameterArray != null) {
            int len = Math.min(parameters.size(), parameterArray.length());
            for (int i = 0; i < len; i++) {
                Object obj = parameterArray.opt(i);
                if (obj != null) {
                    parameters.get(i).deserialize(obj);
                }
            }
        }
    }

    public PluginComponent.Block getPluginComponentBlock() {
        Parent parent = getParent();
        while (parent != null) {
            if (parent instanceof PluginComponent.Pane) {
                return ((PluginComponent.Pane) parent).getBlock();
            }
            parent = parent.getParent();
        }
        return null;
    }

    public String arg(int i) {
        return parameters.get(i).toJava();
    }

    public BlockDefinition getDefinition() {
        return definition;
    }

    public List<BlockParameter> getParameters() {
        return parameters;
    }

    public HBox getHeader() {
        return header;
    }

    public VBox getBody() {
        return body;
    }

    public ContextMenu getContextMenu() {
        return contextMenu;
    }
}
