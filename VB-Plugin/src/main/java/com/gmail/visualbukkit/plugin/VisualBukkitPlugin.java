package com.gmail.visualbukkit.plugin;

import com.google.common.base.Throwables;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class VisualBukkitPlugin extends JavaPlugin {

    private static VisualBukkitPlugin plugin;
    private static String lastErrorBlockID;
    private static Exception lastError;

    private VisualBukkitClient client = new VisualBukkitClient(this);

    @Override
    public void onEnable() {
        plugin = this;
        client.addHandler("deploy", json -> Bukkit.getScheduler().runTask(this, () -> {
            sendFormatted("Received deploy request from the Visual Bukkit app.", Bukkit.getConsoleSender());
            Path jarPath = Paths.get(json.optString("jar-path"));
            if (Files.exists(jarPath)) {
                Path updateDir = Bukkit.getUpdateFolderFile().toPath();
                try {
                    if (Files.notExists(updateDir)) {
                        Files.createDirectories(updateDir);
                    }
                    Files.copy(jarPath, updateDir.resolve(jarPath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                    sendFormatted("Copied the plugin jar to '" + updateDir.toAbsolutePath() + "'.", Bukkit.getConsoleSender());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));
        client.connect();
    }

    @Override
    public void onDisable() {
        client.disconnect();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sendFormatted("&cThe Visual Bukkit command cannot be used from console.", sender);
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 1 && args[0].equalsIgnoreCase("connect")) {
            if (client.isConnected()) {
                sendFormatted("&aThe server is connected to the Visual Bukkit app.", player);
            } else {
                sendFormatted("&7Attempting to connect to the Visual Bukkit app...", player);
                client.connect().whenComplete((o, e) -> Bukkit.getScheduler().runTask(this, () -> {
                    if (e != null) {
                        sendFormatted("&cFailed to connect: " + e.getMessage(), sender);
                        e.printStackTrace();
                    } else {
                        sendFormatted("&aSuccessfully connected.", sender);
                    }
                }));
            }
            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("export")) {
            if (args[1].equalsIgnoreCase("item")) {
                ItemStack item = player.getInventory().getItemInMainHand();
                if (!item.getType().isAir()) {
                    if (validateConnected(player)) {
                        JSONObject json = new JSONObject();
                        json.put("id", "item");
                        json.put("yaml", serializeItem(item));
                        sendFormatted("&7Attempting to export item...", player);
                        client.send(json).whenComplete((o, e) -> Bukkit.getScheduler().runTask(this, () -> {
                            if (e != null) {
                                sendFormatted("&cFailed to export item: " + e.getMessage(), sender);
                                e.printStackTrace();
                            } else {
                                sendFormatted("&aSuccessfully exported item.", sender);
                            }
                        }));
                    }
                } else {
                    sendFormatted("&cYou are not holding an item.", sender);
                }
                return true;
            }

            if (args[1].equalsIgnoreCase("inv")) {
                Block targetBlock = player.getTargetBlockExact(10);
                if (targetBlock != null && targetBlock.getState() instanceof Chest) {
                    if (validateConnected(player)) {
                        JSONObject json = new JSONObject();
                        json.put("id", "inv");
                        ItemStack[] items = ((Chest) targetBlock.getState()).getInventory().getContents();
                        for (int i = 0; i < items.length; i++) {
                            ItemStack item = items[i];
                            if (item != null) {
                                JSONObject itemJson = new JSONObject();
                                itemJson.put("slot", i);
                                itemJson.put("yaml", serializeItem(item));
                                json.append("items", itemJson);
                            }
                        }
                        sendFormatted("&7Attempting to export inventory...", player);
                        client.send(json).whenComplete((o, e) -> Bukkit.getScheduler().runTask(this, () -> {
                            if (e != null) {
                                sendFormatted("&cFailed to export inventory: " + e.getMessage(), sender);
                                e.printStackTrace();
                            } else {
                                sendFormatted("&aSuccessfully exported inventory.", sender);
                            }
                        }));
                    }
                } else {
                    sendFormatted("&cYou must be looking at a chest.", player);
                }
                return true;
            }

            if (args[1].equalsIgnoreCase("loc")) {
                if (validateConnected(player)) {
                    JSONObject json = new JSONObject();
                    json.put("id", "loc");
                    json.put("world", player.getWorld().getName());
                    json.put("x", player.getLocation().getX());
                    json.put("y", player.getLocation().getY());
                    json.put("z", player.getLocation().getZ());
                    json.put("yaw", player.getLocation().getYaw());
                    json.put("pitch", player.getLocation().getPitch());
                    sendFormatted("&7Attempting to export your location...", player);
                    client.send(json).whenComplete((o, e) -> Bukkit.getScheduler().runTask(this, () -> {
                        if (e != null) {
                            sendFormatted("&cFailed to export location: " + e.getMessage(), sender);
                            e.printStackTrace();
                        } else {
                            sendFormatted("&aSuccessfully exported your location.", sender);
                        }
                    }));
                }
                return true;
            }

            if (args[1].equalsIgnoreCase("block-loc")) {
                Block targetBlock = player.getTargetBlockExact(10);
                if (targetBlock != null) {
                    if (validateConnected(player)) {
                        JSONObject json = new JSONObject();
                        json.put("id", "block-loc");
                        json.put("world", targetBlock.getWorld().getName());
                        json.put("x", targetBlock.getX());
                        json.put("y", targetBlock.getY());
                        json.put("z", targetBlock.getZ());
                        sendFormatted("&7Attempting to export block location...", player);
                        client.send(json).whenComplete((o, e) -> Bukkit.getScheduler().runTask(this, () -> {
                            if (e != null) {
                                sendFormatted("&cFailed to export location: " + e.getMessage(), sender);
                                e.printStackTrace();
                            } else {
                                sendFormatted("&aSuccessfully exported block location.", sender);
                            }
                        }));
                    }
                } else {
                    sendFormatted("&cYou must be looking at a block.", player);
                }
                return true;
            }

            if (args[1].equalsIgnoreCase("error")) {
                if (lastError != null) {
                    if (validateConnected(player)) {
                        JSONObject json = new JSONObject();
                        json.put("id", "error-report");
                        json.put("block", lastErrorBlockID);
                        json.put("exception", Throwables.getStackTraceAsString(lastError));
                        sendFormatted("&7Attempting to export error...", player);
                        client.send(json).whenComplete((o, e) -> Bukkit.getScheduler().runTask(this, () -> {
                            if (e != null) {
                                sendFormatted("&cFailed to export error: " + e.getMessage(), sender);
                                e.printStackTrace();
                            } else {
                                sendFormatted("&aSuccessfully exported error.", sender);
                            }
                        }));
                    }
                } else {
                    sendFormatted("&cAn error has not occurred in a Visual Bukkit plugin.", sender);
                }
                return true;
            }
        }

        if (args.length >= 2 && (args[0].equalsIgnoreCase("name") || args[0].equalsIgnoreCase("lore"))) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (!item.getType().isAir()) {
                StringJoiner joiner = new StringJoiner(" ");
                for (int i = 1; i < args.length; i++) {
                    joiner.add(ChatColor.translateAlternateColorCodes('&', args[i]));
                }
                ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta != null) {
                    if (args[0].equalsIgnoreCase("name")) {
                        itemMeta.setDisplayName(joiner.toString());
                    } else {
                        itemMeta.setLore(Arrays.asList(joiner.toString().split("\\|")));
                    }
                    item.setItemMeta(itemMeta);
                }
                sendFormatted("&aSet item " + args[0] + " to: " + joiner, sender);
            } else {
                sendFormatted("&cYou are not holding an item.", sender);
            }
            return true;
        }

        sendColored("&9Visual Bukkit Commands", player);
        sendColored("&3/vb connect &b- connects to the app", player);
        sendColored("&3/vb export item &b- exports held item", player);
        sendColored("&3/vb export inv &b- exports target chest inventory", player);
        sendColored("&3/vb export loc &b- exports current location", player);
        sendColored("&3/vb export block-loc &b- exports target block location", player);
        sendColored("&3/vb export error &b- exports last error", player);
        sendColored("&3/vb name <string> &b- sets name of held item", player);
        sendColored("&3/vb lore <string> &b- sets lore of held item (line separator: '|')", player);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("connect", "export", "name", "lore");
        }
        if (args.length > 0 && args[0].equalsIgnoreCase("export")) {
            return Arrays.asList("item", "inv", "loc", "block-loc", "error");
        }
        return null;
    }

    public static void reportError(String id, Exception error) {
        lastErrorBlockID = id;
        lastError = error;
        error.printStackTrace();
        plugin.getLogger().severe("========================================================");
        plugin.getLogger().severe("An error has occurred in a Visual Bukkit plugin.");
        plugin.getLogger().severe("Use '/vb export error' to view the block that caused it.");
        plugin.getLogger().severe("========================================================");
    }

    private boolean validateConnected(Player player) {
        if (client.isConnected()) {
            return true;
        }
        sendFormatted("&cThe server is not connected to the Visual Bukkit app.", player);
        TextComponent textComponent = new TextComponent("[Attempt to Connect]");
        textComponent.setColor(net.md_5.bungee.api.ChatColor.GOLD);
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("/vb connect")));
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vb connect"));
        player.spigot().sendMessage(textComponent);
        return false;
    }

    private String serializeItem(ItemStack item) {
        YamlConfiguration config = new YamlConfiguration();
        config.set("item", item);
        return config.saveToString();
    }

    private void sendFormatted(String str, CommandSender sender) {
        sendColored("&7[&9VisualBukkit&7]&r " + str, sender);
    }

    private void sendColored(String str, CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str));
    }
}
