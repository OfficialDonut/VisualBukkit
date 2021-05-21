package com.gmail.visualbukkit.plugin;

import org.bukkit.Bukkit;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class VisualBukkitClient {

    private Map<String, Consumer<JSONObject>> handlers = new ConcurrentHashMap<>();
    private VisualBukkitPlugin plugin;
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public VisualBukkitClient(VisualBukkitPlugin plugin) {
        this.plugin = plugin;
    }

    public CompletableFuture<Void> connect() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            synchronized (this) {
                if (isConnected()) {
                    future.complete(null);
                } else {
                    try {
                        socket = new Socket(InetAddress.getLoopbackAddress(), 56420);
                        input = new DataInputStream(socket.getInputStream());
                        output = new DataOutputStream(socket.getOutputStream());
                        Bukkit.getScheduler().runTaskAsynchronously(plugin, this::readInput);
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
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            synchronized (this) {
                if (isConnected()) {
                    try {
                        output.writeUTF(json.toString());
                        future.complete(null);
                    } catch (IOException e) {
                        future.completeExceptionally(e);
                    }
                } else {
                    future.completeExceptionally(new IllegalStateException("Client not connected to server"));
                }
            }
        });
        return future;
    }

    private void readInput() {
        while (isConnected()) {
            try {
                JSONObject json = new JSONObject(input.readUTF());
                Consumer<JSONObject> handler = handlers.get(json.optString("id"));
                if (handler != null) {
                    handler.accept(json);
                }
            } catch (JSONException ignored) {
            } catch (IOException e) {
                if (isConnected()) {
                    disconnect();
                }
            }
        }
    }

    public void addHandler(String id, Consumer<JSONObject> handler) {
        handlers.put(id, handler);
    }

    public synchronized boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    public synchronized void disconnect() {
        safeClose(socket);
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
