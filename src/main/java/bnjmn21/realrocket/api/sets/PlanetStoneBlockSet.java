package bnjmn21.realrocket.api.sets;

import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.data.recipe.StoneTypeEntry;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;
import com.gregtechceu.gtceu.data.recipe.misc.StoneMachineRecipes;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;

import bnjmn21.realrocket.RealRocket;
import bnjmn21.realrocket.api.RRRegistrate;
import bnjmn21.realrocket.common.content.LootTables;
import bnjmn21.realrocket.common.content.Models;
import bnjmn21.realrocket.util.StonecuttingRecipeHelper;
import com.tterrag.registrate.providers.*;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;

import java.util.function.Consumer;

import static bnjmn21.realrocket.RealRocket.id;
import static com.gregtechceu.gtceu.api.GTValues.*;

/**
 * A stone material set consisting of normal as well as cobbled versions, with their respective slabs, stairs, etc.
 * Also adds recipes for conversion between the items and GT Ores
 */
public class PlanetStoneBlockSet {

    public final String name;
    public final BlockSetType blockSetType;

    public final BlockEntry<Block> cobbled;
    public final BlockEntry<SlabBlock> cobbledSlab;
    public final BlockEntry<StairBlock> cobbledStair;
    public final BlockEntry<Block> normal;
    public final BlockEntry<SlabBlock> slab;
    public final BlockEntry<StairBlock> stair;

    public final BlockEntry<ButtonBlock> button;
    public final BlockEntry<WallBlock> cobbledWall;

    public PlanetStoneBlockSet(RRRegistrate registrate, String name, String name_en_us, MapColor mapColor) {
        this.name = name;
        this.blockSetType = BlockSetType.register(new BlockSetType(id(name).toString()));

        this.cobbled = registrate.block(name + "_cobblestone", Block::new)
                .initialProperties(() -> Blocks.COBBLESTONE)
                .lang("Cobbled " + name_en_us + " Rock")
                .properties(properties -> properties.mapColor(mapColor))
                .blockstate(Models::randomRotated)
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .simpleItem()
                .register();
        this.cobbledSlab = registrate.block(name + "_cobblestone_slab", SlabBlock::new)
                .initialProperties(() -> Blocks.COBBLESTONE_SLAB)
                .lang("Cobbled " + name_en_us + " Rock Slab")
                .blockstate(this.slabBlockstate(this.cobbled))
                .tag(BlockTags.SLABS, BlockTags.MINEABLE_WITH_PICKAXE)
                .item()
                .model(this.slabItemModel(this.cobbled))
                .tag(ItemTags.SLABS)
                .build()
                .register();
        this.cobbledStair = registrate
                .block(name + "_cobblestone_stair", (p) -> new StairBlock(this.cobbled::getDefaultState, p))
                .initialProperties(() -> Blocks.STONE_STAIRS)
                .lang("Cobbled " + name_en_us + " Rock Stairs")
                .tag(BlockTags.STAIRS, BlockTags.MINEABLE_WITH_PICKAXE)
                .blockstate(this.stairBlockstate(this.cobbled))
                .item()
                .tag(ItemTags.STAIRS)
                .build()
                .register();
        this.normal = registrate.block(name + "_stone", Block::new)
                .initialProperties(() -> Blocks.STONE)
                .lang(name_en_us + " Rock")
                .properties(properties -> properties.mapColor(mapColor))
                .blockstate(Models::randomRotated)
                .tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .loot(LootTables.requiresSilkTouchElse(this.cobbled::asItem))
                .simpleItem()
                .register();
        this.slab = registrate.block(name + "_stone_slab", SlabBlock::new)
                .initialProperties(() -> Blocks.STONE_SLAB)
                .lang(name_en_us + " Rock Slab")
                .blockstate(this.slabBlockstate(this.normal))
                .tag(BlockTags.SLABS, BlockTags.MINEABLE_WITH_PICKAXE)
                .item()
                .model(this.slabItemModel(this.normal))
                .tag(ItemTags.SLABS)
                .build()
                .register();
        this.stair = registrate
                .block(name + "_stone_stair", (p) -> new StairBlock(this.normal::getDefaultState, p))
                .initialProperties(() -> Blocks.STONE_STAIRS)
                .lang(name_en_us + " Rock Stairs")
                .tag(BlockTags.STAIRS, BlockTags.MINEABLE_WITH_PICKAXE)
                .blockstate(this.stairBlockstate(this.normal))
                .item()
                .tag(ItemTags.STAIRS)
                .build()
                .register();
        this.button = registrate
                .block(name + "_stone_button", (p) -> new ButtonBlock(p, this.blockSetType, 30, false))
                .initialProperties(() -> Blocks.STONE_BUTTON)
                .lang(name_en_us + " Rock Button")
                .tag(BlockTags.BUTTONS, BlockTags.MINEABLE_WITH_PICKAXE)
                .blockstate(this.buttonBlockstate(this.normal))
                .item()
                .model(this.buttonItemModel(this.normal))
                .tag(ItemTags.BUTTONS)
                .build()
                .register();
        this.cobbledWall = registrate
                .block(name + "_cobblestone_wall", WallBlock::new)
                .initialProperties(() -> Blocks.COBBLESTONE_WALL)
                .lang("Cobbled " + name_en_us + " Wall")
                .tag(BlockTags.WALLS, BlockTags.MINEABLE_WITH_PICKAXE)
                .blockstate(this.wallBlockstate(this.cobbled))
                .item()
                .tag(ItemTags.WALLS)
                .model(this.wallItemModel(this.cobbled))
                .build()
                .register();

        registrate.addRecipes(this::addRecipes);
        registrate.addTagPrefix(() -> TagPrefix.oreTagPrefix(name, BlockTags.MINEABLE_WITH_PICKAXE)
                .langValue(name_en_us + " %s Ore")
                .registerOre(
                        () -> this.normal.orElse(Blocks.STONE).defaultBlockState(),
                        null,
                        BlockBehaviour.Properties.of()
                                .mapColor(mapColor)
                                .requiresCorrectToolForDrops()
                                .strength(3.0F, 3.0F),
                        RealRocket.id("block/" + name + "_stone")));

        registrate.addRawLang("tagprefix." + name, name_en_us + " %s Ore");
    }

