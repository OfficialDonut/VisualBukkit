package com.gmail.visualbukkit.gui;

import com.gmail.visualbukkit.VisualBukkitApp;
import com.gmail.visualbukkit.ui.LanguageManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.Objects;

public class SoftwareLicenseGui extends Stage {
    public final TableView<Software> table = new TableView<>();
    public final ObservableList<Software> data =
            FXCollections.observableArrayList(
                    new Software("Noto Sans", "SIL Open Font License 1.1")
            ).sorted(new SoftwareComparator());

    public SoftwareLicenseGui(Stage parent) {
        TableColumn<Software, String> applications = new TableColumn<>(LanguageManager.get("software.software"));
        applications.setMinWidth(300);
        applications.setCellValueFactory(t -> t.getValue().name);
        TableColumn<Software, String> licenses = new TableColumn<>(LanguageManager.get("software.license"));
        licenses.setMinWidth(200);
        licenses.setCellValueFactory(t -> t.getValue().license);

        table.getColumns().addAll(applications, licenses);

        table.setItems(data);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(table);

        try (InputStream iconInputStream = VisualBukkitApp.class.getResourceAsStream("/images/icon.png")) {
            this.getIcons().add(new Image(Objects.requireNonNull(iconInputStream)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(borderPane, 512, 384);

        VisualBukkitApp.getSettingsManager().style(scene.getRoot());

        this.setTitle(LanguageManager.get("software.title"));
        this.setResizable(false);
        this.setX(parent.getX() + 60);
        this.setY(parent.getY() + 60);
        this.setScene(scene);
        this.show();
    }

    public static class Software {
        SimpleStringProperty name;
        SimpleStringProperty license;

        public Software(String name, String license) {
            this.name = new SimpleStringProperty(name);
            this.license = new SimpleStringProperty(license);
        }
    }

    public static class SoftwareComparator implements Comparator<Software> {
        @Override
        public int compare(Software first, Software second) {
            return first.name.getName().compareTo(second.name.getName());
        }
    }
}