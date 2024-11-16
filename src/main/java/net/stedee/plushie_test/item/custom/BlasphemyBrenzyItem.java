package net.stedee.plushie_test.item.custom;

import net.minecraft.nbt.IntTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
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
        if (!(entity instanceof Player)) { return false;}
        Vec3 from = entity.getEyePosition(1F);
        Vec3 look = entity.getViewVector(1F);

        // see if can reach entities
        double d0 = ((Player) entity).getBlockReach();
        double entityReach = ((Player) entity).getEntityReach();
        double d1;
        if (((Player)entity).isCreative()) {
            d0 = 6.0D;
        }
        d0 = d1 = Math.max(d0, entityReach);
        d1 *= d1;
        Vec3 vec32 = from.add(look.x * d0, look.y * d0, look.z * d0);
        AABB aabb = entity.getBoundingBox().expandTowards(look.scale(d0)).inflate(1.0D, 1.0D, 1.0D);
        EntityHitResult entityhitresult = ProjectileUtil.getEntityHitResult(entity, from, vec32, aabb, (p_234237_) -> !p_234237_.isSpectator() && p_234237_.isPickable(), d1);

        // see if can reach blocks
        double blockReachDistance = ((Player)entity).getBlockReach();
        Vec3 to = from.add(look.x * blockReachDistance, look.y * blockReachDistance, look.z * blockReachDistance);
        BlockHitResult blockhitresult = entity.level().clip(new ClipContext(from, to, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, entity));

        if ((blockhitresult.getType() != HitResult.Type.MISS) || (entityhitresult != null ? entityhitresult.getType() != HitResult.Type.MISS : false)) {
            return super.onEntitySwing(stack, entity);
        }
        if (!entity.level().isClientSide()) {
            float pDamage = eDamage * (EnchantmentHelper.getEnchantmentLevel(ModdedEnchantments.BLASPHEMY_DAMAGE.get(), entity) + 1);

            Entity blasphemy = super.makeProjectile(new BlasphemyBrenzyProjectile(entity.level(), entity, 0, 0, 0, pDamage * ((Player) entity).getAttackStrengthScale(0)), entity, look);

            entity.level().addFreshEntity(blasphemy);
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1F, 1F);
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
            double reach = 1;

            AttributeModifier eModifier = new AttributeModifier(UUID.fromString("06c477b9-d96b-4197-b3ef-d8b6fa78872b"), "blasphemy_brenzy_reach", reach, AttributeModifier.Operation.ADDITION);
            AttributeModifier bModifier = new AttributeModifier(UUID.fromString("d26de641-c527-42e0-a1f9-c0e6b03b76a8"), "blasphemy_brenzy_reach", reach, AttributeModifier.Operation.ADDITION);

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
