
package com.microwavedsalad.civilizationmod.progression;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

public class EndBlocker {

    private static final int REQUIRED_DAYS_END = 1000;

    @SubscribeEvent
    public static void onEndPortalEyeInsert(BlockEvent.EntityPlaceEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        int ticksPlayed = player.getStats().getValue(Stats.CUSTOM.get(Stats.PLAY_TIME));
        int daysPlayed = ticksPlayed / 24000;

        BlockState placedState = event.getPlacedBlock();

        if (placedState.getBlock() instanceof EndPortalFrameBlock && daysPlayed < REQUIRED_DAYS_END) {
            // Check if this frame now has an eye
            if (placedState.getValue(EndPortalFrameBlock.HAS_EYE)) {
                // Cancel the placement so the eye is never added
                event.setCanceled(true);
                player.displayClientMessage(Component.literal("❌ You must survive " + REQUIRED_DAYS_END + " days before entering the Nether! " +
                        "(You’ve played " + daysPlayed + " days)"), true);
            }
        }
    }
}
