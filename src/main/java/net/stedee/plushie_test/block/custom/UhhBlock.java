package net.stedee.plushie_test.block.custom;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;

public class UhhBlock extends Block {

    public UhhBlock(Properties pProperties) {
        super(pProperties);
    }

    @SuppressWarnings("null")
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip,
            TooltipFlag pFlag) {
        pTooltip.add(Component.translatable(pStack.getDescriptionId() + ".tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

}
