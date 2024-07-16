package net.stedee.plushie_test.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.stedee.plushie_test.plushie_test;
import net.stedee.plushie_test.block.custom.SeamstressTableBlockEntity;
import net.stedee.plushie_test.inventory.custom.SeamstressTableMenu;
import net.stedee.plushie_test.network.C2SClearPacket;
import net.stedee.plushie_test.network.PacketHandler;

public class SeamstressTableScreen extends AbstractContainerScreen<SeamstressTableMenu> {

    private final ResourceLocation GUI = new ResourceLocation(plushie_test.MOD_ID, "textures/gui/seamstress_table_gui.png");
    private Button button;
    private SeamstressTableMenu pMenu;
    private SeamstressTableBlockEntity blockEntity;

    private static final Component SWITCH_BUTTON =
            Component.translatable("gui." + plushie_test.MOD_ID + ".seamstress_table_screen.button.text.switch");

    public SeamstressTableScreen(SeamstressTableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
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

        int buttonWidth = 50;
        int buttonHeight = 16;
        this.button = Button.builder(
                    SWITCH_BUTTON,
                    this::handleSwitchButton)
                    .bounds(this.leftPos + this.imageWidth / 2 - buttonWidth / 2, this.topPos + 55, buttonWidth, buttonHeight)
                    .tooltip(Tooltip.create(SWITCH_BUTTON))
                    .build();
        this.addRenderableWidget(button);
    }

    private void handleSwitchButton(Button button) {
        PacketHandler.INSTANCE.sendToServer(new C2SClearPacket());
        this.pMenu.tileEntity.fromResult = !this.pMenu.tileEntity.fromResult;
        return;
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
        renderArrow(pGuiGraphics);
    }

    private void renderArrow(GuiGraphics pGuiGraphics) {
        if (!this.pMenu.tileEntity.fromResult) {
            pGuiGraphics.blit(GUI, leftPos+99, topPos+34, 182, 0, 17, 11);
        } else {
            pGuiGraphics.blit(GUI, leftPos+98, topPos+34, 182, 14, 17, 11);
        }
    }

    @Override
    protected void containerTick() {
        if(button.isHovered()) {
            button.setFocused(true);
        }
        else {
            button.setFocused(false);
        }
        super.containerTick();
    }
}