package us.donut.visualbukkit.editor;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.blocks.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SelectorPane extends VBox {

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
    private ChoiceBox<String> categoryChoiceBox = new ChoiceBox<>();
    private TextField searchField = new TextField();

    public SelectorPane() {
        VBox content = new VBox();
        ScrollPane scrollPane = new ScrollPane(content);
        getChildren().add(scrollPane);
        content.getStyleClass().add("selector-pane");
        content.prefWidthProperty().bind(widthProperty());
        content.prefHeightProperty().bind(heightProperty());
        DragManager.enableDragTarget(this);
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
        HBox categoryHBox = new HBox(10, new Label("Category:\t"), categoryChoiceBox);
        HBox searchHBox = new HBox(10, new Label("Search:\t"), searchField);
        categoryHBox.setAlignment(Pos.CENTER_LEFT);
        searchHBox.setAlignment(Pos.CENTER_LEFT);

        categoryChoiceBox.setFocusTraversable(false);
        categoryChoiceBox.getItems().add("All");
        categoryChoiceBox.setValue("All");
        categoryChoiceBox.setOnAction(e -> blockInfoNodes.forEach(this::updateVisibility));
        searchField.textProperty().addListener((observable, oldValue, newValue) -> blockInfoNodes.forEach(this::updateVisibility));

        content.getChildren().addAll(selectorTitle, categoryHBox, searchHBox, statementBox, expressionBox);

        for (BlockInfo<?> blockInfo : BlockRegistry.getAll()) {
            BlockInfo<?>.Node blockInfoNode = blockInfo.createNode();
            blockInfoNodes.add(blockInfoNode);

            String[] categories = blockInfo.getCategories();
            if (categories != null) {
                for (String category : blockInfo.getCategories()) {
                    if (!categoryChoiceBox.getItems().contains(category)) {
                        int i = categoryChoiceBox.getItems().filtered(item -> category.compareTo(item) > 0).size();
                        categoryChoiceBox.getItems().add(i, category);
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

    private void updateVisibility(BlockInfo<?>.Node blockInfoNode) {
        String category = categoryChoiceBox.getValue();
        String search = searchField.getText().toLowerCase();
        boolean state =
                (category.equalsIgnoreCase("All") || blockInfoNode.getBlockInfo().inCategory(category)) &&
                (search.isEmpty() || blockInfoNode.getText().toLowerCase().contains(search));
        blockInfoNode.setVisible(state);
        blockInfoNode.setManaged(state);
    }
}
