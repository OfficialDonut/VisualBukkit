package com.gmail.visualbukkit;

import com.gmail.visualbukkit.blocks.Block;
import com.gmail.visualbukkit.blocks.StatementBlock;
import com.gmail.visualbukkit.blocks.definitions.core.*;
import com.gmail.visualbukkit.project.CopyPasteManager;
import com.gmail.visualbukkit.project.PluginComponent;
import com.gmail.visualbukkit.project.ProjectManager;
import com.gmail.visualbukkit.reflection.ClassInfo;
import com.gmail.visualbukkit.reflection.ConstructorInfo;
import com.gmail.visualbukkit.reflection.MethodInfo;
import com.gmail.visualbukkit.rpc.VisualBukkitGrpc;
import com.gmail.visualbukkit.rpc.VisualBukkitRPC;
import com.google.protobuf.ByteString;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import org.controlsfx.control.PopOver;

import javax.net.ssl.KeyManagerFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.*;
import java.util.logging.Level;

public class VisualBukkitGrpcServer extends VisualBukkitGrpc.VisualBukkitImplBase {

    private static final VisualBukkitGrpcServer instance = new VisualBukkitGrpcServer();
    private final Properties properties = new Properties();
    private Server server;

    public static VisualBukkitGrpcServer getInstance() {
        return instance;
    }

    private VisualBukkitGrpcServer() {
        Path propertiesFile = VisualBukkitApp.getDataDirectory().resolve("grpc.properties");
        if (Files.notExists(propertiesFile)) {
            try (InputStream is = getClass().getResourceAsStream("/config/grpc.properties"); OutputStream os = Files.newOutputStream(propertiesFile)) {
                is.transferTo(os);
            } catch (IOException e) {
                VisualBukkitApp.getLogger().log(Level.SEVERE, "Failed to create " + propertiesFile, e);
            }
        }
        try (InputStream is = Files.newInputStream(propertiesFile)) {
            properties.load(is);
        } catch (IOException e) {
            VisualBukkitApp.getLogger().log(Level.SEVERE, "Failed to read " + propertiesFile, e);
        }
    }

    public void start() throws IOException, NoSuchAlgorithmException, KeyStoreException, CertificateException, UnrecoverableKeyException {
        stop();
        int port = Integer.parseInt(properties.getProperty("port"));
        boolean useTls = "true".equalsIgnoreCase(properties.getProperty("tls.enabled"));
        if (useTls) {
            KeyStore keyStore = KeyStore.getInstance(new File(properties.getProperty("tls.keystore.file")), properties.getProperty("tls.keystore.password").toCharArray());
            KeyManagerFactory kmFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmFactory.init(keyStore, properties.getProperty("tls.key.password").toCharArray());
            ServerCredentials credentials = TlsServerCredentials.newBuilder().keyManager(kmFactory.getKeyManagers()).build();
            server = Grpc.newServerBuilderForPort(port, credentials).addService(this).build();
        } else {
            server = ServerBuilder.forPort(port).addService(this).build();
        }
        server.start();
        VisualBukkitApp.getLogger().info("gRPC server running on port " + port + " " + (useTls ? "(encrypted)" : "(unencrypted)"));
    }

    public void stop() {
        if (server != null) {
            server.shutdownNow();
        }
    }

    @Override
    public void ping(VisualBukkitRPC.PingRequest request, StreamObserver<VisualBukkitRPC.Response> responseObserver) {
        Platform.runLater(() -> {
            try {
                VisualBukkitRPC.Response.Builder response = VisualBukkitRPC.Response.newBuilder();
                TextInputDialog responseDialog = new TextInputDialog();
                responseDialog.setTitle("Ping Request");
                responseDialog.setContentText("Response:");
                responseDialog.setHeaderText(null);
                responseDialog.setGraphic(null);
                responseDialog.showAndWait().ifPresent(response::setMessage);
                responseObserver.onNext(response.build());
            } finally {
                responseObserver.onCompleted();
            }
        });
    }

