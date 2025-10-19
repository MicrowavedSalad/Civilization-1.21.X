package com.microwavedsalad.civilizationmod.progressionscreen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ProgressionScreen extends Screen {

    public ProgressionScreen() {
        super(Component.literal("Progression Screen"));
    }

    @Override
    protected void init() {
        // Close button
        addRenderableWidget(Button.builder(Component.literal("Close"), b -> this.onClose())
                .bounds(this.width / 2 - 50, this.height / 2 - 10, 100, 20)
                .build());
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        // Background
        this.renderBackground(graphics, 0, 0, 0f);
        super.render(graphics, mouseX, mouseY, partialTicks);

        Minecraft mc = Minecraft.getInstance();
        if (mc.level != null) {
            long ticks = mc.level.getGameTime();
            long days = ticks / 24000;
            String age = AgeHudOverlay.getAgeForTicks(ticks);

            String text = "Current Age: " + age + " (" + days + " days)";
            graphics.drawString(this.font, text, this.width / 3 - this.font.width(text) / 2, 20, 0xFFFFFF, false);
        }
    }

    @Override
    public void onClose() {
        super.onClose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