    private NonNullBiConsumer<DataGenContext<Block, SlabBlock>, RegistrateBlockstateProvider> slabBlockstate(
                                                                                                             BlockEntry<Block> base) {
        return (ctx, prov) -> {
            ResourceLocation texture = prov.blockTexture(base.get());
            String name = ctx.getId().getPath();
            prov.slabBlock(
                    ctx.getEntry(),
                    prov.models().slab(name, texture, texture, texture),
                    prov.models().slabTop(name + "_top", texture, texture, texture),
                    prov.cubeAll(base.get()));
        };
    }

    private NonNullBiConsumer<DataGenContext<Item, BlockItem>, RegistrateItemModelProvider> slabItemModel(
                                                                                                          BlockEntry<Block> base) {
        return (ctx, prov) -> {
            ResourceLocation texture = base.getId().withPrefix("block/");
            String name = ctx.getId().getPath();
            prov.slab(name, texture, texture, texture);
        };
    }

    private NonNullBiConsumer<DataGenContext<Block, StairBlock>, RegistrateBlockstateProvider> stairBlockstate(
                                                                                                               BlockEntry<Block> base) {
        return (ctx, prov) -> prov.stairsBlock(
                ctx.getEntry(),
                prov.blockTexture(base.get()));
    }

    private NonNullBiConsumer<DataGenContext<Block, ButtonBlock>, RegistrateBlockstateProvider> buttonBlockstate(
                                                                                                                 BlockEntry<Block> base) {
        return (ctx, prov) -> prov.buttonBlock(
                ctx.getEntry(),
                prov.blockTexture(base.get()));
    }

    private NonNullBiConsumer<DataGenContext<Item, BlockItem>, RegistrateItemModelProvider> buttonItemModel(
                                                                                                            BlockEntry<Block> ignoredBase) {
        return (ctx, prov) -> prov.buttonInventory(
                ctx.getName(), this.normal.getId().withPrefix("block/"));
    }

    private NonNullBiConsumer<DataGenContext<Block, WallBlock>, RegistrateBlockstateProvider> wallBlockstate(
                                                                                                             BlockEntry<Block> base) {
        return (ctx, prov) -> prov.wallBlock(
                ctx.getEntry(),
                prov.blockTexture(base.get()));
    }

    private NonNullBiConsumer<DataGenContext<Item, BlockItem>, RegistrateItemModelProvider> wallItemModel(
                                                                                                          BlockEntry<Block> ignoredBase) {
        return (ctx, prov) -> prov.wallInventory(
                ctx.getName(), this.normal.getId().withPrefix("block/"));
    }

    private void addRecipes(Consumer<FinishedRecipe> consumer) {
        this.normalCobbledConversionRecipes(consumer);
        this.stonecuttingRecipes(consumer);
        this.craftingRecipes(consumer);

        // gt machine recipes
        StoneMachineRecipes.registerStoneTypeRecipes(consumer,
                new StoneTypeEntry.Builder(RealRocket.MOD_ID, this.name)
                        .stone(this.normal.asItem())
                        .slab(this.slab.asItem())
                        .stair(this.stair.asItem())
                        .button(this.button.asItem())
                        .build());
        StoneMachineRecipes.registerStoneTypeRecipes(consumer,
                new StoneTypeEntry.Builder(RealRocket.MOD_ID, this.name + "_cobblestone")
                        .stone(this.cobbled.asItem())
                        .slab(this.cobbledSlab.asItem())
                        .stair(this.cobbledStair.asItem())
                        .wall(this.cobbledWall.asItem())
                        .build());
    }

