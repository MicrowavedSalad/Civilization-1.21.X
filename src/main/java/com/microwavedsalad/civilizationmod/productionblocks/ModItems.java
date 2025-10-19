package com.microwavedsalad.civilizationmod.productionblocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.Registries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, "civilizationmod");

    public static final DeferredHolder<Item, BlockItem> LUMBERMILL_ITEM =
            ITEMS.register("lumbermill",
                    () -> new BlockItem(ModBlocks.LUMBERMILL.get(),
                            new Item.Properties()));

    public static void registerItems(net.neoforged.bus.api.IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
