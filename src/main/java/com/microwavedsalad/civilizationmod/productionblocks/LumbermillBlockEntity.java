package com.microwavedsalad.civilizationmod.productionblocks;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Holder;

public class LumbermillBlockEntity extends ChestBlockEntity {

    private int tickCounter = 0;
    private static final int TICKS_PER_MC_DAY = 24000;
    private static final int WOOD_PER_DAY = 8;

    // Automatically assigned wood type
    private Item woodType = Items.OAK_LOG;

    public LumbermillBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LUMBERMILL.get(), pos, state);
    }

    // Automatically detect wood type based on biome
    private void updateWoodType(Level level, BlockPos pos) {
        Holder<Biome> biomeHolder = level.getBiome(pos);
        ResourceKey<Biome> biomeKey = biomeHolder.unwrapKey().orElse(null);

        if (biomeKey == null) {
            woodType = Items.OAK_LOG; // default
            return;
        }

        switch (biomeKey.location().getPath()) {
            case "birch_forest", "birch_forest_hills", "old_growth_birch_forest" -> woodType = Items.BIRCH_LOG;
            case "jungle", "jungle_hills", "jungle_edge" -> woodType = Items.JUNGLE_LOG;
            case "taiga", "snowy_taiga", "taiga_hills", "snowy_taiga_hills" -> woodType = Items.SPRUCE_LOG;
            case "dark_forest", "dark_forest_hills" -> woodType = Items.DARK_OAK_LOG;
            case "savanna", "savanna_plateau" -> woodType = Items.ACACIA_LOG;
            default -> woodType = Items.OAK_LOG;
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, LumbermillBlockEntity chest) {
        if (level.isClientSide) return;

        chest.tickCounter++;

        // Every Minecraft day, add wood
        if (chest.tickCounter >= TICKS_PER_MC_DAY) {
            chest.tickCounter = 0;

            // Update wood type based on biome each day
            chest.updateWoodType(level, pos);

            chest.addWood();
        }
    }

    private void addWood() {
        SimpleContainer container = new SimpleContainer(27);

        // Copy current chest inventory
        for (int i = 0; i < this.getContainerSize(); i++) {
            container.setItem(i, this.getItem(i));
        }

        ItemStack woodStack = new ItemStack(woodType, WOOD_PER_DAY);

        // Try to add wood
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack current = container.getItem(i);
            if (current.isEmpty()) {
                container.setItem(i, woodStack);
                break;
            } else if (current.is(woodType) && current.getCount() < current.getMaxStackSize()) {
                int space = current.getMaxStackSize() - current.getCount();
                int toAdd = Math.min(space, WOOD_PER_DAY);
                current.grow(toAdd);
                break;
            }
        }

        // Save back to chest
        for (int i = 0; i < this.getContainerSize(); i++) {
            this.setItem(i, container.getItem(i));
        }
    }
}