    private void normalCobbledConversionRecipes(Consumer<FinishedRecipe> consumer) {
        VanillaRecipeHelper.addSmeltingRecipe(consumer, id(this.name + "_cobblestone_smelting"), this.cobbled.asStack(),
                this.normal.asStack(), 0.0F);
        GTRecipeTypes.FORGE_HAMMER_RECIPES.recipeBuilder(id(this.name + "_cobble_forge_hammer"))
                .inputItems(this.normal.asItem())
                .outputItems(this.cobbled.asItem())
                .duration(12)
                .EUt(VH[ULV])
                .save(consumer);
        GTRecipeTypes.ROCK_BREAKER_RECIPES.recipeBuilder(id(this.name + "_stone_rock_breaker"))
                .notConsumable(this.normal.asItem())
                .outputItems(this.normal.asItem())
                .duration(16)
                .EUt(VA[HV])
                .addData("fluidA", "minecraft:lava")
                .addData("fluidB", "minecraft:water")
                .save(consumer);
        GTRecipeTypes.ROCK_BREAKER_RECIPES.recipeBuilder(id(this.name + "_cobblestone_rock_breaker"))
                .notConsumable(this.cobbled.asItem())
                .outputItems(this.cobbled.asItem())
                .duration(16)
                .EUt(VA[HV])
                .addData("fluidA", "minecraft:lava")
                .addData("fluidB", "minecraft:water")
                .save(consumer);
    }

    private void stonecuttingRecipes(Consumer<FinishedRecipe> consumer) {
        StonecuttingRecipeHelper.addStonecuttingRecipe(
                consumer,
                id(this.name + "_stone_slab_stonecutting"),
                Ingredient.of(this.normal.asItem()),
                this.slab.asStack(2));
        StonecuttingRecipeHelper.addStonecuttingRecipe(
                consumer,
                id(this.name + "_cobblestone_slab_stonecutting"),
                Ingredient.of(this.cobbled.asItem()),
                this.cobbledSlab.asStack(2));
        StonecuttingRecipeHelper.addStonecuttingRecipe(
                consumer,
                id(this.name + "_stone_stair_stonecutting"),
                Ingredient.of(this.normal.asItem()),
                this.stair.asStack());
        StonecuttingRecipeHelper.addStonecuttingRecipe(
                consumer,
                id(this.name + "_cobblestone_stair_stonecutting"),
                Ingredient.of(this.cobbled.asItem()),
                this.cobbledStair.asStack());
        StonecuttingRecipeHelper.addStonecuttingRecipe(
                consumer,
                id(this.name + "_cobblestone_wall_stonecutting"),
                Ingredient.of(this.cobbled.asItem()),
                this.cobbledWall.asStack());
    }

    private void craftingRecipes(Consumer<FinishedRecipe> consumer) {
        VanillaRecipeHelper.addShapedRecipe(consumer, id(this.name + "_stone_slab_crafting"),
                this.slab.asStack(6),
                "SSS",
                'S', this.normal.asItem());
        VanillaRecipeHelper.addShapedRecipe(consumer, id(this.name + "_cobblestone_slab_crafting"),
                this.cobbledSlab.asStack(6),
                "SSS",
                'S', this.cobbled.asItem());
        VanillaRecipeHelper.addShapedRecipe(consumer, id(this.name + "_stone_stair_crafting"),
                this.stair.asStack(4),
                "S  ", "SS ", "SSS",
                'S', this.normal.asItem());
        VanillaRecipeHelper.addShapedRecipe(consumer, id(this.name + "_cobblestone_stair_crafting"),
                this.cobbledStair.asStack(4),
                "S  ", "SS ", "SSS",
                'S', this.cobbled.asItem());
        VanillaRecipeHelper.addShapedRecipe(consumer, id(this.name + "_cobblestone_stair_crafting"),
                this.cobbledStair.asStack(4),
                "S  ", "SS ", "SSS",
                'S', this.cobbled.asItem());
        VanillaRecipeHelper.addShapedRecipe(consumer, id(this.name + "_cobblestone_wall_crafting"),
                this.cobbledWall.asStack(6),
                "SSS", "SSS",
                'S', this.cobbled.asItem());
        VanillaRecipeHelper.addShapedRecipe(consumer, id(this.name + "_stone_button_crafting"),
                this.button.asStack(),
                "S",
                'S', this.normal.asItem());
    }
}