    @Override
    public void importItemStack(VisualBukkitRPC.ImportItemStackRequest request, StreamObserver<VisualBukkitRPC.Response> responseObserver) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(VisualBukkitApp.localizedText("dialog.confirm_import_item"));
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    CopyPasteManager.copyExpression(new ExprSerializedItemStack(request.getYaml()));
                    VisualBukkitApp.displayInfo(VisualBukkitApp.localizedText("notification.imported_item"));
                }
            });
        });
        responseObserver.onNext(VisualBukkitRPC.Response.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void importLocation(VisualBukkitRPC.ImportLocationRequest request, StreamObserver<VisualBukkitRPC.Response> responseObserver) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(VisualBukkitApp.localizedText("dialog.confirm_import_location"));
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    ClassInfo bukkitClass = ClassInfo.of("org.bukkit.Bukkit");
                    ClassInfo locationClass = ClassInfo.of("org.bukkit.Location");
                    ClassInfo worldClass = ClassInfo.of("org.bukkit.World");
                    ConstructorInfo locationConstructor = locationClass.getConstructor(worldClass, ClassInfo.of(double.class), ClassInfo.of(double.class), ClassInfo.of(double.class), ClassInfo.of(float.class), ClassInfo.of(float.class)).orElseThrow();
                    ExprMethod getWorldMethod = new ExprMethod(bukkitClass, bukkitClass.getMethod("getWorld", ClassInfo.of(String.class)).orElseThrow(), new ExprString(request.getWorld()));
                    ExprNewObject newLocation = new ExprNewObject(locationClass, locationConstructor, getWorldMethod, new ExprNumber(request.getX()), new ExprNumber(request.getY()), new ExprNumber(request.getZ()), new ExprNumber(request.getYaw()), new ExprNumber(request.getPitch()));
                    CopyPasteManager.copyExpression(newLocation);
                }
            });
        });
        responseObserver.onNext(VisualBukkitRPC.Response.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void importInventory(VisualBukkitRPC.ImportInventoryRequest request, StreamObserver<VisualBukkitRPC.Response> responseObserver) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(VisualBukkitApp.localizedText("dialog.confirm_import_inventory"));
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    List<StatementBlock> blocks = new ArrayList<>();
                    ClassInfo bukkitClass = ClassInfo.of("org.bukkit.Bukkit");
                    ClassInfo invClass = ClassInfo.of("org.bukkit.inventory.Inventory");
                    ClassInfo invHolderClass = ClassInfo.of("org.bukkit.inventory.InventoryHolder");
                    ClassInfo itemstackClass = ClassInfo.of("org.bukkit.inventory.ItemStack");
                    MethodInfo createInvMethod = bukkitClass.getMethod("createInventory", invHolderClass, ClassInfo.of(int.class), ClassInfo.of(String.class)).orElseThrow();
                    MethodInfo setItemMethod = invClass.getMethod("setItem", ClassInfo.of(int.class), itemstackClass).orElseThrow();
                    blocks.add(new StatSetLocalVariable("exported-inventory", new ExprMethod(bukkitClass, createInvMethod, new ExprNull(), new ExprNumber(request.getSize()), new ExprString(request.getTitle()))));
                    for (VisualBukkitRPC.InventorySlot slot : request.getSlotList()) {
                        blocks.add(new StatMethod(invClass, setItemMethod, new ExprLocalVariable("exported-inventory"), new ExprNumber(slot.getSlot()), new ExprSerializedItemStack(slot.getYaml())));
                    }
                    CopyPasteManager.copyStatements(blocks);
                }
            });
        });
        responseObserver.onNext(VisualBukkitRPC.Response.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void reportException(VisualBukkitRPC.ReportExceptionRequest request, StreamObserver<VisualBukkitRPC.Response> responseObserver) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(VisualBukkitApp.localizedText("dialog.confirm_report_exception"));
            alert.setHeaderText(null);
            alert.setGraphic(null);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    for (PluginComponent pluginComponent : ProjectManager.current().getPluginComponents()) {
                        if (pluginComponent.containsBlock(request.getBlockUUID())) {
                            ProjectManager.current().openPluginComponent(pluginComponent, true);
                            Platform.runLater(() -> {
                                Node node = VisualBukkitApp.getPrimaryStage().getScene().lookup("#" + request.getBlockUUID());
                                if (node instanceof Block block) {
                                    Parent parent = block.getParent();
                                    while (parent != null && !(parent instanceof ScrollPane)) {
                                        parent = parent.getParent();
                                    }
                                    ScrollPane scrollPane = (ScrollPane) parent;
                                    double height = scrollPane.getViewportBounds().getHeight();
                                    scrollPane.setVvalue(scrollPane.getVmax() * ((((block.getBoundsInParent().getMaxY() + block.getBoundsInParent().getMinY()) / 2) - 0.5 * height) / (scrollPane.getContent().getBoundsInLocal().getHeight() - height)));
                                    PopOver popOver = new PopOver(new TextArea(request.getStacktrace()));
                                    popOver.getStyleClass().add("exception-popover");
                                    popOver.setAnimated(false);
                                    popOver.setDetachable(false);
                                    popOver.show(block);
                                    block.pseudoClassStateChanged(Block.INVALID_STYLE_CLASS, true);
                                    VisualBukkitApp.displayInfo(VisualBukkitApp.localizedText("notification.reported_exception"));
                                }
                            });
                            return;
                        }
                    }
                    VisualBukkitApp.displayInfo(VisualBukkitApp.localizedText("notification.reported_exception_failure"));
                }
            });
        });
        responseObserver.onNext(VisualBukkitRPC.Response.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void deployPlugin(VisualBukkitRPC.DeployPluginRequest request, StreamObserver<VisualBukkitRPC.JarFile> responseObserver) {
        Platform.runLater(() -> {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText(VisualBukkitApp.localizedText("dialog.confirm_deploy_plugin"));
                alert.setHeaderText(null);
                alert.setGraphic(null);
                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType == ButtonType.OK) {
                        Path buildDir = ProjectManager.current().getBuildDirectory().resolve("target");
                        if (Files.exists(buildDir)) {
                            try (DirectoryStream<Path> stream = Files.newDirectoryStream(buildDir, "*.jar")) {
                                for (Path path : stream) {
                                    if (!path.getFileName().toString().startsWith("original-")) {
                                        deployPlugin(path, responseObserver);
                                        return;
                                    }
                                }
                                VisualBukkitApp.displayError(VisualBukkitApp.localizedText("notification.no_plugin_jar"));
                            } catch (IOException e) {
                                VisualBukkitApp.displayException(e);
                            }
                        } else {
                            VisualBukkitApp.displayError(VisualBukkitApp.localizedText("notification.no_plugin_jar"));
                        }
                    }
                });
            } finally {
                responseObserver.onCompleted();
            }
        });
    }

    private void deployPlugin(Path jarPath, StreamObserver<VisualBukkitRPC.JarFile> responseObserver) throws IOException {
        try (InputStream is = Files.newInputStream(jarPath)) {
            byte[] bytes = new byte[1024 * 1024];
            int bytesRead;
            while ((bytesRead = is.read(bytes)) != -1) {
                responseObserver.onNext(VisualBukkitRPC.JarFile.newBuilder()
                        .setName(jarPath.getFileName().toString())
                        .setContents(ByteString.copyFrom(bytes, 0, bytesRead))
                        .build());
            }
        }
    }
}
