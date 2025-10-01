package bnjmn21.realrocket.util;

import com.gregtechceu.gtceu.api.block.IMachineBlock;
import com.gregtechceu.gtceu.api.pattern.*;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public interface PatternBuilder {
    PatternBuilder aisle(String... aisle);
    default PatternBuilder repeatedAisle(int repetitions, String... aisle) {
        for (int i = 0; i < repetitions; i++) {
            aisle(aisle);
        }
        return this;
    }
    PatternBuilder where(char symbol, TraceabilityPredicate blockMatcher, BlockState shapeInfo);

    default PatternBuilder where(char symbol, TraceabilityPredicate blockMatcher, Block shapeInfo) {
        where(symbol, blockMatcher, shapeInfo.defaultBlockState());
        return this;
    }

    default PatternBuilder where(String symbol, TraceabilityPredicate blockMatcher, BlockState shapeInfo) {
        where(symbol.charAt(0), blockMatcher, shapeInfo);
        return this;
    }

    default PatternBuilder where(String symbol, TraceabilityPredicate blockMatcher, Block shapeInfo) {
        where(symbol.charAt(0), blockMatcher, shapeInfo.defaultBlockState());
        return this;
    }

    default PatternBuilder where(String symbol, Block block) {
        where(symbol, Predicates.blocks(block), block.defaultBlockState());
        return this;
    }

    default PatternBuilder where(String symbol, TraceabilityPredicate blockMatcher, IMachineBlock block, Direction facing) {
        where(symbol, blockMatcher, block.self().defaultBlockState().setValue(block.getRotationState().property, facing));
        return this;
    }

    default PatternBuilder where(String symbol, TagKey<Block> blockMatcher, Block shapeInfo) {
        where(symbol, Predicates.blockTag(blockMatcher), shapeInfo.defaultBlockState());
        return this;
    }

    class BlockPatternBuilder implements PatternBuilder {
        FactoryBlockPattern inner;

        private BlockPatternBuilder(FactoryBlockPattern inner) {
            this.inner = inner;
        }

        public static BlockPattern create(Consumer<PatternBuilder> build) {
            BlockPatternBuilder builder = new BlockPatternBuilder(FactoryBlockPattern.start());
            build.accept(builder);
            return builder.inner.build();
        }

        @Override
        public PatternBuilder aisle(String... aisle) {
            inner.aisle(aisle);
            return this;
        }

        @Override
        public PatternBuilder where(char symbol, TraceabilityPredicate blockMatcher, BlockState shapeInfo) {
            inner.where(symbol, blockMatcher);
            return this;
        }
    }

    class ShapeInfoBuilder implements PatternBuilder {
        MultiblockShapeInfo.ShapeInfoBuilder inner;

        public ShapeInfoBuilder(MultiblockShapeInfo.ShapeInfoBuilder inner) {
            this.inner = inner;
        }

        public static MultiblockShapeInfo create(Consumer<PatternBuilder> build) {
            ShapeInfoBuilder builder = new ShapeInfoBuilder(MultiblockShapeInfo.builder());
            build.accept(builder);
            return builder.inner.build();
        }

        @Override
        public PatternBuilder aisle(String... aisle) {
            inner.aisle(aisle);
            return this;
        }

        @Override
        public PatternBuilder where(char symbol, TraceabilityPredicate blockMatcher, BlockState shapeInfo) {
            inner.where(symbol, shapeInfo);
            return this;
        }
    }
}
