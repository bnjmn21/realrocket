package bnjmn21.realrocket.api.rocket;

import bnjmn21.realrocket.common.data.RRLang;

import com.gregtechceu.gtceu.api.capability.recipe.CWURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.content.SerializerInteger;

import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;

import org.apache.commons.lang3.mutable.MutableInt;

import java.util.List;

/**
 * Thrust in newton
 */
public class ThrustRecipeCapability extends RecipeCapability<Integer> {

    public static ThrustRecipeCapability CAP = new ThrustRecipeCapability();

    protected ThrustRecipeCapability() {
        super("thrust", 0x00FFFF, false, 10, SerializerInteger.INSTANCE);
    }

    @Override
    public Integer copyInner(Integer content) {
        return content;
    }

    @Override
    public Integer copyWithModifier(Integer content, ContentModifier modifier) {
        return modifier.apply(content);
    }

    @Override
    public void addXEIInfo(WidgetGroup group, int xOffset, GTRecipe recipe, List<Content> contents, boolean perTick,
                           boolean isInput, MutableInt yOffset) {
        int thrust = contents.stream().map(Content::getContent).mapToInt(CWURecipeCapability.CAP::of).sum();
        group.addWidget(new LabelWidget(3 - xOffset, yOffset.addAndGet(10),
                RRLang.THRUST.apply(String.valueOf(thrust))));
    }
}
