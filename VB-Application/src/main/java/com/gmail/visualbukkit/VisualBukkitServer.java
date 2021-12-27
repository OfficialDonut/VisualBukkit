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
import com.gmail.visualbukkit.ui.SelectionManager;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import org.controlsfx.control.PopOver;
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
                if ("error-report".equals(id)) {
                    Platform.runLater(() -> {
                        Statement.Block block = ProjectManager.getCurrentProject().getDebugMap().get(json.optString("block"));
                        if (block != null) {
                            PluginComponent.Block pluginComponent = block.getPluginComponentBlock();
                            if (pluginComponent != null) {
                                ProjectManager.getCurrentProject().openPluginComponent(pluginComponent);
                                ScrollPane scrollPane = (ScrollPane) pluginComponent.getTab().getContent();
                                double height = scrollPane.getViewportBounds().getHeight();
                                scrollPane.setVvalue(scrollPane.getVmax() * ((((block.getBoundsInParent().getMaxY() + block.getBoundsInParent().getMinY()) / 2) - 0.5 * height) / (scrollPane.getContent().getBoundsInLocal().getHeight() - height)));
                                PopOver popOver = new PopOver(new Label(json.optString("exception")));
                                popOver.setAnimated(false);
                                popOver.setDetachable(false);
                                popOver.show(block);
                                SelectionManager.select(block);
                                NotificationManager.displayMessage(LanguageManager.get("message.imported_error.title"), LanguageManager.get("message.imported_error.content"));
                                return;
                            }
                        }
                        NotificationManager.displayError(LanguageManager.get("message.imported_error_fail.title"), LanguageManager.get("message.imported_error_fail.content"));
                    });
                } else if ("item".equals(id)) {
                    Platform.runLater(() -> {
                        CopyPasteManager.copyExpression(((ExprSerializedItem) BlockRegistry.getExpression("expr-serialized-item")).createBlock(json.optString("yaml", "")));
                        NotificationManager.displayMessage(LanguageManager.get("message.imported_item.title"), LanguageManager.get("message.imported_item.content"));
                    });
                } else if ("inv".equals(id)) {
                    Platform.runLater(() -> {
                        PluginComponent.Block guiBlock = BlockRegistry.getPluginComponent("comp-create-gui").createBlock();
                        ProjectManager.getCurrentProject().createPluginComponent(guiBlock, true);
                        JSONArray itemArray = json.optJSONArray("items");
                        if (itemArray != null) {
                            for (Object obj : itemArray) {
                                if (obj instanceof JSONObject itemJson) {
                                    Statement.Block setItemBlock = BlockRegistry.getStatement("org.bukkit.inventory.Inventory#setItem(int,org.bukkit.inventory.ItemStack)").createBlock();
                                    Expression.Block slotBlock = BlockRegistry.getExpression("expr-number").createBlock();
                                    ((InputParameter) slotBlock.getParameters()[0]).getControl().setText(String.valueOf(itemJson.optInt("slot")));
                                    ((ExpressionParameter) setItemBlock.getParameters()[0]).setExpression(BlockRegistry.getExpression("expr-created-gui-inventory").createBlock()).run();
                                    ((ExpressionParameter) setItemBlock.getParameters()[1]).setExpression(slotBlock).run();
                                    ((ExpressionParameter) setItemBlock.getParameters()[2]).setExpression(((ExprSerializedItem) BlockRegistry.getExpression("expr-serialized-item")).createBlock(itemJson.optString("yaml", ""))).run();
                                    guiBlock.getStatementHolder().addLast(setItemBlock).run();
                                }
                            }
                        }
                        NotificationManager.displayMessage(LanguageManager.get("message.imported_gui.title"), LanguageManager.get("message.imported_gui.content"));
                    });
                } else if ("loc".equals(id) || "block-loc".equals(id)) {
                    Platform.runLater(() -> {
                        boolean isBlockLoc = "block-loc".equals(id);
                        Expression.Block locBlock = BlockRegistry.getExpression(isBlockLoc ? "org.bukkit.Location#Location(org.bukkit.World,double,double,double)" : "org.bukkit.Location#Location(org.bukkit.World,double,double,double,float,float)").createBlock();
                        Expression.Block worldBlock = BlockRegistry.getExpression("expr-new-string").createBlock();
                        ((StringLiteralParameter) worldBlock.getParameters()[0]).getControl().setText(json.optString("world", ""));
                        ((ExpressionParameter) locBlock.getParameters()[0]).setExpression(worldBlock).run();
                        String[] fields = isBlockLoc ? new String[]{"x", "y", "z"} : new String[]{"x", "y", "z", "yaw", "pitch"};
                        for (int i = 0; i < fields.length; i++) {
                            Expression.Block numberBlock = BlockRegistry.getExpression("expr-number").createBlock();
                            ((InputParameter) numberBlock.getParameters()[0]).getControl().setText(String.valueOf(json.optDouble(fields[i])));
                            ((ExpressionParameter) locBlock.getParameters()[i + 1]).setExpression(numberBlock).run();
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
