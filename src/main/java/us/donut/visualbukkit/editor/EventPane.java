package us.donut.visualbukkit.editor;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.EnumMemberValue;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.reflections.Reflections;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.plugin.modules.PluginModule;
import us.donut.visualbukkit.plugin.modules.classes.PlaceholderEvent;
import us.donut.visualbukkit.util.CenteredHBox;
import us.donut.visualbukkit.util.TitleLabel;

import java.lang.reflect.Modifier;
import java.util.*;

public class EventPane extends BlockPane {

    public static final Class<?>[] EVENTS;

    static {
        Reflections reflections = new Reflections("org.bukkit.event");
        Set<Class<?>> events = new TreeSet<>(Comparator.comparing(Class::getSimpleName));
        for (Class<? extends Event> clazz : reflections.getSubTypesOf(Event.class)) {
            if (!Modifier.isAbstract(clazz.getModifiers()) && !clazz.isAnnotationPresent(Deprecated.class)) {
                events.add(clazz);
            }
        }
        events.add(PlaceholderEvent.class);
        EVENTS = events.toArray(new Class<?>[0]);
    }

    @SuppressWarnings("unchecked")
    public static void promptNew(Project project) {
        ChoiceDialog<Class<?>> dialog = new ChoiceDialog<>();
        ComboBox<Class<?>> comboBox = (ComboBox<Class<?>>) ((GridPane) dialog.getDialogPane().getContent()).getChildren().get(1);
        comboBox.setConverter(new StringConverter<Class<?>>() {
            @Override
            public String toString(Class<?> clazz) {
                return clazz != null ? clazz.getSimpleName() : null;
            }
            @Override
            public Class<?> fromString(String string) {
                return null;
            }
        });
        dialog.setTitle("New Event");
        dialog.setContentText("Event:");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.getItems().addAll(EVENTS);
        project.getEvents().forEach(event -> dialog.getItems().remove(event.getEvent()));
        Optional<Class<?>> result = dialog.showAndWait();
        if (result.isPresent()) {
            EventPane eventPane = new EventPane(project, result.get());
            project.add(eventPane);
            eventPane.open();
            project.getTabPane().getSelectionModel().select(eventPane);
        }
    }

    private Class<? extends Event> event;
    private ComboBox<String> priorityComboBox = new ComboBox<>();

    @SuppressWarnings("unchecked")
    public EventPane(Project project, Class<?> event) {
        super(project, event.getSimpleName());
        if (!Event.class.isAssignableFrom(event)) {
            throw new IllegalArgumentException(event.getCanonicalName() + " is not an event");
        }
        this.event = (Class<? extends Event>) event;
        priorityComboBox.getItems().addAll(Arrays.stream(EventPriority.values()).map(Enum::toString).toArray(String[]::new));
        priorityComboBox.setValue("HIGHEST");
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this event?");
            confirmation.setHeaderText(null);
            confirmation.setGraphic(null);
            if (confirmation.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                project.remove(this);
                VisualBukkit.displayMessage("Successfully deleted event");
            }
        });
        getInfoArea().getChildren().add(new VBox(5,
                new CenteredHBox(10, new TitleLabel("Event: " + getText(), 2), deleteButton),
                new CenteredHBox(10, new Label("Listener priority:"), priorityComboBox)));
    }

    @Override
    public void insertInto(CtClass mainClass) throws Exception {
        StringJoiner stringJoiner = new StringJoiner("\n");
        getBlockArea().getBlocks(true).forEach(block -> stringJoiner.add(block.toJava()));
        String src =
                "public void on" + event.getSimpleName() + "(" + event.getCanonicalName() + " event) {" +
                "Object localVarScope = event;" +
                stringJoiner.toString() +
                "VariableManager.deleteLocalVars(localVarScope);}";
        CtMethod eventMethod = CtMethod.make(src, mainClass);
        ConstPool methodConstPool = eventMethod.getMethodInfo().getConstPool();
        AnnotationsAttribute annotationsAttribute = new AnnotationsAttribute(methodConstPool, AnnotationsAttribute.visibleTag);
        Annotation annotation = new Annotation("org.bukkit.event.EventHandler", methodConstPool);
        EnumMemberValue priority = new EnumMemberValue(methodConstPool);
        priority.setType(EventPriority.class.getCanonicalName());
        priority.setValue(priorityComboBox.getValue());
        annotation.addMemberValue("priority", priority);
        annotationsAttribute.setAnnotation(annotation);
        eventMethod.getMethodInfo().addAttribute(annotationsAttribute);
        mainClass.addMethod(eventMethod);
    }

    @Override
    public void unload(ConfigurationSection section) {
        super.unload(section);
        section.set("priority", priorityComboBox.getValue());
    }

    @Override
    public void load(ConfigurationSection section) throws Exception {
        super.load(section);
        String priority = section.getString("priority");
        if (priority != null) {
            priorityComboBox.setValue(priority);
        }
    }

    @Override
    public PluginModule[] getModules() {
        if (event == PlaceholderEvent.class) {
            return new PluginModule[]{PluginModule.PlACEHOLDERAPI};
        }
        return null;
    }

    public Class<? extends Event> getEvent() {
        return event;
    }
}
