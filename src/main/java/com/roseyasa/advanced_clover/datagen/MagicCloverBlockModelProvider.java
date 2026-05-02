package com.roseyasa.advanced_clover.datagen;

import com.roseyasa.advanced_clover.Main;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.MultiPartBlockStateBuilder;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static com.roseyasa.advanced_clover.registry.BlockRegister.*;

public class MagicCloverBlockModelProvider extends BlockStateProvider {
    public MagicCloverBlockModelProvider(PackOutput gen, ExistingFileHelper helper) {
        super(gen, Main.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        // 生成模型
        // 三叶草
        for (int i = 1; i <= 4; i++) {
            models().withExistingParent("block/three_leaf_clover_" + i, mcLoc("block/flowerbed_" + i))
                    .texture("flowerbed", modLoc("block/three_leaf_clover"))
                    .texture("stem", modLoc("block/clover_stem"))
                    .renderType("cutout");
        }

        // 四叶草
        for (int i = 1; i <= 4; i++) {
            models().withExistingParent("block/four_leaf_clover_" + i, mcLoc("block/flowerbed_" + i))
                    .texture("flowerbed", modLoc("block/four_leaf_clover"))
                    .texture("stem", modLoc("block/clover_stem"))
                    .renderType("cutout");
        }

        // 生成 blockstates
        // 三叶草
        MultiPartBlockStateBuilder threeLeafBuilder = getMultipartBuilder(BLOCK_THREE_LEAF_CLOVER.get());
        for (int modelNum = 1; modelNum <= 4; modelNum++) {
            for (Direction dir : Direction.Plane.HORIZONTAL) {

                MultiPartBlockStateBuilder.PartBuilder p = threeLeafBuilder.part()
                        .modelFile(models().getExistingFile(modLoc("block/three_leaf_clover_" + modelNum)))
                        .rotationY((int) (dir.toYRot() + 180) % 360)
                        .addModel()
                        .condition(BlockStateProperties.HORIZONTAL_FACING, dir);
                switch (modelNum) {
                    case 1:
                        p.condition(BlockStateProperties.FLOWER_AMOUNT, 1, 2, 3, 4).end();
                        break;
                    case 2:
                        p.condition(BlockStateProperties.FLOWER_AMOUNT, 2, 3, 4).end();
                        break;
                    case 3:
                        p.condition(BlockStateProperties.FLOWER_AMOUNT, 3, 4).end();
                        break;
                    case 4:
                        p.condition(BlockStateProperties.FLOWER_AMOUNT, 4).end();
                        break;
                }

            }
        }


        // 四叶草
        MultiPartBlockStateBuilder fourLeafBuilder = getMultipartBuilder(BLOCK_FOUR_LEAF_CLOVER.get());
        for (int modelNum = 1; modelNum <= 4; modelNum++) {
            for (Direction dir : Direction.Plane.HORIZONTAL) {
                MultiPartBlockStateBuilder.PartBuilder p = fourLeafBuilder.part()
                        .modelFile(models().getExistingFile(modLoc("block/four_leaf_clover_" + modelNum)))
                        .rotationY((int) (dir.toYRot() + 180) % 360)
                        .addModel()
                        .condition(BlockStateProperties.HORIZONTAL_FACING, dir);
                switch (modelNum) {
                    case 1:
                        p.condition(BlockStateProperties.FLOWER_AMOUNT, 1, 2, 3, 4).end();
                        break;
                    case 2:
                        p.condition(BlockStateProperties.FLOWER_AMOUNT, 2, 3, 4).end();
                        break;
                    case 3:
                        p.condition(BlockStateProperties.FLOWER_AMOUNT, 3, 4).end();
                        break;
                    case 4:
                        p.condition(BlockStateProperties.FLOWER_AMOUNT, 4).end();
                        break;
                }
            }
        }
    }


}