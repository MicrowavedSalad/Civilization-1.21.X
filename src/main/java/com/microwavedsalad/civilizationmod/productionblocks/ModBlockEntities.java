package com.microwavedsalad.civilizationmod.productionblocks;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, "civilizationmod");

    // Register the custom chest block entity
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LumbermillBlockEntity>> LUMBERMILL =
            BLOCK_ENTITY_TYPES.register("lumbermill",
                    () -> BlockEntityType.Builder
                            .of(LumbermillBlockEntity::new, ModBlocks.LUMBERMILL.get())
                            .build(null));
}