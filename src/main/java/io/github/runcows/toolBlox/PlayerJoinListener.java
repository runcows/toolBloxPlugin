package io.github.runcows.toolBlox;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.Collection;

public class PlayerJoinListener implements Listener {
    ToolBlox plugin = ToolBlox.getInstance();
    FileConfiguration config;

    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event)
    {
        Collection<NamespacedKey> keys = new ArrayList<NamespacedKey>();
        keys.add(plugin.getWrenchNamespacedKey());
        //keys.add(plugin.getHoseNamespacedKey());
        event.getPlayer().discoverRecipes(keys);
    }
}
