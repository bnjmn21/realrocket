package bnjmn21.realrocket.mixin;

import bnjmn21.realrocket.RealRocket;
import bnjmn21.realrocket.common.worldgen.RandomCraters;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;

import com.mojang.serialization.Codec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin to manually add our own density functions to minecraft's bootstrap code,
 * since there is no forge registry for it.
 */
@Mixin(DensityFunctions.class)
public class MixinDensityFunctions {

    @Inject(at = @At("HEAD"), method = "bootstrap(Lnet/minecraft/core/Registry;)Lcom/mojang/serialization/Codec;")
    private static void bootstrap(
                                  Registry<Codec<? extends DensityFunction>> registry,
                                  CallbackInfoReturnable<Codec<? extends DensityFunction>> cir) {
        Registry.register(registry, RealRocket.id("random_craters"), RandomCraters.CODEC.codec());
    }
}
