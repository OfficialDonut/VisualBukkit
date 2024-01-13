package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockRegistry;
import com.gmail.visualbukkit.reflection.ClassRegistry;
import com.google.common.io.MoreFiles;
import com.google.common.io.RecursiveDeleteOption;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.apache.commons.lang3.StringUtils;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProjectManager {

    private static final Path projectsDirectory = VisualBukkitApp.getDataDirectory().resolve("projects");
    private static Project currentProject;

    public static Project current() {
        return currentProject;
    }

    public static void openInitial() {
        Set<String> projects = findProjects();
        if (projects.isEmpty()) {
            open("DefaultProject");
            return;
        }
        if (projects.size() == 1) {
            open(projects.iterator().next());
            return;
        }
        String projectName = VisualBukkitApp.getData().optString("current-project", "");
        if (projects.contains(projectName)) {
            open(projectName);
        } else {
            promptOpen(false);
        }
    }

    public static Set<String> findProjects() {
        if (Files.notExists(projectsDirectory)) {
            return Collections.emptySet();
        }
        try (Stream<Path> stream = Files.list(projectsDirectory)) {
            return stream.filter(Files::isDirectory).map(p -> p.getFileName().toString()).collect(Collectors.toSet());
        } catch (IOException e) {
            VisualBukkitApp.displayException(e);
            return Collections.emptySet();
        }
    }

    public static void open(String projectName) {
        try {
            if (currentProject != null) {
                currentProject.save();
            }
            BlockRegistry.clear();
            ClassRegistry.clear();
            currentProject = new Project(projectsDirectory.resolve(projectName));
            currentProject.open();
        } catch (IOException e) {
            VisualBukkitApp.displayException(e);
        }
    }

    public static void promptCreate(boolean canCancel) {
        TextInputDialog createDialog = new TextInputDialog();
        createDialog.setTitle(VisualBukkitApp.localizedText("window.create_project"));
        createDialog.setContentText(VisualBukkitApp.localizedText("dialog.create_project"));
        createDialog.setHeaderText(null);
        createDialog.setGraphic(null);
        Optional<String> name = createDialog.showAndWait();
        if (name.isPresent() && isProjectNameValid(name.get())) {
            open(name.get());
        } else if (!canCancel) {
            promptCreate(false);
        }
    }

    public static void promptOpen(boolean canCancel) {
        ChoiceDialog<String> openDialog = new ChoiceDialog<>();
        openDialog.setTitle(VisualBukkitApp.localizedText("window.open_project"));
        openDialog.setContentText(VisualBukkitApp.localizedText("dialog.open_project"));
        openDialog.setHeaderText(null);
        openDialog.setGraphic(null);
        openDialog.getItems().addAll(findProjects());
        openDialog.showAndWait().ifPresentOrElse(ProjectManager::open, () -> {
            if (!canCancel) {
                promptOpen(false);
            }
        });
    }

    public static void promptRename() {
        TextInputDialog renameDialog = new TextInputDialog();
        renameDialog.setTitle(VisualBukkitApp.localizedText("window.rename_project"));
        renameDialog.setContentText(VisualBukkitApp.localizedText("dialog.rename_project"));
        renameDialog.setHeaderText(null);
        renameDialog.setGraphic(null);
        renameDialog.showAndWait().ifPresent(name -> {
            if (isProjectNameValid(name)) {
                try {
                    currentProject.save();
                    Files.move(currentProject.getDirectory(), projectsDirectory.resolve(name));
                    currentProject = null;
                    open(name);
                } catch (IOException e) {
                    VisualBukkitApp.displayException(e);
                }
            }
        });
    }

    public static void promptDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(String.format(VisualBukkitApp.localizedText("dialog.confirm_delete"), currentProject.getName()));
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.YES)).setDefaultButton(false);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setDefaultButton(true);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.YES) {
                try {
                    MoreFiles.deleteRecursively(currentProject.getDirectory(), RecursiveDeleteOption.ALLOW_INSECURE);
                    currentProject = null;
                    Set<String> projects = findProjects();
                    if (projects.isEmpty()) {
                        promptCreate(false);
                    } else {
                        promptOpen(false);
                    }
                } catch (IOException e) {
                    VisualBukkitApp.displayException(e);
                }
            }
        });
    }

    public static void promptExport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Zip", "*.zip"));
        File file = fileChooser.showSaveDialog(VisualBukkitApp.getPrimaryStage());
        if (file != null) {
            ZipUtil.pack(ProjectManager.current().getDirectory().toFile(), file);
            VisualBukkitApp.displayInfo(VisualBukkitApp.localizedText("notification.exported_project"));
        }
    }

    public static void promptImport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Zip", "*.zip"));
        File file = fileChooser.showOpenDialog(VisualBukkitApp.getPrimaryStage());
        if (file != null) {
            promptImport(file);
        }
    }

    public static void promptImport(File file) {
        TextInputDialog importDialog = new TextInputDialog();
        importDialog.setTitle(VisualBukkitApp.localizedText("window.import_project"));
        importDialog.setContentText(VisualBukkitApp.localizedText("dialog.create_project"));
        importDialog.setHeaderText(null);
        importDialog.setGraphic(null);
        importDialog.showAndWait().ifPresent(name -> {
            if (isProjectNameValid(name)) {
                ZipUtil.unpack(file, projectsDirectory.resolve(name).toFile());
                open(name);
                VisualBukkitApp.displayInfo(VisualBukkitApp.localizedText("notification.imported_project"));
            } else {
                promptImport(file);
            }
        });
    }

    private static boolean isProjectNameValid(String name) {
        if (name.isBlank() || !StringUtils.isAlphanumeric(name)) {
            VisualBukkitApp.displayError(VisualBukkitApp.localizedText("notification.project_invalid_name"));
            return false;
        }
        if (Files.exists(projectsDirectory.resolve(name))) {
            VisualBukkitApp.displayError(VisualBukkitApp.localizedText("notification.project_duplicate"));
            return false;
        }
        return true;
    }
}
