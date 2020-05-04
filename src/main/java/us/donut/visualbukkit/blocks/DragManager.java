package us.donut.visualbukkit.blocks;

import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class DragManager {

    private static Map<Dragboard, CodeBlock> blockTransfers = new HashMap<>();
    private static Map<BlockContainer, Map<Integer, Boolean>> validationCache = new HashMap<>();

    public static void enableDragging(Node node) {
        if (!(node instanceof CodeBlock) && !(node instanceof BlockInfo.Node)) {
            throw new IllegalArgumentException();
        }

        node.setOnDragDetected(e -> {
            ClipboardContent content = new ClipboardContent();
            content.putString("block-transfer");
            Dragboard dragboard = node.startDragAndDrop(TransferMode.MOVE);
            SnapshotParameters snapshotParameters = new SnapshotParameters();
            snapshotParameters.setFill(Color.TRANSPARENT);
            dragboard.setDragView(node.snapshot(snapshotParameters, null));
            dragboard.setContent(content);
            e.consume();
        });

        node.setOnDragDone(e -> {
            blockTransfers.remove(e.getDragboard());
            validationCache.clear();
        });
    }

    public static <T extends Pane & BlockContainer> void enableBlockContainer(T container) {
        container.setOnDragOver(e -> {
            int cacheIndex = getIndexAt(container, e.getY());
            Map<Integer, Boolean> cache = validationCache.computeIfAbsent(container, c -> new HashMap<>());
            Boolean cachedValue = cache.get(cacheIndex);
            if (cachedValue == null) {
                Object source = e.getGestureSource();
                CodeBlock block =
                        source instanceof CodeBlock ? (CodeBlock) source :
                        source instanceof BlockInfo.Node ? ((BlockInfo<?>.Node) source).getBlockInfo().createBlock() : null;
                if (block != null && !block.equals(container) && !(isChild(container, block))) {
                    boolean valid = container.canAccept(block, e.getY());
                    cache.put(cacheIndex, valid);
                    if (valid) {
                        e.acceptTransferModes(TransferMode.MOVE);
                        blockTransfers.put(e.getDragboard(), block);
                    }
                }
            } else if (cachedValue) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
            e.consume();
        });

        container.setOnDragDropped(e -> {
            container.accept(blockTransfers.get(e.getDragboard()), e.getY());
            e.setDropCompleted(true);
            e.consume();
        });
    }

    public static int getIndexAt(Pane pane, double y) {
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
