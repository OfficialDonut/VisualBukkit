package us.donut.visualbukkit.plugin;

import com.google.common.collect.ForwardingMultimap;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import us.donut.visualbukkit.plugin.modules.classes.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("unused")
public class UtilMethods {

    public static boolean checkEquals(Object o1, Object o2) {
        if (o1 == null || o2 == null) {
            return false;
        }
        return o1 instanceof Number && o2 instanceof Number ? ((Number) o1).doubleValue() == ((Number) o2).doubleValue() : o1.equals(o2);
    }

    public static void createExplosion(Location loc, float power, boolean fire, boolean breakBlocks) {
        loc.getWorld().createExplosion(loc, power, fire, breakBlocks);
    }

    public static ItemStack createPotion(PotionType potionType) {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
        if (potionMeta != null) {
            potionMeta.setBasePotionData(new PotionData(potionType));
            item.setItemMeta(potionMeta);
        }
        return item;
    }

    public static void dropItem(ItemStack item, Location loc, boolean naturally) {
        if (naturally) {
            loc.getWorld().dropItemNaturally(loc, item);
        } else {
            loc.getWorld().dropItem(loc, item);
        }
    }

    public static void generateTree(TreeType treeType, Location loc) {
        loc.getWorld().generateTree(loc, treeType);
    }

    public static Object getRandomElement(List list) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

    public static ShapedRecipe getShapedRecipe(ItemStack result, Material... ingredients) {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(PluginMain.getInstance(), UUID.randomUUID().toString()), result);
        for (int i = 0; i < ingredients.length; i++) {
            Material ingredient = ingredients[i];
            if (ingredient != null && ingredient != Material.AIR) {
                if (i < 3) {
                    recipe.shape("012");
                } else if (i < 6) {
                    recipe.shape("012", "345");
                } else {
                    recipe.shape("012", "345", "678");
                }
            }
        }
        for (int i = 0; i < ingredients.length; i++) {
            Material ingredient = ingredients[i];
            if (ingredient != null && ingredient != Material.AIR) {
                recipe.setIngredient(Character.forDigit(i, 10), ingredient);
            }
        }
        return recipe;
    }

    public static boolean isNumber(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isType(Object obj, Class<?> clazz) {
        return obj != null && clazz.isAssignableFrom(obj.getClass());
    }

    public static void playSound(Sound sound, Location loc, float vol, float pitch) {
        loc.getWorld().playSound(loc, sound, vol, pitch);
    }

    public static void setBlockData(Block block, byte data) throws InvocationTargetException, IllegalAccessException {
        ReflectionUtil.getDeclaredMethod(block.getClass(), "setData", byte.class).invoke(block, data);
    }

    public static void setDurability(ItemStack item, int damage) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta instanceof Damageable) {
            ((Damageable) itemMeta).setDamage(damage);
        }
    }

    public static void setItemLore(ItemStack item, List lore) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            List<String> coloredLore = new ArrayList<>(lore.size());
            for (Object obj : lore) {
                coloredLore.add(PluginMain.color((String) obj));
            }
            itemMeta.setLore(coloredLore);
            item.setItemMeta(itemMeta);
        }
    }

    public static void setItemName(ItemStack item, String name) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(PluginMain.color(name));
            item.setItemMeta(itemMeta);
        }
    }

    public static void setOwningPlayer(ItemStack item, OfflinePlayer player) {
        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        if (skullMeta != null) {
            skullMeta.setOwningPlayer(player);
            item.setItemMeta(skullMeta);
        }
    }

    public static void setSkin(Player player, String value, String signature) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Object entityPlayer = ReflectionUtil.getDeclaredMethod(player.getClass(), "getHandle").invoke(player);
        Object gameProfile = ReflectionUtil.getDeclaredMethod(ReflectionUtil.getNmsClass("EntityHuman"), "getProfile").invoke(entityPlayer);
        Object propertyMap = ReflectionUtil.getDeclaredMethod(gameProfile.getClass(), "getProperties").invoke(gameProfile);
        Object property = ReflectionUtil.getDeclaredConstructor(ReflectionUtil.getClass("com.mojang.authlib.properties.Property"), String.class, String.class, String.class).newInstance("textures", value, signature);
        ReflectionUtil.getDeclaredMethod(ForwardingMultimap.class, "removeAll", Object.class).invoke(propertyMap, "textures");
        ReflectionUtil.getDeclaredMethod(ForwardingMultimap.class, "put", Object.class, Object.class).invoke(propertyMap, "textures", property);
    }

    public static void spawnEntity(EntityType entityType, Location loc) {
        loc.getWorld().spawnEntity(loc, entityType);
    }

    public static void spawnParticle(int num, Particle particle, Location loc) {
        loc.getWorld().spawnParticle(particle, loc, num);
    }

    public static void strikeLightning(Location loc, boolean fake) {
        if (fake) {
            loc.getWorld().strikeLightningEffect(loc);
        } else {
            loc.getWorld().strikeLightning(loc);
        }
    }
}
