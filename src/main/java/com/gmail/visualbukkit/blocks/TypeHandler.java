package com.gmail.visualbukkit.blocks;

import com.google.common.base.CaseFormat;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.apache.commons.lang.ClassUtils;
import org.bukkit.*;
import org.bukkit.attribute.Attributable;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.Metadatable;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import java.io.File;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

public class TypeHandler {

    private static BiMap<Class<?>, String> aliases = HashBiMap.create();
    private static Map<String, Function<String, String>> stringParsers = new HashMap<>();

    static {
        register(Block.class, "block");
        register(Boolean.class, "boolean", s -> "Boolean.valueOf(" + s + ")");
        register(ClickType.class, "inventory click type");
        register(ConfigurationSection.class, "config");
        register(Enchantment.class, "enchantment", s -> "((org.bukkit.enchantments.Enchantment) org.bukkit.enchantments.Enchantment.class.getDeclaredField(" + s + ").get(null))");
        register(Entity.class, "entity");
        register(EntityType.class, "entity type", s -> "EntityType.valueOf(" + s + ".toUpperCase())");
        register(File.class, "file");
        register(Item.class, "dropped item");
        register(ItemStack.class, "item");
        register(Inventory.class, "inventory");
        register(InventoryView.class, "inventory view");
        register(List.class, "list");
        register(LivingEntity.class, "living entity");
        register(LocalDateTime.class, "date");
        register(Location.class, "location");
        register(Material.class, "material", s -> "Material.valueOf(" + s + ".toUpperCase())");
        register(Number.class, "number", s -> "Double.valueOf(" + s + ")");
        register(Object.class, "object");
        register(OfflinePlayer.class, "player", s -> "Bukkit.getOfflinePlayer(" + s + ")");
        register(Particle.class, "particle", s -> "Particle.valueOf(" + s + ".toUpperCase())");
        register(Player.class, "online player", s -> "Bukkit.getPlayer(" + s + ")");
        register(PreparedStatement.class, "SQL statement");
        register(Scoreboard.class, "scoreboard");
        register(Sound.class, "sound", s -> "Sound.valueOf(" + s + ".toUpperCase())");
        register(String.class, "string");
        register(UUID.class, "UUID", s -> "UUID.fromString(" + s + ")");
        register(Vector.class, "vector");
        register(World.class, "world", s -> "Bukkit.getWorld(" + s + ")");
    }

    public static void register(Class<?> clazz, String alias, Function<String, String> stringParser) {
        stringParsers.put(alias, stringParser);
        register(clazz, alias);
    }

    public static void register(Class<?> clazz, String alias) {
        aliases.put(clazz, alias);
    }

    public static boolean canConvert(Class<?> from, Class<?> to) {
        return convert(from, to, "") != null;
    }

    public static String convert(Class<?> from, Class<?> to, String src) {
        if (to.isAssignableFrom(from)) {
            return src;
        }
        if (to == String.class) {
            return "String.valueOf(" + src + ")";
        }
        if (isPrimitiveNumber(from) && isPrimitiveNumber(to)) {
            return "((" + to.getSimpleName() + ")" + src + ")";
        }
        if (to.isPrimitive() && from == ClassUtils.primitiveToWrapper(to)) {
            return src + "." + to.getSimpleName() + "Value()";
        }
        if (Number.class.isAssignableFrom(from) && isPrimitiveNumber(to)) {
            return src + "." + to.getSimpleName() + "Value()";
        }
        if (isPrimitiveNumber(from) && to == Number.class) {
            return boxPrimitive(from, src);
        }
        if (from.isPrimitive() && to == ClassUtils.primitiveToWrapper(from)) {
            return boxPrimitive(from, src);
        }
        if (from.isPrimitive() && to == Object.class) {
            return boxPrimitive(from, src);
        }
        if (from.isAssignableFrom(to)) {
            return "((" + to.getCanonicalName() + ")" + src + ")";
        }
        if (from == Object.class && isPrimitiveNumber(to)) {
            return convert(Number.class, to, "((Number)" + src + ")");
        }
        if (from == Object.class && to.isPrimitive()) {
            Class<?> wrapper = ClassUtils.primitiveToWrapper(to);
            return convert(wrapper, to, "((" + wrapper.getCanonicalName() + ")" + src + ")");
        }
        if (CommandSender.class.isAssignableFrom(from) && (to == OfflinePlayer.class || to == ProjectileSource.class || to == Metadatable.class || to == PersistentDataHolder.class)) {
            return "((" + to.getCanonicalName() + ")" + src + ")";
        }
        if (Entity.class.isAssignableFrom(from) && to == Sittable.class) {
            return "((" + to.getCanonicalName() + ")" + src + ")";
        }
        if ((CommandSender.class.isAssignableFrom(from) || Entity.class.isAssignableFrom(from)) && to == Attributable.class) {
            return "((" + to.getCanonicalName() + ")" + src + ")";
        }
        if (ItemStack.class.isAssignableFrom(from) && to == PersistentDataHolder.class) {
            return src + ".getItemMeta()";
        }
        return null;
    }

    private static String boxPrimitive(Class<?> primitive, String src) {
        Class<?> wrapper = ClassUtils.primitiveToWrapper(primitive);
        return "new " + wrapper.getCanonicalName() + "(" + src + ")";
    }

    public static boolean isNumber(Class<?> clazz) {
        return Number.class.isAssignableFrom(clazz) || isPrimitiveNumber(clazz);
    }

    public static boolean isPrimitiveNumber(Class<?> clazz) {
        return clazz.isPrimitive() && clazz != boolean.class && clazz != char.class;
    }

    public static String getUserFriendlyName(Class<?> clazz) {
        String name = getAlias(clazz);
        if (name != null) {
            return name;
        }
        for (Map.Entry<Class<?>, String> entry : aliases.entrySet()) {
            if (entry.getKey().isAssignableFrom(clazz) && entry.getKey() != Object.class) {
                return entry.getValue();
            }
        }
        if (isNumber(clazz)) {
            return "number";
        }
        if (clazz == Class.class) {
            return "type";
        }
        name = clazz.getSimpleName();
        if (name.toUpperCase().equals(name)) {
            return name;
        }
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name).replace("_", " ");
    }

    public static String getAlias(Class<?> type) {
        return aliases.get(type);
    }

    public static Class<?> getType(String alias) {
        return aliases.inverse().get(alias);
    }

    public static Set<String> getAliases() {
        return Collections.unmodifiableSet(aliases.values());
    }

    public static Map<String, Function<String, String>> getStringParsers() {
        return Collections.unmodifiableMap(stringParsers);
    }
}
