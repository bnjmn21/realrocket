package bnjmn21.realrocket.mixin;

import net.minecraftforge.data.loading.DatagenModLoader;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Set;

/**
 * Mixin that automatically stops the datagen task once it completes.
 */
@Mixin(DatagenModLoader.class)
public class MixinDatagenModLoader {

    @Inject(at = @At("TAIL"), method = "begin", remap = false)
    private static void begin(
                              Set<String> mods, Path path, Collection<Path> inputs, Collection<Path> existingPacks,
                              Set<String> existingMods, boolean serverGenerators, boolean clientGenerators,
                              boolean devToolGenerators,
                              boolean reportsGenerator, boolean structureValidator, boolean flat, String assetIndex,
                              File assetsDir,
                              CallbackInfo ci) {
        System.exit(0);
    }
}
