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
import us.donut.visualbukkit.plugin.modules.PluginModule;
import us.donut.visualbukkit.util.Loadable;

import java.util.*;

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
        if (!project.getTabPane().getTabs().contains(this)) {
            project.getTabPane().getTabs().add(this);
        }
    }

    public abstract void insertInto(CtClass mainClass) throws Exception;

    public Set<PluginModule> findUsedModules() {
        Set<PluginModule> modules = new HashSet<>();
        collectModules(blockArea, modules);
        return modules;
    }

    private void collectModules(Pane pane, Set<PluginModule> modules) {
        for (Node child : pane.getChildren()) {
            if (child instanceof CodeBlock) {
                CodeBlock block = (CodeBlock) child;
                PluginModule[] requiredModules = BlockRegistry.getInfo(block).getModules();
                if (requiredModules != null) {
                    Collections.addAll(modules, requiredModules);
                }
            }
            if (child instanceof Pane) {
                collectModules((Pane) child, modules);
            }
        }
    }

    @Override
    public void unload(ConfigurationSection section) {
        if (getTabPane() != null) {
            section.set("open", true);
        }
        List<StatementBlock> blocks = blockArea.getBlocks(false);
        ConfigurationSection blocksSection = section.createSection("blocks");
        for (int i = 0; i < blocks.size(); i++) {
            CodeBlock block = blocks.get(i);
            ConfigurationSection blockSection = blocksSection.createSection(String.valueOf(i));
            blockSection.set("block-type", block.getClass().getCanonicalName());
            block.unload(blockSection);
        }
    }

    @Override
    public void load(ConfigurationSection section) throws Exception {
        if (section.getBoolean("open")) {
            open();
        }
        ConfigurationSection blocksSection = section.getConfigurationSection("blocks");
        if (blocksSection != null) {
            for (String key : blocksSection.getKeys(false)) {
                ConfigurationSection blockSection = blocksSection.getConfigurationSection(key);
                if (blockSection != null) {
                    String blockType = blockSection.getString("block-type");
                    if (blockType != null) {
                        CodeBlock block = BlockRegistry.getInfo(blockType).createBlock();
                        block.load(blockSection);
                        blockArea.getChildren().add(block);
                    }
                }
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
