package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.generated.EventComponent;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
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
import org.json.JSONArray;
import org.json.JSONObject;

public sealed abstract class BlockNode extends StyleableVBox permits PluginComponent.Block, Statement.Block, Expression.Block {

    private static final PseudoClass INVALID_STYLE_CLASS = PseudoClass.getPseudoClass("invalid");
    private static final PseudoClass SELECTED_STYLE_CLASS = PseudoClass.getPseudoClass("selected");

    private BlockDefinition definition;
    private StyleableHBox header;
    private StyleableVBox body;
    private ContextMenu contextMenu = new ContextMenu();

    protected BlockParameter<?>[] parameters;

    public BlockNode(BlockDefinition definition, StyleableVBox bodyVBox, BlockParameter<?>... parameters) {
        this.definition = definition;
        this.parameters = parameters;
        this.body = bodyVBox == null ? this : bodyVBox;

        header = new StyleableHBox();
        header.getStyleClass().add("block-header");
        header.getChildren().add(new Label(definition.getFullTitle()));

        body.getStyleClass().add("block");
        body.getChildren().add(header);
        body.setOnContextMenuRequested(e -> {
            contextMenu.show(VisualBukkitApp.getScene().getWindow(), e.getScreenX(), e.getScreenY());
            VisualBukkitApp.getScene().getWindow().requestFocus();
            e.consume();
        });

        int maxLen = -1;
        for (BlockParameter<?> parameter : parameters) {
            body.getChildren().add(parameter);
            int len = parameter.getLabelText().length();
            if (len > maxLen) {
                maxLen = len;
            }
        }
        for (BlockParameter<?> parameter : parameters) {
            parameter.getLabel().setText(String.format("%-" + (maxLen + 1) + "s", parameter.getLabelText() + ":"));
        }

        SelectionManager.register(this);
    }

    public BlockNode(BlockDefinition definition, BlockParameter<?>... parameters) {
        this(definition, null, parameters);
    }

    public void addToHeader(Node node) {
        if (header.getChildren().size() == 1) {
            header.getChildren().add(new SpacerRegion());
        }
        header.getChildren().add(node);
    }

    public void setValid(boolean state) {
        body.pseudoClassStateChanged(INVALID_STYLE_CLASS, !state);
    }

    public void setSelected(boolean state) {
        body.pseudoClassStateChanged(SELECTED_STYLE_CLASS, state);
    }

    public abstract void handleSelectedAction(KeyEvent e);

    public void update() {
        setValid(true);
        for (BlockParameter<?> parameter : parameters) {
            parameter.update();
        }
    }

    protected boolean checkForPluginComponent(Class<? extends PluginComponent> pluginComponent) {
        PluginComponent.Block block = getPluginComponentBlock();
        if (block == null || !pluginComponent.isAssignableFrom(block.getDefinition().getClass())) {
            setValid(false);
            return false;
        }
        return true;
    }

    protected boolean checkForContainer(Class<? extends Container> container) {
        Parent parent = getParent();
        while (parent != null) {
            if (parent instanceof Container.Block && container.isAssignableFrom(((Container.Block) parent).getDefinition().getClass())) {
                return true;
            }
            parent = parent.getParent();
        }
        setValid(false);
        return false;
    }

    protected boolean checkForEvent(ClassInfo event) {
        PluginComponent.Block block = getPluginComponentBlock();
        if (!(block instanceof EventComponent.Block) || !event.equals(((EventComponent.Block) block).getEvent())) {
            setValid(false);
            return false;
        }
        return true;
    }

    public void toggleExpressionParameters(boolean state) {
        for (BlockParameter<?> parameter : parameters) {
            if (parameter instanceof ExpressionParameter expr) {
                expr.toggle(state);
            }
        }
    }

    public PluginComponent.Block getPluginComponentBlock() {
        Parent parent = getParent();
        while (parent != null) {
            if (parent instanceof PluginComponent.Block b) {
                return b;
            }
            if (parent instanceof StatementHolder s && s.getOwner() instanceof PluginComponent.Block b) {
                return b;
            }
            parent = parent.getParent();
        }
        return null;
    }

    public void prepareBuild(BuildContext buildContext) {
        for (BlockParameter<?> parameter : parameters) {
            parameter.prepareBuild(buildContext);
        }
    }

    public JSONObject serialize() {
        JSONObject json = new JSONObject();
        json.put("=", definition.getID());
        for (BlockParameter<?> parameter : parameters) {
            json.append("parameters", parameter.serialize());
        }
        return json;
    }

    public void deserialize(JSONObject json) {
        JSONArray parameterArray = json.optJSONArray("parameters");
        if (parameterArray != null) {
            int len = Math.min(parameters.length, parameterArray.length());
            for (int i = 0; i < len; i++) {
                Object obj = parameterArray.opt(i);
                if (obj != null) {
                    parameters[i].deserialize(obj);
                }
            }
        }
    }

    public String arg(int i) {
        return parameters[i].toJava();
    }

    public BlockDefinition getDefinition() {
        return definition;
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

    public BlockParameter<?>[] getParameters() {
        return parameters;
    }
}
