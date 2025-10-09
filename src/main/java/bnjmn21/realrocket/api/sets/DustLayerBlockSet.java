package bnjmn21.realrocket.api.sets;

import bnjmn21.realrocket.api.RRRegistrate;
import bnjmn21.realrocket.common.content.LootTables;
import bnjmn21.realrocket.common.content.Models;

import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.MapColor;

import com.tterrag.registrate.util.entry.BlockEntry;

import java.util.function.Supplier;

/**
 * - A snow-layer-like block
 * - A full block that is falling
 */
public class DustLayerBlockSet {

    public final String name;

    // public final BlockEntry<SnowLayerBlock> layer;
    public final BlockEntry<SandBlock> block;

    public DustLayerBlockSet(
                             RRRegistrate registrate,
                             String name,
                             String name_en_us,
                             MapColor mapColor,
                             Supplier<Material> item) {
        this.name = name;

        this.block = registrate.block(
                name + "_block",
                (p) -> new SandBlock(item.get().getMaterialRGB(), p))
                .lang(name_en_us + " Block")
                .initialProperties(() -> Blocks.SAND)
                .properties(properties -> properties.mapColor(mapColor))
                .blockstate(Models::randomRotated)
                .tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .loot(LootTables.requiresSilkTouchElse(
                        () -> ChemicalHelper.get(TagPrefix.dust, item.get(), 1).getItem()))
                .simpleItem()
                .register();
        // this.layer = registrate.block(name + "_layer", SnowLayerBlock::new)
        // .lang(name_en_us)
        // .initialProperties(() -> Blocks.SNOW)
        // .properties(properties -> properties.mapColor(mapColor))
        // .loot(LootTables.snowLayerLikeLootTable(
        // () -> ChemicalHelper.get(TagPrefix.dustTiny, item.get(), 1).getItem())
        // )
        // .blockstate(DustLayerBlockSet.snowLayers(id("block/" + name + "_block")))
        // .tag(BlockTags.MINEABLE_WITH_SHOVEL)
        // .item()
        // .model(this.snowLayerItem(name))
        // .build()
        // .register();
    }

    // private NonNullBiConsumer<DataGenContext<Item, BlockItem>, RegistrateItemModelProvider> snowLayerItem(String
    // name) {
    // return (ctx, prov) ->
    // prov.withExistingParent(ctx.getName(), id("block/" + name + "_layer_height2"));
    // }
    //
    // public static NonNullBiConsumer<DataGenContext<Block, SnowLayerBlock>, RegistrateBlockstateProvider>
    // snowLayers(ResourceLocation texture) {
    // return (ctx, prov) -> {
    // final VariantBlockStateBuilder[] models = {prov.getVariantBuilder(ctx.getEntry())};
    // IntStream.range(1, 8).forEachOrdered(i -> {
    // models[0] = models[0]
    // .partialState()
    // .with(SnowLayerBlock.LAYERS, i)
    // .modelForState()
    // .modelFile(snowLayerModel(ctx, prov, i * 2, texture))
    // .addModel();
    // });
    //
    // ModelFile cubeAll = prov.models().cubeAll(ctx.getName(), texture);
    // models[0]
    // .partialState()
    // .with(SnowLayerBlock.LAYERS, 8)
    // .modelForState()
    // .modelFile(cubeAll)
    // .addModel();
    // };
    // }

    // public static ModelFile snowLayerModel(
    // DataGenContext<Block, ? extends Block> ctx,
    // RegistrateBlockstateProvider prov,
    // int layers,
    // ResourceLocation texture
    // ) {
    // return prov.models().withExistingParent(
    // ctx.getName() + "_height" + layers,
    // new ResourceLocation("minecraft", "block/snow_height" + layers)
    // )
    // .texture("texture", texture)
    // .texture("particle", texture);
    // }
}
