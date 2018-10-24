package com.github.azulaloi.dtnatura.trees.overworld;

import com.ferreusveritas.dynamictrees.blocks.BlockDynamicSapling;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.overworld.block.logs.BlockOverworldLog2;
import com.github.azulaloi.dtnatura.DynamicTreesNatura;
import com.github.azulaloi.dtnatura.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.List;

public class TreeWillow extends TreeFamily {
    public class SpeciesWillow extends Species {

        SpeciesWillow (TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModBlocks.willowLeavesProperties);

            setBasicGrowingParameters(0.3f, 14.0f, upProbability, lowestBranchHeight, 6f);

            setDynamicSapling(new BlockDynamicSapling("willowsapling").getDefaultState());

            envFactor(BiomeDictionary.Type.SWAMP, 1.9f);

            generateSeed();
            setupStandardSeedDropping();
        }

        @Override
        public boolean isBiomePerfect(Biome biome) { return isOneOfBiomes(biome, Biomes.PLAINS); }
    }

    public TreeWillow() {
        super(new ResourceLocation(DynamicTreesNatura.MODID, "willow"));

        IBlockState primLog = NaturaOverworld.overworldLog2.getDefaultState().withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.WILLOW);

        setPrimitiveLog(primLog);

        ModBlocks.willowLeavesProperties.setTree(this);
    }

    @Override
    public ItemStack getPrimitiveLogItemStack(int qty) {
        return new ItemStack(NaturaOverworld.overworldLog2, MathHelper.clamp(qty, 0, 64), 0);
    }


    @Override
    public void createSpecies() {
        setCommonSpecies(new SpeciesWillow(this));
    }

    @Override
    public List<Block> getRegisterableBlocks(List<Block> blockList) {
        blockList.add(getCommonSpecies().getDynamicSapling().getBlock());
        return super.getRegisterableBlocks(blockList);
    }
}
