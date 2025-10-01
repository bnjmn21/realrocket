package bnjmn21.realrocket.common.content;

import bnjmn21.realrocket.RealRocket;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;

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

    public static void orientableVerticalTranslucent(
            DataGenContext<Block, ? extends Block> ctx,
            RegistrateBlockstateProvider prov
    ) {
        ModelFile model = prov.models().withExistingParent(ctx.getName(), prov.mcLoc("orientable_vertical"))
                .texture("front", RealRocket.id("block/" + ctx.getName() + "_front"))
                .texture("side", RealRocket.id("block/" + ctx.getName() + "_side"))
                .renderType("translucent");
        prov.simpleBlock(ctx.getEntry(), model);
    }

    public static void simpleBlockWithExistingModel(DataGenContext<Block, ? extends Block> ctx, RegistrateBlockstateProvider prov) {
        prov.simpleBlock(ctx.getEntry(), prov.models().getExistingFile(ctx.getId().withPrefix("block/")));
    }
}