package us.donut.visualbukkit.editor;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.blocks.*;
import us.donut.visualbukkit.util.DataConfig;
import us.donut.visualbukkit.util.DataFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BlockCanvas extends Pane {

    private Pane innerPane = new Pane();
    private String name;
    private boolean panning;
    private double panX;
    private double panY;

    public BlockCanvas(String name) {
        this.name = name;
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
        MenuItem invalidPasteItem = new MenuItem("Paste");
        MenuItem pasteItem = new MenuItem("Paste");
        MenuItem organizeItem = new MenuItem("Organize");
        MenuItem clearItem = new MenuItem("Clear");
        MenuItem renameItem = new MenuItem("Rename");
        MenuItem deleteItem = new MenuItem("Delete");
        MenuItem newCanvasItem = new MenuItem("New Canvas");
        MenuItem importItem = new MenuItem("Import Block");
        invalidPasteItem.setStyle("-fx-opacity: 0.5;");
        pasteItem.setOnAction(e -> {
            UndoManager.capture();
            add((StatementBlock) CopyPasteManager.paste(), contextMenu.getX(), contextMenu.getY());
        });
        organizeItem.setOnAction(e -> {
            UndoManager.capture();
            organize();
        });
        clearItem.setOnAction(e -> {
            UndoManager.capture();
            clear();
        });
        renameItem.setOnAction(e -> {
            TextInputDialog renameDialog = new TextInputDialog();
            renameDialog.setTitle("Rename Canvas");
            renameDialog.setContentText("New name:");
            renameDialog.setHeaderText(null);
            renameDialog.setGraphic(null);
            String newName = renameDialog.showAndWait().orElse("").trim();
            if (!newName.isEmpty()) {
                if (!newName.equalsIgnoreCase("Main Canvas")) {
                    ProjectManager.getCurrentProject().renameCanvas(this, newName);
                    VisualBukkit.displayMessage("Renamed canvas", "Successfully renamed canvas");
                } else {
                    VisualBukkit.displayError("Invalid canvas name", "There can only be one main canvas");
                }
            } else {
                VisualBukkit.displayError("Invalid canvas name", "Canvas name cannot be empty");
            }
        });
        deleteItem.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this canvas?", ButtonType.YES, ButtonType.CANCEL);
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.YES) {
                    ProjectManager.getCurrentProject().deleteCanvas(this);
                    VisualBukkit.displayMessage("Deleted canvas", "Successfully deleted canvas");
                }
            });
        });
        newCanvasItem.setOnAction(e -> {
            TextInputDialog newCanvasDialog = new TextInputDialog();
            newCanvasDialog.setTitle("New Canvas");
            newCanvasDialog.setContentText("Canvas name:");
            newCanvasDialog.setHeaderText(null);
            newCanvasDialog.setGraphic(null);
            String canvasName = newCanvasDialog.showAndWait().orElse("").trim();
            if (!canvasName.isEmpty()) {
                if (!canvasName.equalsIgnoreCase("Main Canvas")) {
                    ProjectManager.getCurrentProject().createCanvas(canvasName);
                    VisualBukkit.displayMessage("Created canvas", "Successfully created canvas");
                } else {
                    VisualBukkit.displayError("Invalid canvas name", "There can only be one main canvas");
                }
            }
        });
        importItem.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("YAML", "*.yml"));
            File file = fileChooser.showOpenDialog(VisualBukkit.getInstance().getPrimaryStage());
            if (file != null) {
                DataFile dataFile = new DataFile(file.toPath());
                StatementDefinition<?> statement = BlockRegistry.getStatement(dataFile.getString("="));
                if (statement != null) {
                    add(statement.createBlock(dataFile), contextMenu.getX(), contextMenu.getY());
                } else {
                    VisualBukkit.displayError("Import failed", "Failed to import block");
                }
                VisualBukkit.displayMessage("Imported block", "Successfully imported block");
            }
        });
        contextMenu.getItems().addAll(name.equalsIgnoreCase("Main Canvas") ?
                new MenuItem[]{pasteItem, organizeItem, clearItem, newCanvasItem, importItem} :
                new MenuItem[]{pasteItem, organizeItem, clearItem, renameItem, deleteItem, newCanvasItem, importItem});
        setOnMousePressed(e -> ContextMenuManager.hide());
        setOnContextMenuRequested(e -> {
            contextMenu.getItems().set(0, CopyPasteManager.getCopied() instanceof StatementDefinition ? pasteItem : invalidPasteItem);
            ContextMenuManager.show(this, contextMenu, e);
        });
    }

    public void add(Node node) {
        innerPane.getChildren().add(node);
    }

    public void add(Node node, double screenX, double screenY) {
        innerPane.getChildren().add(node);
        Point2D point = innerPane.screenToLocal(screenX, screenY);
        node.relocate(point.getX(), point.getY());
    }

    public void remove(Node node) {
        innerPane.getChildren().remove(node);
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

    @SuppressWarnings("unchecked")
    public List<StructureBlock> getStructures() {
        return (List<StructureBlock>) (Object) innerPane.getChildren().filtered(node -> node instanceof StructureBlock);
    }

    public void saveTo(DataConfig config) {
        List<DataConfig> childConfigs = new ArrayList<>();
        for (Node child : innerPane.getChildren()) {
            if (child instanceof StatementBlock) {
                StatementBlock statement = (StatementBlock) child;
                while (statement != null) {
                    DataConfig childConfig = new DataConfig();
                    childConfig.set("=", statement.getIdentifier());
                    if (!statement.hasPrevious()) {
                        childConfig.set("x", statement.getLayoutX());
                        childConfig.set("y", statement.getLayoutY());
                    }
                    statement.saveTo(childConfig);
                    childConfigs.add(childConfig);
                    statement = statement.getNext();
                }
            }
        }
        config.set("children", childConfigs);
    }

    public void loadFrom(DataConfig config) {
        List<DataConfig> childConfigs = config.getConfigList("children");
        StatementBlock lastChild = null;
        for (DataConfig childConfig : childConfigs) {
            StatementDefinition<?> statement = BlockRegistry.getStatement(childConfig.getString("="));
            if (statement != null) {
                StatementBlock child = statement.createBlock(childConfig);
                if (!childConfig.contains("x") && lastChild != null) {
                    lastChild.connectNext(child);
                } else {
                    add(child);
                    child.relocate(childConfig.getDouble("x"), childConfig.getDouble("y"));
                }
                lastChild = child;
            }
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
