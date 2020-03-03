package us.donut.visualbukkit.blocks;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import us.donut.visualbukkit.blocks.syntax.ExpressionParameter;
import us.donut.visualbukkit.editor.BlockPane;
import us.donut.visualbukkit.editor.SelectorPane;
import us.donut.visualbukkit.plugin.PluginBuilder;

import java.util.HashMap;
import java.util.Map;

public class DragManager {

    private static Map<Dragboard, CodeBlock> blockTransfers = new HashMap<>();
    private static Map<Pane, Map<Integer, Boolean>> validationCache = new HashMap<>();

    public static void enableDragging(Node node) {
        if (!(node instanceof CodeBlock) && !(node instanceof BlockInfo.Node)) {
            throw new IllegalArgumentException();
        }

        node.setOnDragDetected(e -> {
            ClipboardContent content = new ClipboardContent();
            content.putString("block-transfer");
            Dragboard dragboard = node.startDragAndDrop(TransferMode.MOVE);
            dragboard.setDragView(node.snapshot(null, null));
            dragboard.setContent(content);
            e.consume();
        });

        node.setOnDragDone(e -> {
            blockTransfers.remove(e.getDragboard());
            validationCache.clear();
        });
    }

    public static void enableDragTarget(Pane pane) {
        if (!(pane instanceof ParentBlock) && !(pane instanceof ExpressionParameter) &&
                !(pane instanceof BlockPane.BlockArea) && !(pane instanceof SelectorPane)) {
            throw new IllegalArgumentException();
        }

        pane.setOnDragOver(e -> {
            int cacheIndex = getIndexAt(pane, e.getY());
            Map<Integer, Boolean> cache = validationCache.computeIfAbsent(pane, p -> new HashMap<>());
            Boolean cachedValue = cache.get(cacheIndex);
            if (cachedValue != null) {
                if (cachedValue) {
                    e.acceptTransferModes(TransferMode.MOVE);
                }
                return;
            }

            Object source = e.getGestureSource();
            CodeBlock block =
                    source instanceof CodeBlock ? (CodeBlock) source :
                    source instanceof BlockInfo.Node ? ((BlockInfo<?>.Node) source).getBlockInfo().createBlock() : null;
            if (block != null && !block.equals(pane) && !(isChild(pane, block))) {
                boolean valid = canMove(block, pane, e.getY());
                cache.put(cacheIndex, valid);
                if (valid) {
                    e.acceptTransferModes(TransferMode.MOVE);
                    blockTransfers.put(e.getDragboard(), block);
                }
            }
            e.consume();
        });

        pane.setOnDragDropped(e -> {
            move(blockTransfers.get(e.getDragboard()), pane, e.getY());
            e.setDropCompleted(true);
            e.consume();
        });
    }

    public static boolean canMove(CodeBlock block, Pane pane, double yCoord) {
        boolean valid = false;
        if (pane instanceof SelectorPane) {
            Pane parent = (Pane) block.getParent();
            if (parent != null) {
                BlockPane blockPane = block.getBlockPane();
                if (parent instanceof ExpressionParameter) {
                    ExpressionParameter expressionParameter = (ExpressionParameter) parent;
                    expressionParameter.setExpression(null);
                    valid = PluginBuilder.isCodeValid(blockPane);
                    expressionParameter.setExpression((ExpressionBlock) block);
                } else {
                    int currentIndex = parent.getChildren().indexOf(block);
                    parent.getChildren().remove(block);
                    valid = PluginBuilder.isCodeValid(blockPane);
                    parent.getChildren().add(currentIndex, block);
                }
            }
        } else if (pane instanceof ExpressionParameter) {
            if (block instanceof ExpressionBlock) {
                ExpressionParameter expressionParameter = (ExpressionParameter) pane;
                ExpressionParameter parent = (ExpressionParameter) block.getParent();
                ExpressionBlock newExpression = (ExpressionBlock) block;
                ExpressionBlock currentExpression = expressionParameter.getExpression();
                if (parent != null) {
                    parent.setExpression(null);
                }
                expressionParameter.setExpression(newExpression);
                valid = PluginBuilder.isCodeValid(block.getBlockPane());
                expressionParameter.setExpression(currentExpression);
                if (parent != null) {
                    parent.setExpression(newExpression);
                }
            }
        } else if (block instanceof StatementBlock) {
            Pane parent = (Pane) block.getParent();
            int currentIndex = -1;
            if (parent != null) {
                currentIndex = parent.getChildren().indexOf(block);
                parent.getChildren().remove(currentIndex);
            }
            int index = getIndexAt(pane, yCoord);
            pane.getChildren().add(index, block);
            valid = PluginBuilder.isCodeValid(block.getBlockPane());
            pane.getChildren().remove(index);
            if (parent != null) {
                parent.getChildren().add(currentIndex, block);
            }
        }
        return valid;
    }

    public static void move(CodeBlock block, Pane pane, double yCoord) {
        UndoManager.capture();
        if (pane instanceof SelectorPane) {
            Parent parent = block.getParent();
            if (parent instanceof ExpressionParameter) {
                ((ExpressionParameter) parent).setExpression(null);
            } else if (parent instanceof Pane) {
                ((Pane) parent).getChildren().remove(block);
            }
        } else if (pane instanceof ExpressionParameter) {
            ExpressionParameter parent = (ExpressionParameter) block.getParent();
            if (parent != null) {
                parent.setExpression(null);
            }
            ((ExpressionParameter) pane).setExpression((ExpressionBlock) block);
        } else {
            Pane parent = (Pane) block.getParent();
            if (parent != null) {
                parent.getChildren().remove(block);
            }
            pane.getChildren().add(getIndexAt(pane, yCoord), block);
        }
        Platform.runLater(block::onDragDrop);
    }

    private static int getIndexAt(Pane pane, double y) {
        for (int i = 0; i < pane.getChildren().size(); i++) {
            Node node = pane.getChildren().get(i);
            if (node instanceof CodeBlock && node.getLayoutY() > y) {
                return i;
            }
        }
        return pane.getChildren().isEmpty() ? 0 : pane.getChildren().size();
    }

    private static boolean isChild(Pane child, Pane parent) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            if (child.equals(node) || (node instanceof Pane && isChild(child, (Pane) node))) {
                return true;
            }
        }
        return false;
    }
}
