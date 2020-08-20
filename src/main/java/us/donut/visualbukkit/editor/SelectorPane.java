package us.donut.visualbukkit.editor;

import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.blocks.*;
import us.donut.visualbukkit.util.CenteredHBox;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

public class SelectorPane extends TabPane {

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

    public SelectorPane() {
        setSide(Side.LEFT);
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        Map<StatementCategory, Set<StatementLabel>> labels = new TreeMap<>();
        for (StatementDefinition<?> statement : BlockRegistry.getStatements()) {
            for (StatementCategory category : statement.getCategories()) {
                labels.computeIfAbsent(category, k -> new TreeSet<>(Comparator.comparing(Labeled::getText))).add(statement.createLabel());
            }
        }
        for (Map.Entry<StatementCategory, Set<StatementLabel>> entry : labels.entrySet()) {
            TextField searchField = new TextField();
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                newValue = newValue.toLowerCase();
                for (Label label : entry.getValue()) {
                    boolean state = newValue.isEmpty() || label.getText().toLowerCase().contains(newValue);
                    label.setVisible(state);
                    label.setManaged(state);
                }
            });
            Label titleLabel = new Label(entry.getKey().getLabel());
            titleLabel.setUnderline(true);
            VBox titleArea = new VBox(10, titleLabel, new CenteredHBox(10, new Label("Search:"), searchField));
            VBox labelArea = new VBox(10);
            labelArea.getChildren().addAll(entry.getValue());
            ScrollPane scrollPane = new ScrollPane(labelArea);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            Tab tab = new Tab(entry.getKey().getLabel(), new VBox(titleArea, scrollPane));
            VBox content = new VBox(titleArea, scrollPane);
            content.getStyleClass().add("selector-pane");
            tab.setContent(content);
            getTabs().add(tab);
        }

        setOnDragOver(e -> {
            Object source = e.getGestureSource();
            if (source instanceof StatementBlock) {
                e.acceptTransferModes(TransferMode.ANY);
            }
            e.consume();
        });

        setOnDragDropped(e -> {
            UndoManager.capture();
            ((StatementBlock) e.getGestureSource()).disconnect();
            e.setDropCompleted(true);
            e.consume();
        });

        setOnMousePressed(e -> ContextMenuManager.hide());
    }
}
