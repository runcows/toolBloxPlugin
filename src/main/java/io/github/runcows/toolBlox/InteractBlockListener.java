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

            case ACACIA_FENCE_GATE, BAMBOO_FENCE_GATE, BIRCH_FENCE_GATE,
                 CHERRY_FENCE_GATE, CRIMSON_FENCE_GATE, DARK_OAK_FENCE_GATE,
                 JUNGLE_FENCE_GATE, MANGROVE_FENCE_GATE, OAK_FENCE_GATE,
                 SPRUCE_FENCE_GATE, WARPED_FENCE_GATE->
            {// all fence gates
                // Mode 1: facing4
                // Mode 2: in_wall
            }
            case ACACIA_FENCE, BAMBOO_FENCE, BIRCH_FENCE, CHERRY_FENCE, CRIMSON_FENCE,
                 DARK_OAK_FENCE, JUNGLE_FENCE, MANGROVE_FENCE, OAK_FENCE, NETHER_BRICK_FENCE,
                 SPRUCE_FENCE, WARPED_FENCE, IRON_BARS, GLASS_PANE,
                 GRAY_STAINED_GLASS_PANE, BLACK_STAINED_GLASS_PANE,
                 GREEN_STAINED_GLASS_PANE, BLUE_STAINED_GLASS_PANE,
                 BROWN_STAINED_GLASS_PANE, LIGHT_BLUE_STAINED_GLASS_PANE,
                 LIGHT_GRAY_STAINED_GLASS_PANE, CYAN_STAINED_GLASS_PANE,
                 LIME_STAINED_GLASS_PANE, MAGENTA_STAINED_GLASS_PANE,
                 ORANGE_STAINED_GLASS_PANE, PINK_STAINED_GLASS_PANE,
                 PURPLE_STAINED_GLASS_PANE, RED_STAINED_GLASS_PANE,
                 WHITE_STAINED_GLASS_PANE, YELLOW_STAINED_GLASS_PANE ->
            {// all fences & iron bars, all glass panes
                // Mode 1: East (true false)
                // Mode 2: North (true false)
                // Mode 3: West (true false)
                // Mode 4: South (true false)
            }
            case ACACIA_WOOD, BIRCH_WOOD, CHERRY_WOOD, DARK_OAK_WOOD, JUNGLE_WOOD,
                 MANGROVE_WOOD, OAK_WOOD, SPRUCE_WOOD, STRIPPED_ACACIA_WOOD,
                 STRIPPED_BIRCH_WOOD, STRIPPED_CHERRY_WOOD, STRIPPED_DARK_OAK_WOOD,
                 STRIPPED_JUNGLE_WOOD, STRIPPED_MANGROVE_WOOD, STRIPPED_OAK_WOOD,
                 STRIPPED_SPRUCE_WOOD, WARPED_STEM, CRIMSON_STEM, CRIMSON_HYPHAE,
                 WARPED_HYPHAE, STRIPPED_CRIMSON_STEM, STRIPPED_WARPED_STEM,
                 STRIPPED_CRIMSON_HYPHAE, STRIPPED_WARPED_HYPHAE, ACACIA_LOG,
                 BIRCH_LOG, CHERRY_LOG, JUNGLE_LOG, MANGROVE_LOG, STRIPPED_CHERRY_LOG,
                 STRIPPED_BIRCH_LOG, STRIPPED_ACACIA_LOG, STRIPPED_JUNGLE_LOG,
                 STRIPPED_MANGROVE_LOG, STRIPPED_OAK_LOG, OAK_LOG, STRIPPED_DARK_OAK_LOG,
                 DARK_OAK_LOG, STRIPPED_SPRUCE_LOG, SPRUCE_LOG, BASALT, POLISHED_BASALT,
                 PURPUR_PILLAR, CHAIN, QUARTZ_PILLAR, BONE_BLOCK, DEEPSLATE, HAY_BLOCK,
                 OCHRE_FROGLIGHT, PEARLESCENT_FROGLIGHT, VERDANT_FROGLIGHT->
            {// all axis flip blocks
                // axis flip
            }
            case ACACIA_TRAPDOOR, BAMBOO_TRAPDOOR, BIRCH_TRAPDOOR, CHERRY_TRAPDOOR,
                 COPPER_TRAPDOOR, CRIMSON_TRAPDOOR, DARK_OAK_TRAPDOOR,
                 EXPOSED_COPPER_TRAPDOOR, IRON_TRAPDOOR, JUNGLE_TRAPDOOR,
                 MANGROVE_TRAPDOOR, OAK_TRAPDOOR, OXIDIZED_COPPER_TRAPDOOR,
                 SPRUCE_TRAPDOOR, WARPED_TRAPDOOR, WAXED_COPPER_TRAPDOOR,
                 WAXED_EXPOSED_COPPER_TRAPDOOR, WAXED_OXIDIZED_COPPER_TRAPDOOR,
                 WAXED_WEATHERED_COPPER_TRAPDOOR, WEATHERED_COPPER_TRAPDOOR ->
            {// all trapdoors
                // Mode 1: facing4
                // Mode 2: half (bottom top)
            }
            case DARK_OAK_DOOR, ACACIA_DOOR, BAMBOO_DOOR, BIRCH_DOOR,
                 CHERRY_DOOR, COPPER_DOOR, CRIMSON_DOOR, EXPOSED_COPPER_DOOR,
                 IRON_DOOR, JUNGLE_DOOR, MANGROVE_DOOR, OAK_DOOR,
                 OXIDIZED_COPPER_DOOR, SPRUCE_DOOR, WARPED_DOOR,
                 WAXED_COPPER_DOOR, WAXED_EXPOSED_COPPER_DOOR,
                 WAXED_OXIDIZED_COPPER_DOOR, WAXED_WEATHERED_COPPER_DOOR,
                 WEATHERED_COPPER_DOOR ->
            {// all doors
                // make sure it updates both blocks
                // Mode 1: facing4
                // Mode 2: hinge (left right)
            }
            case BLACK_BED, BLUE_BED, BROWN_BED, LIGHT_BLUE_BED,
                 CYAN_BED, GRAY_BED, GREEN_BED, LIGHT_GRAY_BED,
                 LIME_BED, MAGENTA_BED, ORANGE_BED, PINK_BED,
                 PURPLE_BED, RED_BED, WHITE_BED, YELLOW_BED ->
            {// all beds
                // rotate around the part clicked
                // make sure to move / rotate the other part
                // facing4
            }
            case ACACIA_SLAB, ANDESITE_SLAB, BAMBOO_MOSAIC_SLAB, BAMBOO_SLAB,
                 BIRCH_SLAB, BLACKSTONE_SLAB, BRICK_SLAB, CHERRY_SLAB,
                 COBBLED_DEEPSLATE_SLAB, COBBLESTONE_SLAB, CRIMSON_SLAB,
                 CUT_COPPER_SLAB, CUT_SANDSTONE_SLAB, CUT_RED_SANDSTONE_SLAB,
                 DARK_OAK_SLAB, DARK_PRISMARINE_SLAB, DEEPSLATE_TILE_SLAB,
                 DIORITE_SLAB, END_STONE_BRICK_SLAB, EXPOSED_CUT_COPPER_SLAB,
                 GRANITE_SLAB, JUNGLE_SLAB, MANGROVE_SLAB, MOSSY_COBBLESTONE_SLAB,
                 MOSSY_STONE_BRICK_SLAB, MUD_BRICK_SLAB, NETHER_BRICK_SLAB,
                 OAK_SLAB, OXIDIZED_CUT_COPPER_SLAB, PETRIFIED_OAK_SLAB,
                 POLISHED_ANDESITE_SLAB, SANDSTONE_SLAB, POLISHED_BLACKSTONE_BRICK_SLAB,
                 POLISHED_BLACKSTONE_SLAB, POLISHED_DEEPSLATE_SLAB,POLISHED_DIORITE_SLAB,
                 POLISHED_GRANITE_SLAB, POLISHED_TUFF_SLAB, PRISMARINE_BRICK_SLAB,
                 PRISMARINE_SLAB, PURPUR_SLAB, RED_SANDSTONE_SLAB, QUARTZ_SLAB,
                 STONE_SLAB,STONE_BRICK_SLAB, SMOOTH_QUARTZ_SLAB, RED_NETHER_BRICK_SLAB,
                 SMOOTH_RED_SANDSTONE_SLAB, SMOOTH_SANDSTONE_SLAB, SMOOTH_STONE_SLAB,
                 SPRUCE_SLAB, TUFF_BRICK_SLAB, TUFF_SLAB, WARPED_SLAB,
                 WAXED_CUT_COPPER_SLAB, WAXED_EXPOSED_CUT_COPPER_SLAB,
                 WAXED_OXIDIZED_CUT_COPPER_SLAB, WAXED_WEATHERED_CUT_COPPER_SLAB,
                 WEATHERED_CUT_COPPER_SLAB ->
            {// all slabs
                // make sure type isnt double
                // type swap (bottom top)
            }
            case STONE_BRICK_STAIRS, STONE_STAIRS, ACACIA_STAIRS, ANDESITE_STAIRS,
                 BAMBOO_MOSAIC_STAIRS, BAMBOO_STAIRS, BIRCH_STAIRS, BLACKSTONE_STAIRS,
                 BRICK_STAIRS, CHERRY_STAIRS, COBBLED_DEEPSLATE_STAIRS,
                 COBBLESTONE_STAIRS, CRIMSON_STAIRS, CUT_COPPER_STAIRS,
                 DARK_OAK_STAIRS, DARK_PRISMARINE_STAIRS, DEEPSLATE_BRICK_STAIRS,
                 DEEPSLATE_TILE_STAIRS, DIORITE_STAIRS, END_STONE_BRICK_STAIRS,
                 EXPOSED_CUT_COPPER_STAIRS, GRANITE_STAIRS, JUNGLE_STAIRS,
                 MANGROVE_STAIRS, MOSSY_COBBLESTONE_STAIRS, MOSSY_STONE_BRICK_STAIRS,
                 MUD_BRICK_STAIRS, NETHER_BRICK_STAIRS, OAK_STAIRS,
                 OXIDIZED_CUT_COPPER_STAIRS, POLISHED_ANDESITE_STAIRS,
                 POLISHED_BLACKSTONE_BRICK_STAIRS, POLISHED_BLACKSTONE_STAIRS,
                 POLISHED_DEEPSLATE_STAIRS, POLISHED_DIORITE_STAIRS,
                 POLISHED_GRANITE_STAIRS, POLISHED_TUFF_STAIRS, PRISMARINE_BRICK_STAIRS,
                 PRISMARINE_STAIRS, PURPUR_STAIRS, QUARTZ_STAIRS, RED_NETHER_BRICK_STAIRS,
                 RED_SANDSTONE_STAIRS, SANDSTONE_STAIRS, SMOOTH_QUARTZ_STAIRS,
                 SMOOTH_RED_SANDSTONE_STAIRS, SMOOTH_SANDSTONE_STAIRS, SPRUCE_STAIRS,
                 TUFF_BRICK_STAIRS, TUFF_STAIRS, WARPED_STAIRS, WAXED_CUT_COPPER_STAIRS,
                 WAXED_EXPOSED_CUT_COPPER_STAIRS, WAXED_OXIDIZED_CUT_COPPER_STAIRS,
                 WAXED_WEATHERED_CUT_COPPER_STAIRS, WEATHERED_CUT_COPPER_STAIRS ->
            {// all stairs
                // Mode 1: facing4
                // Mode 2: half (bottom top)
                // Mode 3: shape (straight, outer_right, outer_left, inner_left, inner_right)
            }
            case BAMBOO_BUTTON, ACACIA_BUTTON, BIRCH_BUTTON, CHERRY_BUTTON,
                 CRIMSON_BUTTON, DARK_OAK_BUTTON, JUNGLE_BUTTON, MANGROVE_BUTTON,
                 OAK_BUTTON, POLISHED_BLACKSTONE_BUTTON, SPRUCE_BUTTON,
                 STONE_BUTTON, WARPED_BUTTON, LEVER ->
            {// all buttons, levers
                // make sure to check support
                // Mode 1: facing4
                // Mode 2: face (wall, floor, ceiling)
            }
            case GRINDSTONE ->
            {// grindstone
                // Mode 1: facing4
                // Mode 2: face (wall, floor, ceiling)
            }
            case ANDESITE_WALL, BLACKSTONE_WALL, BRICK_WALL, COBBLESTONE_WALL,
                 GRANITE_WALL, DIORITE_WALL, PRISMARINE_WALL, TUFF_WALL,
                 SANDSTONE_WALL, COBBLED_DEEPSLATE_WALL, DEEPSLATE_BRICK_WALL,
                 DEEPSLATE_TILE_WALL, END_STONE_BRICK_WALL, MOSSY_COBBLESTONE_WALL,
                 MOSSY_STONE_BRICK_WALL, MUD_BRICK_WALL, NETHER_BRICK_WALL,
                 POLISHED_BLACKSTONE_BRICK_WALL, POLISHED_BLACKSTONE_WALL,
                 POLISHED_DEEPSLATE_WALL, STONE_BRICK_WALL, POLISHED_TUFF_WALL,
                 RED_NETHER_BRICK_WALL, RED_SANDSTONE_WALL, TUFF_BRICK_WALL ->
            {// all walls
                // Mode 1: East (none, low, tall)
                // Mode 2: North (none, low, tall)
                // Mode 3: West (none, low, tall)
                // Mode 4: South (none, low, tall)
                // Mode 5?: Up (true false)   Should we even allow this?
            }
            case BLACK_GLAZED_TERRACOTTA, GRAY_GLAZED_TERRACOTTA, GREEN_GLAZED_TERRACOTTA,
                 BLUE_GLAZED_TERRACOTTA, BROWN_GLAZED_TERRACOTTA, CYAN_GLAZED_TERRACOTTA,
                 LIGHT_BLUE_GLAZED_TERRACOTTA, LIGHT_GRAY_GLAZED_TERRACOTTA,
                 LIME_GLAZED_TERRACOTTA, MAGENTA_GLAZED_TERRACOTTA, ORANGE_GLAZED_TERRACOTTA,
                 PINK_GLAZED_TERRACOTTA, PURPLE_GLAZED_TERRACOTTA, RED_GLAZED_TERRACOTTA,
                 WHITE_GLAZED_TERRACOTTA, YELLOW_GLAZED_TERRACOTTA, JACK_O_LANTERN,
                 CARVED_PUMPKIN, BEE_NEST, BEEHIVE, STONECUTTER, LOOM, FURNACE,
                 SMOKER, BLAST_FURNACE, CAMPFIRE, SOUL_CAMPFIRE, ANVIL, CHIPPED_ANVIL,
                 DAMAGED_ANVIL, DECORATED_POT, CHISELED_BOOKSHELF, LECTERN,
                 WARPED_WALL_HANGING_SIGN, ACACIA_WALL_HANGING_SIGN, BAMBOO_WALL_HANGING_SIGN,
                 BIRCH_WALL_HANGING_SIGN, CHERRY_WALL_HANGING_SIGN, CRIMSON_WALL_HANGING_SIGN,
                 DARK_OAK_WALL_HANGING_SIGN, JUNGLE_WALL_HANGING_SIGN, MANGROVE_WALL_HANGING_SIGN,
                 OAK_WALL_HANGING_SIGN, SPRUCE_WALL_HANGING_SIGN, ENDER_CHEST, CREEPER_WALL_HEAD,
                 DRAGON_WALL_HEAD, PIGLIN_WALL_HEAD, PLAYER_WALL_HEAD, ZOMBIE_WALL_HEAD,
                 WITHER_SKELETON_WALL_SKULL, SKELETON_WALL_SKULL, REPEATER, COMPARATOR,
                 CALIBRATED_SCULK_SENSOR->
            {// BASIC FACING 4
                // facing4
                if (!rotateFacing4(clickedBlock))
                {
                    //Something wrong
                }
            }
            case WHITE_WALL_BANNER, BLACK_WALL_BANNER, BLUE_WALL_BANNER, BROWN_WALL_BANNER,
                 CYAN_WALL_BANNER, GRAY_WALL_BANNER, GREEN_WALL_BANNER, LIGHT_BLUE_WALL_BANNER,
                 LIGHT_GRAY_WALL_BANNER, LIME_WALL_BANNER, MAGENTA_WALL_BANNER,
                 ORANGE_WALL_BANNER, PINK_WALL_BANNER, PURPLE_WALL_BANNER, RED_WALL_BANNER,
                 YELLOW_WALL_BANNER, LADDER, WARPED_WALL_SIGN, ACACIA_WALL_SIGN, BAMBOO_WALL_SIGN,
                 BIRCH_WALL_SIGN, CHERRY_WALL_SIGN, CRIMSON_WALL_SIGN, DARK_OAK_WALL_SIGN,
                 JUNGLE_WALL_SIGN, MANGROVE_WALL_SIGN, OAK_WALL_SIGN, SPRUCE_WALL_SIGN->
            {// all wall banners, ladders too, wall signs
                // facing4, check support
            }
            case BLACK_BANNER, BLUE_BANNER, BROWN_BANNER, CYAN_BANNER, GRAY_BANNER,
                 GREEN_BANNER, LIGHT_BLUE_BANNER, LIGHT_GRAY_BANNER, LIME_BANNER,
                 MAGENTA_BANNER, ORANGE_BANNER, PINK_BANNER, PURPLE_BANNER, RED_BANNER,
                 WHITE_BANNER, YELLOW_BANNER, ACACIA_SIGN, BAMBOO_SIGN, BIRCH_SIGN, CHERRY_SIGN,
                 SPRUCE_SIGN, CRIMSON_SIGN, DARK_OAK_SIGN, JUNGLE_SIGN, MANGROVE_SIGN,
                 OAK_SIGN, WARPED_SIGN, CREEPER_HEAD, DRAGON_HEAD, PIGLIN_HEAD, PLAYER_HEAD,
                 ZOMBIE_HEAD, SKELETON_SKULL, WITHER_SKELETON_SKULL->
            {// floor banners and floor signs, floor heads and skulls
                // rotation 16 (0-15)
            }
            case AMETHYST_CLUSTER, LARGE_AMETHYST_BUD, MEDIUM_AMETHYST_BUD, SMALL_AMETHYST_BUD ->
            {// amethyst buds and clusters
                // facing6, check support
            }
            case BIG_DRIPLEAF, BIG_DRIPLEAF_STEM, SMALL_DRIPLEAF ->
            {// big and small dripleaf
                // facing4, update upper or lower blocks to match
            }
            case TORCH, REDSTONE_TORCH, SOUL_TORCH,
                 WALL_TORCH, SOUL_WALL_TORCH, REDSTONE_WALL_TORCH ->
            {// all torch types
                // facing4 for wall torches, then cycle to regular torch, back to facing4
                // also make sure to check support for wall torch
            }
            case LANTERN, SOUL_LANTERN ->
            {// Lanterns and soul lanterns
                // hanging (true false) check support
            }
            case BELL->
            {
                // check support for all
                // Mode 1: facing4
                // Mode 2: attachment (floor, ceiling, single wall, double wall)
            }
            case ACACIA_HANGING_SIGN, BAMBOO_HANGING_SIGN, BIRCH_HANGING_SIGN,
                 CHERRY_HANGING_SIGN, CRIMSON_HANGING_SIGN, OAK_HANGING_SIGN,
                 DARK_OAK_HANGING_SIGN, JUNGLE_HANGING_SIGN, MANGROVE_HANGING_SIGN,
                 SPRUCE_HANGING_SIGN, WARPED_HANGING_SIGN->
            {// all non wall hanging signs
                // Mode 1: rotation 16 (0-15)
                // Mode 2: attached (true false)
            }
            case CHEST, TRAPPED_CHEST ->
            {// chest and trapped chest
                // check type single
                // facing4
            }
            case HOPPER ->
            {// just the hopper
                // facing5 ? (north east south west down)
                if (!rotateFacing5(clickedBlock))
                {
                    //Something wrong
                }
            }

            // Maybe for rails just rotate through shape like debug stick? Idk
            case RAIL ->
            {// just regular rails
                // Mode 1: Rotate
                    // NS <-> EW
                    // Ascending rotation, check support, otherwise turn flat
                    // Rotate curves, aaaaaa
                // Mode 2: Shape Change (Straight, Curve, Ascending [check support] )
            }
            case ACTIVATOR_RAIL, DETECTOR_RAIL, POWERED_RAIL ->
            {// other rails, don't curve
                // Mode 1: Rotate, same as above, but no curve
                // Mode 2: Shape change (Straight ascending [check support] )
            }
        }
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
    private boolean rotateFacing5 (Block clickedBlock)
    {
        Directional block = (Directional) clickedBlock;
        BlockFace facing = block.getFacing();
        switch (facing)
        {
            case NORTH ->
            {
                block.setFacing(BlockFace.EAST);
            }
            case EAST ->
            {
                block.setFacing(BlockFace.SOUTH);
            }
            case SOUTH ->
            {
                block.setFacing(BlockFace.WEST);
            }
            case WEST ->
            {
                block.setFacing(BlockFace.DOWN);
            }
            case DOWN ->
            {
                block.setFacing(BlockFace.NORTH);
            }
            default ->
            {
                return false;
            }
        }
        return true;
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
