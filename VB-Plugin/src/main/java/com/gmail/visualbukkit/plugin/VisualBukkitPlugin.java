package com.gmail.visualbukkit.plugin;

import com.gmail.visualbukkit.rpc.VisualBukkitGrpc;
import com.gmail.visualbukkit.rpc.VisualBukkitRPC;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class VisualBukkitPlugin extends JavaPlugin {

    private static ManagedChannel grpcChannel;
    private static VisualBukkitGrpc.VisualBukkitStub grpcStub;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        setupGrpc();
    }

    @Override
    public void onDisable() {
        shutdownGrpc();
    }

    private void setupGrpc() {
        shutdownGrpc();
        grpcChannel = ManagedChannelBuilder.forAddress(getConfig().getString("host"), getConfig().getInt("port")).usePlaintext().build();
        grpcStub = VisualBukkitGrpc.newStub(grpcChannel);
    }

    private void shutdownGrpc() {
        if (grpcChannel != null) {
            grpcChannel.shutdownNow();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            reloadConfig();
            setupGrpc();
            sendFormatted("&aSuccessfully reloaded plugin.", sender);
            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("export") && args[1].equalsIgnoreCase("item")) {
            if (sender instanceof Player player) {
                ItemStack item = player.getInventory().getItemInMainHand();
                if (!item.getType().isAir()) {
                    YamlConfiguration yaml = new YamlConfiguration();
                    yaml.set("item", item);
                    sendFormatted("&eExporting item...", player);
                    grpcStub.importItemStack(VisualBukkitRPC.ImportItemStackRequest.newBuilder().setYaml(yaml.saveToString()).build(), new StreamObserver<>() {
                        @Override
                        public void onNext(VisualBukkitRPC.Response response) {
                            Bukkit.getScheduler().runTask(VisualBukkitPlugin.this, () -> sendFormatted("&aSuccessfully exported item.", player));
                        }
                        @Override
                        public void onError(Throwable e) {
                            Bukkit.getScheduler().runTask(VisualBukkitPlugin.this, () -> sendFormatted("&cFailed to export item: " + e.getMessage(), player));
                            e.printStackTrace();
                        }
                        @Override
                        public void onCompleted() {}
                    });
                } else {
                    sendFormatted("&cYou must be holding an item to use this command.", sender);
                }
            } else {
                sendFormatted("&cThis command must be executed by a player.", sender);
            }
            return true;
        }

        return false;
    }

    private void sendFormatted(String str, CommandSender sender) {
        sendColored("&7[&9VisualBukkit&7]&r " + str, sender);
    }

    private void sendColored(String str, CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str));
    }
}
