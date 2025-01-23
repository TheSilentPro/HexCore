package tsp.hexcore.input;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import tsp.hexcore.input.registry.InputRegistry;

/**
 * @author TheSilentPro (Silent)
 */
public class InputListener implements Listener {

    private final InputRegistry registry;

    public InputListener(JavaPlugin plugin, InputRegistry registry) {
        this.registry = registry;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    // handlers might want to cancel the event, therefor MONITOR is not used.
    // HIGHEST to allow other handlers to cancel the event
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInput(AsyncPlayerChatEvent event) {
        registry.process(event.getPlayer().getUniqueId(), event.getMessage(), event);
    }

}