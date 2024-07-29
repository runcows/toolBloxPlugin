package io.github.runcows.toolBlox;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;


public class InteractBlockListener implements Listener {
    ToolBlox plugin = ToolBlox.getInstance();
    FileConfiguration config;

    @EventHandler
    public void onInteractWithBlock (PlayerInteractEvent event)
    {
        config = plugin.getConfig();
        Player player = event.getPlayer();
        //first check should be items, then split or return
        //event.getItem().


        if(event.getClickedBlock().getType().equals(Material.CRAFTER))
        {

        }


    }
}
