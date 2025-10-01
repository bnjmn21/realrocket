package bnjmn21.realrocket.mixin;

import com.gregtechceu.gtceu.api.gui.widget.PhantomFluidWidget;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

/**
 * Mixin to temporarily fix {@link PhantomFluidWidget},
 */
@Mixin(value = PhantomFluidWidget.class, remap = false)
public class MixinPhantomFluidWidgetBugfix {

    @Shadow
    private Supplier<FluidStack> phantomFluidGetter;

    @Inject(method = "<init>()V", at = @At(value = "TAIL"))
    private void constructorTail(CallbackInfo ci) {
        phantomFluidGetter = () -> null;
    }
}
