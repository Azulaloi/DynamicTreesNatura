package com.github.azulaloi.dtnatura.trees.nether;

import com.ferreusveritas.dynamictrees.blocks.BlockDynamicSapling;
import com.ferreusveritas.dynamictrees.blocks.BlockRooty;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.nether.block.logs.BlockNetherLog;
import com.github.azulaloi.dtnatura.DynamicTreesNatura;
import com.github.azulaloi.dtnatura.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.List;

public class TreeDark extends TreeFamily {

    public class SpeciesDark extends Species {

        SpeciesDark(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModBlocks.darkwoodLeavesProperties);

            setBasicGrowingParameters(0.1f, 7.0f, upProbability, lowestBranchHeight, 4.0f);

            setDynamicSapling(new BlockDynamicSapling("darksapling").getDefaultState());

            envFactor(BiomeDictionary.Type.NETHER, 1.5f);

            generateSeed();
            setupStandardSeedDropping();

            //addDropCreator(new DropCreator(NaturaCommons.potashApple));

            clearAcceptableSoils();
            addAcceptableSoil(Blocks.NETHERRACK);
        }

        @Override
        public boolean isBiomePerfect(Biome biome) {
            return isOneOfBiomes(biome, Biomes.HELL);
        }

        @Override
        public BlockRooty getRootyBlock() {
            return ModBlocks.blockRootyNetherrack;
        }
    }

    public TreeDark() {
        super(new ResourceLocation(DynamicTreesNatura.MODID, "dark"));

        IBlockState primLog = NaturaNether.netherLog.getDefaultState().withProperty(BlockNetherLog.TYPE, BlockNetherLog.LogType.DARKWOOD);

        setPrimitiveLog(primLog);

        ModBlocks.darkwoodLeavesProperties.setTree(this);

    }

    @Override
    public ItemStack getPrimitiveLogItemStack(int qty) {
        return new ItemStack(NaturaNether.netherLog, MathHelper.clamp(qty, 0, 64), 1);
    }

    @Override
    public void createSpecies() {
        setCommonSpecies(new SpeciesDark(this));
    }

    @Override
    public List<Block> getRegisterableBlocks(List<Block> blockList) {
        blockList.add(getCommonSpecies().getDynamicSapling().getBlock());
        return super.getRegisterableBlocks(blockList);
    }
}
