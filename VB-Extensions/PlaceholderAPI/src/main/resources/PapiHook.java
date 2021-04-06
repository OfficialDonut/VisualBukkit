import org.bukkit.entity.Player;

import java.util.function.Function;

public class PapiHook {

    private PapiExpansion expansion = new PapiExpansion();

    public PapiHook() {
        expansion.register();
    }

    public void registerPlaceholder(String identifier, Function<Player, String> function) {
        expansion.registerPlaceholder(identifier, function);
    }
}