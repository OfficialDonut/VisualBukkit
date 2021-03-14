package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.gui.SoundManager;
import com.gmail.visualbukkit.gui.StyleableHBox;
import com.gmail.visualbukkit.plugin.BuildContext;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Bounds;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.json.JSONObject;

import java.awt.geom.Point2D;

public abstract class Statement extends BlockDefinition<Statement.Block> {

    public Statement(String id) {
        super(id);
    }

    public static abstract class Block extends CodeBlock<Statement> {

        private static final Image COLLAPSE_IMAGE = new Image(Statement.class.getResourceAsStream("/images/collapse.png"));
        private static final Image EXPAND_IMAGE = new Image(Statement.class.getResourceAsStream("/images/expand.png"));

        private BooleanProperty collapsed = new SimpleBooleanProperty(false);
        private VBox statementHolder = new VBox();
        private StatementConnector next = new StatementConnector(this, statementHolder);
        private StatementConnector previous;

        public Block(Statement statement, BlockParameter... parameters) {
            super(statement);

            if (parameters.length > 0) {
                Button toggleCollapseButton = new Button();
                toggleCollapseButton.setOnAction(e -> {
                    if (currentContextMenu != null) {
                        currentContextMenu.hide();
                    }
                    collapsedProperty().set(!isCollapsed());
                    SoundManager.WHOOSH.play();
                });
                toggleCollapseButton.graphicProperty().bind(Bindings
                        .when(collapsedProperty())
                        .then(new ImageView(EXPAND_IMAGE))
                        .otherwise(new ImageView(COLLAPSE_IMAGE)));

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);
                addHeaderNode(new StyleableHBox(new Label(statement.getTitle()), spacer, toggleCollapseButton));
                addParameterLines(parameters);
            } else {
                addHeaderNode(new Label(statement.getTitle()));
            }

            MenuItem copyItem = new MenuItem(VisualBukkitApp.getString("context_menu.copy"));
            MenuItem cutItem = new MenuItem(VisualBukkitApp.getString("context_menu.cut"));
            MenuItem deleteItem = new MenuItem(VisualBukkitApp.getString("context_menu.delete"));
            MenuItem copyStackItem = new MenuItem(VisualBukkitApp.getString("context_menu.copy_stack"));
            MenuItem cutStackItem = new MenuItem(VisualBukkitApp.getString("context_menu.cut_stack"));
            MenuItem deleteStackItem = new MenuItem(VisualBukkitApp.getString("context_menu.delete_stack"));
            MenuItem pasteBeforeItem = new MenuItem(VisualBukkitApp.getString("context_menu.paste_before"));
            MenuItem pasteAfterItem = new MenuItem(VisualBukkitApp.getString("context_menu.paste_after"));
            copyItem.setOnAction(e -> copy());
            cutItem.setOnAction(e -> UndoManager.run(cut()));
            deleteItem.setOnAction(e -> UndoManager.run(delete()));
            copyStackItem.setOnAction(e -> copyStack());
            cutStackItem.setOnAction(e -> UndoManager.run(cutStack()));
            deleteStackItem.setOnAction(e -> UndoManager.run(deleteStack()));
            pasteBeforeItem.setOnAction(e -> UndoManager.run(paste(true)));
            pasteAfterItem.setOnAction(e -> UndoManager.run(paste(false)));

            getContextMenu().getItems().addAll(
                    copyItem, cutItem, deleteItem, new SeparatorMenuItem(),
                    copyStackItem, cutStackItem, deleteStackItem, new SeparatorMenuItem(),
                    pasteBeforeItem, pasteAfterItem);

            getContextMenu().setOnShowing(e -> {
                boolean state = !(CopyPasteManager.peek() instanceof Statement);
                pasteBeforeItem.setDisable(state);
                pasteAfterItem.setDisable(state);
            });

            getChildren().addAll(next, statementHolder);
            getSyntaxBox().getStyleClass().add("statement-block");

            getSyntaxBox().setOnDragOver(e -> {
                if (previous.isAcceptingConnections()) {
                    Bounds bounds = previous.localToScreen(previous.getBoundsInLocal());
                    if (e.getScreenX() > bounds.getMinX() && e.getScreenX() < bounds.getMaxX()) {
                        double deltaY = e.getScreenY() - bounds.getMinY();
                        if (deltaY > 0 && deltaY < previous.getMaxHeight()) {
                            previous.show();
                        }
                    }
                }
            });

            getSyntaxBox().setOnDragDetected(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
                    SnapshotParameters snapshotParameters = new SnapshotParameters();
                    snapshotParameters.setFill(Color.TRANSPARENT);
                    Image image = snapshot(snapshotParameters, new WritableImage((int) Math.min(getWidth(), 500), (int) Math.min(getHeight(), 500)));
                    dragboard.setDragView(image, Math.min(image.getWidth(), e.getX()), Math.min(image.getHeight(), e.getY()));
                    ClipboardContent content = new ClipboardContent();
                    content.put(StatementConnector.POINT_DATA_FORMAT, new Point2D.Double(e.getX(), e.getY()));
                    dragboard.setContent(content);
                    setOpacity(0.5);
                    next.setAcceptingConnections(false);
                }
                e.consume();
            });

            setOnDragDone(e -> {
                setOpacity(1);
                next.setAcceptingConnections(true);
                e.consume();
            });
        }

        @Override
        public HBox addParameterLine(String name, BlockParameter parameter) {
            HBox hBox = super.addParameterLine(name, parameter);
            hBox.visibleProperty().bind(collapsedProperty().not());
            hBox.managedProperty().bind(hBox.visibleProperty());
            return hBox;
        }

        @Override
        protected void handleSelectedAction(KeyEvent e) {
            KeyCode key = e.getCode();
            if (e.isShortcutDown()) {
                if (key == KeyCode.C) {
                    copy();
                } else if (key == KeyCode.X) {
                    UndoManager.run(cut());
                }
            } else if (key == KeyCode.DELETE) {
                UndoManager.run(delete());
            }
        }

        public void copy() {
            CopyPasteManager.copyStatement(this, false);
        }

        public UndoManager.RevertableAction cut() {
            copy();
            return delete();
        }

        public UndoManager.RevertableAction delete() {
            StatementConnector oldPrevious = previous;
            return new UndoManager.RevertableAction() {
                @Override
                public void run() {
                    previous.disconnect().run();
                    oldPrevious.connect(next.getConnected()).run();
                }
                @Override
                public void revert() {
                    oldPrevious.connect(Statement.Block.this).run();
                }
            };
        }

        public UndoManager.RevertableAction paste(boolean before) {
            return (before ? previous : next).connect(CopyPasteManager.pasteStatement());
        }

        public void copyStack() {
            CopyPasteManager.copyStatement(this, true);
        }

        public UndoManager.RevertableAction cutStack() {
            copyStack();
            return deleteStack();
        }

        public UndoManager.RevertableAction deleteStack() {
            return previous.disconnect();
        }

        public void collapseStack(boolean collapse) {
            collapsed.set(collapse);
            if (next.hasConnection()) {
                next.getConnected().collapseStack(collapse);
            }
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

        protected void setPrevious(StatementConnector previous) {
            this.previous = previous;
        }

        public Block getLast() {
            return next.hasConnection() ? next.getConnected().getLast() : this;
        }

        public StatementConnector getPrevious() {
            return previous;
        }

        public StatementConnector getNext() {
            return next;
        }

        public boolean isCollapsed() {
            return collapsed.get();
        }

        public BooleanProperty collapsedProperty() {
            return collapsed;
        }
    }
}
