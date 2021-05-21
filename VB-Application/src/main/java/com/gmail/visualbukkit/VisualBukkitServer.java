package com.gmail.visualbukkit;

import com.gmail.visualbukkit.blocks.*;
import com.gmail.visualbukkit.blocks.definitions.ExprSerializedItem;
import com.gmail.visualbukkit.blocks.parameters.ExpressionParameter;
import com.gmail.visualbukkit.blocks.parameters.InputParameter;
import com.gmail.visualbukkit.blocks.parameters.StringLiteralParameter;
import com.gmail.visualbukkit.project.ProjectManager;
import com.gmail.visualbukkit.ui.CopyPasteManager;
import com.gmail.visualbukkit.ui.LanguageManager;
import com.gmail.visualbukkit.ui.NotificationManager;
import javafx.application.Platform;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

public class VisualBukkitServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataInputStream input;
    private DataOutputStream output;

    public CompletableFuture<Void> start() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        VisualBukkitApp.getExecutorService().submit(() -> {
            synchronized (this) {
                if (isRunning()) {
                    future.complete(null);
                } else {
                    try {
                        serverSocket = new ServerSocket(56420, 0, InetAddress.getLoopbackAddress());
                        VisualBukkitApp.getExecutorService().submit(this::acceptClients);
                        future.complete(null);
                    } catch (IOException e) {
                        future.completeExceptionally(e);
                    }
                }
            }
        });
        return future;
    }

    public CompletableFuture<Void> send(JSONObject json) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        VisualBukkitApp.getExecutorService().submit(() -> {
            synchronized (this) {
                if (isClientConnected()) {
                    try {
                        output.writeUTF(json.toString());
                        future.complete(null);
                    } catch (IOException e) {
                        future.completeExceptionally(e);
                    }
                } else {
                    future.completeExceptionally(new IllegalStateException("client not connected"));
                }
            }
        });
        return future;
    }

    private void acceptClients() {
        while (isRunning()) {
            try {
                Socket socket = serverSocket.accept();
                synchronized (this) {
                    kickClient();
                    clientSocket = socket;
                    input = new DataInputStream(clientSocket.getInputStream());
                    output = new DataOutputStream(clientSocket.getOutputStream());
                    VisualBukkitApp.getExecutorService().submit(this::readInput);
                    System.out.println("A localhost server connected to the app.");
                }
            } catch (IOException e) {
                if (isRunning()) {
                    stop();
                }
            }
        }
    }

    private void readInput() {
        while (isClientConnected()) {
            try {
                JSONObject json = new JSONObject(input.readUTF());
                String id = json.optString("id");
                if ("item".equals(id)) {
                    Platform.runLater(() -> {
                        CopyPasteManager.copyExpression(((ExprSerializedItem) BlockRegistry.getExpression("expr-serialized-item")).createBlock(json.optString("yaml", "")));
                        NotificationManager.displayMessage(LanguageManager.get("message.imported_item.title"), LanguageManager.get("message.imported_item.content"));
                    });
                } else if ("inv".equals(id)) {
                    Platform.runLater(() -> {
                        PluginComponent.Block guiBlock = BlockRegistry.getPluginComponent("comp-create-gui").createBlock();
                        ProjectManager.getCurrentProject().addPluginComponent(guiBlock);
                        JSONArray itemArray = json.optJSONArray("items");
                        if (itemArray != null) {
                            for (Object obj : itemArray) {
                                if (obj instanceof JSONObject) {
                                    JSONObject itemJson = (JSONObject) obj;
                                    Statement.Block setItemBlock = BlockRegistry.getStatement("1b2f17c0b118cd5036f48cbd82ad5845").createBlock();
                                    Expression.Block slotBlock = BlockRegistry.getExpression("expr-number").createBlock();
                                    ((InputParameter) slotBlock.getParameters().get(0)).setText(String.valueOf(itemJson.optInt("slot")));
                                    ((ExpressionParameter) setItemBlock.getParameters().get(0)).setExpression(BlockRegistry.getExpression("expr-gui-inventory").createBlock()).run();
                                    ((ExpressionParameter) setItemBlock.getParameters().get(1)).setExpression(slotBlock).run();
                                    ((ExpressionParameter) setItemBlock.getParameters().get(2)).setExpression(((ExprSerializedItem) BlockRegistry.getExpression("expr-serialized-item")).createBlock(itemJson.optString("yaml", ""))).run();
                                    guiBlock.getStatementHolder().addLast(setItemBlock);
                                }
                            }
                        }
                        NotificationManager.displayMessage(LanguageManager.get("message.imported_gui.title"), LanguageManager.get("message.imported_gui.content"));
                    });
                } else if ("loc".equals(id) || "block-loc".equals(id)) {
                    Platform.runLater(() -> {
                        boolean isBlockLoc = "block-loc".equals(id);
                        Expression.Block locBlock = BlockRegistry.getExpression(isBlockLoc ? "3b9e7a63077b10c70abc69e996e984bb" : "e6dcf84ff601849c49921eb275bb5c32").createBlock();
                        Expression.Block worldBlock = BlockRegistry.getExpression("expr-new-string").createBlock();
                        ((StringLiteralParameter) worldBlock.getParameters().get(0)).setText(json.optString("world", ""));
                        ((ExpressionParameter) locBlock.getParameters().get(0)).setExpression(worldBlock).run();
                        String[] fields = isBlockLoc ? new String[]{"x", "y", "z"} : new String[]{"x", "y", "z", "yaw", "pitch"};
                        for (int i = 0; i < fields.length; i++) {
                            Expression.Block numberBlock = BlockRegistry.getExpression("expr-number").createBlock();
                            ((InputParameter) numberBlock.getParameters().get(0)).setText(String.valueOf(json.optDouble(fields[i])));
                            ((ExpressionParameter) locBlock.getParameters().get(i + 1)).setExpression(numberBlock).run();
                        }
                        CopyPasteManager.copyExpression(locBlock);
                        NotificationManager.displayMessage(LanguageManager.get("message.imported_location.title"), LanguageManager.get("message.imported_location.content"));
                    });
                }
            } catch (JSONException ignored) {
            } catch (IOException e) {
                if (isClientConnected()) {
                    kickClient();
                }
            }
        }
    }

    public synchronized boolean isClientConnected() {
        return clientSocket != null && clientSocket.isConnected() && !clientSocket.isClosed();
    }

    public synchronized boolean isRunning() {
        return serverSocket != null && !serverSocket.isClosed();
    }

    public synchronized void stop() {
        kickClient();
        safeClose(serverSocket);
    }

    private void kickClient() {
        safeClose(clientSocket);
        safeClose(input);
        safeClose(output);
    }

    private void safeClose(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception ignored) {}
        }
    }
}
