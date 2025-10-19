
package com.microwavedsalad.civilizationmod.progressionscreen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent.Chat;
import net.neoforged.bus.api.SubscribeEvent;

public class AgeHudOverlay {

    @SubscribeEvent
    public static void onRenderHUD(Chat event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return;

        long ticks = mc.level.getGameTime();       // World ticks
        long days = ticks / 24000;                 // Convert to days
        String age = getAgeForTicks(ticks);

        GuiGraphics graphics = event.getGuiGraphics();
        String text = "Current Age: " + age + " (" + days + " days)";

        // Draw top-left corner
        graphics.drawString(mc.font, text, 5, 5, 0xFFFFFF, false);
    }

    public static String getAgeForTicks(long ticks) {
        long days = ticks / 24000;
        if (days < 50) return "Stone Age";
        if (days < 100) return "Copper Age";
        if (days < 250) return "Iron Age";
        if (days < 500) return "Exploration Age";
        if (days < 750) return "Advancement Age";
        if (days >= 1000) return "Developed Age";
        return "Endgame";
    }
}

