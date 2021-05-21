package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.definitions.StatComment;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.ui.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Bounds;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public abstract class Statement extends BlockDefinition {

    public Statement(String id) {
        super(id);
    }

    @Override
    public abstract Block createBlock();

    @Override
    public Block createBlock(JSONObject json) {
        return (Block) super.createBlock(json);
    }

    public static abstract class Block extends CodeBlock {

        private static Image COLLAPSE_IMAGE;
        private static Image EXPAND_IMAGE;

        private StatementConnector statementConnector = new StatementConnector(block -> UndoManager.run(getStatementHolder().add(this, block)));
        private BooleanProperty collapsed = new SimpleBooleanProperty(false);

        static {
            try (InputStream inputStream1 = Statement.class.getResourceAsStream("/images/collapse.png");
                 InputStream inputStream2 = Statement.class.getResourceAsStream("/images/expand.png")) {
                COLLAPSE_IMAGE = new Image(inputStream1);
                EXPAND_IMAGE = new Image(inputStream2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public Block(Statement statement, BlockParameter... parameters) {
            super(statement);

            getBody().getStyleClass().add("statement-block");
            getChildren().add(statementConnector);

            if (parameters.length > 0) {
                Button toggleCollapseButton = new Button();
                toggleCollapseButton.setOnAction(e -> {
                    collapsed.set(!isCollapsed());
                    SoundManager.WHOOSH.play();
                });
                toggleCollapseButton.graphicProperty().bind(Bindings
                        .when(collapsed)
                        .then(new ImageView(EXPAND_IMAGE))
                        .otherwise(new ImageView(COLLAPSE_IMAGE)));
                addToHeader(toggleCollapseButton);
                addParameterLines(statement.getParameterNames(), parameters);
            }

            getBody().setOnDragDetected(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
                    SnapshotParameters snapshotParameters = new SnapshotParameters();
                    snapshotParameters.setFill(Color.TRANSPARENT);
                    Image image = snapshot(snapshotParameters, new WritableImage((int) Math.min(getWidth(), 500), (int) Math.min(getHeight(), 500)));
                    dragboard.setDragView(image, -1, -1);
                    ClipboardContent content = new ClipboardContent();
                    content.putString("");
                    dragboard.setContent(content);
                    setAcceptingConnections(false);
                    List<Block> blocks = getStatementHolder().getBlocks();
                    for (int i = blocks.indexOf(this); i < blocks.size(); i++) {
                        blocks.get(i).setAcceptingConnections(false);
                    }
                }
                e.consume();
            });

            setOnDragDone(e -> {
                if (!e.isDropCompleted() && getStatementHolder() != null) {
                    List<Block> blocks = getStatementHolder().getBlocks();
                    for (int i = blocks.indexOf(this); i < blocks.size(); i++) {
                        blocks.get(i).setAcceptingConnections(true);
                    }
                }
                e.consume();
            });

            getBody().setOnDragOver(e -> {
                Bounds bounds = getBody().localToScreen(getBody().getBoundsInLocal());
                if (e.getScreenX() > bounds.getMinX() && e.getScreenX() < bounds.getMaxX()) {
                    double deltaY = e.getScreenY() - bounds.getMinY();
                    if (deltaY > 0 && deltaY < statementConnector.getMaxHeight()) {
                        Block previous = getStatementHolder().getPrevious(this);
                        if (previous != null) {
                            previous.statementConnector.show();
                        }
                    }
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

        @Override
        public HBox addParameterLine(String name, BlockParameter parameter) {
            HBox hBox = super.addParameterLine(name, parameter);
            hBox.visibleProperty().bind(collapsedProperty().not());
            hBox.managedProperty().bind(hBox.visibleProperty());
            return hBox;
        }

        protected boolean checkForPrevious(String statementID) {
            List<Block> blocks = getStatementHolder().getBlocks();
            for (int i = blocks.indexOf(this) - 1; i >= 0; i--) {
                Statement statement = blocks.get(i).getDefinition();
                if (!(statement instanceof StatComment)) {
                    if (!statement.getID().equals(statementID)) {
                        setInvalid(String.format(LanguageManager.get("error.invalid_block_placement"), BlockRegistry.getStatement(statementID).getTitle()));
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
            setAcceptingConnections(true);
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

        protected void setAcceptingConnections(boolean state) {
            setOpacity(state ? 1 : 0.5);
            statementConnector.setAcceptingConnections(state);
        }

        public boolean isCollapsed() {
            return collapsed.get();
        }

        public StatementHolder getStatementHolder() {
            return (StatementHolder) getParent();
        }

        public StatementConnector getStatementConnector() {
            return statementConnector;
        }

        @Override
        public Statement getDefinition() {
            return (Statement) super.getDefinition();
        }

        public BooleanProperty collapsedProperty() {
            return collapsed;
        }
    }
}
