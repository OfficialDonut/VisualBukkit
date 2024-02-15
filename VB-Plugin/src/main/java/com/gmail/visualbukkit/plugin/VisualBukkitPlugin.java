package com.gmail.visualbukkit.plugin;

import com.gmail.visualbukkit.rpc.VisualBukkitGrpc;
import com.gmail.visualbukkit.rpc.VisualBukkitRPC;
import com.google.common.base.Throwables;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.security.KeyStore;

public class VisualBukkitPlugin extends JavaPlugin {

    private static VisualBukkitPlugin plugin;
    private static ManagedChannel grpcChannel;
    private static VisualBukkitGrpc.VisualBukkitStub grpcStub;
    private static VisualBukkitRPC.ReportExceptionRequest reportExceptionRequest;

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        setupGrpc();
    }

    @Override
    public void onDisable() {
        shutdownGrpc();
    }

    private void setupGrpc() {
        try {
            shutdownGrpc();
            String host = getConfig().getString("grpc.host");
            int port = getConfig().getInt("grpc.port");
            if (getConfig().getBoolean("grpc.tls.enabled")) {
                if (getConfig().isSet("grpc.tls.truststore.file")) {
                    TrustManagerFactory tmFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                    tmFactory.init(KeyStore.getInstance(new File(getConfig().getString("grpc.tls.truststore.file")), getConfig().getString("grpc.tls.truststore.password").toCharArray()));
                    ChannelCredentials credentials = TlsChannelCredentials.newBuilder().trustManager(tmFactory.getTrustManagers()).build();
                    grpcChannel = Grpc.newChannelBuilderForAddress(host, port, credentials).build();
                } else {
                    grpcChannel = ManagedChannelBuilder.forAddress(host, port).build();
                }
            } else {
                grpcChannel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
            }
            grpcStub = VisualBukkitGrpc.newStub(grpcChannel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void shutdownGrpc() {
        if (grpcChannel != null) {
            grpcChannel.shutdownNow();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
            sendColored("&b=== Visual Bukkit Commands ===", sender);
            sendColored("&9/vb &3reload", sender);
            sendColored("&9/vb &3ping", sender);
            sendColored("&9/vb &3export item", sender);
            sendColored("&9/vb &3export error", sender);
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            reloadConfig();
            setupGrpc();
            sendFormatted("&aSuccessfully reloaded plugin.", sender);
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("ping")) {
            sendFormatted("&eSending ping...", sender);
            grpcStub.ping(VisualBukkitRPC.PingRequest.newBuilder().build(), new StreamObserver<>() {
                @Override
                public void onNext(VisualBukkitRPC.Response response) {
                    if (response.hasMessage()) {
                        sendFormatted("&aSuccessfully received ping response: " + response.getMessage(), sender);
                    } else {
                        sendFormatted("&aSuccessfully received ping response.", sender);
                    }
                }
                @Override
                public void onError(Throwable throwable) {
                    Bukkit.getScheduler().runTask(VisualBukkitPlugin.this, () -> sendFormatted("&cFailed to ping: " + throwable.getMessage(), sender));
                    throwable.printStackTrace();
                }
                @Override
                public void onCompleted() {}
            });
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
                        public void onError(Throwable throwable) {
                            Bukkit.getScheduler().runTask(VisualBukkitPlugin.this, () -> sendFormatted("&cFailed to export item: " + throwable.getMessage(), player));
                            throwable.printStackTrace();
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

        if (args.length == 2 && args[0].equalsIgnoreCase("export") && args[1].equalsIgnoreCase("loc")) {
            if (sender instanceof Player player) {
                sendFormatted("&eExporting location...", player);
                exportLocation(player.getLocation(), player);
            } else {
                sendFormatted("&cThis command must be executed by a player.", sender);
            }
            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("export") && args[1].equalsIgnoreCase("block-loc")) {
            if (sender instanceof Player player) {
                Block targetBlock = player.getTargetBlockExact(10);
                if (targetBlock != null) {
                    sendFormatted("&eExporting block location...", player);
                    exportLocation(targetBlock.getLocation(), player);
                } else {
                    sendFormatted("&cYou must be looking at a block.", player);
                }
            } else {
                sendFormatted("&cThis command must be executed by a player.", sender);
            }
            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("export") && args[1].equalsIgnoreCase("chest-inv")) {
            if (sender instanceof Player player) {
                Block targetBlock = player.getTargetBlockExact(10);
                if (targetBlock != null && targetBlock.getState() instanceof Chest chest) {
                    VisualBukkitRPC.ImportInventoryRequest.Builder requestBuilder = VisualBukkitRPC.ImportInventoryRequest.newBuilder();
                    requestBuilder.setTitle(chest.getCustomName() != null ? chest.getCustomName() : "Chest").setSize(chest.getBlockInventory().getSize());
                    ItemStack[] contents = chest.getBlockInventory().getContents();
                    for (int i = 0; i < contents.length; i++) {
                        ItemStack item = contents[i];
                        if (item != null && !item.getType().isAir()) {
                            YamlConfiguration yaml = new YamlConfiguration();
                            yaml.set("item", item);
                            requestBuilder.addSlot(VisualBukkitRPC.InventorySlot.newBuilder().setSlot(i).setYaml(yaml.saveToString()).build());
                        }
                    }
                    sendFormatted("&eExporting chest inventory...", player);
                    grpcStub.importInventory(requestBuilder.build(), new StreamObserver<>() {
                        @Override
                        public void onNext(VisualBukkitRPC.Response response) {
                            Bukkit.getScheduler().runTask(VisualBukkitPlugin.this, () -> sendFormatted("&aSuccessfully exported chest inventory.", player));
                        }
                        @Override
                        public void onError(Throwable throwable) {
                            Bukkit.getScheduler().runTask(VisualBukkitPlugin.this, () -> sendFormatted("&cFailed to export chest inventory: " + throwable.getMessage(), player));
                            throwable.printStackTrace();
                        }
                        @Override
                        public void onCompleted() {}
                    });
                } else {
                    sendFormatted("&cYou must be looking at a chest.", player);
                }
            } else {
                sendFormatted("&cThis command must be executed by a player.", sender);
            }
            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("export") && args[1].equalsIgnoreCase("error")) {
            if (reportExceptionRequest != null) {
                sendFormatted("&eExporting error...", sender);
                grpcStub.reportException(reportExceptionRequest, new StreamObserver<>() {
                    @Override
                    public void onNext(VisualBukkitRPC.Response response) {
                        Bukkit.getScheduler().runTask(VisualBukkitPlugin.this, () -> sendFormatted("&aSuccessfully exported error.", sender));
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        Bukkit.getScheduler().runTask(VisualBukkitPlugin.this, () -> sendFormatted("&cFailed to export error: " + throwable.getMessage(), sender));
                        throwable.printStackTrace();
                    }
                    @Override
                    public void onCompleted() {}
                });
            } else {
                sendFormatted("&cThere is no error to export.", sender);
            }
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("deploy")) {
            sendFormatted("&eDeploying plugin...", sender);
            grpcStub.deployPlugin(VisualBukkitRPC.DeployPluginRequest.newBuilder().build(), new JarStreamObserver(this, sender));
            return true;
        }

        return false;
    }

    public static void reportException(String blockUUID, Throwable throwable) {
        reportExceptionRequest = VisualBukkitRPC.ReportExceptionRequest.newBuilder()
                .setBlockUUID(blockUUID)
                .setStacktrace(Throwables.getStackTraceAsString(throwable))
                .build();
        throwable.printStackTrace();
        plugin.getLogger().severe("========================================================");
        plugin.getLogger().severe("An error has occurred in a Visual Bukkit plugin.");
        plugin.getLogger().severe("Use '/vb export error' to view the block that caused it.");
        plugin.getLogger().severe("========================================================");
    }

    private void exportLocation(Location loc, Player player) {
        VisualBukkitRPC.ImportLocationRequest request = VisualBukkitRPC.ImportLocationRequest.newBuilder()
                .setWorld(loc.getWorld().getName())
                .setX(loc.getX())
                .setY(loc.getY())
                .setZ(loc.getZ())
                .setYaw(loc.getYaw())
                .setPitch(loc.getPitch())
                .build();
        grpcStub.importLocation(request, new StreamObserver<>() {
            @Override
            public void onNext(VisualBukkitRPC.Response response) {
                Bukkit.getScheduler().runTask(VisualBukkitPlugin.this, () -> sendFormatted("&aSuccessfully exported location.", player));
            }

            @Override
            public void onError(Throwable throwable) {
                Bukkit.getScheduler().runTask(VisualBukkitPlugin.this, () -> sendFormatted("&cFailed to export location: " + throwable.getMessage(), player));
                throwable.printStackTrace();
            }

            @Override
            public void onCompleted() {
            }
        });
    }

    protected void sendFormatted(String str, CommandSender sender) {
        sendColored("&7[&9VisualBukkit&7]&r " + str, sender);
    }

    protected void sendColored(String str, CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str));
    }
}
