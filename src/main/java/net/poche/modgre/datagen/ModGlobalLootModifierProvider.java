package net.poche.modgre.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.poche.modgre.Modgre;
import net.poche.modgre.item.ModItems;
import net.poche.modgre.loot.AddItemModifier;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output) {
        super(output, Modgre.MOD_ID);
    }

    @Override
    protected void start() {
        add("fragment_from_block", new AddItemModifier(new LootItemCondition[] {
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.SAND).build(),
                LootItemRandomChanceCondition.randomChance(0.08f).build()}, ModItems.STRANGE_FRAGMENT.get()));
    }
}
