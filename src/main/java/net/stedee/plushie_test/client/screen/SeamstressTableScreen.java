package net.stedee.plushie_test.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.stedee.plushie_test.plushie_test;
import net.stedee.plushie_test.block.custom.SeamstressTableBlockEntity;
import net.stedee.plushie_test.inventory.custom.Seamstress.SeamstressTableMenu;

public class SeamstressTableScreen extends AbstractContainerScreen<SeamstressTableMenu> {

    private final ResourceLocation GUI = new ResourceLocation(plushie_test.MOD_ID, "textures/gui/seamstress_table_gui.png");
    private SeamstressTableMenu pMenu;
    private SeamstressTableBlockEntity blockEntity;

    public SeamstressTableScreen(SeamstressTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageHeight = 176;
        this.imageWidth = 176;
        this.inventoryLabelY = imageHeight - 93;
        this.inventoryLabelX = 9;
        this.titleLabelX = 9;
        this.titleLabelY = 4;
        this.pMenu = pMenu;
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
    }

    @Override
    protected void init() {
        super.init();

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        BlockEntity be = this.pMenu.tileEntity;
        if(be instanceof SeamstressTableBlockEntity blockEntity) {
            this.blockEntity = blockEntity;
        } else {
            plushie_test.LOGGER.error("BlockEntity at %s is not of type!\n", this.blockEntity.getBlockPos()); // debug
            return;
        }
    }

    @SuppressWarnings("null")
    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        pGuiGraphics.blit(GUI, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @SuppressWarnings("null")
    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics); // background first
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
