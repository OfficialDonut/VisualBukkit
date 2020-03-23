package us.donut.visualbukkit.editor;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.eclipse.fx.ui.controls.tabpane.DndTabPane;
import org.eclipse.fx.ui.controls.tabpane.DndTabPaneFactory;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.blocks.TypeHandler;
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

public class Project {

    private String name;
    private Path folder;
    private DataFile dataFile;
    private Pane projectPane;
    private DndTabPane tabPane;
    private PluginEnablePane pluginEnablePane = new PluginEnablePane(this);
    private List<CommandPane> commands = new ArrayList<>();
    private List<EventPane> events = new ArrayList<>();
    private List<ProcedurePane> procedures = new ArrayList<>();
    private List<FunctionPane> functions = new ArrayList<>();
    private boolean loaded = false;

    public Project(String name) throws IOException {
        this.name = name;
        folder = ProjectManager.getProjectsFolder().resolve(name);
        if (Files.notExists(folder)) {
            Files.createDirectory(folder);
        }
        dataFile = new DataFile(folder.resolve("data.yml"));
        projectPane = new Pane();
        tabPane = (DndTabPane) DndTabPaneFactory.createDefaultDnDPane(DndTabPaneFactory.FeedbackType.MARKER, null).getChildren().get(0);
    }

    public void load() {
        if (loaded) {
            return;
        }
        loaded = true;

        ConfigurationSection procedureSection = dataFile.getConfig().getConfigurationSection("procedures");
        if (procedureSection != null) {
            for (String procedure : procedureSection.getKeys(false)) {
                List<String> parameters = procedureSection.getConfigurationSection(procedure).getStringList("parameters");
                procedures.add(new ProcedurePane(this, procedure, parameters.stream().map(TypeHandler::getType).toArray(Class[]::new)));
            }
        }

        ConfigurationSection functionSection = dataFile.getConfig().getConfigurationSection("functions");
        if (functionSection != null) {
            for (String function : functionSection.getKeys(false)) {
                List<String> parameters = functionSection.getConfigurationSection(function).getStringList("parameters");
                functions.add(new FunctionPane(this, function, parameters.stream().map(TypeHandler::getType).toArray(Class[]::new)));
            }
        }

        for (ProcedurePane procedure : procedures) {
            try {
                procedure.load(procedureSection.getConfigurationSection(procedure.getProcedure()));
                projectPane.procedureTree.add(procedure.getProjectStructureLabel());
            } catch (Exception e) {
                procedures.remove(procedure);
                VisualBukkit.displayException("Failed to load procedure", e);
            }
        }

        for (FunctionPane function : functions) {
            try {
                function.load(functionSection.getConfigurationSection(function.getFunction()));
                projectPane.functionTree.add(function.getProjectStructureLabel());
            } catch (Exception e) {
                functions.remove(function);
                VisualBukkit.displayException("Failed to load procedure", e);
            }
        }

        ConfigurationSection commandSection = getDataFile().getConfig().getConfigurationSection("commands");
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

        ConfigurationSection eventSection = getDataFile().getConfig().getConfigurationSection("events");
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

        ConfigurationSection pluginEnableSection = getDataFile().getConfig().getConfigurationSection("plugin-enable");
        if (pluginEnableSection != null) {
            try {
                pluginEnablePane.load(pluginEnableSection);
            } catch (Exception e) {
                VisualBukkit.displayException("Failed to load plugin enable", e);
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
        } else if (blockPane instanceof ProcedurePane) {
            procedures.add((ProcedurePane) blockPane);
            projectPane.procedureTree.add(blockPane.getProjectStructureLabel());
        } else if (blockPane instanceof FunctionPane) {
            functions.add((FunctionPane) blockPane);
            projectPane.functionTree.add(blockPane.getProjectStructureLabel());
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
        } else if (blockPane instanceof ProcedurePane) {
            procedures.remove(blockPane);
            projectPane.procedureTree.remove(blockPane.getProjectStructureLabel());
        } else if (blockPane instanceof FunctionPane) {
            functions.remove(blockPane);
            projectPane.functionTree.remove(blockPane.getProjectStructureLabel());
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
        pluginEnablePane.unload(data.createSection("plugin-enable"));
        commands.forEach(command -> command.unload(data.createSection("commands." + command.getCommand())));
        procedures.forEach(procedure -> procedure.unload(data.createSection("procedures." + procedure.getProcedure())));
        functions.forEach(function -> function.unload(data.createSection("functions." + function.getFunction())));
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

    public PluginEnablePane getPluginEnablePane() {
        return pluginEnablePane;
    }

    public List<CommandPane> getCommands() {
        return commands;
    }

    public List<EventPane> getEvents() {
        return events;
    }

    public List<ProcedurePane> getProcedures() {
        return procedures;
    }

    public List<FunctionPane> getFunctions() {
        return functions;
    }

    public List<? extends BlockPane> getBlockPanes() {
        List<BlockPane> blockPanes = new ArrayList<>(1 + commands.size() + events.size() + procedures.size() + functions.size());
        blockPanes.add(pluginEnablePane);
        blockPanes.addAll(commands);
        blockPanes.addAll(events);
        blockPanes.addAll(procedures);
        blockPanes.addAll(functions);
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
        private TreeNode procedureTree = new TreeNode("Procedures");
        private TreeNode functionTree = new TreeNode("Functions");
        private TextField pluginNameField = new TextField();
        private TextField pluginVerField = new TextField();
        private TextField pluginAuthorField = new TextField();
        private TextField pluginDescField = new TextField();
        private TextField pluginOutputDirField = new TextField();

        public Pane() {
            getStyleClass().add("project-pane");
            YamlConfiguration data = dataFile.getConfig();
            pluginNameField.setText(data.getString("plugin.name", ""));
            pluginVerField.setText(data.getString("plugin.version", ""));
            pluginAuthorField.setText(data.getString("plugin.author", ""));
            pluginDescField.setText(data.getString("plugin.description", ""));
            pluginOutputDirField.setText(data.getString("plugin.output-dir", folder.resolve("output").toString()));

            Button buildButton = new Button("Build Plugin");
            buildButton.setOnAction(e -> {
                try {
                    PluginBuilder.build(Project.this);
                    VisualBukkit.displayMessage("Successfully built plugin");
                } catch (Exception ex) {
                    VisualBukkit.displayException("Failed to build plugin", ex);
                }
            });

            Button newCommandButton = new Button("New Command");
            newCommandButton.setOnAction(e -> CommandPane.promptNew(Project.this));

            Button newEventButton = new Button("New Event");
            newEventButton.setOnAction(e -> EventPane.promptNew(Project.this));

            Button newProcedureButton = new Button("New Procedure");
            newProcedureButton.setOnAction(e -> ProcedurePane.promptNew(Project.this));

            Button newFunctionButton = new Button("New Function");
            newFunctionButton.setOnAction(e -> FunctionPane.promptNew(Project.this));

            Label title = new Label("Project Manager");
            title.getStyleClass().add("title-label");
            newEventButton.prefWidthProperty().bind(newCommandButton.widthProperty());
            newProcedureButton.prefWidthProperty().bind(newCommandButton.widthProperty());
            newFunctionButton.prefWidthProperty().bind(newCommandButton.widthProperty());
            TreeNode structureTree = new TreeNode("Project Structure");
            structureTree.add(pluginEnablePane.getProjectStructureLabel(), commandTree, eventTree, procedureTree, functionTree);
            structureTree.toggle();
            getChildren().addAll(title, new Label("Name: " + name), structureTree,
                    newCommandButton, newEventButton, newProcedureButton, newFunctionButton);

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
