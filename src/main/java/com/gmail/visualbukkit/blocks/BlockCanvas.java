package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.VisualBukkit;
import com.gmail.visualbukkit.gui.ContextMenuManager;
import com.gmail.visualbukkit.gui.CopyPasteManager;
import com.gmail.visualbukkit.gui.NotificationManager;
import com.gmail.visualbukkit.gui.UndoManager;
import com.gmail.visualbukkit.plugin.ProjectManager;
import com.gmail.visualbukkit.util.DataFile;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

public class BlockCanvas extends Pane implements Comparable<BlockCanvas> {

    private Pane innerPane = new Pane();
    private String name;
    private boolean panning;
    private double panX;
    private double panY;

    public BlockCanvas(String canvasName) {
        name = canvasName;
        setStyle("-fx-font-family: \"Jetbrains Mono\"");
        getChildren().add(innerPane);
        innerPane.setMaxWidth(1);
        innerPane.setMaxHeight(1);

        setOnDragOver(e -> {
            Object source = e.getGestureSource();
            if (source instanceof StatementLabel || source instanceof StatementBlock) {
                e.acceptTransferModes(TransferMode.ANY);
            }
            e.consume();
        });

        setOnDragDropped(e -> {
            UndoManager.capture();
            Object source = e.getGestureSource();
            StatementBlock block;
            if (source instanceof StatementLabel) {
                block = ((StatementLabel) source).getStatement().createBlock();
                add(block, e.getScreenX(), e.getScreenY());
            } else {
                block = (StatementBlock) source;
                block.disconnect();
                if (!innerPane.equals(block.getParent())) {
                    add(block, e.getScreenX(), e.getScreenY());
                }
            }
            block.update();
            e.setDropCompleted(true);
            e.consume();
        });

        setOnMouseDragged(e -> {
            if (panning && !innerPane.getChildren().isEmpty() && e.getButton() == MouseButton.PRIMARY) {
                pan(e.getScreenX() - panX, e.getScreenY() - panY);
            }
            panX = e.getScreenX();
            panY = e.getScreenY();
            panning = true;
            e.consume();
        });

        setOnMouseReleased(e -> {
            panning = false;
            e.consume();
        });

        setOnScroll(e -> {
            if (!innerPane.getChildren().isEmpty()) {
                if (e.isShortcutDown()) {
                    zoom(e.getDeltaY() > 0 ? 1.15 : 0.85);
                } else if (e.isShiftDown()) {
                    pan(e.getDeltaX(), 0);
                } else {
                    pan(0, e.getDeltaY());
                }
            }
            e.consume();
        });

        setOnMouseClicked(e -> {
            ContextMenuManager.hide();
            if (e.getButton() == MouseButton.PRIMARY) {
                VisualBukkit.getInstance().getElementInspector().uninspect();
            }
            e.consume();
        });

        VisualBukkit.getInstance().getScene().setOnKeyReleased(e -> {
            if (e.isShortcutDown()) {
                if (e.getCode() == KeyCode.EQUALS) {
                    zoom(1.15);
                } else if (e.getCode() == KeyCode.MINUS) {
                    zoom(0.85);
                }
            }
        });

        ContextMenu contextMenu = new ContextMenu();
        MenuItem pasteItem = new MenuItem("Paste");
        MenuItem organizeItem = new MenuItem("Organize");
        MenuItem clearItem = new MenuItem("Clear");
        MenuItem renameItem = new MenuItem("Rename");
        MenuItem deleteItem = new MenuItem("Delete");
        MenuItem importItem = new MenuItem("Import Block");
        MenuItem invalidPasteItem = new MenuItem("Paste");
        invalidPasteItem.setStyle("-fx-opacity: 0.5;");
        contextMenu.getItems().addAll(pasteItem, organizeItem, clearItem, renameItem, deleteItem, importItem);

        pasteItem.setOnAction(e -> {
            UndoManager.capture();
            StatementBlock block = CopyPasteManager.pasteStack();
            add(block, contextMenu.getX(), contextMenu.getY());
            block.update();
        });

        organizeItem.setOnAction(e -> {
            UndoManager.capture();
            organize();
        });

        clearItem.setOnAction(e -> {
            UndoManager.capture();
            clear();
        });

        renameItem.setOnAction(e -> ProjectManager.getCurrentProject().promptRenameCanvas(this));

        deleteItem.setOnAction(e -> ProjectManager.getCurrentProject().promptDeleteCanvas(this));

        importItem.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
            File file = fileChooser.showOpenDialog(VisualBukkit.getInstance().getPrimaryStage());
            if (file != null) {
                DataFile dataFile = new DataFile(file.toPath());
                StatementDefinition<?> statement = BlockRegistry.getStatement(dataFile.getJson().optString("="));
                if (statement != null) {
                    UndoManager.capture();
                    StatementBlock block = statement.createBlock(dataFile.getJson());
                    add(block, contextMenu.getX(), contextMenu.getY());
                    block.update();
                } else {
                    NotificationManager.displayError("Import failed", "Failed to import block");
                }
                NotificationManager.displayMessage("Imported block", "Successfully imported block");
            }
        });

        setOnContextMenuRequested(e -> {
            contextMenu.getItems().set(0, CopyPasteManager.peek() instanceof StatementDefinition ? pasteItem : invalidPasteItem);
            ContextMenuManager.show(this, contextMenu, e);
        });
    }

    private void add(Node node, double screenX, double screenY) {
        innerPane.getChildren().add(node);
        Point2D point = innerPane.screenToLocal(screenX, screenY);
        Point2D offset = VisualBukkit.getInstance().getLastOffset();
        point = point.subtract(offset);
        node.relocate(point.getX(), point.getY());
        VisualBukkit.getInstance().setLastOffset(0, 0);
    }

    public void pan(double deltaX, double deltaY) {
        for (Node child : innerPane.getChildren()) {
            child.relocate(child.getLayoutX() + deltaX, child.getLayoutY() + deltaY);
        }
    }

    public void zoom(double factor) {
        innerPane.setScaleX(innerPane.getScaleX() * factor);
        innerPane.setScaleY(innerPane.getScaleY() * factor);
    }

    public void organize() {
        innerPane.setScaleX(1);
        innerPane.setScaleY(1);
        int x = 25;
        for (Node child : innerPane.getChildren()) {
            child.relocate(x, 25);
            if (child instanceof Pane) {
                x += ((Pane) child).getWidth() + 10;
            }
        }
    }

    public void clear() {
        innerPane.getChildren().clear();
    }

    public JSONObject serialize() {
        JSONObject obj = new JSONObject();
        JSONArray statementArray = new JSONArray();
        for (Node child : innerPane.getChildren()) {
            if (child instanceof StatementBlock) {
                StatementBlock statement = (StatementBlock) child;
                while (statement != null) {
                    JSONObject statementObj = statement.serialize();
                    statementObj.put("=", BlockRegistry.getIdentifier(statement));
                    if (!statement.hasPrevious()) {
                        statementObj.put("x", statement.getLayoutX());
                        statementObj.put("y", statement.getLayoutY());
                    }
                    statementArray.put(statementObj);
                    statement = statement.getNext();
                }
            }
        }
        obj.put("children", statementArray);
        return obj;
    }

    public void deserialize(JSONObject obj) {
        StatementBlock lastStatement = null;
        JSONArray statementArray = obj.optJSONArray("children");
        if (statementArray != null) {
            for (Object o : statementArray) {
                if (o instanceof JSONObject) {
                    JSONObject statementObj = (JSONObject) o;
                    StatementDefinition<?> statement = BlockRegistry.getStatement(statementObj.optString("="));
                    if (statement != null) {
                        StatementBlock block = statement.createBlock(statementObj);
                        if (lastStatement != null && !statementObj.has("x")) {
                            lastStatement.connectNext(block);
                        } else {
                            innerPane.getChildren().add(block);
                            block.relocate(statementObj.optDouble("x"), statementObj.optDouble("y"));
                        }
                        block.update();
                        lastStatement = block;
                    }
                }
            }
        }
    }

    @Override
    public int compareTo(BlockCanvas canvas) {
        return name.compareTo(canvas.name);
    }

    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("unchecked")
    public List<CodeBlock> getCodeBlocks() {
        return (List<CodeBlock>) (Object) innerPane.getChildren().filtered(node -> node instanceof CodeBlock);
    }

    public String getName() {
        return name;
    }
}
