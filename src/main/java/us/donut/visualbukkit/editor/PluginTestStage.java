package us.donut.visualbukkit.editor;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javassist.CtClass;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.bukkit.plugin.java.JavaPlugin;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.plugin.PluginBuilder;
import us.donut.visualbukkit.plugin.PluginMain;
import us.donut.visualbukkit.util.CenteredHBox;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PluginTestStage extends Stage {

    private SplitPane rootPane = new SplitPane();
    private Map<PlayerMock, VBox> playerVBoxes = new HashMap<>();
    private Map<PlayerMock, TextArea> playerOutputs = new HashMap<>();
    private ListView<String> playerListView = new ListView<>();
    private VBox outputVBox = new VBox(10);
    private TextArea errorTextArea = new TextArea();
    private ServerMock serverMock;

    @SuppressWarnings("unchecked")
    public PluginTestStage(Project project) {
        initModality(Modality.APPLICATION_MODAL);
        initOwner(VisualBukkit.getInstance().getPrimaryStage());
        setScene(new Scene(rootPane, 1000, 600));
        setTitle("Plugin Test");
        setOnCloseRequest(e -> MockBukkit.unmock());
        serverMock = MockBukkit.mock();
        try {
            CtClass mainCtClass = PluginBuilder.getCtClass(PluginMain.class, null);
            for (BlockPane blockPane : project.getBlockPanes()) {
                blockPane.insertInto(mainCtClass);
            }
            Class<? extends JavaPlugin> mainClass = (Class<? extends JavaPlugin>) mainCtClass.toClass();
            String pluginYml = PluginBuilder.createYml(project, "TestPlugin", mainClass.getName());
            init();
            try (InputStream ymlStream = new ByteArrayInputStream(pluginYml.getBytes(StandardCharsets.UTF_8))) {
                MockBukkit.loadWith(mainClass, ymlStream);
            } catch (Exception e) {
                displayException(e);
            }
        } catch (Exception e) {
            VisualBukkit.displayException("Failed to test plugin", e);
            close();
        }
    }

    private void init() {
        VBox playerVBox = new VBox(10);
        playerVBox.setPadding(new Insets(5, 0, 5, 0));
        playerVBox.setAlignment(Pos.TOP_CENTER);
        playerListView.prefHeightProperty().bind(playerVBox.heightProperty());
        playerListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                PlayerMock player = serverMock.getPlayer(newValue.intValue());
                updateOutput(player);
                if (outputVBox.getChildren().size() == 2) {
                    outputVBox.getChildren().add(playerVBoxes.get(player));
                } else {
                    outputVBox.getChildren().set(2, playerVBoxes.get(player));
                }
            }
        });
        Button addPlayerButton = new Button("Add Player");
        addPlayerButton.setOnAction(e -> addPlayer());
        playerVBox.getChildren().addAll(new Label("Players"), playerListView, addPlayerButton);

        outputVBox.setFillWidth(false);
        outputVBox.setAlignment(Pos.TOP_CENTER);
        outputVBox.setPadding(new Insets(5, 0, 5, 0));
        TextArea errorArea = new TextArea();
        errorArea.setEditable(false);
        outputVBox.getChildren().addAll(new Label("Error Log"), errorArea);

        rootPane.getItems().addAll(playerVBox, outputVBox);
        rootPane.setDividerPositions(0.25);
    }

    private void addPlayer() {
        try {
            PlayerMock player = serverMock.addPlayer();
            TextArea outputTextArea = new TextArea();
            outputTextArea.setEditable(false);
            TextField messageField = new TextField();
            Button sendButton = new Button("Send");
            sendButton.setOnAction(buttonEvent -> {
                try {
                    String message = messageField.getText();
                    messageField.clear();
                    if (message.startsWith("/")) {
                        player.performCommand(message.substring(1));
                    } else {
                        player.chat(message);
                    }
                    updateOutput(player);
                } catch (Exception ex) {
                    displayException(ex);
                }
            });
            VBox vbox = new VBox(10,
                    new Label("Output for " + player.getName()),
                    outputTextArea,
                    new CenteredHBox(10, new Label("Message/Command:"), messageField, sendButton));
            vbox.setFillWidth(false);
            vbox.setAlignment(Pos.TOP_CENTER);
            playerVBoxes.put(player, vbox);
            playerOutputs.put(player, outputTextArea);
            playerListView.getItems().add(player.getName());
            updateOutput(player);
        } catch (Exception ex) {
            displayException(ex);
        }
    }

    private void updateOutput(PlayerMock player) {
        TextArea outputTextArea = playerOutputs.get(player);
        String message;
        while ((message = player.nextMessage()) != null) {
            outputTextArea.setText(outputTextArea.getText() + "\n" + message);
        }
    }

    private void displayException(Exception e) {
        errorTextArea.setText(errorTextArea.getText() + "\n" + ExceptionUtils.getStackTrace(e));
    }
}
