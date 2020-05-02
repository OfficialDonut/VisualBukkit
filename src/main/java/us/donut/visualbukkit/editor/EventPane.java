package us.donut.visualbukkit.editor;

import com.google.common.reflect.ClassPath;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import org.bukkit.event.Event;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.blocks.BlockRegistry;
import us.donut.visualbukkit.util.TitleLabel;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.*;

@SuppressWarnings("UnstableApiUsage")
public class EventPane extends BlockPane {

    public static Class<?>[] EVENTS;

    static {
        try {
            ClassPath classPath = ClassPath.from(BlockRegistry.class.getClassLoader());
            String eventPackage = "org.bukkit.event";
            Set<Class<?>> events = new TreeSet<>(Comparator.comparing(Class::getSimpleName));
            for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClassesRecursive(eventPackage)) {
                Class<?> clazz = classInfo.load();
                if (Event.class.isAssignableFrom(clazz) && !Modifier.isAbstract(clazz.getModifiers()) && !clazz.isAnnotationPresent(Deprecated.class)) {
                    events.add(classInfo.load());
                }
            }
            EVENTS = events.toArray(new Class<?>[0]);
        } catch (IOException e) {
            VisualBukkit.displayException("Failed to load events", e);
        }
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

    @SuppressWarnings("unchecked")
    public EventPane(Project project, Class<?> event) {
        super(project, event.getSimpleName());
        if (!Event.class.isAssignableFrom(event)) {
            throw new IllegalArgumentException(event.getCanonicalName() + " is not an event");
        }
        this.event = (Class<? extends Event>) event;
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
        getInfoArea().getChildren().addAll(new TitleLabel("Event: " + getText(), 2), deleteButton);
    }

    @Override
    public void insertInto(CtClass mainClass) throws Exception {
        StringJoiner stringJoiner = new StringJoiner("\n");
        getBlockArea().getBlocks(true).forEach(block -> stringJoiner.add(block.toJava()));
        String src =
                "public void on" + event.getSimpleName() + "(" + event.getCanonicalName() + " event) {" +
                "Map tempVariables = new HashMap();" +
                stringJoiner.toString() + "}";
        CtMethod eventMethod = CtMethod.make(src, mainClass);
        ConstPool methodConstPool = eventMethod.getMethodInfo().getConstPool();
        AnnotationsAttribute annotationsAttribute = new AnnotationsAttribute(methodConstPool, AnnotationsAttribute.visibleTag);
        annotationsAttribute.setAnnotation(new Annotation("org.bukkit.event.EventHandler", methodConstPool));
        eventMethod.getMethodInfo().addAttribute(annotationsAttribute);
        mainClass.addMethod(eventMethod);
    }

    public Class<? extends Event> getEvent() {
        return event;
    }
}
