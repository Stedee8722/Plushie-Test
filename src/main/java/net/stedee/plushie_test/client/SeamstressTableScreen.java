package net.stedee.plushie_test.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.stedee.plushie_test.plushie_test;
import net.stedee.plushie_test.inventory.custom.SeamstressContainer;

public class SeamstressTableScreen extends AbstractContainerScreen<SeamstressContainer> {

    private final ResourceLocation GUI = new ResourceLocation(plushie_test.MOD_ID, "textures/gui/seamstress_table_gui.png");

    public SeamstressTableScreen(SeamstressContainer pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.inventoryLabelY = imageHeight - 93;
        this.inventoryLabelX = 9;
        this.titleLabelX = 9;
        this.titleLabelY = 4;
    }

    @SuppressWarnings("null")
    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        pGuiGraphics.blit(GUI, (this.width - this.imageWidth) / 2, (this.height - this.imageHeight) / 2, 0, 0, this.imageWidth, this.imageHeight);
    }

}
