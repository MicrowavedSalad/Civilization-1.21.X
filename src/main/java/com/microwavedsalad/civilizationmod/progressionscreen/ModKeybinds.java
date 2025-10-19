package com.microwavedsalad.civilizationmod.progressionscreen;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import org.lwjgl.glfw.GLFW;
import net.neoforged.bus.api.SubscribeEvent;

public class ModKeybinds {

    public static KeyMapping OPEN_PROGRESSION_SCREEN;

    public static void register() {
        OPEN_PROGRESSION_SCREEN = new KeyMapping(
                "key.vanillamodchanges.open_progression",
                GLFW.GLFW_KEY_O,
                "key.categories.gameplay"
        );
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();

        // Only run if we are in a world
        if (mc.level == null) return;

        // Only open screen if no other GUI is open
        if (mc.screen == null && OPEN_PROGRESSION_SCREEN.isDown()) {
            mc.setScreen(new ProgressionScreen());
        }
    }
}
