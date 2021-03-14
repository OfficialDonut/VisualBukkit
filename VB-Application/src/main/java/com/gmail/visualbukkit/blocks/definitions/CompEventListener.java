package com.gmail.visualbukkit.blocks.definitions;

import com.gmail.visualbukkit.blocks.BlockRegistry;
import com.gmail.visualbukkit.blocks.PluginComponent;
import com.gmail.visualbukkit.blocks.parameters.BlockParameter;
import com.gmail.visualbukkit.blocks.parameters.ChoiceParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import com.gmail.visualbukkit.plugin.PluginModule;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.json.JSONObject;

import java.util.*;

public class CompEventListener extends PluginComponent {

    private static String[] priorities = Arrays.stream(EventPriority.values()).map(Enum::name).sorted().toArray(String[]::new);
    private static Map<String, Class<? extends Event>> events = new HashMap<>();
    private static Map<Class<? extends Event>, PluginModule> pluginModules = new HashMap<>();
    private static Set<String> eventNames = new HashSet<>();

    public CompEventListener() {
        super("comp-event-listener");
    }

    @SuppressWarnings("unchecked")
    public static void registerEvent(JSONObject json) {
        registerEvent((Class<? extends Event>) BlockRegistry.getClass(json.optString("event")), PluginModule.get(json.optString("plugin-module")));
    }

    public static void registerEvent(Class<? extends Event> event, PluginModule module) {
        registerEvent(event);
        if (module != null) {
            pluginModules.put(event, module);
        }
    }

    public static void registerEvent(Class<? extends Event> event) {
        events.put(event.getSimpleName(), event);
        eventNames.add(event.getSimpleName());
    }

    @Override
    public Block createBlock() {
        ChoiceParameter eventChoice = new ChoiceParameter(eventNames);
        ChoiceParameter priorityChoice = new ChoiceParameter(priorities);
        priorityChoice.setValue("NORMAL");
        EventBlock block = new EventBlock(this, eventChoice, priorityChoice);
        block.getTab().textProperty().bind(eventChoice.valueProperty());
        return block;
    }

    public static class EventBlock extends PluginComponent.Block {

        public EventBlock(PluginComponent pluginComponent, BlockParameter... parameters) {
            super(pluginComponent, parameters);
        }

        @Override
        public void prepareBuild(BuildContext buildContext) {
            super.prepareBuild(buildContext);
            Class<?> event = getEvent();
            if (pluginModules.containsKey(event)) {
                buildContext.addPluginModule(pluginModules.get(event));
            }
            buildContext.getMetaData().increment("event-number");
            buildContext.getMainClass().addMethod(
                    "@EventHandler(priority=EventPriority." + arg(1) + ")" +
                    "public void on" + arg(0) + buildContext.getMetaData().getInt("event-number") + "(" + event.getCanonicalName() + " event) throws Exception {" +
                    buildContext.getLocalVariableDeclarations() +
                    getChildJava() +
                    "}");
        }

        public Class<? extends Event> getEvent() {
            return events.get(arg(0));
        }
    }
}
