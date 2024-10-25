package net.stedee.plushie_test.advancement.custom;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.stedee.plushie_test.plushie_test;

public class PlayerDropItem extends SimpleCriterionTrigger<PlayerDropItem.TriggerInstance> {
    public static final ResourceLocation ID = new ResourceLocation(plushie_test.MOD_ID, "dropped_item");

    @Override
    protected TriggerInstance createInstance(JsonObject jsonObject, ContextAwarePredicate contextAwarePredicate, DeserializationContext deserializationContext) {
        ItemPredicate droppedItem = ItemPredicate.fromJson(jsonObject.get("item"));
        return new TriggerInstance(droppedItem, contextAwarePredicate);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ItemPredicate droppedItem;

        public TriggerInstance(ItemPredicate droppedItem, ContextAwarePredicate contextAwarePredicate) {
            super(ID, contextAwarePredicate);
            this.droppedItem = droppedItem;
        }

        public static TriggerInstance playerDropItemTrigger(ContextAwarePredicate contextAwarePredicate, ItemPredicate pItem) {
            return new TriggerInstance(pItem, contextAwarePredicate);
        }
    }
}
