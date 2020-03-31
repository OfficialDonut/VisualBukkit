package us.donut.visualbukkit.editor;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javassist.CtClass;
import org.bukkit.configuration.ConfigurationSection;
import us.donut.visualbukkit.blocks.*;
import us.donut.visualbukkit.plugin.PluginBuilder;
import us.donut.visualbukkit.util.Loadable;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockPane extends Tab implements Loadable {

    private Project project;
    private HBox infoArea = new HBox(15);
    private BlockArea blockArea = new BlockArea();
    private Label projectStructureLabel;

    public BlockPane(Project project, String tabText) {
        super(tabText);
        this.project = project;
        ScrollPane scrollPane = new ScrollPane(blockArea);
        VBox content = new VBox(infoArea, scrollPane);
        scrollPane.prefHeightProperty().bind(content.heightProperty());
        blockArea.prefWidthProperty().bind(scrollPane.widthProperty());
        blockArea.prefHeightProperty().bind(scrollPane.heightProperty());
        infoArea.getStyleClass().add("block-pane-info-area");
        projectStructureLabel = new Label(getText());
        projectStructureLabel.setOnMouseEntered(e -> projectStructureLabel.setStyle("-fx-text-fill: gold;"));
        projectStructureLabel.setOnMouseExited(e -> projectStructureLabel.setStyle(null));
        projectStructureLabel.setOnMouseClicked(e -> open());
        setContent(content);
    }

    public void open() {
        project.getTabPane().getTabs().add(this);
    }

    public abstract void insertInto(CtClass mainClass) throws Exception;

    @Override
    public void unload(ConfigurationSection section) {
        if (getTabPane() != null) {
            section.set("open", true);
        }
        List<StatementBlock> blocks = blockArea.getBlocks(false);
        for (int i = 0; i < blocks.size(); i++) {
            CodeBlock block = blocks.get(i);
            String className = block.getClass().getCanonicalName().replace('.', '_');
            block.unload(section.createSection("blocks." + i + className));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void load(ConfigurationSection section) throws Exception {
        if (section.getBoolean("open")) {
            open();
        }
        ConfigurationSection blockSection = section.getConfigurationSection("blocks");
        if (blockSection != null) {
            for (String key : blockSection.getKeys(false)) {
                String className = key.substring(1).replace('_', '.');
                Class<? extends CodeBlock> blockClass = (Class<? extends CodeBlock>) Class.forName(className);
                CodeBlock block = BlockRegistry.getInfo(blockClass).createBlock();
                block.load(blockSection.getConfigurationSection(key));
                blockArea.getChildren().add(block);
            }
        }
    }

    public Project getProject() {
        return project;
    }

    public HBox getInfoArea() {
        return infoArea;
    }

    public BlockArea getBlockArea() {
        return blockArea;
    }

    public Label getProjectStructureLabel() {
        return projectStructureLabel;
    }

    public class BlockArea extends VBox implements BlockContainer {

        private double contextMenuYCoord;

        public BlockArea() {
            getStyleClass().add("block-area");
            DragManager.enableBlockContainer(this);
            MenuItem pasteItem = new MenuItem("Paste");
            pasteItem.setOnAction(e -> CopyPasteManager.paste(this, contextMenuYCoord));
            ContextMenu contextMenu = new ContextMenu(pasteItem);
            setOnMouseClicked(e -> contextMenu.hide());
            setOnContextMenuRequested(e -> {
                contextMenu.show(this, e.getScreenX(), e.getScreenY());
                contextMenuYCoord = e.getY();
                e.consume();
            });
        }

        @Override
        public boolean canAccept(CodeBlock block, double yCoord) {
            boolean valid = false;
            if (block instanceof StatementBlock) {
                Pane parent = (Pane) block.getParent();
                int currentIndex = -1;
                if (parent != null) {
                    currentIndex = parent.getChildren().indexOf(block);
                    parent.getChildren().remove(currentIndex);
                }
                int index = DragManager.getIndexAt(this, yCoord);
                getChildren().add(index, block);
                valid = PluginBuilder.isCodeValid(block.getBlockPane());
                getChildren().remove(index);
                if (parent != null) {
                    parent.getChildren().add(currentIndex, block);
                }
            }
            return valid;
        }

        @Override
        public void accept(CodeBlock block, double yCoord) {
            UndoManager.capture();
            Pane parent = (Pane) block.getParent();
            if (parent != null) {
                parent.getChildren().remove(block);
            }
            getChildren().add(DragManager.getIndexAt(this, yCoord), block);
            Platform.runLater(block::onDragDrop);
        }

        @Override
        public List<StatementBlock> getBlocks(boolean ignoreDisabled) {
            List<StatementBlock> blocks = new ArrayList<>();
            for (Node child : blockArea.getChildren()) {
                if (child instanceof StatementBlock && (!ignoreDisabled || ((StatementBlock) child).isEnabled())) {
                    blocks.add((StatementBlock) child);
                }
            }
            return blocks;
        }

        public BlockPane getBlockPane() {
            return BlockPane.this;
        }
    }
}
