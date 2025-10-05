package bnjmn21.realrocket.api.celestial_body;

import bnjmn21.realrocket.api.RRRegistries;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VirtualLevels {
    public static Registry<CelestialBody> getCbRegistry(Level level) {
        RegistryAccess regs = level.registryAccess();
        return regs.registry(RRRegistries.CELESTIAL_BODIES).orElseThrow();
    }

    public static Set<VirtualLevelKey> discoveredLevels(Player player) {
        Registry<CelestialBody> reg = getCbRegistry(player.level());
        Stream<VirtualLevelKey> physical = reg.stream()
                .flatMap(b -> {
                    if (b.body().isDiscovered().equals(Optional.of(true))) {
                        return b.body().levels();
                    }
                    return Stream.empty();
                })
                .map(VirtualLevelKey.Planet::new);
        Set<VirtualLevelKey> levels = physical.collect(Collectors.toSet());
        getLevelAt(player).map(levels::add);
        return levels;
    }

    public static Optional<VirtualLevelKey> getLevelAt(Level level, BlockPos pos) {
        return planetLevels(level).keySet().stream().filter(lvl -> lvl.isIn(level.dimension(), pos)).findAny().map(p -> p);
    }

    public static Optional<VirtualLevelKey> getLevelAt(Player player) {
        return getLevelAt(player.level(), player.blockPosition());
    }

    public static VirtualLevelKey overworld() {
        return new VirtualLevelKey.Planet(ResourceKey.create(Registries.DIMENSION, new ResourceLocation("overworld")));
    }

    @ParametersAreNonnullByDefault
    public static class CollectLevels implements PreparableReloadListener {
        @Override
        public @NotNull CompletableFuture<Void> reload(
                PreparationBarrier preparationBarrier, ResourceManager resourceManager,
                ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler,
                Executor backgroundExecutor, Executor gameExecutor) {
            INVALIDATE_PLANET_LEVELS = true;
            INVALIDATE_LEVELS = true;
            return CompletableFuture.runAsync(() -> {
                //noinspection DataFlowIssue
                preparationBarrier.wait(null);
            }, backgroundExecutor);
        }
    }

    private static boolean INVALIDATE_PLANET_LEVELS = true;
    private static Map<VirtualLevelKey.Planet, CelestialBody> PLANET_LEVELS;
    public static Map<VirtualLevelKey.Planet, CelestialBody> planetLevels(Level level) {
        if (PLANET_LEVELS != null && !INVALIDATE_PLANET_LEVELS) {
            return PLANET_LEVELS;
        }

        INVALIDATE_PLANET_LEVELS = false;
        Registry<CelestialBody> reg = getCbRegistry(level);
        PLANET_LEVELS = reg.stream()
                .flatMap(b -> b.body().levels().map(l -> Pair.of(b, l)))
                .collect(Collectors.toMap(
                        p -> new VirtualLevelKey.Planet(p.getSecond()),
                        Pair::getFirst));
        return PLANET_LEVELS;
    }

    private static boolean INVALIDATE_LEVELS = true;
    private static Map<VirtualLevelKey, CelestialBody> LEVELS;
    public static Map<VirtualLevelKey, CelestialBody> levels(Level level) {
        if (LEVELS != null && !INVALIDATE_LEVELS) {
            return LEVELS;
        }

        INVALIDATE_LEVELS = false;
        Map<VirtualLevelKey.Planet, CelestialBody> planets = planetLevels(level);
        LEVELS = new HashMap<>(planets);
        return LEVELS;
    }
}
