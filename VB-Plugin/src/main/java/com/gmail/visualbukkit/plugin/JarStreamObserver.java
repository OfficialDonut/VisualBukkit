package com.gmail.visualbukkit.plugin;

import com.gmail.visualbukkit.rpc.VisualBukkitRPC;
import io.grpc.stub.StreamObserver;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JarStreamObserver implements StreamObserver<VisualBukkitRPC.JarFile> {

    private final VisualBukkitPlugin plugin;
    private final CommandSender commandSender;
    private BufferedOutputStream bos;

    public JarStreamObserver(VisualBukkitPlugin plugin, CommandSender commandSender) {
        this.plugin = plugin;
        this.commandSender = commandSender;
    }

    @Override
    public void onNext(VisualBukkitRPC.JarFile jarFile) {
        try {
            if (bos == null) {
                Path jarPath = Bukkit.getUpdateFolderFile().toPath().resolve(jarFile.getName());
                Files.createDirectories(jarPath.getParent());
                bos = new BufferedOutputStream(Files.newOutputStream(jarPath));
            }
            jarFile.getContents().writeTo(bos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        closeJar();
        throwable.printStackTrace();
        Bukkit.getScheduler().runTask(plugin, () -> plugin.sendFormatted("&cFailed to deploy plugin: " + throwable.getMessage(), commandSender));
    }

    @Override
    public void onCompleted() {
        closeJar();
        Bukkit.getScheduler().runTask(plugin, () -> plugin.sendFormatted(bos != null ? "&aSuccessfully deployed plugin, server restart required." : "&cFailed to deploy plugin.", commandSender));
    }

    private void closeJar() {
        if (bos != null) {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
