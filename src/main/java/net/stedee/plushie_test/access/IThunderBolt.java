package net.stedee.plushie_test.access;

import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public interface IThunderBolt {
    default void plushieTest$setCauseItem(@Nullable ItemStack pCause) {
    }
}
