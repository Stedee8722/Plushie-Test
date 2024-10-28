package net.stedee.plushie_test.advancement.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.stedee.plushie_test.plushie_test;
import org.jetbrains.annotations.NotNull;

public class PlayerDropItemTrigger extends SimpleCriterionTrigger<PlayerDropItemTrigger.TriggerInstance> {
    public static ResourceLocation ID = new ResourceLocation(plushie_test.MOD_ID, "dropped_item");

    @Override
    protected @NotNull TriggerInstance createInstance(JsonObject pJson, @NotNull ContextAwarePredicate predicate, @NotNull DeserializationContext deserializationContext) {
        ItemPredicate droppedItem = ItemPredicate.fromJson(pJson.get("item"));
        ResourceLocation damageType = new ResourceLocation(GsonHelper.getAsString(pJson, "damage_type"));
        return new TriggerInstance(droppedItem, damageType);
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    public void trigger(Entity player, ItemEntity pItem, DamageSource damageType) {
        if (!(player instanceof Player)) {
            return;
        }
        this.trigger((ServerPlayer) player, i -> (i.check(player, pItem, damageType)));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ItemPredicate item;
        private final ResourceLocation damageType;

        public TriggerInstance(ItemPredicate pItem, ResourceLocation damageType) {
            super(ID, ContextAwarePredicate.ANY);
            this.item = pItem;
            this.damageType = damageType;
        }

        public static TriggerInstance create(ItemPredicate predicate, ResourceLocation damageType) {
            return new TriggerInstance(predicate, damageType);
        }

        private boolean check(Entity player, ItemEntity pItem, DamageSource pDamageType) {
            return item.matches(pItem.getItem()) && (pDamageType.getMsgId().toLowerCase().matches(damageType.getPath())) && player != null;
        }
    }
}
