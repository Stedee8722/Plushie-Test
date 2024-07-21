package net.stedee.plushie_test.block;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.stedee.plushie_test.plushie_test;
import net.stedee.plushie_test.block.custom.AlchemicalTableBlockEntity;
import net.stedee.plushie_test.block.custom.SeamstressTableBlockEntity;

public class ModdedBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = 
        DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, plushie_test.MOD_ID);

    @SuppressWarnings("null")
    public static final RegistryObject<BlockEntityType<SeamstressTableBlockEntity>> SEAMSTRESS_TABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("seamstress_table_block_entity",
        () -> BlockEntityType.Builder.of(SeamstressTableBlockEntity::new, ModdedBlocks.SEAMSTRESS_TABLE.get()).build(null));

    @SuppressWarnings("null")
    public static final RegistryObject<BlockEntityType<AlchemicalTableBlockEntity>> ALCHEMICAL_TABLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("alchemical_table_block_entity",
        () -> BlockEntityType.Builder.of(AlchemicalTableBlockEntity::new, ModdedBlocks.ALCHEMICAL_TABLE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
