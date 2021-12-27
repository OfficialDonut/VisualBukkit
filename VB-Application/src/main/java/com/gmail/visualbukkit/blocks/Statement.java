package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.definitions.StatComment;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.ui.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public non-sealed abstract class Statement extends BlockDefinition {

    public Statement(String id, String title, String tag, String description) {
        super(id, title, tag, description);
    }

    @Override
    public StatementSource createSource() {
        return new StatementSource(this);
    }

    @Override
    public abstract Block createBlock();

    @Override
    public Block createBlock(JSONObject json) {
        return (Block) super.createBlock(json);
    }

    @Override
    public int compareTo(BlockDefinition obj) {
        if (obj instanceof PluginComponent) {
            return 1;
        }
        if (obj instanceof Expression) {
            return -1;
        }
        return super.compareTo(obj);
    }

    public static non-sealed abstract class Block extends BlockNode {

        private static Image COLLAPSE_IMAGE;
        private static Image EXPAND_IMAGE;

        static {
            try (InputStream inputStream1 = Statement.class.getResourceAsStream("/images/collapse.png");
                 InputStream inputStream2 = Statement.class.getResourceAsStream("/images/expand.png")) {
                COLLAPSE_IMAGE = new Image(inputStream1);
                EXPAND_IMAGE = new Image(inputStream2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private BooleanProperty collapsed = new SimpleBooleanProperty(false);
        private StatementConnector connector;

        public Block(Statement statement, BlockParameter<?>... parameters) {
            super(statement, new StyleableVBox(), parameters);

            connector = new StatementConnector() {
                @Override
                public void accept(Block block) {
                    UndoManager.run(getStatementHolder().add(Block.this, block));
                }
            };

            getBody().getStyleClass().add("statement-block");
            getChildren().addAll(getBody(), connector);

            if (parameters.length > 0) {
                for (BlockParameter<?> parameter : parameters) {
                    parameter.visibleProperty().bind(collapsedProperty().not());
                    parameter.managedProperty().bind(parameter.visibleProperty());
                }

                Button toggleCollapseButton = new Button();
                addToHeader(toggleCollapseButton);

                toggleCollapseButton.setOnAction(e -> {
                    collapsed.set(!isCollapsed());
                    SoundManager.WHOOSH.play();
                });

                toggleCollapseButton.graphicProperty().bind(Bindings
                        .when(collapsed)
                        .then(new ImageView(EXPAND_IMAGE))
                        .otherwise(new ImageView(COLLAPSE_IMAGE)));
            }

            getBody().setOnDragDetected(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
                    SnapshotParameters snapshotParameters = new SnapshotParameters();
                    snapshotParameters.setFill(Color.TRANSPARENT);
                    Image image = getBody().snapshot(snapshotParameters, new WritableImage((int) Math.min(getWidth(), 500), (int) Math.min(getHeight(), 500)));
                    dragboard.setDragView(image);
                    ClipboardContent content = new ClipboardContent();
                    content.putString("");
                    dragboard.setContent(content);
                    List<Block> blocks = getStatementHolder().getBlocks();
                    for (int i = blocks.indexOf(this); i < blocks.size(); i++) {
                        blocks.get(i).setDisable(true);
                    }
                    e.consume();
                }
            });

            setOnDragDone(e -> {
                StatementConnector.hideCurrent();
                if (!e.isDropCompleted() && getStatementHolder() != null) {
                    List<Block> blocks = getStatementHolder().getBlocks();
                    for (int i = blocks.indexOf(this); i < blocks.size(); i++) {
                        blocks.get(i).setDisable(false);
                    }
                }
                e.consume();
            });

            getBody().setOnDragOver(e -> {
                Object source = e.getGestureSource();
                if (source instanceof StatementSource || source instanceof Statement.Block) {
                    StatementConnector connectorToShow = 2 * e.getY() > getBody().getHeight() ? connector : getStatementHolder().getPreviousConnector(this);
                    connectorToShow.show();
                }
            });

            setOnDragOver(e -> {
                Object source = e.getGestureSource();
                if (source instanceof StatementSource || source instanceof Statement.Block) {
                    e.acceptTransferModes(TransferMode.ANY);
                    e.consume();
                }
            });

            setOnDragDropped(e -> {
                Object source = e.getGestureSource();
                if (source instanceof StatementSource || source instanceof Statement.Block) {
                    StatementConnector.getCurrent().accept(source instanceof StatementSource s ? s.getBlockDefinition().createBlock() : (Statement.Block) source);
                    SoundManager.SNAP.play();
                    e.setDropCompleted(true);
                    e.consume();
                }
            });

            ActionMenuItem pasteBeforeItem = new ActionMenuItem(LanguageManager.get("context_menu.paste_before"), e -> UndoManager.run(getStatementHolder().add(getStatementHolder().getPrevious(this), CopyPasteManager.pasteStatement())));
            ActionMenuItem pasteAfterItem = new ActionMenuItem(LanguageManager.get("context_menu.paste_after"), e -> UndoManager.run(getStatementHolder().add(this, CopyPasteManager.pasteStatement())));

            getContextMenu().getItems().addAll(
                    new ActionMenuItem(LanguageManager.get("context_menu.copy"), e -> CopyPasteManager.copyStatement(this, false)),
                    new ActionMenuItem(LanguageManager.get("context_menu.cut"), e -> {
                        CopyPasteManager.copyStatement(this, false);
                        UndoManager.run(getStatementHolder().remove(this));
                    }),
                    new ActionMenuItem(LanguageManager.get("context_menu.delete"), e -> UndoManager.run(getStatementHolder().remove(this))),
                    new SeparatorMenuItem(),
                    new ActionMenuItem(LanguageManager.get("context_menu.copy_stack"), e -> CopyPasteManager.copyStatement(this, true)),
                    new ActionMenuItem(LanguageManager.get("context_menu.cut_stack"), e -> {
                        CopyPasteManager.copyStatement(this, true);
                        UndoManager.run(getStatementHolder().removeStack(this));
                    }),
                    new ActionMenuItem(LanguageManager.get("context_menu.delete_stack"), e -> UndoManager.run(getStatementHolder().removeStack(this))),
                    new SeparatorMenuItem(), pasteBeforeItem, pasteAfterItem);

            getContextMenu().setOnShowing(e -> {
                boolean state = !CopyPasteManager.isStatementCopied();
                pasteBeforeItem.setDisable(state);
                pasteAfterItem.setDisable(state);
            });
        }

        protected boolean checkForPrevious(Class<? extends Statement> statement) {
            List<Block> blocks = getStatementHolder().getBlocks();
            for (int i = blocks.indexOf(this) - 1; i >= 0; i--) {
                Statement prev = blocks.get(i).getDefinition();
                if (!(prev instanceof StatComment)) {
                    if (prev.getClass() != statement) {
                        setValid(false);
                        return false;
                    }
                    return true;
                }
            }
            return false;
        }

        @Override
        public void handleSelectedAction(KeyEvent e) {
            if (e.isShortcutDown()) {
                if (e.getCode() == KeyCode.C) {
                    CopyPasteManager.copyStatement(this, false);
                } else if (e.getCode() == KeyCode.X) {
                    CopyPasteManager.copyStatement(this, false);
                    UndoManager.run(getStatementHolder().remove(this));
                }
            } else if (e.getCode() == KeyCode.DELETE) {
                UndoManager.run(getStatementHolder().remove(this));
            }
        }

        @Override
        public void update() {
            super.update();
            setDisable(false);
        }

        public abstract String toJava();

        @Override
        public JSONObject serialize() {
            JSONObject json = super.serialize();
            if (isCollapsed()) {
                json.put("collapsed", true);
            }
            return json;
        }

        @Override
        public void deserialize(JSONObject json) {
            super.deserialize(json);
            if (json.optBoolean("collapsed")) {
                collapsed.set(true);
            }
        }

        public boolean isCollapsed() {
            return collapsed.get();
        }

        public BooleanProperty collapsedProperty() {
            return collapsed;
        }

        public StatementConnector getConnector() {
            return connector;
        }

        public StatementHolder getStatementHolder() {
            return (StatementHolder) getParent();
        }

        @Override
        public Statement getDefinition() {
            return (Statement) super.getDefinition();
        }
    }
}
