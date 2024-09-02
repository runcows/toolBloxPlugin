package io.github.runcows.toolBlox;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ToolBlox extends JavaPlugin {

//To-do
    // Wrench
        // Rotate blocks
        // I have a list of blocks in my discord dms with myself
        // different behaviour with each block, kinda, but matches
        // some blocks have multiple block states, do a left click to change wrench mode, debug stick style
    // Hose
        // Waterlog blocks using buckets in your inventory
        // Right click with hose on unwaterlogged block: uses a water bucket from your inventory to waterlog the block
        // Right click with hose on waterlogged block: uses an empty bucket from your inventory to unwaterlog the block


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

    private NamespacedKey wrenchNamespacedKey;
    private NamespacedKey hoseNamespacedKey;
    public NamespacedKey getWrenchNamespacedKey() {
        return wrenchNamespacedKey;
    }
    public NamespacedKey getHoseNamespacedKey() {
        return hoseNamespacedKey;
    }

    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        config = this.getConfig();
        setupRecipes();
        //we'll want an event for player join to give them the recipe
        getServer().getPluginManager().registerEvents(new InteractBlockListener(), this);


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

    private void setupRecipes()
    {
        ItemStack wrench = new ItemStack(Material.CARROT_ON_A_STICK);
        ItemMeta wrenchMeta = wrench.getItemMeta();
        wrenchMeta.setCustomModelData(config.getInt("wrenchCustomModelData"));
        wrenchMeta.setDisplayName(hex(config.getString("wrenchDisplayName")));
        wrenchMeta.setLore(config.getStringList("wrenchLore"));
        wrench.setItemMeta(wrenchMeta);
        wrench.addEnchantment(Enchantment.LURE,1);
        NamespacedKey wrenchKey = new NamespacedKey(this, "wrench");
        this.wrenchNamespacedKey = wrenchKey;
        ShapedRecipe wrenchRecipe = new ShapedRecipe(wrenchKey, wrench);
        wrenchRecipe.shape(" R "," RR","R  ");
        Material R;
        if((R = Material.getMaterial(config.getString("wrenchMaterial"))) == null)
        {//if the config fetch gets incorrect material, just make it copper ingot
            R = Material.COPPER_INGOT;
        }
        wrenchRecipe.setIngredient('R', R);
        Bukkit.addRecipe(wrenchRecipe);


        // ADD THE HOSE RECIPE AT SOME POINT
    }
}
