
package com.microwavedsalad.civilizationmod.progression;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;

public class NetherBlocker {

    private static final int REQUIRED_DAYS_NETHER = 250;
    //private static final int REQUIRED_DAYS_END = 1000;

    @SubscribeEvent
    public static void onTravel(EntityTravelToDimensionEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        int ticksPlayed = player.getStats().getValue(Stats.CUSTOM.get(Stats.PLAY_TIME));
        int daysPlayed = ticksPlayed / 24000;

        ResourceKey<Level> toDim = event.getDimension();

        if (toDim.equals(Level.NETHER) && daysPlayed < REQUIRED_DAYS_NETHER) {
            event.setCanceled(true);
            player.sendSystemMessage(Component.literal(
                    "❌ You must survive " + REQUIRED_DAYS_NETHER + " days before entering the Nether! " +
                            "(You’ve played " + daysPlayed + " days)"
            ));
        }


        // if (toDim.equals(Level.END) && daysPlayed < REQUIRED_DAYS_END) {
        //     event.setCanceled(true);
        //     player.sendSystemMessage(Component.literal(
        //             "❌ You must survive " + REQUIRED_DAYS_END + " days before entering the End! " +
        //             "(You’ve played " + daysPlayed + " days)"
        //     ));}


    }
}

