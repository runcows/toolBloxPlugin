package io.github.runcows.toolBlox;

import org.bukkit.plugin.java.JavaPlugin;

public final class ToolBlox extends JavaPlugin {

//To-do
    // Wrench
        // Rotate blocks
        // I have a list of blocks in my discord dms with myself
        // different behaviour with each block, kinda, but matches
        // some blocks have multiple block states, do a shift right click to change wrench mode, debug stick style
    // Hose
        // Waterlog blocks using buckets in your inventory
        // Right click with hose on unwaterlogged block: uses a water bucket from your inventory to waterlog the block
        // Right click with hose on waterlogged block: uses an empty bucket from your inventory to unwaterlog the block
    // Both
        // custom crafing recipe and item
        // figure out how to get the recipe to appear in the recipe book
        // maybe just 1.21+ since item and enchants, for right click detection?
        // figure out right click detection, can we do any item before 1.21? or do we need the custom enchants?
        // im guessing we need to have an event for that
        // custom model data and textures: panic
    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
