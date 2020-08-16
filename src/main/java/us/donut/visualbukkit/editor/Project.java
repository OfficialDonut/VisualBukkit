package us.donut.visualbukkit.editor;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.eclipse.fx.ui.controls.tabpane.DndTabPane;
import org.eclipse.fx.ui.controls.tabpane.DndTabPaneFactory;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.plugin.PluginBuilder;
import us.donut.visualbukkit.util.CenteredHBox;
import us.donut.visualbukkit.util.DataConfig;
import us.donut.visualbukkit.util.DataFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class Project {

    private String name;
    private DataFile dataFile;
    private TabPane tabPane;
    private Pane projectPane;
    private List<BlockCanvas> canvases = new ArrayList<>();
    private TextField pluginNameField = new TextField();
    private TextField pluginVerField = new TextField();
    private TextField pluginAuthorField = new TextField();
    private TextField pluginDescField = new TextField();
    private TextField pluginWebsiteField = new TextField();
    private TextField pluginDependField = new TextField();
    private TextField pluginSoftDependField = new TextField();
    private TextField pluginOutputDirField = new TextField();
    private TextArea pluginConfigTextArea = new TextArea();

    public Project(String name) {
        this.name = name;
        Path folder = ProjectManager.getProjectsFolder().resolve(name);
        if (Files.notExists(folder)) {
            try {
                Files.createDirectory(folder);
            } catch (IOException e) {
                VisualBukkit.displayException("Failed to create project folder", e);
            }
        }
        Path dataFilePath = folder.resolve("data.yml");
        if (Files.exists(dataFilePath)) {
            try {
                Files.copy(dataFilePath, folder.resolve("data-backup.yml"), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ignored) {}
        }
        dataFile = new DataFile(dataFilePath);
        tabPane = (DndTabPane) DndTabPaneFactory.createDefaultDnDPane(DndTabPaneFactory.FeedbackType.MARKER, null).getChildren().get(0);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        projectPane = new Pane();
        List<DataConfig> canvasConfigs = dataFile.getConfigList("canvases");
        for (DataConfig canvasConfig : canvasConfigs) {
            createCanvas(canvasConfig.getString("name")).loadFrom(canvasConfig);
        }
        if (canvases.isEmpty()) {
            createCanvas("Main Canvas");
        }
        pluginNameField.setText(dataFile.getString("plugin.name", ""));
        pluginVerField.setText(dataFile.getString("plugin.version", ""));
        pluginAuthorField.setText(dataFile.getString("plugin.author", ""));
        pluginDescField.setText(dataFile.getString("plugin.description", ""));
        pluginWebsiteField.setText(dataFile.getString("plugin.website", ""));
        pluginDependField.setText(dataFile.getString("plugin.depend", ""));
        pluginSoftDependField.setText(dataFile.getString("plugin.soft-depend", ""));
        pluginOutputDirField.setText(dataFile.getString("plugin.output-dir", folder.toString()));
        pluginConfigTextArea.setText(dataFile.getString("plugin.config", ""));
    }

    public BlockCanvas createCanvas(String canvasName) {
        BlockCanvas canvas = new BlockCanvas(canvasName);
        canvases.add(canvas);
        Tab tab = new Tab(canvasName, canvas);
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
        return canvas;
    }

    public void renameCanvas(BlockCanvas canvas, String newName) {
        for (Tab tab : tabPane.getTabs()) {
            if (tab.getText().equalsIgnoreCase(canvas.getName())) {
                tab.setText(newName);
            }
        }
        canvas.setName(newName);
    }

    public void deleteCanvas(BlockCanvas canvas) {
        canvases.remove(canvas);
        tabPane.getTabs().removeIf(tab -> canvas.getName().equalsIgnoreCase(tab.getText()));
    }

    public void save() throws IOException {
        dataFile.clear();
        List<DataConfig> canvasConfigs = new ArrayList<>();
        for (BlockCanvas canvas : canvases) {
            DataConfig canvasConfig = new DataConfig();
            canvasConfig.set("name", canvas.getName());
            canvas.saveTo(canvasConfig);
            canvasConfigs.add(canvasConfig);
        }
        dataFile.set("canvases", canvasConfigs);
        dataFile.set("plugin.name", pluginNameField.getText());
        dataFile.set("plugin.version", pluginVerField.getText());
        dataFile.set("plugin.author", pluginAuthorField.getText());
        dataFile.set("plugin.description", pluginDescField.getText());
        dataFile.set("plugin.website", pluginWebsiteField.getText());
        dataFile.set("plugin.depend", pluginDependField.getText());
        dataFile.set("plugin.soft-depend", pluginSoftDependField.getText());
        dataFile.set("plugin.output-dir", pluginOutputDirField.getText());
        dataFile.set("plugin.config", pluginConfigTextArea.getText());
        dataFile.save();
    }

    public BlockCanvas getCurrentCanvas() {
        Tab currentTab = tabPane.getSelectionModel().getSelectedItem();
        return currentTab != null && currentTab.getContent() instanceof BlockCanvas ? (BlockCanvas) currentTab.getContent() : null;
    }

    public String getName() {
        return name;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public Pane getProjectPane() {
        return projectPane;
    }

    public List<BlockCanvas> getCanvases() {
        return canvases;
    }

    public String getPluginName() {
        return pluginNameField.getText();
    }

    public String getPluginVer() {
        return pluginVerField.getText();
    }

    public String getPluginAuthor() {
        return pluginAuthorField.getText();
    }

    public String getPluginDesc() {
        return pluginDescField.getText();
    }

    public String getPluginWebsite() {
        return pluginWebsiteField.getText();
    }

    public String getPluginDepend() {
        return pluginDependField.getText();
    }

    public String getPluginSoftDepend() {
        return pluginSoftDependField.getText();
    }

    public Path getPluginOutputDir() {
        return Paths.get(pluginOutputDirField.getText());
    }

    public String getPluginConfig() {
        return pluginConfigTextArea.getText();
    }

    private class Pane extends ScrollPane {

        public Pane() {
            Button editConfigButton = new Button("Edit Config");
            editConfigButton.setOnAction(e -> {
                if (pluginConfigTextArea.getParent() != null) {
                    pluginConfigTextArea.getScene().getWindow().hide();
                }
                Stage stage = new Stage();
                stage.initOwner(VisualBukkit.getInstance().getPrimaryStage());
                stage.setTitle("Plugin Config");
                ScrollPane scrollPane = new ScrollPane(pluginConfigTextArea);
                scrollPane.setFitToWidth(true);
                scrollPane.setFitToHeight(true);
                Scene scene = new Scene(scrollPane, 600, 600);
                scene.getStylesheets().add("/style.css");
                stage.setScene(scene);
                stage.show();
            });

            Button buildButton = new Button("Build Plugin");
            buildButton.setOnAction(e -> {
                try {
                    PluginBuilder.build(Project.this);
                } catch (IOException | CannotCompileException | NotFoundException ex) {
                    VisualBukkit.displayException("Failed to build plugin", ex);
                }
            });

            pluginOutputDirField.setEditable(false);
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Output Directory");
            chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            pluginOutputDirField.setOnMouseClicked(e -> {
                File dir = chooser.showDialog(VisualBukkit.getInstance().getPrimaryStage());
                if (dir != null) {
                    pluginOutputDirField.setText(dir.toPath().toString());
                }
            });

            pluginConfigTextArea.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
                if (e.getCode() == KeyCode.TAB) {
                    pluginConfigTextArea.insertText(pluginConfigTextArea.getCaretPosition(), "  ");
                    e.consume();
                }
            });

            Label titleLabel = new Label("Plugin Information");
            titleLabel.setUnderline(true);
            VBox content = new VBox();
            content.getStyleClass().add("project-pane");
            content.getChildren().addAll(
                    titleLabel,
                    new CenteredHBox(10, new Label("Name:       "), pluginNameField),
                    new CenteredHBox(10, new Label("Version:    "), pluginVerField),
                    new CenteredHBox(10, new Label("Author:     "), pluginAuthorField),
                    new CenteredHBox(10, new Label("Description:"), pluginDescField),
                    new CenteredHBox(10, new Label("Website:    "), pluginWebsiteField),
                    new CenteredHBox(10, new Label("Depend:     "), pluginDependField),
                    new CenteredHBox(10, new Label("Soft Depend:"), pluginSoftDependField),
                    new CenteredHBox(10, new Label("Output dir: "), pluginOutputDirField),
                    editConfigButton, buildButton);

            setFitToWidth(true);
            setFitToHeight(true);
            setContent(content);
            setOnMousePressed(e -> ContextMenuManager.hide());
        }
    }
}
