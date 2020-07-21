package us.donut.visualbukkit.editor;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.bukkit.configuration.file.YamlConfiguration;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.VisualBukkitLauncher;
import us.donut.visualbukkit.blocks.*;
import us.donut.visualbukkit.blocks.syntax.ExpressionParameter;
import us.donut.visualbukkit.plugin.PluginBuilder;
import us.donut.visualbukkit.util.CenteredHBox;
import us.donut.visualbukkit.util.ComboBoxView;
import us.donut.visualbukkit.util.TitleLabel;
import us.donut.visualbukkit.util.TreeNode;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectorPane extends VBox implements BlockContainer {

    static {
        try {
            Tooltip tooltip = new Tooltip();
            Class<?> clazz = Class.forName("javafx.scene.control.Tooltip$TooltipBehavior");
            Constructor<?> constructor = clazz.getDeclaredConstructor(Duration.class, Duration.class, Duration.class, boolean.class);
            constructor.setAccessible(true);
            Object tooltipBehavior = constructor.newInstance(new Duration(150), new Duration(60000), new Duration(0), false);
            Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
            fieldBehavior.setAccessible(true);
            fieldBehavior.set(tooltip, tooltipBehavior);
        } catch (Exception e) {
            VisualBukkit.displayException("Failed to modify tooltips", e);
        }
    }

    private List<BlockInfo<?>.Node> blockInfoNodes = new ArrayList<>();
    private ComboBoxView<String> categoryComboBox = new ComboBoxView<>();
    private ComboBoxView<Class<?>> eventComboBox = new ComboBoxView<>();
    private ComboBoxView<String> returnTypeComboBox = new ComboBoxView<>();
    private CheckBox statementCheckBox = new CheckBox("Statements");
    private CheckBox expressionCheckBox = new CheckBox("Expressions");
    private TreeNode pinnedBlocks = new TreeNode("Pinned Blocks");
    private TextField searchField = new TextField();

    public SelectorPane() {
        VBox blockSelector = new VBox();
        VBox blocksArea = new VBox();
        blockSelector.getStyleClass().add("selector-pane");
        blocksArea.getStyleClass().add("selector-pane");
        backgroundProperty().bind(blockSelector.backgroundProperty());
        ScrollPane scrollPane = new ScrollPane(blocksArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        getChildren().addAll(blockSelector, new Separator(), scrollPane);
        DragManager.enableBlockContainer(this);

        TitleLabel selectorTitle = new TitleLabel("Block Selector", 1.5, true);
        TitleLabel statementTitle = new TitleLabel("Statements", 1.5, true);
        TitleLabel expressionTitle = new TitleLabel("Expressions", 1.5, true);
        VBox statementBox = new VBox(10, statementTitle);
        VBox expressionBox = new VBox(10, expressionTitle);
        statementBox.setFillWidth(false);
        expressionBox.setFillWidth(false);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> blockInfoNodes.forEach(this::updateVisibility));

        categoryComboBox.setFocusTraversable(false);
        categoryComboBox.getComboBox().getItems().add("---");
        categoryComboBox.getComboBox().setValue("---");
        categoryComboBox.getComboBox().valueProperty().addListener((observable, oldValue, newValue) -> blockInfoNodes.forEach(this::updateVisibility));

        eventComboBox.getComboBox().setConverter(new StringConverter<Class<?>>() {
            @Override
            public String toString(Class<?> clazz) {
                return clazz == Any.class ? "---" : clazz != null ? clazz.getSimpleName() : null;
            }
            @Override
            public Class<?> fromString(String string) {
                return null;
            }
        });
        eventComboBox.setFocusTraversable(false);
        eventComboBox.getComboBox().getItems().add(Any.class);
        eventComboBox.getComboBox().getItems().addAll(EventPane.EVENTS);
        eventComboBox.getComboBox().setValue(Any.class);
        eventComboBox.getComboBox().valueProperty().addListener((observable, oldValue, newValue) -> blockInfoNodes.forEach(this::updateVisibility));

        returnTypeComboBox.setFocusTraversable(false);
        returnTypeComboBox.getComboBox().getItems().add("---");
        returnTypeComboBox.getComboBox().getItems().addAll(TypeHandler.getAliases());
        returnTypeComboBox.getComboBox().setValue("---");
        returnTypeComboBox.getComboBox().valueProperty().addListener((observable, oldValue, newValue) -> blockInfoNodes.forEach(this::updateVisibility));

        statementCheckBox.setFocusTraversable(false);
        statementCheckBox.setSelected(true);
        statementCheckBox.setOnAction(e -> {
            boolean state = statementCheckBox.isSelected();
            statementBox.setVisible(state);
            statementBox.setManaged(state);
        });

        expressionCheckBox.setFocusTraversable(false);
        expressionCheckBox.setSelected(true);
        expressionCheckBox.setOnAction(e -> {
            boolean state = expressionCheckBox.isSelected();
            expressionBox.setVisible(state);
            expressionBox.setManaged(state);
        });

        for (String blockType : VisualBukkitLauncher.DATA_FILE.getConfig().getStringList("pinned-blocks")) {
            BlockInfo<?> blockInfo = BlockRegistry.getInfo(blockType);
            if (blockInfo != null) {
                pin(blockInfo);
            } else {
                VisualBukkit.displayError("Failed to load pinned block " + blockType);
            }
        }

        blockSelector.getChildren().addAll(selectorTitle,
                new CenteredHBox(10, new Label("Category:"), categoryComboBox),
                new CenteredHBox(10, new Label("Event:   "), eventComboBox),
                new CenteredHBox(10, new Label("Returns: "), returnTypeComboBox),
                new CenteredHBox(10, new Label("Type:    "), statementCheckBox),
                new CenteredHBox(10, new Label("         "), expressionCheckBox),
                new CenteredHBox(10, new Label("Search:  "), searchField),
                pinnedBlocks);

        blocksArea.getChildren().addAll(statementBox, expressionBox);

        for (BlockInfo<?> blockInfo : BlockRegistry.getAll()) {
            BlockInfo<?>.Node blockInfoNode = blockInfo.createNode();
            blockInfoNodes.add(blockInfoNode);

            String[] categories = blockInfo.getCategories();
            if (categories != null) {
                for (String category : blockInfo.getCategories()) {
                    if (!categoryComboBox.getComboBox().getItems().contains(category)) {
                        int i = categoryComboBox.getComboBox().getItems().filtered(item -> category.compareTo(item) > 0).size();
                        categoryComboBox.getComboBox().getItems().add(i, category);
                    }
                }
            }

            VBox vBox = StatementBlock.class.isAssignableFrom(blockInfo.getBlockType()) ? statementBox : expressionBox;
            int i = 1 + vBox.getChildren()
                    .filtered(node -> node instanceof BlockInfo.Node && blockInfo.getName().compareTo(((BlockInfo<?>.Node) node).getText()) > 0)
                    .size();
            vBox.getChildren().add(i, blockInfoNode);

            MenuItem pinItem = new MenuItem("Pin");
            pinItem.setOnAction(e -> {
                pin(blockInfo);
                YamlConfiguration config = VisualBukkitLauncher.DATA_FILE.getConfig();
                List<String> pinned = config.getStringList("pinned-blocks");
                pinned.add(blockInfo.getBlockType().getCanonicalName());
                config.set("pinned-blocks", pinned);
            });
            ContextMenu contextMenu = new ContextMenu(pinItem);
            blockInfoNode.setOnContextMenuRequested(e -> {
                contextMenu.show(blockInfoNode, e.getScreenX(), e.getScreenY());
                e.consume();
            });
        }
    }

    @Override
    public boolean canAccept(CodeBlock block, double yCoord) {
        boolean valid = false;
        Pane parent = (Pane) block.getParent();
        if (parent != null) {
            BlockPane blockPane = block.getBlockPane();
            if (parent instanceof ExpressionParameter) {
                ExpressionParameter expressionParameter = (ExpressionParameter) parent;
                expressionParameter.setExpression(null);
                valid = PluginBuilder.isCodeValid(blockPane);
                expressionParameter.setExpression((ExpressionBlock<?>) block);
            } else {
                int currentIndex = parent.getChildren().indexOf(block);
                parent.getChildren().remove(block);
                valid = PluginBuilder.isCodeValid(blockPane);
                parent.getChildren().add(currentIndex, block);
            }
        }
        return valid;
    }

    @Override
    public void accept(CodeBlock block, double yCoord) {
        UndoManager.capture();
        Parent parent = block.getParent();
        if (parent instanceof ExpressionParameter) {
            ((ExpressionParameter) parent).setExpression(null);
        } else if (parent instanceof Pane) {
            ((Pane) parent).getChildren().remove(block);
        }
        Platform.runLater(block::onDragDrop);
    }

    @Override
    public List<? extends CodeBlock> getBlocks(boolean ignoreDisabled) {
        return Collections.emptyList();
    }

    public ComboBoxView<String> getCategoryComboBox() {
        return categoryComboBox;
    }

    public ComboBoxView<Class<?>> getEventComboBox() {
        return eventComboBox;
    }

    public ComboBoxView<String> getReturnTypeComboBox() {
        return returnTypeComboBox;
    }

    public CheckBox getStatementCheckBox() {
        return statementCheckBox;
    }

    public CheckBox getExpressionCheckBox() {
        return expressionCheckBox;
    }

    private void pin(BlockInfo<?> blockInfo) {
        BlockInfo<?>.Node node = blockInfo.createNode();
        pinnedBlocks.add(node);
        MenuItem unpinItem = new MenuItem("Unpin");
        unpinItem.setOnAction(e -> {
            pinnedBlocks.remove(node);
            YamlConfiguration config = VisualBukkitLauncher.DATA_FILE.getConfig();
            List<String> pinned = config.getStringList("pinned-blocks");
            pinned.remove(blockInfo.getBlockType().getCanonicalName());
            config.set("pinned-blocks", pinned);
        });
        ContextMenu contextMenu = new ContextMenu(unpinItem);
        node.setOnContextMenuRequested(e -> {
            contextMenu.show(node, e.getScreenX(), e.getScreenY());
            e.consume();
        });
    }

    private void updateVisibility(BlockInfo<?>.Node blockInfoNode) {
        BlockInfo<?> blockInfo = blockInfoNode.getBlockInfo();
        String category = categoryComboBox.getComboBox().getValue();
        Class<?> event = eventComboBox.getComboBox().getValue();
        String returnType = returnTypeComboBox.getComboBox().getValue();
        String search = searchField.getText().toLowerCase();
        boolean state =
                (category.equals("---") || checkCategory(blockInfo, category)) &&
                (event == Any.class || checkEvent(blockInfo, event)) &&
                (returnType.equals("---") || checkReturnType(blockInfo, TypeHandler.getType(returnType))) &&
                (search.isEmpty() || blockInfoNode.getText().toLowerCase().contains(search));
        blockInfoNode.setVisible(state);
        blockInfoNode.setManaged(state);
    }

    private boolean checkCategory(BlockInfo<?> blockInfo, String category) {
        if (blockInfo.getCategories() != null) {
            for (String blockCategory : blockInfo.getCategories()) {
                if (blockCategory.equalsIgnoreCase(category)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkEvent(BlockInfo<?> blockInfo, Class<?> clazz) {
        if (blockInfo.getEvents() != null) {
            for (Class<?> event : blockInfo.getEvents()) {
                if (event.isAssignableFrom(clazz)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkReturnType(BlockInfo<?> blockInfo, Class<?> returnType) {
        Class<?> blockReturn = blockInfo.getReturnType();
        return blockReturn != null &&
                (returnType.isAssignableFrom(blockReturn) ||
                (TypeHandler.isNumber(returnType) && TypeHandler.isNumber(blockReturn)) ||
                (blockReturn == boolean.class && returnType == Boolean.class));
    }

    private static class Any {}
}
