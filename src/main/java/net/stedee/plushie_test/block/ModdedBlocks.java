package net.stedee.plushie_test.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.stedee.plushie_test.plushie_test;
import net.stedee.plushie_test.block.custom.SeamstressTableBlock;
import net.stedee.plushie_test.block.custom.UhhBlock;
import net.stedee.plushie_test.item.ModdedItems;

import java.util.function.Supplier;

public class ModdedBlocks {
    public static final DeferredRegister<Block> BLOCKS = 
        DeferredRegister.create(ForgeRegistries.BLOCKS, plushie_test.MOD_ID);

    public static final RegistryObject<Block> SEAMSTRESS_TABLE = registerBlock("seamstress_table",
        () -> new SeamstressTableBlock(BlockBehaviour.Properties.of().mapColor(MapColor.NONE).noOcclusion().instrument(NoteBlockInstrument.BASS).strength(2.5F).sound(SoundType.WOOD)));
    
    public static final RegistryObject<Block> UHH_BLOCK = registerBlock("uhh_block",
        () -> new UhhBlock(BlockBehaviour.Properties.of().mapColor(MapColor.NONE).noOcclusion().instrument(NoteBlockInstrument.BIT).strength(2.5F).sound(SoundType.METAL)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModdedItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().stacksTo(64).rarity(Rarity.EPIC)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
