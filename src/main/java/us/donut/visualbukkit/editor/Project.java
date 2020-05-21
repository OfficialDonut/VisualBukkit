package us.donut.visualbukkit.editor;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.controlsfx.control.CheckComboBox;
import org.eclipse.fx.ui.controls.tabpane.DndTabPane;
import org.eclipse.fx.ui.controls.tabpane.DndTabPaneFactory;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.blocks.TypeHandler;
import us.donut.visualbukkit.plugin.PluginBuilder;
import us.donut.visualbukkit.plugin.hooks.PluginHookManager;
import us.donut.visualbukkit.util.CenteredHBox;
import us.donut.visualbukkit.util.DataFile;
import us.donut.visualbukkit.util.TitleLabel;
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
    private PluginConfigPane pluginConfigPane = new PluginConfigPane(this);
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
                procedure.load(procedureSection.getConfigurationSection(procedure.getMethodName()));
                projectPane.procedureTree.add(procedure.getProjectStructureLabel());
            } catch (Exception e) {
                procedures.remove(procedure);
                VisualBukkit.displayException("Failed to load procedure", e);
            }
        }

        for (FunctionPane function : functions) {
            try {
                function.load(functionSection.getConfigurationSection(function.getMethodName()));
                projectPane.functionTree.add(function.getProjectStructureLabel());
            } catch (Exception e) {
                functions.remove(function);
                VisualBukkit.displayException("Failed to load procedure", e);
            }
        }

        ConfigurationSection commandSection = dataFile.getConfig().getConfigurationSection("commands");
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

        ConfigurationSection eventSection = dataFile.getConfig().getConfigurationSection("events");
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

        ConfigurationSection pluginEnableSection = dataFile.getConfig().getConfigurationSection("plugin-enable");
        if (pluginEnableSection != null) {
            try {
                pluginEnablePane.load(pluginEnableSection);
            } catch (Exception e) {
                VisualBukkit.displayException("Failed to load plugin enable", e);
            }
        }

        ConfigurationSection pluginConfigSection = dataFile.getConfig().getConfigurationSection("plugin-config");
        if (pluginConfigSection != null) {
            pluginConfigPane.load(pluginConfigSection);
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
        data.set("plugin-hooks", projectPane.pluginHooksBox.getCheckModel().getCheckedItems());
        data.set("plugin.name", getPluginName());
        data.set("plugin.version", getPluginVer());
        data.set("plugin.author", getPluginAuthor());
        data.set("plugin.description", getPluginDesc());
        data.set("plugin.website", getPluginWebsite());
        data.set("plugin.depend", getPluginDepend());
        data.set("plugin.soft-depend", getPluginSoftDepend());
        data.set("plugin.output-dir", getPluginOutputDir().toString());
        data.set("notes", projectPane.projectNotesArea.getText());
        pluginConfigPane.unload(data.createSection("plugin-config"));
        pluginEnablePane.unload(data.createSection("plugin-enable"));
        commands.forEach(command -> command.unload(data.createSection("commands." + command.getCommand())));
        procedures.forEach(procedure -> procedure.unload(data.createSection("procedures." + procedure.getMethodName())));
        functions.forEach(function -> function.unload(data.createSection("functions." + function.getMethodName())));
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

    public PluginConfigPane getPluginConfigPane() {
        return pluginConfigPane;
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

    public List<String> getPluginHooks() {
        return projectPane.pluginHooksBox.getCheckModel().getCheckedItems();
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

    public String getPluginWebsite() {
        return projectPane.pluginWebsiteField.getText();
    }

    public String getPluginDepend() {
        return projectPane.pluginDependField.getText();
    }

    public String getPluginSoftDepend() {
        return projectPane.pluginSoftDependField.getText();
    }

    public Path getPluginOutputDir() {
        return Paths.get(projectPane.pluginOutputDirField.getText());
    }

    public class Pane extends VBox {

        private TreeNode commandTree = new TreeNode("Commands");
        private TreeNode eventTree = new TreeNode("Events");
        private TreeNode procedureTree = new TreeNode("Procedures");
        private TreeNode functionTree = new TreeNode("Functions");
        private CheckComboBox<String> pluginHooksBox = new CheckComboBox<>();
        private TextField pluginNameField = new TextField();
        private TextField pluginVerField = new TextField();
        private TextField pluginAuthorField = new TextField();
        private TextField pluginDescField = new TextField();
        private TextField pluginWebsiteField = new TextField();
        private TextField pluginDependField = new TextField();
        private TextField pluginSoftDependField = new TextField();
        private TextField pluginOutputDirField = new TextField();
        private TextArea projectNotesArea = new TextArea();

        public Pane() {
            getStyleClass().add("project-pane");
            YamlConfiguration data = dataFile.getConfig();
            pluginNameField.setText(data.getString("plugin.name", ""));
            pluginVerField.setText(data.getString("plugin.version", ""));
            pluginAuthorField.setText(data.getString("plugin.author", ""));
            pluginDescField.setText(data.getString("plugin.description", ""));
            pluginWebsiteField.setText(data.getString("plugin.website", ""));
            pluginDependField.setText(data.getString("plugin.depend", ""));
            pluginSoftDependField.setText(data.getString("plugin.soft-depend", ""));
            pluginOutputDirField.setText(data.getString("plugin.output-dir", folder.resolve("output").toString()));
            projectNotesArea.setText(data.getString("notes", ""));

            pluginHooksBox.setStyle("-fx-focus-color: -fx-control-inner-background;");
            pluginHooksBox.setMaxWidth(150);
            List<String> hooks = data.getStringList("plugin-hooks");
            for (String pluginName : PluginHookManager.getPluginNames()) {
                pluginHooksBox.getItems().add(pluginName);
                if (hooks.contains(pluginName)) {
                    pluginHooksBox.getCheckModel().check(pluginName);
                }
            }

            Button newCommandButton = new Button("New Command");
            newCommandButton.setOnAction(e -> CommandPane.promptNew(Project.this));

            Button newEventButton = new Button("New Event");
            newEventButton.setOnAction(e -> EventPane.promptNew(Project.this));

            Button newProcedureButton = new Button("New Procedure");
            newProcedureButton.setOnAction(e -> ProcedurePane.promptNew(Project.this));

            Button newFunctionButton = new Button("New Function");
            newFunctionButton.setOnAction(e -> FunctionPane.promptNew(Project.this));

            GridPane buttonGrid = new GridPane();
            buttonGrid.setHgap(5);
            buttonGrid.setVgap(5);
            buttonGrid.add(newCommandButton, 0, 0);
            buttonGrid.add(newEventButton, 1, 0);
            buttonGrid.add(newProcedureButton, 0, 1);
            buttonGrid.add(newFunctionButton, 1, 1);
            buttonGrid.getChildren().forEach(child -> {
                ((Button) child).setMaxWidth(Double.MAX_VALUE);
                GridPane.setFillWidth(child, true);
            });

            TreeNode structureTree = new TreeNode("Project Structure");
            structureTree.toggle();
            structureTree.add(
                    pluginConfigPane.getProjectStructureLabel(),
                    pluginEnablePane.getProjectStructureLabel(),
                    commandTree, eventTree, procedureTree, functionTree);

            Button buildButton = new Button("Build Plugin");
            buildButton.setOnAction(e -> {
                try {
                    PluginBuilder.build(Project.this);
                    VisualBukkit.displayMessage("Successfully built plugin");
                } catch (Exception ex) {
                    VisualBukkit.displayException("Failed to build plugin", ex);
                }
            });

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

            Button testButton = new Button("Test Plugin");
            testButton.prefWidthProperty().bind(buildButton.widthProperty());
            testButton.setOnAction(e -> new PluginTestStage(Project.this).show());

            getChildren().addAll(
                    new TitleLabel("Project Manager", 1.5, true),
                    new Label("Name: " + name), structureTree,
                    new CenteredHBox(10, new Label("Plugin Hooks:"), pluginHooksBox),
                    buttonGrid, new Separator(),
                    new TitleLabel("Plugin Information", 1.5, true),
                    new CenteredHBox(10, new Label("Name:       "), pluginNameField),
                    new CenteredHBox(10, new Label("Version:    "), pluginVerField),
                    new CenteredHBox(10, new Label("Author:     "), pluginAuthorField),
                    new CenteredHBox(10, new Label("Description:"), pluginDescField),
                    new CenteredHBox(10, new Label("Website:    "), pluginWebsiteField),
                    new CenteredHBox(10, new Label("Depend:     "), pluginDependField),
                    new CenteredHBox(10, new Label("Soft Depend:"), pluginSoftDependField),
                    new CenteredHBox(10, new Label("Output dir: "), pluginOutputDirField),
                    testButton, buildButton, new Separator(),
                    new TitleLabel("Project Notes", 1.5, true), projectNotesArea);
        }
    }
}
