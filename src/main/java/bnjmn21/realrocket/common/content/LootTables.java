package bnjmn21.realrocket.common.content;

import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class LootTables {

    public static AlternativesEntry.Builder silkTouchElseAlternatives(Item withSilkTouch, Item withoutSilkTouch) {
        return AlternativesEntry.alternatives(
                LootItem.lootTableItem(withSilkTouch)
                        .when(MatchTool.toolMatches(
                                ItemPredicate.Builder.item().hasEnchantment(
                                        new EnchantmentPredicate(
                                                Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))))),
                LootItem.lootTableItem(withoutSilkTouch)
                        .when(ExplosionCondition.survivesExplosion()));
    }

    public static <T extends Block> NonNullBiConsumer<RegistrateBlockLootTables, T> requiresSilkTouchElse(
                                                                                                          Supplier<Item> withoutSilkTouch) {
        return (table, block) -> table.add(block, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootTables.silkTouchElseAlternatives(block.asItem(), withoutSilkTouch.get()))));
    }

    public static <T extends SnowLayerBlock> NonNullBiConsumer<RegistrateBlockLootTables, T> snowLayerLikeLootTable(
                                                                                                                    Supplier<Item> snowBallLike) {
        return (table, block) -> table.add(block, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(LootTables.snowLayerLikeAlternatives(block, snowBallLike.get()))));
    }

    public static AlternativesEntry.Builder snowLayerLikeAlternatives(SnowLayerBlock block, Item snowBallLike) {
        Collection<Integer> layers = IntStream.range(0, 8).boxed().toList();
        return AlternativesEntry.alternatives(
                AlternativesEntry.alternatives(layers,
                        i -> LootItem.lootTableItem(snowBallLike).apply(
                                SetItemCountFunction.setCount(ConstantValue.exactly((float) i))).when(
                                        LootItemBlockStatePropertyCondition
                                                .hasBlockStateProperties(block)
                                                .setProperties(
                                                        StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(SnowLayerBlock.LAYERS, i))))
                        .when(
                                InvertedLootItemCondition.invert(
                                        MatchTool.toolMatches(
                                                ItemPredicate.Builder.item().hasEnchantment(
                                                        new EnchantmentPredicate(
                                                                Enchantments.SILK_TOUCH,
                                                                MinMaxBounds.Ints.atLeast(1)))))),
                AlternativesEntry.alternatives(layers,
                        i -> LootItem.lootTableItem(block.asItem()).apply(
                                SetItemCountFunction.setCount(ConstantValue.exactly((float) i))).when(
                                        LootItemBlockStatePropertyCondition
                                                .hasBlockStateProperties(block)
                                                .setProperties(
                                                        StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(SnowLayerBlock.LAYERS, i)))));
    }
}
