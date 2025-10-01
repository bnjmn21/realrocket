package bnjmn21.realrocket.mixin;

import com.gregtechceu.gtceu.common.item.ColorSprayBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

/**
 * Mixin to temporarily fix {@link ColorSprayBehaviour},
 * until <a href="https://github.com/GregTechCEu/GregTech-Modern/pull/3982">#3982</a> gets merged
 */
@Mixin(ColorSprayBehaviour.class)
public class MixinColorSprayBehaviourBugfix {
    /**
     * @author bnjmn21
     * @reason temporary until it is fixed in gtceu
     */
    @Overwrite(remap = false)
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static boolean recolorBlockState(Level level, BlockPos pos, DyeColor color) {
        BlockState state = level.getBlockState(pos);
        for (Property property : state.getProperties()) {
            if (property.getValueClass() == DyeColor.class) {
                level.setBlockAndUpdate(pos, state.setValue(property, color));
                return true;
            }
        }
        return false;
    }
}
