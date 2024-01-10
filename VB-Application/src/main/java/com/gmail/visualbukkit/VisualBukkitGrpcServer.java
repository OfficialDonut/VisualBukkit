package com.gmail.visualbukkit;

import com.gmail.visualbukkit.blocks.definitions.ExprSerializedItemStack;
import com.gmail.visualbukkit.project.CopyPasteManager;
import com.gmail.visualbukkit.rpc.VisualBukkitGrpc;
import com.gmail.visualbukkit.rpc.VisualBukkitRPC;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import javafx.application.Platform;
import javafx.scene.control.TextInputDialog;

import javax.net.ssl.KeyManagerFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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
        VisualBukkitApp.getLogger().info("Received PingRequest: {" + request + "}");
        responseObserver.onNext(ping(request));
        responseObserver.onCompleted();
    }

    private VisualBukkitRPC.Response ping(VisualBukkitRPC.PingRequest request) {
        CompletableFuture<VisualBukkitRPC.Response> future = new CompletableFuture<>();
        Platform.runLater(() -> {
            VisualBukkitRPC.Response.Builder response = VisualBukkitRPC.Response.newBuilder();
            TextInputDialog responseDialog = new TextInputDialog();
            responseDialog.setTitle("Ping Request");
            responseDialog.setContentText("Response:");
            responseDialog.setHeaderText(null);
            responseDialog.setGraphic(null);
            responseDialog.showAndWait().ifPresent(response::setMessage);
            future.complete(response.build());
        });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            return VisualBukkitRPC.Response.newBuilder().build();
        }
    }

    @Override
    public void importItemStack(VisualBukkitRPC.ImportItemStackRequest request, StreamObserver<VisualBukkitRPC.Response> responseObserver) {
        VisualBukkitApp.getLogger().info("Received ImportItemStackRequest: {" + request + "}");
        responseObserver.onNext(importItemStack(request));
        responseObserver.onCompleted();
    }

    private VisualBukkitRPC.Response importItemStack(VisualBukkitRPC.ImportItemStackRequest request) {
        Platform.runLater(() -> {
            CopyPasteManager.copyExpression(new ExprSerializedItemStack(request.getYaml()));
            VisualBukkitApp.displayInfo(VisualBukkitApp.localizedText("notification.imported_item"));
        });
        return VisualBukkitRPC.Response.newBuilder().build();
    }
}
