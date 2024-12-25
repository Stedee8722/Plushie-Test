package net.stedee.plushie_test.item.custom;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.ForgeMod;
import net.stedee.plushie_test.attribute.ModdedAttributes;
import net.stedee.plushie_test.enchantment.ModdedEnchantments;
import net.stedee.plushie_test.entity.custom.BlasphemyBrenzyProjectile;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BlasphemyBrenzyItem extends BlasphemyItem {
    private static final ResourceLocation ALT_FONT = new ResourceLocation("minecraft", "alt");
    private static final Style ROOT_STYLE = Style.EMPTY.withFont(ALT_FONT);
    final float eDamage = 10;
    private static final double REACH = 1.5;

    private static final UUID ENTITY_REACH_UUID = UUID.fromString("06c477b9-d96b-4197-b3ef-d8b6fa78872b");
    private static final UUID BLOCK_REACH_UUID = UUID.fromString("d26de641-c527-42e0-a1f9-c0e6b03b76a8");
    private static final UUID PROJECTILE_DAMAGE_UUID = UUID.fromString("8cda51c5-5d59-4f3c-b0ca-895d9957fc32");

    public BlasphemyBrenzyItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        float attackDamage = (float) pAttackDamageModifier + pTier.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", pAttackSpeedModifier, AttributeModifier.Operation.ADDITION));
        builder.put(ModdedAttributes.PROJECTILE_DAMAGE.get(), new AttributeModifier(PROJECTILE_DAMAGE_UUID, "blasphemy_brenzy_projectile_damage", eDamage, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(BLOCK_REACH_UUID, "blasphemy_brenzy_reach", REACH, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(ENTITY_REACH_UUID, "blasphemy_brenzy_reach", REACH, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
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

        ((Player) entity).sweepAttack();

        if ((blockhitresult.getType() != HitResult.Type.MISS) || (entityhitresult != null && entityhitresult.getType() != HitResult.Type.MISS)) {
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

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }
}