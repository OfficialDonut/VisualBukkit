package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.blocks.BlockRegistry;
import com.gmail.visualbukkit.reflection.ClassRegistry;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import org.apache.commons.lang3.StringUtils;

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
        createDialog.setContentText(VisualBukkitApp.localizedText("dialog.project_create"));
        Optional<String> name = createDialog.showAndWait();
        if (name.isPresent() && isProjectNameValid(name.get())) {
            open(name.get());
        } else if (!canCancel) {
            promptCreate(false);
        }
    }

    public static void promptOpen(boolean canCancel) {
        ChoiceDialog<String> openDialog = new ChoiceDialog<>();
        openDialog.setContentText(VisualBukkitApp.localizedText("dialog.project_open"));
        openDialog.getItems().addAll(findProjects());
        openDialog.showAndWait().ifPresentOrElse(ProjectManager::open, () -> {
            if (!canCancel) {
                promptOpen(false);
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
