package com.gmail.visualbukkit.ui;

import com.gmail.visualbukkit.VisualBukkitApp;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopupWindow extends Stage {

    public PopupWindow(String title, Parent content) {
        initOwner(VisualBukkitApp.getPrimaryStage());
        initModality(Modality.APPLICATION_MODAL);
        setScene(new Scene(content));
        setTitle(title);
        setResizable(false);
    }
}
