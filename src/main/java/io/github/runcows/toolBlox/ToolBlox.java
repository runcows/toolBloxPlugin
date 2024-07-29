package io.github.runcows.toolBlox;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static ToolBlox instance;
    public FileConfiguration config;
    public ToolBlox()
    {
        instance = this;
    }
    public static ToolBlox getInstance()
    {
        return instance;
    }

    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        config = this.getConfig();
    }

    @Override
    public void onDisable()
    {
        saveConfig();
    }

    //this method's code is from Maxx_Qc on this thread (https://www.spigotmc.org/threads/use-hex-color-codes-in-clickable-message.476327/)
    private static final Pattern HEX_PATTERN = Pattern.compile("&(#\\w{6})");
    public static String hex(String str)
    {
        Matcher matcher = HEX_PATTERN.matcher(ChatColor.translateAlternateColorCodes('&', str));
        StringBuffer buffer = new StringBuffer();

        while (matcher.find())
            matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of(matcher.group(1)).toString());

        return matcher.appendTail(buffer).toString();
    }
}
