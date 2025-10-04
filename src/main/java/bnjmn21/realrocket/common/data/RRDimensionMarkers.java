package bnjmn21.realrocket.common.data;

import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.world.level.block.Block;

import static bnjmn21.realrocket.api.RRRegistries.REGISTRATE;

public class RRDimensionMarkers {
    public static BlockEntry<Block> createMarker(String name) {
        return REGISTRATE.block("%s_marker".formatted(name), Block::new)
                .lang(FormattingUtil.toEnglishName(name))
                .blockstate((ctx, prov) -> prov.simpleBlock(ctx.get(), prov.models().cube(ctx.getName(),
                                prov.modLoc("block/dim_markers/%s/top".formatted(name)),
                                prov.modLoc("block/dim_markers/%s/top".formatted(name)),
                                prov.modLoc("block/dim_markers/%s/side1".formatted(name)),
                                prov.modLoc("block/dim_markers/%s/side1".formatted(name)),
                                prov.modLoc("block/dim_markers/%s/side2".formatted(name)),
                                prov.modLoc("block/dim_markers/%s/side2".formatted(name)))
                        .texture("particle", "#north")
                        .guiLight(BlockModel.GuiLight.FRONT)))
                .simpleItem()
                .register();
    }
}
