package io.github.runcows.toolBlox;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.ShulkerBox;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Piston;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


public class InteractBlockListener implements Listener {
    ToolBlox plugin = ToolBlox.getInstance();
    FileConfiguration config;

    @EventHandler
    public void onInteractWithBlock (PlayerInteractEvent event)
    {
        //first check should be items, then split or return
        ItemStack item = event.getItem();
        if(
                item.getType() == Material.CARROT_ON_A_STICK
                && item.getItemMeta().getCustomModelData() == 179932
                && item.containsEnchantment(Enchantment.LURE)
        )
        {// Wrench
            onWrenchUse(event,item);
        }
        else if(
                item.getType() == Material.WARPED_FUNGUS_ON_A_STICK
                && item.getItemMeta().getCustomModelData() == 179933
                && item.containsEnchantment(Enchantment.LURE)
        )
        {// Hose
            onHoseUse(event,item);
        }
    }

    private void onWrenchUse(PlayerInteractEvent event, ItemStack item)
    {
        config = plugin.getConfig();
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();
        Material blockType = clickedBlock.getType();
        //figure out how to add custom data to items as rotation modes
        switch (blockType)
        {
            case DROPPER, DISPENSER, BARREL, OBSERVER, LIGHTNING_ROD ->
            {// Simple 6 direction
                // can just rotate
                if (!rotateFacing6(clickedBlock))
                {
                    //Something wrong
                }
            }
            case SHULKER_BOX, BLUE_SHULKER_BOX,
                 BLACK_SHULKER_BOX, CYAN_SHULKER_BOX, GRAY_SHULKER_BOX,
                 BROWN_SHULKER_BOX, GREEN_SHULKER_BOX, LIME_SHULKER_BOX,
                 ORANGE_SHULKER_BOX, LIGHT_BLUE_SHULKER_BOX, MAGENTA_SHULKER_BOX,
                 PINK_SHULKER_BOX, PURPLE_SHULKER_BOX, RED_SHULKER_BOX,
                 WHITE_SHULKER_BOX, YELLOW_SHULKER_BOX, LIGHT_GRAY_SHULKER_BOX ->
            {// Shulkers
                // need to check if open? is that possible? rotate if not
                ShulkerBox shulker = (ShulkerBox) clickedBlock.getBlockData();
                if (shulker.getInventory().getViewers().isEmpty())
                {
                    //rotate
                    if (!rotateFacing6(clickedBlock))
                    {
                        //Something wrong
                    }
                }
            }
            case PISTON, STICKY_PISTON ->
            {// Pistons
                // need to check if extended, then rotate if not
                Piston piston = (Piston) clickedBlock.getBlockData();
                if (!piston.isExtended())
                {
                    //rotate
                    if (!rotateFacing6(clickedBlock))
                    {
                        //Something wrong
                    }
                }
            }

            case CRAFTER ->
            {// Crafter
                // use Crafter.Orientation
                // Mode 1: East west north south
                // Mode 2: Sideways Up Down
            }

            case ACACIA_FENCE_GATE, BAMBOO_FENCE_GATE, BIRCH_FENCE_GATE, CHERRY_FENCE_GATE,
                 CRIMSON_FENCE_GATE, DARK_OAK_FENCE_GATE, JUNGLE_FENCE_GATE, MANGROVE_FENCE_GATE,
                 OAK_FENCE_GATE, SPRUCE_FENCE_GATE, WARPED_FENCE_GATE->
            {// all fence gates
                // Mode 1: facing4
                // Mode 2: in_wall
            }
            //case FENCES ->
            //{// all fences & iron bars, all glass panes
                // Mode 1: East (true false)
                // Mode 2: North (true false)
                // Mode 3: West (true false)
                // Mode 4: South (true false)
            //}
            //case AXIS ->
            //{// all wood and logs, and stripped versions,
               // basalt, polished basalt, purpur pillar, chain,
               // quartz pillar, bone block, deepslate, stems and hyphae,
               // hay bale, all froglights

                // axis flip
            //}
            //case TRAPDOORS ->
            //{// all trapdoors
                // Mode 1: facing4
                // Mode 2: half (bottom top)
            //}
            //case DOORS ->
            //{// all doors
                // make sure it updates both blocks
                // Mode 1: facing4
                // Mode 2: hinge (left right)
            //}
            //case BEDS ->
            //{// all beds
                // rotate around the part clicked
                // make sure to move / rotate the other part
                // facing4
            //}
            //case SLABS ->
            //{// all slabs
                // make sure type isnt double
                // type swap (bottom top)
            //}
            //case STAIRS ->
            //{// all stairs
                // Mode 1: facing4
                // Mode 2: half (bottom top)
                // Mode 3: shape (straight, outer_right, outer_left, inner_left, inner_right)
            //}
            //case BUTTONS ->
            //{// all buttons, levers
                // make sure to check support
                // Mode 1: facing4
                // Mode 2: face (wall, floor, ceiling)
            //}
            //case GRINDSTONE ->
            //{// grindstone
                // Mode 1: facing4
                // Mode 2: face (wall, floor, ceiling)
            //}
            //case WALLS ->
            //{// all walls
                // Mode 1: East (none, low, tall)
                // Mode 2: North (none, low, tall)
                // Mode 3: West (none, low, tall)
                // Mode 4: South (none, low, tall)
                // Mode 5?: Up (true false)   Should we even allow this?
            //}
            //case BASIC FACING 4 ->
            //{// all glazed terracotta, jackolantern, carved pumpkin,
               // beenest, beehive, stonecutter, loom, furnace, smoker,
               // blast furnace, campfire, soul campfire, all anvils,
               // decorated pot, chiseled bookshelf, lectern,
               // all wall hanging signs, ender chest, wall heads and skulls,
               // repeater, comparator, calibrated skulk sensor
                // facing4
            //}
            //case WALL_BANNER ->
            //{// all wall banners, ladders too, wall signs
                // facing4, check support
            //}
            //case BANNER, SIGN ->
            //{// floor banners and floor signs, floor heads and skulls
                // rotation 16 (0-15)
            //}
            //case AMETHYST_BUDS ->
            //{// amethyst buds and clusters
                // facing6, check support
            //}
            //case BIG & SMALL DRIPLEAF
            //{// big and small dripleaf
                // facing4, update upper or lower blocks to match
            //}
            //case TORCHES ->
            //{// all torch types
                // facing4 for wall torches, then cycle to regular torch, back to facing4
                // also make sure to check support for wall torch
            //}
            //case LANTERNS ->
            //{// Lanterns and soul lanterns
                // hanging (true false) check support
            //}
            //case BELL->
            //{
                // check support for all
                // Mode 1: facing4
                // Mode 2: attachment (floor, ceiling, single wall, double wall)
            //}
            //case HANGING SIGNS (NOT WALL) ->
            //{// all non wall hanging signs
                // Mode 1: rotation 16 (0-15)
                // Mode 2: attached (true false)
            //}
            //case CHEST ->
            //{// chest and trapped chest
                // check type single
                // facing4
            //}
            //case HOPPER ->
            //{// just the hopper
                // facing5 ? (north east south west down)
            //}

            // Maybe for rails just rotate through shape like debug stick? Idk
            //case RAIL ->
            //{// just regular rails
                // Mode 1: Rotate
                    // NS <-> EW
                    // Ascending rotation, check support, otherwise turn flat
                    // Rotate curves, aaaaaa
                // Mode 2: Shape Change (Straight, Curve, Ascending [check support] )
            //}
            //case other rails ->
            //{// powered, detector, activator
                // Mode 1: Rotate, same as above, but no curve
                // Mode 2: Shape change (Straight ascending [check support] )
            //}
        }
    }
    private boolean rotateFacing6 (Block clickedBlock)
    {
        Directional block = (Directional) clickedBlock;
        BlockFace facing = block.getFacing();
        switch (facing)
        {
            case UP ->
            {
                block.setFacing(BlockFace.EAST);
            }
            case EAST ->
            {
                block.setFacing(BlockFace.NORTH);
            }
            case NORTH ->
            {
                block.setFacing(BlockFace.DOWN);
            }
            case DOWN ->
            {
                block.setFacing(BlockFace.SOUTH);
            }
            case SOUTH ->
            {
                block.setFacing(BlockFace.WEST);
            }
            case WEST ->
            {
                block.setFacing(BlockFace.UP);
            }
            default ->
            {
                return false;
            }
        }
        return true;
    }
    private boolean rotateFacing4 (Block clickedBlock)
    {
        Directional block = (Directional) clickedBlock;
        BlockFace facing = block.getFacing();
        switch (facing)
        {
            case EAST ->
            {
                block.setFacing(BlockFace.NORTH);
            }
            case NORTH ->
            {
                block.setFacing(BlockFace.WEST);
            }
            case WEST ->
            {
                block.setFacing(BlockFace.SOUTH);
            }
            case SOUTH ->
            {
                block.setFacing(BlockFace.EAST);
            }
            default ->
            {
                return false;
            }
        }
        return true;
    }

    private void onHoseUse(PlayerInteractEvent event, ItemStack item)
    {
        config = plugin.getConfig();
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();
        //if(clickedBlock.getType().equals(Material.CRAFTER))
        //{

        //}
    }
}
