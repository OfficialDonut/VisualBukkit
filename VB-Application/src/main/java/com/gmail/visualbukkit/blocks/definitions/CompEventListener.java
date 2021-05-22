package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.ClassInfo;
import com.gmail.visualbukkit.blocks.PluginComponent;
import com.gmail.visualbukkit.blocks.Statement;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.project.BuildContext;
import com.gmail.visualbukkit.project.PluginModule;
import com.gmail.visualbukkit.ui.LanguageManager;
import com.gmail.visualbukkit.ui.StyleableHBox;
import com.gmail.visualbukkit.ui.StyleableVBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.PopOver;
import org.json.JSONObject;

import java.util.*;
import java.util.function.Predicate;

public class CompEventListener extends PluginComponent {

    private static Map<String, JSONObject> eventMap = new TreeMap<>();
    private static String[] priorities = {"HIGH", "HIGHEST", "LOW", "LOWEST", "MONITOR", "NORMAL"};

    public CompEventListener() {
        super("comp-event-listener");
    }

    @Override
    public Block createBlock() {
        return new EventBlock(new ChoiceParameter(eventMap.keySet()), new ChoiceParameter(priorities));
    }

    public static void addEvent(JSONObject json) {
        eventMap.put(ClassInfo.of(json.getString("event")).getDisplayClassName(), json);
    }

    public static void clearEvents() {
        eventMap.clear();
    }

    public class EventBlock extends PluginComponent.Block {

        public EventBlock(ChoiceParameter eventChoice, ChoiceParameter priorityChoice) {
            super(CompEventListener.this, eventChoice, priorityChoice);
            getTab().textProperty().bind(eventChoice.valueProperty());
            priorityChoice.setValue("NORMAL");
            eventChoice.setOnShown(e -> eventChoice.hide());

            ObservableList<String> events = FXCollections.observableArrayList(eventMap.keySet());
            FilteredList<String> eventList = new FilteredList<>(events);
            ListView<String> listView = new ListView<>(eventList);

            TextField searchField = new TextField();
            Predicate<String> filter = event -> StringUtils.containsIgnoreCase(event, searchField.getText());
            searchField.textProperty().addListener((o, oldValue, newValue) -> eventList.setPredicate(filter::test));

            PopOver selector = new PopOver(new StyleableVBox(new StyleableHBox(new Label(LanguageManager.get("label.search")), searchField), listView));
            selector.getStyleClass().add("popover-selector");
            selector.setOnShowing(e -> listView.getSelectionModel().clearSelection());
            selector.setArrowLocation(PopOver.ArrowLocation.TOP_LEFT);
            selector.setAnimated(false);

            listView.setPlaceholder(new Label(LanguageManager.get("label.empty_list")));
            listView.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> {
                if (newValue != null) {
                    eventChoice.setValue(newValue);
                    selector.hide();
                }
            });

            eventChoice.valueProperty().addListener((o, oldValue, newValue) -> {
                if (newValue != null) {
                    for (Statement.Block block : getStatementHolder().getBlocks()) {
                        block.update();
                    }
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
            JSONObject json = eventMap.get(arg(0));
            PluginModule module = PluginModule.get(json.optString("module"));
            if (module != null) {
                buildContext.addPluginModule(module);
            }
            buildContext.getMetadata().increment("event-number");
            buildContext.getMainClass().addMethod(
                    "@EventHandler(priority=EventPriority." + arg(1) + ")" +
                    "public void on" + arg(0) + buildContext.getMetadata().getInt("event-number") + "(" + json.getString("event") + " event) throws Exception {" +
                    buildContext.getLocalVariableDeclarations() +
                    toJava() +
                    "}");
        }

        public ClassInfo getEvent() {
            return ClassInfo.of(eventMap.get(arg(0)).getString("event"));
        }
    }
}
