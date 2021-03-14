package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import javafx.collections.ListChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;

public abstract class PluginComponent extends BlockDefinition<PluginComponent.Block> {

    public PluginComponent(String id) {
        super(id);
    }

    public static abstract class Block extends CodeBlock<PluginComponent> {

        private VBox statementHolder = new VBox();
        private StatementConnector next = new StatementConnector(this, statementHolder);
        private Tab tab;

        public Block(PluginComponent pluginComponent, BlockParameter... parameters) {
            super(pluginComponent);

            addHeaderNode(new Label(pluginComponent.getTitle()));
            addParameterLines(parameters);

            Region spacer = new Region();
            spacer.setPrefHeight(1000);
            VBox content = new VBox(this, spacer);
            content.getStyleClass().add("plugin-component-pane");
            ScrollPane scrollPane = new ScrollPane(content);
            tab = new Tab(pluginComponent.getTitle(), scrollPane);

            MenuItem collapseAllItem = new MenuItem(VisualBukkitApp.getString("context_menu.collapse_all"));
            MenuItem expandAllItem = new MenuItem(VisualBukkitApp.getString("context_menu.expand_all"));
            MenuItem pasteAfterItem = new MenuItem(VisualBukkitApp.getString("context_menu.paste_after"));
            collapseAllItem.setOnAction(e -> next.getConnected().collapseStack(true));
            expandAllItem.setOnAction(e -> next.getConnected().collapseStack(false));
            pasteAfterItem.setOnAction(e -> UndoManager.run(next.connect(CopyPasteManager.pasteStatement())));
            getContextMenu().getItems().addAll(collapseAllItem, expandAllItem, new SeparatorMenuItem(), pasteAfterItem);
            getContextMenu().setOnShowing(e -> pasteAfterItem.setDisable(!(CopyPasteManager.peek() instanceof Statement)));

            getChildren().addAll(next, statementHolder);
            getSyntaxBox().getStyleClass().add("plugin-component-block");

            statementHolder.getChildren().addListener((ListChangeListener<? super Node>)  change -> {
                boolean state = statementHolder.getChildren().isEmpty();
                collapseAllItem.setDisable(state);
                expandAllItem.setDisable(state);
            });

            scrollPane.setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY && CodeBlock.currentSelected != null) {
                    CodeBlock.currentSelected.unselect();
                }
                e.consume();
            });

            content.setOnDragOver(e -> {
                StatementConnector connector = next.hasConnection() ? next.getConnected().getLast().getNext() : next;
                if (connector.isAcceptingConnections()) {
                    Bounds bounds = connector.localToScreen(connector.getBoundsInLocal());
                    if (e.getScreenX() > bounds.getMinX() && e.getScreenX() < bounds.getMaxX()) {
                        double deltaY = e.getScreenY() - bounds.getMinY();
                        if (deltaY > 0 && deltaY < connector.getMaxHeight()) {
                            connector.show();
                        }
                    }
                }
                e.consume();
            });
        }

        @Override
        public void update() {
            super.update();
            if (next.hasConnection()) {
                next.getConnected().update();
            }
        }

        @Override
        public void prepareBuild(BuildContext buildContext) {
            super.prepareBuild(buildContext);
            if (next.hasConnection()) {
                next.getConnected().prepareBuild(buildContext);
            }
        }

        public String getChildJava() {
            StringBuilder builder = new StringBuilder();
            Statement.Block child = next.getConnected();
            while (child != null) {
                builder.append(child.toJava());
                child = child.getNext().getConnected();
            }
            return builder.toString();
        }

        @Override
        public JSONObject serialize() {
            JSONObject json = super.serialize();
            Statement.Block child = next.getConnected();
            while (child != null) {
                json.append("children", child.serialize());
                child = child.getNext().getConnected();
            }
            return json;
        }

        @Override
        public void deserialize(JSONObject json) {
            super.deserialize(json);
            JSONArray childArray = json.optJSONArray("children");
            if (childArray != null) {
                StatementConnector connector = next;
                for (Object obj : childArray) {
                    if (obj instanceof JSONObject) {
                        JSONObject childJson = (JSONObject) obj;
                        Statement statement = BlockRegistry.getStatement(childJson.optString("="));
                        if (statement != null) {
                            Statement.Block block = statement.createBlock(childJson);
                            connector.connect(block).run();
                            connector = block.getNext();
                        }
                    }
                }
            }
        }

        public Tab getTab() {
            return tab;
        }

        public StatementConnector getNext() {
            return next;
        }
    }
}
