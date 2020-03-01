package us.donut.visualbukkit.editor;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.util.StringConverter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.plugin.PluginBuilder;
import us.donut.visualbukkit.util.DataFile;
import us.donut.visualbukkit.util.TreeNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Project {

    private String name;
    private Path folder;
    private DataFile dataFile;
    private Pane projectPane;
    private TabPane tabPane = new TabPane();
    private List<EventPane> events = new ArrayList<>();
    private List<CommandPane> commands = new ArrayList<>();

    public Project(String name) throws IOException {
        this.name = name;
        folder = ProjectManager.getProjectsFolder().resolve(name);
        if (Files.notExists(folder)) {
            Files.createDirectory(folder);
        }
        dataFile = new DataFile(folder.resolve("data.yml"));
        projectPane = new Pane();

        YamlConfiguration data = dataFile.getConfig();
        ConfigurationSection commandSection = data.getConfigurationSection("commands");
        if (commandSection != null) {
            for (String command : commandSection.getKeys(false)) {
                CommandPane commandPane = new CommandPane(this, command);
                try {
                    commandPane.load(commandSection.getConfigurationSection(command));
                    add(commandPane);
                } catch (Exception e) {
                    VisualBukkit.displayException("Failed to load command", e);
                }
            }
        }
        ConfigurationSection eventSection = data.getConfigurationSection("events");
        if (eventSection != null) {
            for (String eventClass : eventSection.getKeys(false)) {
                try {
                    String className = eventClass.replace('_', '.');
                    EventPane eventPane = new EventPane(this, Class.forName(className));
                    eventPane.load(eventSection.getConfigurationSection(eventClass));
                    add(eventPane);
                } catch (Exception e) {
                    VisualBukkit.displayException("Failed to load event", e);
                }
            }
        }
    }

    public void add(BlockPane blockPane) {
        if (blockPane instanceof CommandPane) {
            commands.add((CommandPane) blockPane);
            projectPane.commandTree.add(blockPane.getProjectStructureLabel());
        } else if (blockPane instanceof EventPane) {
            events.add((EventPane) blockPane);
            projectPane.eventTree.add(blockPane.getProjectStructureLabel());
        }
    }

    public void remove(BlockPane blockPane) {
        tabPane.getTabs().remove(blockPane);
        if (blockPane instanceof CommandPane) {
            commands.remove(blockPane);
            projectPane.commandTree.remove(blockPane.getProjectStructureLabel());
        } else if (blockPane instanceof EventPane) {
            events.remove(blockPane);
            projectPane.eventTree.remove(blockPane.getProjectStructureLabel());
        }
    }

    public void save() throws IOException {
        YamlConfiguration data = new YamlConfiguration();
        dataFile.setConfig(data);
        data.set("plugin.name", getPluginName());
        data.set("plugin.version", getPluginVer());
        data.set("plugin.author", getPluginAuthor());
        data.set("plugin.description", getPluginDesc());
        data.set("plugin.output-dir", getPluginOutputDir().toString());
        commands.forEach(commandPane -> commandPane.unload(data.createSection("commands." + commandPane.getCommand())));
        events.forEach(eventPane -> {
            String className = eventPane.getEvent().getCanonicalName().replace('.', '_');
            eventPane.unload(data.createSection("events." + className));
        });
        dataFile.save();
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public Path getFolder() {
        return folder;
    }

    public DataFile getDataFile() {
        return dataFile;
    }

    public Pane getProjectPane() {
        return projectPane;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public List<CommandPane> getCommands() {
        return commands;
    }

    public List<EventPane> getEvents() {
        return events;
    }

    public List<? extends BlockPane> getBlockPanes() {
        List<BlockPane> blockPanes = new ArrayList<>(commands.size() + events.size());
        blockPanes.addAll(commands);
        blockPanes.addAll(events);
        return blockPanes;
    }

    public String getPluginName() {
        return projectPane.pluginNameField.getText();
    }

    public String getPluginVer() {
        return projectPane.pluginVerField.getText();
    }

    public String getPluginAuthor() {
        return projectPane.pluginAuthorField.getText();
    }

    public String getPluginDesc() {
        return projectPane.pluginDescField.getText();
    }

    public Path getPluginOutputDir() {
        return Paths.get(projectPane.pluginOutputDirField.getText());
    }

    public class Pane extends VBox {

        private TreeNode commandTree = new TreeNode("Commands");
        private TreeNode eventTree = new TreeNode("Events");
        private TextField pluginNameField = new TextField();
        private TextField pluginVerField = new TextField();
        private TextField pluginAuthorField = new TextField();
        private TextField pluginDescField = new TextField();
        private TextField pluginOutputDirField = new TextField();

        @SuppressWarnings("unchecked")
        public Pane() {
            getStyleClass().add("project-pane");
            YamlConfiguration data = dataFile.getConfig();
            pluginNameField.setText(data.getString("plugin.name", ""));
            pluginVerField.setText(data.getString("plugin.version", ""));
            pluginAuthorField.setText(data.getString("plugin.author", ""));
            pluginDescField.setText(data.getString("plugin.description", ""));
            pluginOutputDirField.setText(data.getString("plugin.output-dir", System.getProperty("user.dir")));

            Button buildButton = new Button("Build Plugin");
            buildButton.setOnAction(e -> {
                try {
                    PluginBuilder.build(Project.this);
                    VisualBukkit.displayMessage("Successfully built plugin");
                } catch (Exception ex) {
                    VisualBukkit.displayException("Failed to create project", ex);
                }
            });

            Button newCommandButton = new Button("New Command");
            newCommandButton.setOnAction(e -> {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("New Command");
                dialog.setContentText("Command:");
                dialog.setHeaderText(null);
                dialog.setGraphic(null);
                String command = dialog.showAndWait().orElse("").replaceAll("\\s", "");
                if (!command.isEmpty()) {
                    if (StringUtils.isAlphanumeric(command)) {
                        for (CommandPane commandPane : commands) {
                            if (commandPane.getCommand().equalsIgnoreCase(command)) {
                                VisualBukkit.displayError("Command already exists");
                                return;
                            }
                        }
                        CommandPane commandPane = new CommandPane(Project.this, command);
                        add(commandPane);
                        commandPane.open();
                        tabPane.getSelectionModel().select(commandPane);
                    } else {
                        VisualBukkit.displayError("Invalid command name");
                    }
                }
            });

            Button newEventButton = new Button("New Event");
            newEventButton.setOnAction(e -> {
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
                dialog.getItems().addAll(EventPane.EVENTS);
                events.forEach(event -> dialog.getItems().remove(event.getEvent()));
                Optional<Class<?>> result = dialog.showAndWait();
                if (result.isPresent()) {
                    EventPane eventPane = new EventPane(Project.this, result.get());
                    add(eventPane);
                    eventPane.open();
                    tabPane.getSelectionModel().select(eventPane);
                }
            });

            Label title = new Label("Project Manager");
            title.getStyleClass().add("title-label");
            newEventButton.prefWidthProperty().bind(newCommandButton.widthProperty());
            getChildren().addAll(title, new Label("Name: " + name), commandTree, eventTree, newCommandButton, newEventButton);

            Label pluginInfoTitle = new Label("Plugin Information");
            pluginInfoTitle.getStyleClass().add("title-label");
            HBox nameBox = new HBox(5, new Label("Name:\t\t"), pluginNameField);
            HBox verBox = new HBox(5, new Label("Version:\t\t"), pluginVerField);
            HBox authorBox = new HBox(5, new Label("Author:\t\t"), pluginAuthorField);
            HBox descBox = new HBox(5, new Label("Description:\t"), pluginDescField);

            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Output Directory");
            chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            pluginOutputDirField.setEditable(false);
            pluginOutputDirField.setOnMouseClicked(e -> {
                File dir = chooser.showDialog(VisualBukkit.getInstance().getPrimaryStage());
                if (dir != null) {
                    pluginOutputDirField.setText(dir.toPath().toString());
                }
            });
            HBox dirBox = new HBox(5, new Label("Output dir:\t"), pluginOutputDirField);

            getChildren().addAll(new Separator(), pluginInfoTitle);
            for (HBox hBox : new HBox[]{nameBox, verBox, authorBox, descBox, dirBox}) {
                hBox.setAlignment(Pos.CENTER_LEFT);
                getChildren().add(hBox);
            }
            getChildren().add(buildButton);
        }
    }
}
