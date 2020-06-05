package us.donut.visualbukkit.editor;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import us.donut.visualbukkit.VisualBukkit;
import us.donut.visualbukkit.blocks.BlockRegistry;
import us.donut.visualbukkit.blocks.CodeBlock;
import us.donut.visualbukkit.plugin.PluginBuilder;
import us.donut.visualbukkit.plugin.PluginMain;
import us.donut.visualbukkit.util.CenteredHBox;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PluginTestStage extends Stage {

    private SplitPane outputPane = new SplitPane();
    private TextArea errorTextArea = new TextArea();
    private Map<Integer, PlayerVBox> playerVBoxes = new HashMap<>();
    private ListView<String> playerListView = new ListView<>();
    private ServerMock serverMock;
    private Timeline playerUpdater;

    @SuppressWarnings("unchecked")
    public PluginTestStage(Project project) {
        initModality(Modality.APPLICATION_MODAL);
        initOwner(VisualBukkit.getInstance().getPrimaryStage());
        SplitPane rootPane = new SplitPane();
        setScene(new Scene(rootPane, 1000, 600));
        setTitle("Plugin Test");
        setOnCloseRequest(e -> {
            if (playerUpdater != null) {
                playerUpdater.stop();
            }
            MockBukkit.unmock();
        });

        playerListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                outputPane.getItems().set(0, playerVBoxes.get(newValue.intValue()));
            }
        });
        Button addPlayerButton = new Button("Add Player");
        addPlayerButton.setOnAction(e -> addPlayer());
        VBox playerListVBox = new VBox(10, new Label("Players"), playerListView, addPlayerButton);
        playerListVBox.setPadding(new Insets(5, 0, 5, 0));
        playerListVBox.setAlignment(Pos.TOP_CENTER);
        playerListView.prefHeightProperty().bind(playerListVBox.heightProperty());

        VBox errorVBox = new VBox(10, new Label("Errors"), errorTextArea);
        errorVBox.setPadding(new Insets(5, 5, 5, 5));
        errorTextArea.setEditable(false);
        errorTextArea.prefHeightProperty().bind(errorVBox.heightProperty());

        outputPane.getItems().addAll(new VBox(), errorVBox);
        outputPane.setOrientation(Orientation.VERTICAL);
        outputPane.setDividerPositions(0.5, 0.5);

        rootPane.getStylesheets().add("/style.css");
        rootPane.getItems().addAll(playerListVBox, outputPane);
        rootPane.setDividerPositions(0.2, 0.8);

        try {
            serverMock = MockBukkit.mock();
            CtClass mainCtClass = PluginBuilder.getCtClass(PluginMain.class, null);
            for (CodeBlock block : PluginBuilder.getBlocksRecursive(project.getBlockPanes())) {
                PluginBuilder.insertMethods(BlockRegistry.getInfo(block).getUtilMethods(), mainCtClass);
            }
            for (BlockPane blockPane : project.getBlockPanes()) {
                blockPane.insertInto(mainCtClass);
            }
            String joinEventMethod =
                    "public void a" + UUID.randomUUID().toString().replace("-", "") + "(" + PlayerJoinEvent.class.getCanonicalName() + " event) {" +
                    "Bukkit.broadcastMessage(event.getJoinMessage());" +
                    "}";
            String chatEventMethod =
                    "public void a" + UUID.randomUUID().toString().replace("-", "") + "(" + AsyncPlayerChatEvent.class.getCanonicalName() + " event) {" +
                    "if (!event.isCancelled()) {" +
                    "Iterator it = event.getRecipients().iterator();" +
                    "while (it.hasNext()) {" +
                    "((Player) it.next()).sendMessage(\"[\" + event.getPlayer().getName() + \"] \" + event.getMessage());" +
                    "}}}";
            for (String methodString : new String[]{joinEventMethod, chatEventMethod}) {
                CtMethod event = CtMethod.make(methodString, mainCtClass);
                ConstPool methodConstPool = event.getMethodInfo().getConstPool();
                AnnotationsAttribute annotationsAttribute = new AnnotationsAttribute(methodConstPool, AnnotationsAttribute.visibleTag);
                annotationsAttribute.setAnnotation(new Annotation("org.bukkit.event.EventHandler", methodConstPool));
                event.getMethodInfo().addAttribute(annotationsAttribute);
                mainCtClass.addMethod(event);
            }
            Class<? extends JavaPlugin> mainClass = (Class<? extends JavaPlugin>) mainCtClass.toClass();
            String pluginName = project.getPluginName().replaceAll("\\s", "");
            String pluginYml = PluginBuilder.createYml(project, pluginName.isEmpty() ? "VisualBukkitPlugin" : pluginName, mainClass.getName());
            try (InputStream ymlStream = new ByteArrayInputStream(pluginYml.getBytes(StandardCharsets.UTF_8))) {
                MockBukkit.loadWith(mainClass, ymlStream);
                playerUpdater = new Timeline(new KeyFrame(Duration.seconds(0.1), e -> playerVBoxes.forEach((k , v) -> v.update())));
                playerUpdater.setCycleCount(Timeline.INDEFINITE);
                playerUpdater.play();
            }
        } catch (Throwable e) {
            displayException(e);
        }
    }

    private void addPlayer() {
        try {
            PlayerMock player = serverMock.addPlayer();
            playerVBoxes.put(serverMock.getOnlinePlayers().size() - 1, new PlayerVBox(player));
            playerListView.getItems().add(player.getName());
        } catch (Throwable e) {
            displayException(e);
        }
    }

    private void displayException(Throwable e) {
        if (!errorTextArea.getText().isEmpty()) {
            errorTextArea.appendText("\n");
        }
        errorTextArea.appendText(ExceptionUtils.getStackTrace(e));
    }

    private class PlayerVBox extends VBox {

        private TextArea outputTextArea = new TextArea();
        private TextField messageField = new TextField();
        private PlayerMock player;

        public PlayerVBox(PlayerMock player) {
            super(10);
            this.player = player;
            outputTextArea.setEditable(false);
            outputTextArea.prefHeightProperty().bind(heightProperty());
            Button sendButton = new Button("Send");
            sendButton.setOnAction(e -> sendMessage());
            setPadding(new Insets(5, 5, 5, 5));
            messageField.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    Node node = outputPane.getItems().get(0);
                    if (equals(node)) {
                        sendMessage();
                    }
                }
                e.consume();
            });
            getChildren().addAll(new Label("Player: " + player.getName()), outputTextArea, new CenteredHBox(5, new Label("Message/Command:"), messageField, sendButton));
        }

        public void sendMessage() {
            try {
                String message = messageField.getText();
                messageField.clear();
                if (message.startsWith("/")) {
                    player.performCommand(message.substring(1));
                } else {
                    player.chat(message);
                }
            } catch (Throwable ex) {
                displayException(ex);
            }
        }

        public void update() {
            String message;
            while ((message = player.nextMessage()) != null) {
                if (!outputTextArea.getText().isEmpty()) {
                    outputTextArea.appendText("\n");
                }
                outputTextArea.appendText(message);
            }
        }
    }
}
