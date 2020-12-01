package com.gmail.visualbukkit.blocks;

import com.gmail.visualbukkit.blocks.annotations.Category;
import com.gmail.visualbukkit.blocks.components.ChoiceParameter;
import com.gmail.visualbukkit.plugin.BuildContext;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

import java.util.Arrays;
import java.util.Map;
import java.util.WeakHashMap;

@Category(Category.EVENTS)
public abstract class EventBlock extends StructureBlock {

    private static String[] priorities = Arrays.stream(EventPriority.values()).map(Enum::name).sorted().toArray(String[]::new);
    private static Map<BuildContext, Integer> eventCounters = new WeakHashMap<>();

    private Class<? extends Event> event;
    private ChoiceParameter priorityChoice = new ChoiceParameter(priorities);

    public EventBlock(Class<? extends Event> event) {
        this.event = event;
        priorityChoice.setValue("NORMAL");
        init(event.getSimpleName(), " with priority ", priorityChoice);
    }

    @Override
    public void prepareBuild(BuildContext context) {
        context.getMainClass().addMethod(
                "@EventHandler(priority=EventPriority." + priorityChoice.toJava() + ")" +
                "public void on" + event.getSimpleName() + eventCounters.computeIfAbsent(context, c -> 1) + "(" + event.getCanonicalName() + " event) throws Exception {" +
                getChildJava() +
                "}");
        eventCounters.put(context, eventCounters.get(context) + 1);
    }

    public Class<? extends Event> getEvent() {
        return event;
    }
}
