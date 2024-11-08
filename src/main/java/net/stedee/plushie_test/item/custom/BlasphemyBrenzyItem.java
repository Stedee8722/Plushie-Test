package net.stedee.plushie_test.item.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.stedee.plushie_test.enchantment.ModdedEnchantments;
import net.stedee.plushie_test.entity.custom.BlasphemyBrenzyProjectile;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class BlasphemyBrenzyItem extends BlasphemyItem {
    private static final ResourceLocation ALT_FONT = new ResourceLocation("minecraft", "alt");
    private static final Style ROOT_STYLE = Style.EMPTY.withFont(ALT_FONT);
    private static final float eDamage = 4;

    public BlasphemyBrenzyItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties, eDamage);
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack pStack) {
        return Component.translatable(this.getDescriptionId(pStack)).withStyle(ROOT_STYLE);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if ((Minecraft.getInstance().hitResult != null ? Minecraft.getInstance().hitResult.getType() : null) != HitResult.Type.MISS) {
            return super.onEntitySwing(stack, entity);
        }
        if (!entity.level().isClientSide()) {
            float pDamage = eDamage * (EnchantmentHelper.getEnchantmentLevel(ModdedEnchantments.BLASPHEMY_DAMAGE.get(), entity) + 1);

            Vec3 look = entity.getViewVector(1F);
            Entity blasphemy = super.makeProjectile(new BlasphemyBrenzyProjectile(entity.level(), entity, 0, 0, 0, pDamage * ((Player) entity).getAttackStrengthScale(0)), entity, look);

            entity.level().addFreshEntity(blasphemy);
            stack.hurtAndBreak(1, entity, (pOnBroken) -> pOnBroken.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return false;
    }

    @Override
    public void inventoryTick(ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pStack.getTag() != null && !pStack.getTag().contains("Unbreakable")) {
            pStack.addTagElement("Unbreakable", IntTag.valueOf(1));
        }
        if (pEntity instanceof LivingEntity) {

            AttributeModifier eModifier = new AttributeModifier(UUID.fromString("06c477b9-d96b-4197-b3ef-d8b6fa78872b"), "blasphemy_brenzy_reach", 3, AttributeModifier.Operation.ADDITION);
            AttributeModifier bModifier = new AttributeModifier(UUID.fromString("d26de641-c527-42e0-a1f9-c0e6b03b76a8"), "blasphemy_brenzy_reach", 3, AttributeModifier.Operation.ADDITION);

            if (pIsSelected) {
                if (!Objects.requireNonNull(((LivingEntity) pEntity).getAttribute(ForgeMod.ENTITY_REACH.get())).hasModifier(eModifier)) {
                    Objects.requireNonNull(((LivingEntity) pEntity).getAttribute(ForgeMod.ENTITY_REACH.get())).addPermanentModifier(eModifier);
                }
                if (!Objects.requireNonNull(((LivingEntity) pEntity).getAttribute(ForgeMod.BLOCK_REACH.get())).hasModifier(bModifier)) {
                    Objects.requireNonNull(((LivingEntity) pEntity).getAttribute(ForgeMod.BLOCK_REACH.get())).addPermanentModifier(bModifier);
                }
            } else {
                Objects.requireNonNull(((LivingEntity) pEntity).getAttribute(ForgeMod.ENTITY_REACH.get())).removePermanentModifier(UUID.fromString("06c477b9-d96b-4197-b3ef-d8b6fa78872b"));
                Objects.requireNonNull(((LivingEntity) pEntity).getAttribute(ForgeMod.BLOCK_REACH.get())).removePermanentModifier(UUID.fromString("d26de641-c527-42e0-a1f9-c0e6b03b76a8"));
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }
}
