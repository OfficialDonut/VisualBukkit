package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.project.ProjectManager;
import com.gmail.visualbukkit.ui.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Region;
import org.json.JSONArray;
import org.json.JSONObject;

public non-sealed abstract class PluginComponent extends BlockDefinition {

    public PluginComponent(String id, String title, String tag, String description) {
        super(id, title, tag, description);
    }

    @Override
    public BlockSource<?> createSource() {
        return new PluginComponentSource(this);
    }

    @Override
    public abstract Block createBlock();

    @Override
    public Block createBlock(JSONObject json) {
        return (Block) super.createBlock(json);
    }

    @Override
    public int compareTo(BlockDefinition obj) {
        return obj instanceof Statement || obj instanceof Expression ? -1 : super.compareTo(obj);
    }

    public static non-sealed abstract class Block extends BlockNode {

        private StatementHolder statementHolder;
        private StatementConnector statementConnector;
        private Tab tab;
        private Button openButton;

        public Block(PluginComponent pluginComponent, BlockParameter<?>... parameters) {
            super(pluginComponent, parameters);

            getStyleClass().add("plugin-component-block");

            statementConnector = new StatementConnector() {
                @Override
                public void accept(Statement.Block block) {
                    UndoManager.run(getStatementHolder().addFirst(block));
                }
            };
            statementHolder = new StatementHolder(this, statementConnector);

            tab = new Tab(pluginComponent.getTitle(), new Pane());
            tab.setOnClosed(e -> NotificationManager.displayMessage(LanguageManager.get("message.component_close.title"), LanguageManager.get("message.component_close.content")));

            openButton = new Button();
            openButton.textProperty().bind(tab.textProperty());
            openButton.setOnAction(e -> ProjectManager.getCurrentProject().openPluginComponent(this));
            openButton.setContextMenu(new ContextMenu(new ActionMenuItem(LanguageManager.get("context_menu.delete"), e -> UndoManager.run(ProjectManager.getCurrentProject().deletePluginComponent(this)))));

            ActionMenuItem deleteItem = new ActionMenuItem(LanguageManager.get("context_menu.delete"), e -> UndoManager.run(ProjectManager.getCurrentProject().deletePluginComponent(this)));
            ActionMenuItem collapseAllItem = new ActionMenuItem(LanguageManager.get("context_menu.collapse_all"), e -> toggleStatementCollapse(statementHolder, true));
            ActionMenuItem expandAllItem = new ActionMenuItem(LanguageManager.get("context_menu.expand_all"), e -> toggleStatementCollapse(statementHolder, false));
            ActionMenuItem pasteAfterItem = new ActionMenuItem(LanguageManager.get("context_menu.paste_after"), e -> UndoManager.run(statementHolder.addFirst(CopyPasteManager.pasteStatement())));
            getContextMenu().getItems().addAll(deleteItem, new SeparatorMenuItem(), pasteAfterItem, new SeparatorMenuItem(), collapseAllItem, expandAllItem);

            getContextMenu().setOnShowing(e -> {
                boolean state = statementHolder.getChildren().isEmpty();
                collapseAllItem.setDisable(state);
                expandAllItem.setDisable(state);
                pasteAfterItem.setDisable(!CopyPasteManager.isStatementCopied());
            });
        }

        @Override
        public void handleSelectedAction(KeyEvent e) {}

        private void toggleStatementCollapse(StatementHolder statementHolder, boolean state) {
            for (Statement.Block block : statementHolder.getBlocks()) {
                block.collapsedProperty().set(state);
                if (block instanceof Container.Block) {
                    toggleStatementCollapse(((Container.Block) block).getChildHolder(), state);
                }
            }
        }

        @Override
        public void toggleExpressionParameters(boolean state) {
            super.toggleExpressionParameters(state);
            for (Statement.Block block : statementHolder.getBlocks()) {
                block.toggleExpressionParameters(state);
            }
        }

        @Override
        public void prepareBuild(BuildContext buildContext) {
            super.prepareBuild(buildContext);
            statementHolder.setDebugMode(buildContext.isDebugMode());
            for (Statement.Block block : statementHolder.getBlocks()) {
                block.prepareBuild(buildContext);
            }
            if (buildContext.isDebugMode()) {
                buildContext.addUtilMethod(
                        """
                        public static void reportError(String id, Exception error) throws Exception {
                            Class.forName("com.gmail.visualbukkit.plugin.VisualBukkitPlugin").getDeclaredMethod("reportError", String.class, Exception.class).invoke(null, id, error);
                        }
                        """);
            }
        }

        public String getChildJava() {
            return statementHolder.toJava();
        }

        @Override
        public JSONObject serialize() {
            JSONObject json = super.serialize();
            for (Statement.Block block : statementHolder.getBlocks()) {
                json.append("children", block.serialize());
            }
            return json;
        }

        @Override
        public void deserialize(JSONObject json) {
            super.deserialize(json);
            JSONArray childArray = json.optJSONArray("children");
            if (childArray != null) {
                for (Object obj : childArray) {
                    if (obj instanceof JSONObject childJson) {
                        Statement statement = BlockRegistry.getStatement(childJson.optString("="));
                        if (statement != null) {
                            statementHolder.addLast(statement.createBlock(childJson)).run();
                        }
                    }
                }
            }
        }

        public StatementHolder getStatementHolder() {
            return statementHolder;
        }

        public StatementConnector getStatementConnector() {
            return statementConnector;
        }

        public Tab getTab() {
            return tab;
        }

        public Button getOpenButton() {
            return openButton;
        }

        @Override
        public PluginComponent getDefinition() {
            return (PluginComponent) super.getDefinition();
        }

        public class Pane extends ScrollPane {

            private Pane() {
                Region spacer = new Region();
                spacer.setPrefHeight(1000);
                setContent(new StyleableVBox(getBlock(), statementConnector, statementHolder, spacer));
                getStyleClass().add("plugin-component-pane");

                getContent().setOnDragOver(e -> {
                    Object source = e.getGestureSource();
                    if (source instanceof StatementSource || source instanceof Statement.Block) {
                        (e.getY() < statementHolder.getBoundsInParent().getMinY() ? statementConnector : statementHolder.getLastEnabledConnector()).show();
                        e.acceptTransferModes(TransferMode.ANY);
                        e.consume();
                    }
                });

                getContent().setOnDragDropped(e -> {
                    Object source = e.getGestureSource();
                    if (source instanceof StatementSource || source instanceof Statement.Block) {
                        StatementConnector.getCurrent().accept(source instanceof StatementSource s ? s.getBlockDefinition().createBlock() : (Statement.Block) source);
                        SoundManager.SNAP.play();
                        e.setDropCompleted(true);
                        e.consume();
                    }
                });

                getContent().setOnDragExited(e -> StatementConnector.hideCurrent());
            }

            public Block getBlock() {
                return PluginComponent.Block.this;
            }
        }
    }
}
