import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PapiExpansion extends PlaceholderExpansion {

    private Map<String, Function<Player, String>> placeholderFunctions = new HashMap<>();

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return PluginMain.getInstance().getDescription().getAuthors().toString();
    }

    @Override
    public String getVersion() {
        return PluginMain.getInstance().getDescription().getVersion();
    }

    @Override
    public String getIdentifier() {
        return PluginMain.getInstance().getName().toLowerCase();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        return placeholderFunctions.getOrDefault(identifier, p -> null).apply(player);
    }

    protected void registerPlaceholder(String identifier, Function<Player, String> function) {
        placeholderFunctions.put(identifier, function);
    }
}