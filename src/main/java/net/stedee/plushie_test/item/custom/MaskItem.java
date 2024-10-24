package net.stedee.plushie_test.item.custom;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.stedee.plushie_test.item.custom_armor_materials.CustomMaskMaterial;
import net.stedee.plushie_test.plushie_test;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.item.ArmorItem.DISPENSE_ITEM_BEHAVIOR;

public class MaskItem extends Item implements Equipable {

    private final MobEffect effect;
    private final ImmutableMultimap<Attribute, AttributeModifier> defaultModifiers;

    public static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
        /**
         * Dispense the specified stack, play the dispense sound, and spawn particles.
         */
        protected @NotNull ItemStack execute(@NotNull BlockSource pSource, @NotNull ItemStack pStack) {
            return MaskItem.dispenseArmor(pSource, pStack) ? pStack : super.execute(pSource, pStack);
        }
    };

    public static boolean dispenseArmor(BlockSource pSource, ItemStack pStack) {
        BlockPos blockpos = pSource.getPos().relative(pSource.getBlockState().getValue(DispenserBlock.FACING));
        List<LivingEntity> list = pSource.getLevel().getEntitiesOfClass(LivingEntity.class, new AABB(blockpos), EntitySelector.NO_SPECTATORS.and(new EntitySelector.MobCanWearArmorEntitySelector(pStack)));
        if (list.isEmpty()) {
            return false;
        } else {
            LivingEntity livingentity = list.get(0);
            EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(pStack);
            ItemStack itemstack = pStack.split(1);
            livingentity.setItemSlot(equipmentslot, itemstack);
            if (livingentity instanceof Mob) {
                ((Mob)livingentity).setDropChance(equipmentslot, 2.0F);
                ((Mob)livingentity).setPersistenceRequired();
            }

            return true;
        }
    }

    public MaskItem(Properties pProperties, CustomMaskMaterial mats, MobEffect effect) {
        super(pProperties);
        this.effect = effect;
        DispenserBlock.registerBehavior(this, DISPENSE_ITEM_BEHAVIOR);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        UUID uuid = UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150");
        builder.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", (double) 3, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", (double)3.0, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance", (double)0.1, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    @SuppressWarnings("null")
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
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
    public @NotNull EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        return this.swapWithEquipmentSlot(this, pLevel, pPlayer, pUsedHand);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return slot == this.getEquipmentSlot() ? this.defaultModifiers : super.getAttributeModifiers(slot, stack);
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
