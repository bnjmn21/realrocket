package bnjmn21.realrocket.common.block;

import bnjmn21.realrocket.api.rocket.BlockMass;
import bnjmn21.realrocket.api.rocket.Engine;
import bnjmn21.realrocket.api.rocket.RocketLogic;
import bnjmn21.realrocket.api.units.quantities.Mass;
import bnjmn21.realrocket.common.data.RRLang;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class EngineBlock extends Block implements Engine, BlockMass {
    final int tier;
    final Supplier<GTRecipeType> recipeType;
    final @Nullable MutableComponent lore;

    public EngineBlock(Properties properties, int tier, Supplier<GTRecipeType> recipeType, @Nullable MutableComponent lore) {
        super(properties);
        this.tier = tier;
        this.recipeType = recipeType;
        this.lore = lore;
    }

    public static NonNullFunction<Properties, EngineBlock> factory(int tier, Supplier<GTRecipeType> recipeType, @Nullable MutableComponent lore) {
        return props -> new EngineBlock(props, tier, recipeType, lore);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        if (this.lore != null) {
            tooltip.add(this.lore);
        }
        tooltip.add(RRLang.TOOLTIP_TIER.apply(String.valueOf(this.tier)).withStyle(RocketLogic.TIER_COLORS[this.tier]));
    }

    @Override
    public Mass getMass() {
        return new Mass.Tonne(1);
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public GTRecipeType getRecipeType() {
        return recipeType.get();
    }
}
