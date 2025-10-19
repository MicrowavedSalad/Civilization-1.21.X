
package com.microwavedsalad.civilizationmod.progression;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.Set;

public class IronAge {

    //List of blocked items:
    private static final Set<Item> IRON_AGE_BLOCKED_ITEMS = Set.of(
            Items.IRON_SWORD,
            Items.IRON_AXE,
            Items.IRON_PICKAXE,
            Items.IRON_SHOVEL,
            Items.IRON_HOE,
            Items.IRON_CHESTPLATE,
            Items.IRON_LEGGINGS,
            Items.IRON_BOOTS,
            Items.IRON_HELMET,
            Items.ENCHANTING_TABLE,
            Items.ENCHANTED_GOLDEN_APPLE,
            Items.BUCKET,
            Items.ANVIL,
            Items.SHEARS,
            Items.REDSTONE_TORCH,
            Items.PISTON,
            Items.POWERED_RAIL,
            Items.NOTE_BLOCK,
            Items.DETECTOR_RAIL,
            Items.REDSTONE_LAMP,
            Items.ACTIVATOR_RAIL,
            Items.MINECART,
            Items.RAIL,
            Items.HEAVY_WEIGHTED_PRESSURE_PLATE,
            Items.IRON_TRAPDOOR,
            Items.LIGHT_WEIGHTED_PRESSURE_PLATE,
            Items.IRON_BARS,
            Items.IRON_BLOCK
    );

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            // PLAY_TIME is measured in ticks (20 ticks = 1 second, 24000 ticks = 1 Minecraft day)
            int daysPlayed = (int) (player.getStats().getValue(Stats.CUSTOM, Stats.PLAY_TIME) / 24000L);

            ItemStack result = event.getCrafting();

            // Check if result is in the blocked list above
            if (daysPlayed < 100 && IRON_AGE_BLOCKED_ITEMS.contains(result.getItem())) {
                // Prevent crafted item from being given
                result.shrink(result.getCount());

                // Return all ingredients to player
                Container grid = event.getInventory();
                for (int i = 0; i < grid.getContainerSize(); i++) {
                    ItemStack stack = grid.getItem(i);
                    if (!stack.isEmpty()) {
                        player.getInventory().add(stack.copy()); // return item
                        stack.setCount(0); // clear slot
                    }
                }
                player.closeContainer();

                // Notify player
                player.sendSystemMessage(
                        net.minecraft.network.chat.Component.literal(
                                "You need to reach the IRON AGE (100 Days) before you can craft this item!" +
                                        " (You’ve played " + daysPlayed + " days)"
                        )
                );
            }
        }
    }
}
