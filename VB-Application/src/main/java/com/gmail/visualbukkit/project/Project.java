package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.VisualBukkitExtension;
import com.gmail.visualbukkit.blocks.*;
import com.gmail.visualbukkit.blocks.definitions.core.CompCommand;
import com.gmail.visualbukkit.project.maven.MavenDependencyModule;
import com.gmail.visualbukkit.project.maven.MavenModule;
import com.gmail.visualbukkit.project.maven.MavenRepositoryModule;
import com.gmail.visualbukkit.project.maven.MavenUtil;
import com.gmail.visualbukkit.reflection.ClassRegistry;
import com.gmail.visualbukkit.ui.*;
import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import com.google.common.io.Resources;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.controlsfx.control.CheckTreeView;
import org.controlsfx.control.ListSelectionView;
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.control.action.Action;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.util.artifact.JavaScopes;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Project {

    private static final RemoteRepository PAPER_REPOSITORY = new RemoteRepository.Builder("papermc", "default", "https://repo.papermc.io/repository/maven-public/").build();
    private static final Dependency PAPER_DEPENDENCY = new Dependency(new DefaultArtifact("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT"), "provided");

    private final Path directory;
    private final Path dataFile;
    private final Path pluginComponentDirectory;
    private final Path resourcesDirectory;
    private final Path buildDirectory;
    private JSONObject data = new JSONObject();
    private boolean reloadRequired;

    private final BorderPane projectPane = new BorderPane();
    private final SplitPane splitPane = new SplitPane();
    private final VBox placeholderPane = new VBox();
    private final TabPane tabPane = new TabPane();
    private final StatementSelector statementSelector = new StatementSelector();
    private final PluginSettings pluginSettings = new PluginSettings();
    private final ListView<MavenModule> mavenListView = new ListView<>();
    private final ListView<String> javadocsUrlListView;
    private final ListView<String> javadocsValuesListView;

    private final String DEFAULT_JAVADOCS_URL = "https://jd.papermc.io/paper/1.21.4/";
    private final List<String> DEFAULT_JAVADOCS_PACKAGES = new ArrayList<>((List.of("org.bukkit", "org.spigotmc", "co.aikar", "com.destroystokyo.paper", "io.papermc")));
    private final ListSelectionView<PluginModule> moduleSelector = new ListSelectionView<>();
    private static final HashMap<String, List<String>> javadocsMap = new HashMap<>();
    private final ObservableList<PluginComponent> pluginComponents = FXCollections.observableArrayList();
    private final Map<PluginComponent, Tab> openPluginComponents = new HashMap<>();
    private final TextField jarOutputField = new TextField();
    private final TextField packageField = new TextField();
    private final CheckBox debugModeCheckBox = new CheckBox(VisualBukkitApp.localizedText("label.enabled"));

    public Project(Path directory) {
        this.directory = directory;
        dataFile = directory.resolve("data.json");
        pluginComponentDirectory = directory.resolve("plugin_components");
        resourcesDirectory = directory.resolve("resource_files");
        buildDirectory = directory.resolve("build");

        moduleSelector.setSourceHeader(new Label(VisualBukkitApp.localizedText("label.disabled")));
        moduleSelector.setTargetHeader(new Label(VisualBukkitApp.localizedText("label.enabled")));
        for (Action action : moduleSelector.getActions()) {
            action.graphicProperty().unbind();
            action.setGraphic(null);
            action.setText(action instanceof ListSelectionView.MoveToTarget ? ">" : action instanceof ListSelectionView.MoveToTargetAll ? ">>" : action instanceof ListSelectionView.MoveToSource ? "<" : "<<");
        }

        ActionButton addDependButton = new ActionButton(VisualBukkitApp.localizedText("button.add_dependency"), e -> promptAddMavenDependency(null));
        ActionButton addRepoButton = new ActionButton(VisualBukkitApp.localizedText("button.add_repository"), e -> promptAddMavenRepository(null));
        ActionButton editButton = new ActionButton(VisualBukkitApp.localizedText("button.edit"), e -> {
            MavenModule selectedMavenModule = mavenListView.getSelectionModel().getSelectedItem();
            if (selectedMavenModule instanceof MavenDependencyModule mavenDependency) {
                promptAddMavenDependency(mavenDependency);
            } else if (selectedMavenModule instanceof MavenRepositoryModule mavenRepository) {
                promptAddMavenRepository(mavenRepository);
            }
        });
        ActionButton deleteButton = new ActionButton(VisualBukkitApp.localizedText("button.delete"), e -> {
            mavenListView.getItems().remove(mavenListView.getSelectionModel().getSelectedItem());
            reloadRequired = true;
        });
        editButton.disableProperty().bind(mavenListView.getSelectionModel().selectedItemProperty().isNull().or(Bindings.selectBoolean(mavenListView.getSelectionModel().selectedItemProperty(), "userDefined").not()));
        deleteButton.disableProperty().bind(editButton.disableProperty());
        ButtonVBox mavenButtons = new ButtonVBox(addDependButton, addRepoButton, editButton, deleteButton);
        HBox mavenPane = new HBox(mavenListView, mavenButtons);
        mavenPane.getStyleClass().add("maven-settings-pane");
        mavenListView.setPlaceholder(new Label(VisualBukkitApp.localizedText("label.no_maven_dependencies")));
        mavenListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<MavenModule> call(ListView<MavenModule> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(MavenModule item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.toString());
                            setGraphic(item.isUserDefined() ? null : new FontIcon(FontAwesomeSolid.LOCK));
                        } else {
                            setGraphic(null);
                            setText(null);
                        }
                    }
                };
            }
        });

        addMavenRepository(PAPER_REPOSITORY);
        addMavenDependency(PAPER_DEPENDENCY);

        javadocsUrlListView = new ListView<>();
        javadocsMap.clear();
        javadocsUrlListView.getItems().clear();
        javadocsUrlListView.getItems().add(DEFAULT_JAVADOCS_URL);
        javadocsUrlListView.getSelectionModel().select(DEFAULT_JAVADOCS_URL);
        javadocsUrlListView.getItems().add("https://docs.oracle.com/en/java/javase/21/docs/api/java.base/");
        javadocsMap.put("https://jd.papermc.io/paper/1.21.4/", new ArrayList<>(List.of("org.bukkit", "org.spigotmc", "co.aikar", "com.destroystokyo.paper", "io.papermc")));
        javadocsMap.put("https://docs.oracle.com/en/java/javase/21/docs/api/java.base/", new ArrayList<>(List.of("java", "jdk")));
        for (Map.Entry<String, List<String>> entry : javadocsMap.entrySet()) {
            javadocsUrlListView.getItems().add(entry.getKey());
        }

        ActionButton addUrlButton = new ActionButton(VisualBukkitApp.localizedText("button.add_url"), e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle(VisualBukkitApp.localizedText("window.add_javadocs_url"));
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            dialog.setContentText(VisualBukkitApp.localizedText("dialog.add_javadocs_url"));
            dialog.showAndWait().ifPresent(url -> {
                if (url.endsWith("/")) {
                    if (!javadocsMap.containsKey(url)) {
                        javadocsMap.put(url, new ArrayList<>());
                        javadocsUrlListView.getItems().add(url);
                    }
                } else {
                    VisualBukkitApp.displayError(VisualBukkitApp.localizedText("notification.invalid_javadocs_url"));
                }
            });
        });

        ActionButton deleteUrlButton = new ActionButton(VisualBukkitApp.localizedText("button.delete_url"), e -> {
            String selectedUrl = javadocsUrlListView.getSelectionModel().getSelectedItem();
            if (selectedUrl != null) {
                if (selectedUrl.equals("https://jd.papermc.io/paper/1.21.4/") || selectedUrl.equals("https://docs.oracle.com/en/java/javase/21/docs/api/java.base/")) {
                    return;
                }
                javadocsMap.remove(selectedUrl);
                javadocsUrlListView.getItems().remove(selectedUrl);
            }
        });

        ActionButton editUrlButton = new ActionButton(VisualBukkitApp.localizedText("button.edit_url"), e -> {
            String selectedUrl = javadocsUrlListView.getSelectionModel().getSelectedItem();
            if (selectedUrl != null) {
                if (selectedUrl.equals("https://jd.papermc.io/paper/1.21.4/") || selectedUrl.equals("https://docs.oracle.com/en/java/javase/21/docs/api/java.base/")) {
                    return;
                }
                TextInputDialog dialog = new TextInputDialog(selectedUrl);
                dialog.setTitle(VisualBukkitApp.localizedText("window.edit_javadocs_url"));
                dialog.setHeaderText(null);
                dialog.setGraphic(null);
                dialog.setContentText(VisualBukkitApp.localizedText("dialog.edit_javadocs_url"));
                dialog.showAndWait().ifPresent(url -> {
                    if (url.endsWith("/")) {
                        javadocsMap.put(url, javadocsMap.get(selectedUrl));
                        javadocsMap.remove(selectedUrl);
                        javadocsUrlListView.getItems().set(javadocsUrlListView.getItems().indexOf(selectedUrl), url);
                    } else {
                        VisualBukkitApp.displayError(VisualBukkitApp.localizedText("notification.invalid_javadocs_url"));
                    }
                });
            }
        });

        ButtonVBox javadocsButtons = new ButtonVBox(addUrlButton, deleteUrlButton, editUrlButton);
        javadocsButtons.bindSizes();

        deleteUrlButton.disableProperty().bind(
                javadocsUrlListView.getSelectionModel().selectedItemProperty().isNull()
                        .or(javadocsUrlListView.getSelectionModel().selectedItemProperty().isEqualTo("https://jd.papermc.io/paper/1.21.4/"))
                        .or(javadocsUrlListView.getSelectionModel().selectedItemProperty().isEqualTo("https://docs.oracle.com/en/java/javase/21/docs/api/java.base/"))
        );
        editUrlButton.disableProperty().bind(
                javadocsUrlListView.getSelectionModel().selectedItemProperty().isNull()
                        .or(javadocsUrlListView.getSelectionModel().selectedItemProperty().isEqualTo("https://jd.papermc.io/paper/1.21.4/"))
                        .or(javadocsUrlListView.getSelectionModel().selectedItemProperty().isEqualTo("https://docs.oracle.com/en/java/javase/21/docs/api/java.base/"))
        );

        javadocsValuesListView = new ListView<>();
        javadocsValuesListView.setPlaceholder(new Label(VisualBukkitApp.localizedText("label.no_values")));

        ActionButton addValueButton = new ActionButton(VisualBukkitApp.localizedText("button.add_value"), e -> {
            String key = javadocsUrlListView.getSelectionModel().getSelectedItem();
            if (key != null) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle(VisualBukkitApp.localizedText("window.add_value"));
                dialog.setContentText(VisualBukkitApp.localizedText("dialog.add_value"));
                dialog.setHeaderText(null);
                dialog.setGraphic(null);
                dialog.showAndWait().ifPresent(value -> {
                    if (value.isBlank() || javadocsValuesListView.getItems().contains(value)) {
                        return;
                    }
                    List<String> list = javadocsMap.getOrDefault(key, new ArrayList<>());
                    list.add(value);
                    javadocsMap.put(key, list);
                    javadocsValuesListView.getItems().setAll(list);
                });
            }
        });
        ActionButton deleteValueButton = new ActionButton(VisualBukkitApp.localizedText("button.delete_value"), e -> {
            String key = javadocsUrlListView.getSelectionModel().getSelectedItem();
            String selectedValue = javadocsValuesListView.getSelectionModel().getSelectedItem();
            if (key != null && selectedValue != null) {
                List<String> list = javadocsMap.get(key);
                if (list != null) {
                    list.remove(selectedValue);
                    javadocsValuesListView.getItems().setAll(list);
                }
            }
        });
        ActionButton editValueButton = new ActionButton(VisualBukkitApp.localizedText("button.edit_value"), e -> {
            String key = javadocsUrlListView.getSelectionModel().getSelectedItem();
            String selectedValue = javadocsValuesListView.getSelectionModel().getSelectedItem();
            if (key != null && selectedValue != null) {
                TextInputDialog dialog = new TextInputDialog(selectedValue);
                dialog.setTitle(VisualBukkitApp.localizedText("window.edit_value"));
                dialog.setContentText(VisualBukkitApp.localizedText("dialog.edit_value"));
                dialog.setHeaderText(null);
                dialog.setGraphic(null);
                dialog.showAndWait().ifPresent(newVal -> {
                    List<String> list = javadocsMap.get(key);
                    if (list != null) {
                        int index = list.indexOf(selectedValue);
                        if (index != -1) {
                            list.set(index, newVal);
                            javadocsValuesListView.getItems().setAll(list);
                        }
                    }
                });
            }
        });

        addValueButton.disableProperty().bind(
                javadocsUrlListView.getSelectionModel().selectedItemProperty().isNull()
                        .or(javadocsUrlListView.getSelectionModel().selectedItemProperty().isEqualTo("https://jd.papermc.io/paper/1.21.4/"))
                        .or(javadocsUrlListView.getSelectionModel().selectedItemProperty().isEqualTo("https://docs.oracle.com/en/java/javase/21/docs/api/java.base/"))
        );
        deleteValueButton.disableProperty().bind(
                javadocsValuesListView.getSelectionModel().selectedItemProperty().isNull()
                        .or(javadocsUrlListView.getSelectionModel().selectedItemProperty().isEqualTo("https://jd.papermc.io/paper/1.21.4/"))
                        .or(javadocsUrlListView.getSelectionModel().selectedItemProperty().isEqualTo("https://docs.oracle.com/en/java/javase/21/docs/api/java.base/"))
        );
        editValueButton.disableProperty().bind(
                javadocsValuesListView.getSelectionModel().selectedItemProperty().isNull()
                        .or(javadocsUrlListView.getSelectionModel().selectedItemProperty().isEqualTo("https://jd.papermc.io/paper/1.21.4/"))
                        .or(javadocsUrlListView.getSelectionModel().selectedItemProperty().isEqualTo("https://docs.oracle.com/en/java/javase/21/docs/api/java.base/"))
        );

        ButtonVBox javadocsValuesButtons = new ButtonVBox(addValueButton, deleteValueButton, editValueButton);
        javadocsValuesButtons.bindSizes();

        javadocsUrlListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && javadocsMap.containsKey(newVal)) {
                javadocsValuesListView.getItems().setAll(javadocsMap.get(newVal));
            } else {
                javadocsValuesListView.getItems().clear();
            }
        });

        HBox javadocsPane = new HBox(javadocsButtons, javadocsUrlListView, javadocsValuesListView, javadocsValuesButtons);

        javadocsUrlListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            setText(item);
                            if (item.equals("https://jd.papermc.io/paper/1.21.4/") || item.equals("https://docs.oracle.com/en/java/javase/21/docs/api/java.base/")) {
                                setGraphic(new FontIcon(FontAwesomeSolid.LOCK));
                            } else {
                                setGraphic(null);
                            }
                        } else {
                            setText(null);
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        javadocsPane.getStyleClass().add("javadocs-settings-pane");
        javadocsPane.setSpacing(10);
        javadocsValuesListView.setPlaceholder(new Label(VisualBukkitApp.localizedText("label.no_javadocs")));
        javadocsValuesListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null && !empty) {
                            setText(item);
                            if (DEFAULT_JAVADOCS_PACKAGES.contains(item) || item.equals("java") || item.equals("jdk")) {
                                setGraphic(new FontIcon(FontAwesomeSolid.LOCK));
                            } else {
                                setGraphic(null);
                            }
                        } else {
                            setText(null);
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        Tab pluginYmlTab = new Tab(VisualBukkitApp.localizedText("label.plugin_attributes"), pluginSettings.getGrid());
        Tab mavenTab = new Tab(VisualBukkitApp.localizedText("label.maven"), mavenPane);
        Tab modulesTab = new Tab(VisualBukkitApp.localizedText("label.modules"), moduleSelector);
        Tab javadocsTab = new Tab(VisualBukkitApp.localizedText("label.javadocs"), javadocsPane);
        TabPane settingsTabPane = new TabPane(pluginYmlTab, modulesTab, mavenTab, javadocsTab, new Tab());
        settingsTabPane.getStyleClass().add("plugin-settings-tab-pane");
        settingsTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        PopupWindow pluginSettingsWindow = new PopupWindow(VisualBukkitApp.localizedText("window.plugin_settings"), settingsTabPane);
        pluginSettingsWindow.setOnShowing(e -> {
            pluginSettingsWindow.setY(VisualBukkitApp.getPrimaryStage().getY() + VisualBukkitApp.getPrimaryStage().getHeight() / 2 - 300);
            if (settingsTabPane.getSelectionModel().getSelectedItem() != javadocsTab) {
                pluginSettingsWindow.setX(VisualBukkitApp.getPrimaryStage().getX() + VisualBukkitApp.getPrimaryStage().getWidth() / 2 - 400);
                pluginSettingsWindow.setWidth(800);
            }
            else {
                pluginSettingsWindow.setX(VisualBukkitApp.getPrimaryStage().getX() + VisualBukkitApp.getPrimaryStage().getWidth() / 2 - 700);
                pluginSettingsWindow.setWidth(1400);
                javadocsPane.setPrefSize(1400, 600);
            }
            settingsTabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
                if (newTab == javadocsTab) {
                    pluginSettingsWindow.setX(VisualBukkitApp.getPrimaryStage().getX() + VisualBukkitApp.getPrimaryStage().getWidth() / 2 - 700);
                    pluginSettingsWindow.setWidth(1400);
                    javadocsPane.setPrefSize(1400, 600);
                } else {
                    pluginSettingsWindow.setX(VisualBukkitApp.getPrimaryStage().getX() + VisualBukkitApp.getPrimaryStage().getWidth() / 2 - 400);
                    pluginSettingsWindow.setWidth(800);
                }
            });

            CheckBoxTreeItem<Object> rootItem = new CheckBoxTreeItem<>(VisualBukkitApp.localizedText("label.plugin_components"));
            rootItem.setSelected(true);
            rootItem.setExpanded(true);
            for (PluginComponent pluginComponent : pluginComponents) {
                CheckBoxTreeItem<Object> treeItem = new CheckBoxTreeItem<>(pluginComponent);
                treeItem.setSelected(!pluginComponent.isDisabled());
                pluginComponent.disabledProperty().bind(treeItem.selectedProperty().not());
                rootItem.getChildren().add(treeItem);
            }
            Label label = new Label(VisualBukkitApp.localizedText("label.included"));
            GridPane.setValignment(label, VPos.TOP);
            GridPane gridPane = new GridPane();
            gridPane.addRow(0, new Label(VisualBukkitApp.localizedText("label.jar_output")), new HBox(jarOutputField, new IconButton(FontAwesomeRegular.FOLDER_OPEN, e2 -> {
                File dir = new DirectoryChooser().showDialog(pluginSettingsWindow);
                if (dir != null) {
                    jarOutputField.setText(dir.getAbsolutePath());
                }
            })));
            gridPane.addRow(1, new Label(VisualBukkitApp.localizedText("label.package")), packageField);
            gridPane.addRow(2, new Label(VisualBukkitApp.localizedText("label.resources")), new ActionButton(VisualBukkitApp.localizedText("button.open_folder"), e2 -> {
                try {
                    Files.createDirectories(resourcesDirectory);
                    VisualBukkitApp.openURI(resourcesDirectory.toUri());
                } catch (IOException ioe) {
                    VisualBukkitApp.displayException(ioe);
                }
            }));
            CheckTreeView<Object> treeView = new CheckTreeView<>(rootItem);
            gridPane.addRow(3, new Label(VisualBukkitApp.localizedText("label.debug_mode")), new HBox(debugModeCheckBox, new IconButton(FontAwesomeRegular.QUESTION_CIRCLE, e2 -> VisualBukkitApp.openURI(URI.create("https://github.com/OfficialDonut/VisualBukkit/wiki/Development-Assistance-Plugin#debug-mode")))));
            gridPane.addRow(4, label, treeView);
            gridPane.getStyleClass().add("build-settings-pane");
            settingsTabPane.getTabs().set(4, new Tab(VisualBukkitApp.localizedText("label.build"), gridPane));
        });
        pluginSettingsWindow.setOnHidden(e -> {
            if (reloadRequired) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText(String.format(VisualBukkitApp.localizedText("dialog.confirm_reload")));
                alert.setHeaderText(null);
                alert.setGraphic(null);
                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType == ButtonType.OK) {
                        reloadRequired = false;
                        ProjectManager.open(getName());
                    }
                });
            }
        });

        ListView<PluginComponent> pluginComponentListView = new ListView<>(pluginComponents.sorted());
        pluginComponentListView.setPlaceholder(new Label(VisualBukkitApp.localizedText("label.no_plugin_components")));
        pluginComponentListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        HBox pluginComponentHBox = new HBox(pluginComponentListView);
        pluginComponentHBox.getStyleClass().add("plugin-component-list");
        PopupWindow pluginComponentWindow = new PopupWindow(VisualBukkitApp.localizedText("window.plugin_components"), pluginComponentHBox);
        ButtonVBox pluginComponentButtons = new ButtonVBox(
                new ActionButton(VisualBukkitApp.localizedText("button.add"), e -> {
                    pluginComponentWindow.close();
                    promptAddPluginComponent();
                }),
                new ActionButton(VisualBukkitApp.localizedText("button.import"), e -> {
                    pluginComponentWindow.close();
                    promptImportPluginComponent();
                }),
                new ActionButton(VisualBukkitApp.localizedText("button.export"), e -> pluginComponentListView.getSelectionModel().getSelectedItems().forEach(this::promptExportPluginComponent)),
                new ActionButton(VisualBukkitApp.localizedText("button.open"), e -> {
                    pluginComponentWindow.close();
                    pluginComponentListView.getSelectionModel().getSelectedItems().forEach(p -> openPluginComponent(p, true));
                }),
                new ActionButton(VisualBukkitApp.localizedText("button.rename"), e -> new ArrayList<>(pluginComponentListView.getSelectionModel().getSelectedItems()).forEach(this::promptRenamePluginComponent)),
                new ActionButton(VisualBukkitApp.localizedText("button.delete"), e -> new ArrayList<>(pluginComponentListView.getSelectionModel().getSelectedItems()).forEach(this::promptDeletePluginComponent)));
        pluginComponentHBox.getChildren().add(pluginComponentButtons);
        for (int i = 2; i < pluginComponentButtons.getChildren().size(); i++) {
            pluginComponentButtons.getChildren().get(i).disableProperty().bind(pluginComponentListView.getSelectionModel().selectedItemProperty().isNull());
        }
        pluginComponentListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<PluginComponent> call(ListView<PluginComponent> param) {
                ListCell<PluginComponent> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(PluginComponent item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item != null ? item.getName() : "");
                    }
                };
                cell.setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 2) {
                        pluginComponentWindow.close();
                        openPluginComponent(pluginComponentListView.getSelectionModel().getSelectedItem(), true);
                    }
                });
                return cell;
            }
        });

        Button buildButton = new Button(VisualBukkitApp.localizedText("button.build_plugin"));
        buildButton.setGraphic(new FontIcon(FontAwesomeSolid.HAMMER));
        buildButton.setOnAction(e -> {
            buildButton.setDisable(true);
            build().whenComplete((u, t) -> Platform.runLater(() -> buildButton.setDisable(false)));
        });

        HBox buttonBar = new HBox(
                new ActionButton(VisualBukkitApp.localizedText("button.add_component"), FontAwesomeSolid.PLUS, e -> promptAddPluginComponent()),
                new ActionButton(VisualBukkitApp.localizedText("button.plugin_components"), FontAwesomeSolid.LIST, e -> {
                    pluginComponentWindow.show();
                    pluginComponentButtons.bindSizes();
                }),
                new ActionButton(VisualBukkitApp.localizedText("button.plugin_settings"), FontAwesomeSolid.COG, e -> {
                    pluginSettingsWindow.show();
                    mavenButtons.bindSizes();
                }),
                buildButton);
        buttonBar.getStyleClass().add("button-bar");

        placeholderPane.getChildren().add(new Label(VisualBukkitApp.localizedText("label.add_component")));
        placeholderPane.setAlignment(Pos.CENTER);
        placeholderPane.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                promptAddPluginComponent();
            }
        });

        splitPane.getItems().addAll(statementSelector, placeholderPane);
        splitPane.widthProperty().addListener((o, oldValue, newValue) -> Platform.runLater(() -> splitPane.setDividerPositions(0.175)));
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        tabPane.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);
        projectPane.setCenter(splitPane);
        projectPane.getStyleClass().add("project-pane");
        projectPane.setBottom(buttonBar);

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            for (PluginComponent pluginComponent : pluginComponents) {
                if (pluginComponent.getName().equals(newValue.getText())) {
                    try {
                        pluginComponent.load();
                    } catch (IOException e) {
                        VisualBukkitApp.displayException(e);
                    }
                }
            }
        });
    }

    protected void open() throws IOException {
        if (Files.exists(dataFile)) {
            try {
                data = new JSONObject(Files.readString(dataFile));
            } catch (IOException | JSONException e) {
                VisualBukkitApp.getLogger().log(Level.SEVERE, "Failed to load data file", e);
            }
        }

        for (VisualBukkitExtension extension : VisualBukkitApp.getExtensions()) {
            extension.open(this);
        }

        debugModeCheckBox.setSelected(data.optBoolean("debug-mode"));
        jarOutputField.setText(data.optString("jar-output", buildDirectory.resolve("target").toString()));
        packageField.setText(data.optString("package", getName()));
        pluginSettings.deserialize(data.optJSONObject("plugin-settings", new JSONObject()));

        JSONArray enabledModulesJson = data.optJSONArray("enabled-modules");
        List<Object> enabledModules = enabledModulesJson != null ? enabledModulesJson.toList() : Collections.emptyList();
        for (PluginModule module : PluginModuleRegistry.getPluginModules()) {
            (enabledModules.contains(module.getID()) ? moduleSelector.getTargetItems() : moduleSelector.getSourceItems()).add(module);
        }
        JSONArray mavenRepoJson = data.optJSONArray("maven-repositories");
        if (mavenRepoJson != null) {
            for (Object o : mavenRepoJson) {
                mavenListView.getItems().add(MavenRepositoryModule.deserialize((JSONObject) o));
            }
        }
        JSONArray mavenDependJson = data.optJSONArray("maven-dependencies");
        if (mavenDependJson != null) {
            for (Object o : mavenDependJson) {
                mavenListView.getItems().add(MavenDependencyModule.deserialize((JSONObject) o));
            }
        }
        Collections.sort(mavenListView.getItems());

        moduleSelector.getTargetItems().addListener((ListChangeListener<PluginModule>) c -> {
            if (c.next() && (c.wasAdded() || c.wasRemoved())) {
                reloadRequired = true;
            }
        });

        BackgroundTaskExecutor.executeAndWait(
                () -> BlockRegistry.register(Project.class.getClassLoader(), "com.gmail.visualbukkit.blocks.definitions.core"),
                () -> ClassRegistry.register(Project.class.getClassLoader(), "classes/jdk.zip"),
                () -> ClassRegistry.register(Project.class.getClassLoader(), "classes/paper.zip"),
                () -> ClassRegistry.register(Project.class.getClassLoader(), "classes/bungee.zip"),
                () -> mavenListView.getItems().stream().parallel().forEach(MavenModule::enable));

        moduleSelector.getTargetItems().stream().forEach(PluginModule::enable);

        if (Files.exists(pluginComponentDirectory)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(pluginComponentDirectory)) {
                for (Path path : stream) {
                    PluginComponent pluginComponent = new PluginComponent(this, path);
                    pluginComponents.add(pluginComponent);
                }
            }
        }

        JSONArray openPluginComponentsJson = data.optJSONArray("open-plugin-components");
        if (openPluginComponentsJson != null) {
            for (Object obj : openPluginComponentsJson) {
                for (PluginComponent pluginComponent : pluginComponents) {
                    if (pluginComponent.getName().equals(obj)) {
                        openPluginComponent(pluginComponent, false);
                        break;
                    }
                }
            }
        }

        if (data.has("javadocs")) {
            JSONObject javadocsJson = data.getJSONObject("javadocs");
            javadocsMap.clear();
            javadocsUrlListView.getItems().clear();
            for (String url : javadocsJson.keySet()) {
                JSONArray arr = javadocsJson.getJSONArray(url);
                List<String> list = new ArrayList<>();
                for (int i = 0; i < arr.length(); i++) {
                    list.add(arr.getString(i));
                }
                javadocsMap.put(url, list);
                javadocsUrlListView.getItems().add(url);
            }
        }

        if (!javadocsMap.containsKey(DEFAULT_JAVADOCS_URL) || javadocsMap.get(DEFAULT_JAVADOCS_URL).isEmpty()) {
            javadocsMap.put(DEFAULT_JAVADOCS_URL, DEFAULT_JAVADOCS_PACKAGES);
            javadocsUrlListView.getItems().add(DEFAULT_JAVADOCS_URL);
            javadocsValuesListView.getItems().setAll(DEFAULT_JAVADOCS_PACKAGES);
        }
        if (javadocsUrlListView.getSelectionModel().isEmpty()) {
            javadocsUrlListView.getSelectionModel().select(DEFAULT_JAVADOCS_URL);
        }

        tabPane.getSelectionModel().select(data.optInt("selected-tab"));
        statementSelector.reloadStatements();
        VisualBukkitApp.getRootPane().setCenter(projectPane);
        VisualBukkitApp.getData().put("current-project", getName());
        VisualBukkitApp.getPrimaryStage().setTitle("Visual Bukkit - " + getName());
    }

    public void save() throws IOException {
        data.remove("open-plugin-components");
        data.remove("disabled-plugin-components");
        data.remove("enabled-modules");
        data.remove("maven-repositories");
        data.remove("maven-dependencies");
        data.remove("javadocs");
        data.put("selected-tab", tabPane.getSelectionModel().getSelectedIndex());
        data.put("plugin-settings", pluginSettings.serialize());
        data.put("debug-mode", debugModeCheckBox.isSelected());
        data.put("jar-output", jarOutputField.getText());
        data.put("package", packageField.getText());
        for (Tab tab : tabPane.getTabs()) {
            data.append("open-plugin-components", tab.getText());
        }
        for (PluginModule module : moduleSelector.getTargetItems()) {
            data.append("enabled-modules", module.getID());
        }
        for (MavenModule mavenModule : mavenListView.getItems()) {
            if (mavenModule.isUserDefined()) {
                data.append(mavenModule instanceof MavenDependencyModule ? "maven-dependencies" : "maven-repositories", mavenModule.serialize());
            }
        }
        for (VisualBukkitExtension extension : VisualBukkitApp.getExtensions()) {
            extension.save(this);
        }
        for (PluginComponent pluginComponent : pluginComponents) {
            pluginComponent.save();
        }
        JSONObject javadocsJson = new JSONObject();
        for (Map.Entry<String, List<String>> entry : javadocsMap.entrySet()) {
            javadocsJson.put(entry.getKey(), new JSONArray(entry.getValue()));
        }
        data.put("javadocs", javadocsJson);
        Files.createDirectories(directory);
        Files.writeString(dataFile, data.toString(2));
    }

    public void openPluginComponent(PluginComponent pluginComponent, boolean selectTab) {
        Tab tab = openPluginComponents.computeIfAbsent(pluginComponent, k -> {
            Tab newTab = new Tab(k.getName(), pluginComponent.getPane());
            tabPane.getTabs().add(newTab);
            splitPane.getItems().set(1, tabPane);
            newTab.setOnClosed(e -> {
                try {
                    openPluginComponents.remove(pluginComponent);
                    if (tabPane.getTabs().isEmpty()) {
                        splitPane.getItems().set(1, placeholderPane);
                    }
                    pluginComponent.unload();
                    VisualBukkitApp.displayInfo(VisualBukkitApp.localizedText("notification.saved_plugin_component"));
                } catch (IOException ex) {
                    VisualBukkitApp.displayException(ex);
                }
            });
            return newTab;
        });
        if (selectTab) {
            tabPane.getSelectionModel().select(tab);
        }
    }

    public void promptAddPluginComponent() {
        TextField nameField = new TextField();
        SearchableComboBox<BlockFactory<PluginComponentBlock>> typeComboBox = new SearchableComboBox<>();
        typeComboBox.getItems().addAll(new TreeSet<>(BlockRegistry.getPluginComponents()));
        typeComboBox.getSelectionModel().selectFirst();
        nameField.promptTextProperty().bind(typeComboBox.valueProperty().asString());
        GridPane gridPane = new GridPane();
        gridPane.addRow(0, new Label(VisualBukkitApp.localizedText("dialog.add_component_name")), nameField);
        gridPane.addRow(1, new Label(VisualBukkitApp.localizedText("dialog.add_component_type")), typeComboBox);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(VisualBukkitApp.localizedText("window.add_component"));
        dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(gridPane);
        dialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                String name = nameField.getText();
                if (name.isBlank()) {
                    int i = 1;
                    do {
                        name = nameField.getPromptText().replace(" ", "_") + "_" + i++;
                    } while (Files.exists(pluginComponentDirectory.resolve(name)) && i < 999);
                }
                if (isPluginComponentNameValid(name)) {
                    try {
                        PluginComponent pluginComponent = new PluginComponent(this, pluginComponentDirectory.resolve(name), typeComboBox.getValue().newBlock());
                        pluginComponent.save();
                        pluginComponents.add(pluginComponent);
                        openPluginComponent(pluginComponent, true);
                        VisualBukkitApp.displayInfo(VisualBukkitApp.localizedText("notification.added_plugin_component"));
                    } catch (IOException e) {
                        VisualBukkitApp.displayException(e);
                    }
                } else {
                    promptAddPluginComponent();
                }
            }
        });
    }

    public void promptDeletePluginComponent(PluginComponent pluginComponent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(String.format(VisualBukkitApp.localizedText("dialog.confirm_delete"), pluginComponent.getName()));
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    pluginComponent.delete();
                    pluginComponents.remove(pluginComponent);
                    tabPane.getTabs().remove(openPluginComponents.remove(pluginComponent));
                    if (tabPane.getTabs().isEmpty()) {
                        splitPane.getItems().set(1, placeholderPane);
                    }
                    VisualBukkitApp.displayInfo(VisualBukkitApp.localizedText("notification.deleted_plugin_component"));
                } catch (IOException e) {
                    VisualBukkitApp.displayException(e);
                }
            }
        });
    }

    public void promptExportPluginComponent(PluginComponent pluginComponent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Zip", "*.zip"));
        File file = fileChooser.showSaveDialog(VisualBukkitApp.getPrimaryStage());
        if (file != null) {
            try {
                pluginComponent.save();
                ZipUtil.pack(pluginComponent.getDirectory().toFile(), file);
                VisualBukkitApp.displayInfo(VisualBukkitApp.localizedText("notification.exported_plugin_component"));
            } catch (IOException e) {
                VisualBukkitApp.displayException(e);
            }
        }
    }

    public void promptImportPluginComponent() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Zip", "*.zip"));
        File file = fileChooser.showOpenDialog(VisualBukkitApp.getPrimaryStage());
        if (file != null) {
            promptImportPluginComponent(file.toPath());
        }
    }

    public void promptImportPluginComponent(Path file) {
        TextInputDialog importDialog = new TextInputDialog();
        importDialog.setTitle(VisualBukkitApp.localizedText("window.import_plugin_component"));
        importDialog.setContentText(VisualBukkitApp.localizedText("dialog.import_plugin_component"));
        importDialog.setHeaderText(null);
        importDialog.setGraphic(null);
        importDialog.showAndWait().ifPresent(name -> {
            if (isPluginComponentNameValid(name)) {
                ZipUtil.unpack(file.toFile(), pluginComponentDirectory.resolve(name).toFile());
                PluginComponent pluginComponent = new PluginComponent(this, pluginComponentDirectory.resolve(name));
                pluginComponents.add(pluginComponent);
                openPluginComponent(pluginComponent, true);
                VisualBukkitApp.displayInfo(VisualBukkitApp.localizedText("notification.imported_plugin_component"));
            } else {
                promptImportPluginComponent(file);
            }
        });
    }

    public void promptRenamePluginComponent(PluginComponent pluginComponent) {
        TextInputDialog renameDialog = new TextInputDialog();
        renameDialog.setTitle(VisualBukkitApp.localizedText("window.rename_plugin_component"));
        renameDialog.setContentText(VisualBukkitApp.localizedText("dialog.rename_plugin_component"));
        renameDialog.setHeaderText(null);
        renameDialog.setGraphic(null);
        renameDialog.showAndWait().ifPresent(name -> {
            if (isPluginComponentNameValid(name)) {
                try {
                    pluginComponent.save();
                    Files.move(pluginComponent.getDirectory(), pluginComponentDirectory.resolve(name));
                    pluginComponents.remove(pluginComponent);
                    tabPane.getTabs().remove(openPluginComponents.remove(pluginComponent));
                    PluginComponent renamedPluginComponent = new PluginComponent(this, pluginComponentDirectory.resolve(name));
                    pluginComponents.add(renamedPluginComponent);
                    openPluginComponent(renamedPluginComponent, true);
                    VisualBukkitApp.displayInfo(VisualBukkitApp.localizedText("notification.renamed_plugin_component"));
                } catch (IOException e) {
                    VisualBukkitApp.displayException(e);
                }
            } else {
                promptRenamePluginComponent(pluginComponent);
            }
        });
    }

    private boolean isPluginComponentNameValid(String name) {
        if (!name.matches("[_a-zA-Z0-9]+")) {
            VisualBukkitApp.displayError(VisualBukkitApp.localizedText("notification.plugin_component_invalid_name"));
            return false;
        }
        if (Files.exists(pluginComponentDirectory.resolve(name))) {
            VisualBukkitApp.displayError(VisualBukkitApp.localizedText("notification.plugin_component_duplicate"));
            return false;
        }
        return true;
    }

    public void promptAddMavenDependency(MavenDependencyModule dependToEdit) {
        TextField groupIdField = new TextField();
        TextField artifactIdField = new TextField();
        TextField versionField = new TextField();
        SearchableComboBox<String> scopeField = new SearchableComboBox<>();
        scopeField.getItems().setAll(JavaScopes.COMPILE, JavaScopes.PROVIDED, JavaScopes.RUNTIME, JavaScopes.TEST, JavaScopes.SYSTEM);
        scopeField.setValue(JavaScopes.PROVIDED);
        if (dependToEdit != null) {
            groupIdField.setText(dependToEdit.getDependency().getArtifact().getGroupId());
            artifactIdField.setText(dependToEdit.getDependency().getArtifact().getArtifactId());
            versionField.setText(dependToEdit.getDependency().getArtifact().getVersion());
            scopeField.setValue(dependToEdit.getDependency().getScope());
        }
        GridPane gridPane = new GridPane();
        gridPane.addRow(0, new Label("groupId"), groupIdField);
        gridPane.addRow(1, new Label("artifactId"), artifactIdField);
        gridPane.addRow(2, new Label("version"), versionField);
        gridPane.addRow(3, new Label("scope"), scopeField);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(VisualBukkitApp.localizedText("window.add_maven_dependency"));
        dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(gridPane);
        dialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                if (groupIdField.getText().isBlank() || artifactIdField.getText().isBlank() || versionField.getText().isBlank()) {
                    VisualBukkitApp.displayError(VisualBukkitApp.localizedText("notification.invalid_maven_dependency"));
                    return;
                }
                if (dependToEdit != null) {
                    mavenListView.getItems().remove(dependToEdit);
                }
                DefaultArtifact artifact = new DefaultArtifact(String.format("%s:%s:%s", groupIdField.getText(), artifactIdField.getText(), versionField.getText()));
                mavenListView.getItems().add(new MavenDependencyModule(new Dependency(artifact, scopeField.getValue()), true));
                Collections.sort(mavenListView.getItems());
                reloadRequired = true;
            }
        });
    }

    public void promptAddMavenRepository(MavenRepositoryModule repoToEdit) {
        TextField idField = new TextField();
        TextField urlField = new TextField();
        if (repoToEdit != null) {
            idField.setText(repoToEdit.getRepository().getId());
            urlField.setText(repoToEdit.getRepository().getUrl());
        }
        GridPane gridPane = new GridPane();
        gridPane.addRow(0, new Label("id"), idField);
        gridPane.addRow(1, new Label("url"), urlField);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(VisualBukkitApp.localizedText("window.add_maven_repository"));
        dialog.getDialogPane().getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(gridPane);
        dialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                if (idField.getText().isBlank() || urlField.getText().isBlank()) {
                    VisualBukkitApp.displayError(VisualBukkitApp.localizedText("notification.invalid_maven_repository"));
                    return;
                }
                if (repoToEdit != null) {
                    mavenListView.getItems().remove(repoToEdit);
                }
                RemoteRepository repository = new RemoteRepository.Builder(idField.getText(), "default", urlField.getText()).build();
                mavenListView.getItems().add(new MavenRepositoryModule(repository, true));
                Collections.sort(mavenListView.getItems());
                reloadRequired = true;
            }
        });
    }

    public CompletableFuture<Void> build() {
        VisualBukkitApp.getLogger().info("Building plugin...");
        VisualBukkitApp.getLogWindow().show();
        return BackgroundTaskExecutor.execute(() -> {
            try {
                String name = pluginSettings.getPluginName();
                String version = pluginSettings.getPluginVersion();
                if (name.isBlank()) {
                    name = "Plugin";
                }
                if (version.isBlank()) {
                    version = "1.0";
                }
                String packageName = packageField.getText();
                if (packageName.isBlank()) {
                    packageName = getName();
                }

                Path mainDir = buildDirectory.resolve("src").resolve("main");
                Path packageDir = mainDir.resolve("java").resolve(packageName.replace(".", File.separator));
                Path resourcesDir = mainDir.resolve("resources");
                if (Files.exists(buildDirectory)) {
                    MoreFiles.deleteRecursively(buildDirectory, RecursiveDeleteOption.ALLOW_INSECURE);
                }
                Files.createDirectories(packageDir);
                Files.createDirectories(resourcesDir);

                JavaClassSource mainClass = Roaster.parse(JavaClassSource.class, Resources.toString(Project.class.getResource("/plugin/PluginMain.java"), StandardCharsets.UTF_8));
                mainClass.setPackage(packageName);

                if (Files.exists(resourcesDirectory)) {
                    try (Stream<Path> stream = Files.walk(resourcesDirectory)) {
                        for (Path path : stream.toArray(Path[]::new)) {
                            if (Files.isRegularFile(path) && !Files.isHidden(path)) {
                                Path relativePath = resourcesDirectory.relativize(path);
                                Path resourceDirPath = resourcesDir.resolve(relativePath);
                                Files.createDirectories(resourceDirPath.getParent());
                                Files.copy(path, resourceDirPath);
                                String filePath = StringEscapeUtils.escapeJava(relativePath.toString().replace("\\", "/"));
                                MethodSource<JavaClassSource> enableMethod = mainClass.getMethod("onEnable");
                                enableMethod.setBody(enableMethod.getBody() + (filePath.equals("config.yml") ? "saveDefaultConfig();" : ("PluginMain.createResourceFile(\"" + filePath + "\");")));
                            }
                        }
                    }
                }

                BuildInfo buildInfo = new BuildInfo(mainClass, debugModeCheckBox.isSelected());
                for (PluginModule module : moduleSelector.getTargetItems()) {
                    module.prepareBuild(buildInfo);
                }
                for (MavenModule module : mavenListView.getItems()) {
                    module.prepareBuild(buildInfo);
                }

                StringBuilder commandsBuilder = new StringBuilder("commands:\n");
                for (PluginComponent pluginComponent : pluginComponents) {
                    if (pluginComponent.isDisabled()) {
                        continue;
                    }
                    CompletableFuture<PluginComponentBlock> future = new CompletableFuture<>();
                    Platform.runLater(() -> {
                        try {
                            future.complete(pluginComponent.load());
                        } catch (IOException e) {
                            future.completeExceptionally(e);
                        }
                    });
                    PluginComponentBlock block = future.get();
                    block.prepareBuild(buildInfo);
                    if (block instanceof CompCommand command && !command.getName().isBlank()) {
                        commandsBuilder.append("  ").append(command.getName()).append(":\n");
                        if (!command.getAliases().isBlank()) {
                            commandsBuilder.append("    aliases: [").append(command.getAliases()).append("]\n");
                        }
                        if (!command.getDescription().isBlank()) {
                            commandsBuilder.append("    description: \"").append(command.getDescription()).append("\"\n");
                        }
                        if (!command.getPermission().isBlank()) {
                            commandsBuilder.append("    permission: \"").append(command.getPermission()).append("\"\n");
                        }
                        if (!command.getPermissionMessage().isBlank()) {
                            commandsBuilder.append("    permission-message: \"").append(command.getPermissionMessage()).append("\"\n");
                        }
                        if (!command.getUsage().isBlank()) {
                            commandsBuilder.append("    usage: \"").append(command.getUsage()).append("\"\n");
                        }
                    }
                    pluginComponent.unload();
                }

                for (JavaClassSource clazz : buildInfo.getClasses()) {
                    clazz.setPackage(packageName);
                    Files.writeString(packageDir.resolve(clazz.getName() + ".java"), clazz.toString());
                }

                Files.writeString(packageDir.resolve(mainClass.getName() + ".java"), mainClass.toString());
                Files.writeString(buildDirectory.resolve("pom.xml"), MavenUtil.createPom(name.toLowerCase(), version, buildInfo));
                Files.writeString(resourcesDir.resolve("plugin.yml"), pluginSettings.createPluginYml(name, version, mainClass.getQualifiedName()) + "\n" + commandsBuilder);

                for (PluginModule module : moduleSelector.getTargetItems()) {
                    module.prepareBuildDirectory(buildDirectory);
                }

                InvocationRequest request = new DefaultInvocationRequest();
                request.setOutputHandler(s -> VisualBukkitApp.getLogger().info(s));
                request.setErrorHandler(s -> VisualBukkitApp.getLogger().info(s));
                request.setBaseDirectory(buildDirectory.toFile());
                request.setGoals(Arrays.asList("clean", "package"));
                request.setBatchMode(true);
                InvocationResult result = MavenUtil.execute(request);
                if (result.getExecutionException() != null) {
                    VisualBukkitApp.getLogger().log(Level.SEVERE, "Failed to execute maven", result.getExecutionException());
                } else if (result.getExitCode() == 0) {
                    Path jarOutputDir = buildDirectory.resolve("target");
                    if (!jarOutputField.getText().isBlank()) {
                        Path requestedOutputDir = Paths.get(jarOutputField.getText());
                        if (Files.exists(requestedOutputDir) && !requestedOutputDir.equals(jarOutputDir)) {
                            try (DirectoryStream<Path> stream = Files.newDirectoryStream(buildDirectory.resolve("target"), "*.jar")) {
                                for (Path path : stream) {
                                    String fileName = path.getFileName().toString();
                                    if (!fileName.startsWith("original-")) {
                                        Files.copy(path, requestedOutputDir.resolve(path.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                                        jarOutputDir = requestedOutputDir;
                                    }
                                }
                            }
                        }
                    }
                    VisualBukkitApp.getLogger().info("Plugin jar location: " + jarOutputDir);
                }
            } catch (Exception e) {
                VisualBukkitApp.getLogger().log(Level.SEVERE, "Failed to build plugin", e);
            }
        });
    }

    public void addMavenDependency(Dependency dependency) {
        mavenListView.getItems().add(new MavenDependencyModule(dependency, false));
        Collections.sort(mavenListView.getItems());
    }

    public void addMavenRepository(RemoteRepository repository) {
        mavenListView.getItems().add(new MavenRepositoryModule(repository, false));
        Collections.sort(mavenListView.getItems());
    }

    public boolean isOpen(PluginComponent pluginComponent) {
        return openPluginComponents.containsKey(pluginComponent);
    }

    public PluginComponent getOpenPluginComponent() {
        for (Map.Entry<PluginComponent, Tab> entry : openPluginComponents.entrySet()) {
            if (entry.getValue().equals(tabPane.getSelectionModel().getSelectedItem())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Set<String> getPluginComponents(String type) {
        return pluginComponents.stream()
                .filter(p -> type.equals(p.getBlockType().orElse(null)))
                .map(PluginComponent::getName)
                .collect(Collectors.toSet());
    }

    public PluginComponent getPluginComponent(PluginComponentBlock block) {
        for (PluginComponent pluginComponent : pluginComponents) {
            if (block.equals(pluginComponent.getBlock().orElse(null))) {
                return pluginComponent;
            }
        }
        return null;
    }

    public PluginComponent getPluginComponent(String name) {
        for (PluginComponent pluginComponent : pluginComponents) {
            if (pluginComponent.getName().equals(name)) {
                return pluginComponent;
            }
        }
        return null;
    }

    public ObservableList<PluginComponent> getPluginComponents() {
        return pluginComponents;
    }

    public String getName() {
        return directory.getFileName().toString();
    }

    public Path getDirectory() {
        return directory;
    }

    public static HashMap<String, List<String>> getJavadocsMap() {
        return javadocsMap;
    }

    public Path getBuildDirectory() {
        return buildDirectory;
    }

    public JSONObject getData() {
        return data;
    }
}
