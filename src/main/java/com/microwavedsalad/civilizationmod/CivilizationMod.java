package com.microwavedsalad.civilizationmod;

import com.microwavedsalad.civilizationmod.productionblocks.ModBlockEntities;
import com.microwavedsalad.civilizationmod.productionblocks.ModBlocks;
import com.microwavedsalad.civilizationmod.productionblocks.ModItems;
import com.microwavedsalad.civilizationmod.progression.*;
import com.microwavedsalad.civilizationmod.progressionscreen.AgeHudOverlay;
import com.microwavedsalad.civilizationmod.progressionscreen.ModKeybinds;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@Mod(CivilizationMod.MOD_ID)
public class CivilizationMod {
    public static final String MOD_ID = "civilizationmod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CivilizationMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register setup method
        modEventBus.addListener(this::commonSetup);

        // Register core mod content
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
        ModItems.registerItems(modEventBus);

        // Register events on the general NeoForge bus
        NeoForge.EVENT_BUS.register(CopperAge.class);
        NeoForge.EVENT_BUS.register(IronAge.class);
        NeoForge.EVENT_BUS.register(NetherBlocker.class);
        NeoForge.EVENT_BUS.register(ExplorationAge.class);
        NeoForge.EVENT_BUS.register(AdvancementAge.class);
        NeoForge.EVENT_BUS.register(DevelopedAge.class);
        NeoForge.EVENT_BUS.register(EndBlocker.class);
        NeoForge.EVENT_BUS.register(ModKeybinds.class);
        NeoForge.EVENT_BUS.register(AgeHudOverlay.class);
        NeoForge.EVENT_BUS.register(this);

        LOGGER.info("Civilization Mod initialized!");
    }


    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Common setup phase started for Civilization Mod");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Server starting with Civilization Mod loaded");
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        // Placeholder for custom /commands later
    }

    @EventBusSubscriber(modid = CivilizationMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Called once the Minecraft client is initialized
            ModKeybinds.register();
        }

        @SubscribeEvent
        public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
            event.register(ModKeybinds.OPEN_PROGRESSION_SCREEN);
        }
    }

}