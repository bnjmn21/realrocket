package bnjmn21.realrocket.common.content;

import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;

import java.util.Collection;
import java.util.stream.IntStream;

public class Models {
    public static void randomRotated(
            DataGenContext<Block, ? extends Block> ctx,
            RegistrateBlockstateProvider prov
    ) {
        Models.randomRotated(prov.blockTexture(ctx.getEntry())).accept(ctx, prov);
    }

    public static NonNullBiConsumer<DataGenContext<Block, ? extends Block>, RegistrateBlockstateProvider>
    randomRotated(ResourceLocation texture) {
        return (ctx, prov) -> {
            ModelFile cubeAll = prov.models().cubeAll(ctx.getName(), texture);
            ModelFile cubeMirroredAll = prov.models().singleTexture(
                    ctx.getName() + "_mirrored",
                    prov.mcLoc(ModelProvider.BLOCK_FOLDER + "/cube_mirrored_all"),
                    "all", texture
            );
            ConfiguredModel[] models = ConfiguredModel.builder()
                    .modelFile(cubeAll)
                    .rotationY(0)
                    .nextModel()
                    .modelFile(cubeAll)
                    .rotationY(180)
                    .nextModel()
                    .modelFile(cubeMirroredAll)
                    .rotationY(0)
                    .nextModel()
                    .modelFile(cubeMirroredAll)
                    .rotationY(180)
                    .build();
            prov.simpleBlock(ctx.getEntry(), models);
        };
    }
}
