package us.donut.visualbukkit.editor;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.StringConverter;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.blocks.*;
import us.donut.visualbukkit.blocks.syntax.ExpressionParameter;
import us.donut.visualbukkit.plugin.PluginBuilder;
import us.donut.visualbukkit.util.ResizingComboBox;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SelectorPane extends VBox implements BlockContainer {

    static {
        try {
            Tooltip tooltip = new Tooltip();
            Class<?> clazz = tooltip.getClass().getDeclaredClasses()[0];
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
    private ResizingComboBox<String> categoryComboBox = new ResizingComboBox<>();
    private ResizingComboBox<Class<?>> eventComboBox = new ResizingComboBox<>();
    private TextField searchField = new TextField();

    public SelectorPane() {
        VBox content = new VBox();
        ScrollPane scrollPane = new ScrollPane(content);
        getChildren().add(scrollPane);
        content.getStyleClass().add("selector-pane");
        content.prefWidthProperty().bind(widthProperty());
        content.prefHeightProperty().bind(heightProperty());
        DragManager.enableBlockContainer(this);
        Label selectorTitle = new Label("Block Selector");
        Label statementTitle = new Label("Statements");
        Label expressionTitle = new Label("Expressions");
        selectorTitle.getStyleClass().add("title-label");
        statementTitle.getStyleClass().add("title-label");
        expressionTitle.getStyleClass().add("title-label");
        VBox statementBox = new VBox(10, statementTitle);
        VBox expressionBox = new VBox(10, expressionTitle);
        statementBox.setFillWidth(false);
        expressionBox.setFillWidth(false);
        HBox categoryHBox = new HBox(10, new Label("Category:\t"), categoryComboBox);
        HBox eventHBox = new HBox(10, new Label("Event:\t"), eventComboBox);
        HBox searchHBox = new HBox(10, new Label("Search:\t"), searchField);
        categoryHBox.setAlignment(Pos.CENTER_LEFT);
        eventHBox.setAlignment(Pos.CENTER_LEFT);
        searchHBox.setAlignment(Pos.CENTER_LEFT);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> blockInfoNodes.forEach(this::updateVisibility));

        categoryComboBox.setFocusTraversable(false);
        categoryComboBox.getItems().add("---");
        categoryComboBox.setValue("---");
        categoryComboBox.setOnAction(e -> blockInfoNodes.forEach(this::updateVisibility));

        eventComboBox.setFocusTraversable(false);
        eventComboBox.getItems().add(Any.class);
        eventComboBox.getItems().addAll(EventPane.EVENTS);
        eventComboBox.setValue(Any.class);
        eventComboBox.setOnAction(e -> blockInfoNodes.forEach(this::updateVisibility));
        eventComboBox.setConverter(new StringConverter<Class<?>>() {
            @Override
            public String toString(Class<?> clazz) {
                return clazz == Any.class ? "---" : clazz != null ? clazz.getSimpleName() : null;
            }
            @Override
            public Class<?> fromString(String string) {
                return null;
            }
        });

        content.getChildren().addAll(selectorTitle, categoryHBox, eventHBox, searchHBox, statementBox, expressionBox);

        for (BlockInfo<?> blockInfo : BlockRegistry.getAll()) {
            BlockInfo<?>.Node blockInfoNode = blockInfo.createNode();
            blockInfoNodes.add(blockInfoNode);

            String[] categories = blockInfo.getCategories();
            if (categories != null) {
                for (String category : blockInfo.getCategories()) {
                    if (!categoryComboBox.getItems().contains(category)) {
                        int i = categoryComboBox.getItems().filtered(item -> category.compareTo(item) > 0).size();
                        categoryComboBox.getItems().add(i, category);
                    }
                }
            }

            VBox vBox = StatementBlock.class.isAssignableFrom(blockInfo.getType()) ? statementBox : expressionBox;
            int i = 1 + vBox.getChildren()
                    .filtered(node -> node instanceof BlockInfo.Node && blockInfo.getName().compareTo(((BlockInfo<?>.Node) node).getText()) > 0)
                    .size();
            vBox.getChildren().add(i, blockInfoNode);
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
                expressionParameter.setExpression((ExpressionBlock) block);
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

    private void updateVisibility(BlockInfo<?>.Node blockInfoNode) {
        BlockInfo<?> blockInfo = blockInfoNode.getBlockInfo();
        String category = categoryComboBox.getValue();
        Class<?> event = eventComboBox.getValue();
        String search = searchField.getText().toLowerCase();
        boolean state =
                (category.equalsIgnoreCase("---") || checkCategory(blockInfo, category)) &&
                (event == Any.class || checkEvent(blockInfo, event)) &&
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

    private static class Any {}
}
