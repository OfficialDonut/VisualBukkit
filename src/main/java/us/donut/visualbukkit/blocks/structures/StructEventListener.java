package us.donut.visualbukkit.blocks.structures;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.reflections.Reflections;
import us.donut.visualbukkit.blocks.StructureBlock;
import us.donut.visualbukkit.blocks.annotations.Description;
import us.donut.visualbukkit.blocks.syntax.ChoiceParameter;
import us.donut.visualbukkit.blocks.syntax.Syntax;
import us.donut.visualbukkit.plugin.BuildContext;
import us.donut.visualbukkit.plugin.modules.PluginModule;
import us.donut.visualbukkit.plugin.modules.classes.PlaceholderEvent;
import us.donut.visualbukkit.util.ComboBoxView;
import us.donut.visualbukkit.util.DataConfig;

import java.lang.reflect.Modifier;
import java.util.*;

@Description("Runs code when an event occurs")
public class StructEventListener extends StructureBlock {

    private static int counter = 1;
    private static ComboBoxView<Class<? extends Event>> eventComboBoxView = new ComboBoxView<>();
    private static String[] priorities = Arrays.stream(EventPriority.values()).map(Enum::name).sorted().toArray(String[]::new);

    static {
        Set<Class<? extends Event>> events = new TreeSet<>(Comparator.comparing(Class::getSimpleName));
        Reflections reflections = new Reflections("org.bukkit.event");
        for (Class<? extends Event> clazz : reflections.getSubTypesOf(Event.class)) {
            if (!Modifier.isAbstract(clazz.getModifiers()) && !clazz.isAnnotationPresent(Deprecated.class)) {
                events.add(clazz);
            }
        }
        events.add(PlaceholderEvent.class);
        eventComboBoxView.getComboBox().getItems().addAll(events);
        eventComboBoxView.getStylesheets().add("/style.css");
        eventComboBoxView.getComboBox().setConverter(new StringConverter<Class<? extends Event>>() {
            @Override
            public String toString(Class<? extends Event> clazz) {
                return clazz != null ? clazz.getSimpleName() : null;
            }
            @Override
            public Class<? extends Event> fromString(String string) {
                return null;
            }
        });
    }

    private Class<? extends Event> event;
    private Text eventText;

    @Override
    protected Syntax init() {
        ChoiceParameter priorityChoice = new ChoiceParameter(priorities);
        priorityChoice.getComboBox().setValue("NORMAL");
        return new Syntax(eventText = new Text("<...>"), "with priority", priorityChoice);
    }

    @Override
    public void update() {
        super.update();
        if (event == null) {
            eventComboBoxView.getComboBox().getSelectionModel().select(0);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("New Event Listener");
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.getDialogPane().setContent(new HBox(5, new Text("Event:"), eventComboBoxView));
            alert.getDialogPane().setPrefWidth(300);
            Platform.runLater(() -> {
                alert.showAndWait();
                event = eventComboBoxView.getComboBox().getValue();
                eventText.setText(event.getSimpleName());
            });
        }
    }

    @Override
    public void insertInto(JavaClassSource mainSource) {
        String childJava = getChildJava();
        mainSource.addMethod(
                "@EventHandler(priority=EventPriority." + arg(0) + ")" +
                "public void on" + event.getSimpleName() + (counter++) + "(" + event.getCanonicalName() + " event) throws Exception {" +
                BuildContext.declareLocalVariables() +
                childJava +
                "}");
        if (event == PlaceholderEvent.class) {
            BuildContext.addPluginModule(PluginModule.PlACEHOLDERAPI);
        }
    }

    @Override
    public void saveTo(DataConfig config) {
        super.saveTo(config);
        config.set("event", event.getName());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void loadFrom(DataConfig config) {
        super.loadFrom(config);
        try {
            event = (Class<? extends Event>) Class.forName(config.getString("event"));
        } catch (ClassNotFoundException e) {
            event = eventComboBoxView.getComboBox().getItems().get(0);
        }
        eventText.setText(event.getSimpleName());
    }

    public Class<? extends Event> getEvent() {
        return event;
    }
}
