
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

public class ExplorationAge {

    //List of blocked items:
    private static final Set<Item> EXPLORATION_AGE_BLOCKED_ITEMS = Set.of(
            Items.GOLDEN_SWORD,
            Items.GOLDEN_AXE,
            Items.GOLDEN_PICKAXE,
            Items.GOLDEN_SHOVEL,
            Items.GOLDEN_HOE,
            Items.GOLDEN_CHESTPLATE,
            Items.GOLDEN_LEGGINGS,
            Items.GOLDEN_BOOTS,
            Items.GOLDEN_HELMET,
            Items.CLOCK,
            Items.COMPASS,
            Items.MAP,
            Items.SPYGLASS,
            Items.BREWING_STAND

    );

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            // PLAY_TIME is measured in ticks (20 ticks = 1 second, 24000 ticks = 1 Minecraft day)
            int daysPlayed = (int) (player.getStats().getValue(Stats.CUSTOM, Stats.PLAY_TIME) / 24000L);

            ItemStack result = event.getCrafting();

            // Check if result is in the blocked list above
            if (daysPlayed < 250 && EXPLORATION_AGE_BLOCKED_ITEMS.contains(result.getItem())) {
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
                                "You need to reach the EXPLORATION AGE (250 Days) before you can craft this item!" +
                                        " (You’ve played " + daysPlayed + " days)"
                        )
                );
            }
        }
    }
}

