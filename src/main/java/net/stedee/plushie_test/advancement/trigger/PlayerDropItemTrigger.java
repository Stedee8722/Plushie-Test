package net.stedee.plushie_test.advancement.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.stedee.plushie_test.plushie_test;
import org.jetbrains.annotations.NotNull;

public class PlayerDropItemTrigger extends SimpleCriterionTrigger<PlayerDropItemTrigger.TriggerInstance> {
    public static ResourceLocation ID = new ResourceLocation(plushie_test.MOD_ID, "dropped_item");

    @Override
    protected @NotNull TriggerInstance createInstance(JsonObject pJson, @NotNull ContextAwarePredicate predicate, @NotNull DeserializationContext deserializationContext) {
        ItemPredicate droppedItem = ItemPredicate.fromJson(pJson.get("item"));
        ResourceLocation damageType = new ResourceLocation(GsonHelper.getAsString(pJson, "damage_type"));
        ResourceKey<Level> level = pJson.has("dimension") ? ResourceKey.create(Registries.DIMENSION, new ResourceLocation(GsonHelper.getAsString(pJson, "dimension"))) : null;
        return new TriggerInstance(droppedItem, damageType, level);
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    public void trigger(Entity player, ItemEntity pItem, DamageSource damageType) {
        if (!(player instanceof Player)) {
            return;
        }
        this.trigger((ServerPlayer) player, i -> (i.check(player, pItem, damageType, player.level().dimension())));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ItemPredicate item;
        private final ResourceLocation damageType;
        private final ResourceKey<Level> level;

        public TriggerInstance(ItemPredicate pItem, ResourceLocation damageType, ResourceKey<Level> level) {
            super(ID, ContextAwarePredicate.ANY);
            this.item = pItem;
            this.damageType = damageType;
            this.level = level;
        }

        public static TriggerInstance create(ItemPredicate predicate, ResourceLocation damageType, ResourceKey<Level> level) {
            return new TriggerInstance(predicate, damageType, level);
        }

        private boolean check(Entity player, ItemEntity pItem, DamageSource pDamageType, ResourceKey<Level> level) {
            if (this.level != null) {
                return item.matches(pItem.getItem()) && (pDamageType.getMsgId().toLowerCase().matches(damageType.getPath())) && (player != null) && (this.level == level);
            }
            return item.matches(pItem.getItem()) && (pDamageType.getMsgId().toLowerCase().matches(damageType.getPath())) && player != null;
        }
    }
}
