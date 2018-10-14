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

public class TreeFuse extends TreeFamily {

    public class SpeciesFuse extends Species {

        SpeciesFuse (TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModBlocks.fusewoodLeavesProperties);

            setBasicGrowingParameters(0.3f, 12.0f, upProbability, lowestBranchHeight, 8f);

            setDynamicSapling(new BlockDynamicSapling("fusesapling").getDefaultState());

            envFactor(BiomeDictionary.Type.NETHER, 1.5f);

            generateSeed();
            setupStandardSeedDropping();

            clearAcceptableSoils();
            addAcceptableSoil(Blocks.NETHERRACK);
        }

        @Override
        public boolean isBiomePerfect(Biome biome) { return isOneOfBiomes(biome, Biomes.HELL); }

        @Override
        public BlockRooty getRootyBlock() {
            return ModBlocks.blockRootyNetherrack;
        }
    }

    public TreeFuse() {
        super(new ResourceLocation(DynamicTreesNatura.MODID, "fuse"));

        IBlockState primLog = NaturaNether.netherLog.getDefaultState().withProperty(BlockNetherLog.TYPE, BlockNetherLog.LogType.FUSEWOOD);

        setPrimitiveLog(primLog);

        ModBlocks.fusewoodLeavesProperties.setTree(this);
    }

    @Override
    public ItemStack getPrimitiveLogItemStack(int qty) {
        return new ItemStack(NaturaNether.netherLog, MathHelper.clamp(qty, 0, 64), 2);
    }


    @Override
    public void createSpecies() {
        setCommonSpecies(new SpeciesFuse(this));
    }

    @Override
    public List<Block> getRegisterableBlocks(List<Block> blockList) {
        blockList.add(getCommonSpecies().getDynamicSapling().getBlock());
        return super.getRegisterableBlocks(blockList);
    }
}
