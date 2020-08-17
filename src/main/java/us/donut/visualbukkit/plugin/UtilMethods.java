package us.donut.visualbukkit.plugin;

import com.google.common.collect.ForwardingMultimap;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import us.donut.visualbukkit.plugin.modules.classes.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class UtilMethods {

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Object addToObject(Object object, Object delta) {
        if (object instanceof List) {
            ((List) object).add(delta);
            return object;
        } else if (object instanceof Number && delta instanceof Number) {
            return ((Number) object).doubleValue() + ((Number) delta).doubleValue();
        } else if (object instanceof Duration && delta instanceof Duration) {
            return ((Duration) object).plus((Duration) delta);
        }
        throw new IllegalArgumentException("Cannot add " + delta + " to " + object);
    }

    public static boolean checkEquals(Object o1, Object o2) {
        if (o1 == null || o2 == null) {
            return false;
        }
        return o1 instanceof Number && o2 instanceof Number ? ((Number) o1).doubleValue() == ((Number) o2).doubleValue() : o1.equals(o2);
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

    public static Object getRandomElement(List list) {
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }

    public static ShapedRecipe getShapedRecipe(JavaPlugin plugin, ItemStack result, Material... ingredients) {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, UUID.randomUUID().toString()), result);
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

    public static Object removeFromObject(Object object, Object delta) {
        if (object instanceof List) {
            ((List<?>) object).remove(delta);
            return object;
        } else if (object instanceof Number && delta instanceof Number) {
            return ((Number) object).doubleValue() - ((Number) delta).doubleValue();
        } else if (object instanceof Duration && delta instanceof Duration) {
            return ((Duration) object).minus((Duration) delta);
        }
        throw new IllegalArgumentException("Cannot remove " + delta + " from " + object);
    }

    public static void setDurability(ItemStack item, int damage) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta instanceof Damageable) {
            ((Damageable) itemMeta).setDamage(damage);
            item.setItemMeta(itemMeta);
        }
    }

    public static void setItemLore(ItemStack item, List lore) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            List<String> coloredLore = new ArrayList<>(lore.size());
            for (Object obj : lore) {
                coloredLore.add(ChatColor.translateAlternateColorCodes('&', (String) obj));
            }
            itemMeta.setLore(coloredLore);
            item.setItemMeta(itemMeta);
        }
    }

    public static void setItemName(ItemStack item, String name) {
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta != null) {
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
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
}
