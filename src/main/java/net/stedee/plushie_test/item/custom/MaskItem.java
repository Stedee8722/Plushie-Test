package net.stedee.plushie_test.item.custom;

import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.stedee.plushie_test.client.renderer.armor.MaskRenderer;
import net.stedee.plushie_test.item.custom_armor_materials.CustomMaskMaterial;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MaskItem extends ArmorItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private final MobEffect effect;

    public MaskItem(Properties pProperties, CustomMaskMaterial mats, MobEffect effect) {
        super(mats, net.minecraft.world.item.ArmorItem.Type.HELMET, pProperties);
        this.effect = effect;
    }

    @SuppressWarnings("null")
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable(pStack.getDescriptionId() + ".tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @SuppressWarnings("removal")
    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (effect != null) {
            player.addEffect(new MobEffectInstance(effect, 2, 0, false, false, true));
        }
        super.onArmorTick(stack, level, player);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new MaskRenderer();

                // This prepares our GeoArmorRenderer for the current render frame.
                // These parameters may be null however, so we don't do anything further with them
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                this.renderer.withScale(0.7F);

                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    //@Override
    //public void initializeClient(Consumer<IClientItemExtensions> consumer) {
    //    consumer.accept(new IClientItemExtensions() {
    //        private ItemRenderer renderer;
    //        @Override
    //        public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
    //            if (this.renderer == null) {
    //                this.renderer = new AmethystArmorRenderer();
    //            }
    //            this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
    //            return this.renderer;
    //        }
    //    });
    //}
}
