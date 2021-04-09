package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockRegistry;
import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.PluginComponent;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.gui.StyleableHBox;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.PopOver;
import org.json.JSONObject;

import java.util.*;
import java.util.function.Predicate;

public class CompEventListener extends PluginComponent {

    private static String[] priorities = {"HIGH", "HIGHEST", "LOW", "LOWEST", "MONITOR", "NORMAL"};
    private static Map<String, ClassInfo> events = new HashMap<>();
    private static Map<String, String> eventCategories = new HashMap<>();
    private static Map<String, PluginModule> eventModules = new HashMap<>();
    private static Set<String> eventNames = new TreeSet<>();

    public CompEventListener() {
        super("comp-event-listener");
    }

    public static void registerEvent(JSONObject json) {
        ClassInfo event = ClassInfo.of(json.getString("event"));
        String category = BlockRegistry.getString(json.getString("id"), "category", null);
        PluginModule module = PluginModule.get(json.optString("plugin-module"));
        registerEvent(event, category, module);
    }

    public static void registerEvent(ClassInfo event, String category, PluginModule module) {
        events.put(event.getDisplayClassName(), event);
        eventNames.add(event.getDisplayClassName());
        if (category != null) {
            eventCategories.put(event.getDisplayClassName(), category);
        }
        if (module != null) {
            eventModules.put(event.getDisplayClassName(), module);
        }
    }

    @Override
    public Block createBlock() {
        return new EventBlock(new ChoiceParameter(eventNames), new ChoiceParameter(priorities));
    }

    public class EventBlock extends PluginComponent.Block {

        public EventBlock(ChoiceParameter eventChoice, ChoiceParameter priorityChoice) {
            super(CompEventListener.this, eventChoice, priorityChoice);
            getTab().textProperty().bind(eventChoice.valueProperty());
            priorityChoice.setValue("NORMAL");
            eventChoice.setOnShown(e -> eventChoice.hide());

            TreeSet<String> categories = new TreeSet<>(eventCategories.values());
            ObservableList<String> events = FXCollections.observableArrayList(eventNames);
            FilteredList<String> eventList = new FilteredList<>(events);
            ListView<String> listView = new ListView<>(eventList);

            TextField searchField = new TextField();
            ComboBox<String> categoryBox = new ComboBox<>();
            categories.add(VisualBukkitApp.getString("label.all"));
            categoryBox.getItems().addAll(categories);

            Predicate<String> filter = event ->
                    StringUtils.containsIgnoreCase(event, searchField.getText())
                    && (categoryBox.getSelectionModel().getSelectedIndex() == 0 || categoryBox.getValue().equals(eventCategories.get(event)));

            searchField.textProperty().addListener((o, oldValue, newValue) -> eventList.setPredicate(filter::test));
            categoryBox.valueProperty().addListener((o, oldValue, newValue) -> eventList.setPredicate(filter::test));

            PopOver selector = new PopOver(new VBox(new StyleableHBox(new Label(VisualBukkitApp.getString("label.search")), searchField, categoryBox), listView));
            selector.setArrowLocation(PopOver.ArrowLocation.TOP_LEFT);
            selector.setAnimated(false);
            selector.setOnShowing(e -> {
                searchField.clear();
                categoryBox.getSelectionModel().selectFirst();
                listView.getSelectionModel().clearSelection();
            });

            listView.setPlaceholder(new Label(VisualBukkitApp.getString("label.empty_list")));
            listView.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
                if (newValue != null) {
                    eventChoice.setValue(newValue);
                    selector.hide();
                }
            });

            eventChoice.valueProperty().addListener((o, oldValue, newValue) -> {
                if (newValue != null) {
                    update();
                }
            });

            eventChoice.setOnMouseClicked(e -> {
                if (e.getButton() == MouseButton.PRIMARY && !selector.isShowing()) {
                    selector.show(eventChoice);
                }
                e.consume();
            });
        }

        @Override
        public void prepareBuild(BuildContext buildContext) {
            super.prepareBuild(buildContext);
            String event = arg(0);
            if (eventModules.containsKey(event)) {
                buildContext.addPluginModule(eventModules.get(event));
            }
            buildContext.getMetaData().increment("event-number");
            buildContext.getMainClass().addMethod(
                    "@EventHandler(priority=EventPriority." + arg(1) + ")" +
                    "public void on" + event + buildContext.getMetaData().getInt("event-number") + "(" + getEvent().getCanonicalClassName() + " event) throws Exception {" +
                    buildContext.getLocalVariableDeclarations() +
                    getChildJava() +
                    "}");
        }

        public ClassInfo getEvent() {
            return events.get(arg(0));
        }
    }
}
