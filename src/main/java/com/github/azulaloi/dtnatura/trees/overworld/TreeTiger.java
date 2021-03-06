package com.github.azulaloi.dtnatura.trees.overworld;

import com.ferreusveritas.dynamictrees.blocks.BlockDynamicSapling;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.overworld.block.logs.BlockOverworldLog;
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

public class TreeTiger extends TreeFamily {
    public class SpeciesTiger extends Species {

        SpeciesTiger (TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModBlocks.tigerLeavesProperties);

            setBasicGrowingParameters(0.2f, 9.0f, upProbability, lowestBranchHeight, 6f);

            setDynamicSapling(new BlockDynamicSapling("tigersapling").getDefaultState());

            envFactor(BiomeDictionary.Type.FOREST, 1.3f);

            generateSeed();
            setupStandardSeedDropping();
        }

        @Override
        public boolean isBiomePerfect(Biome biome) { return isOneOfBiomes(biome, Biomes.PLAINS); }
    }

    public TreeTiger() {
        super(new ResourceLocation(DynamicTreesNatura.MODID, "tiger"));

        IBlockState primLog = NaturaOverworld.overworldLog.getDefaultState().withProperty(BlockOverworldLog.TYPE, BlockOverworldLog.LogType.TIGER);

        setPrimitiveLog(primLog);

        ModBlocks.tigerLeavesProperties.setTree(this);
    }

    @Override
    public ItemStack getPrimitiveLogItemStack(int qty) {
        return new ItemStack(NaturaOverworld.overworldLog, MathHelper.clamp(qty, 0, 64), 3);
    }


    @Override
    public void createSpecies() {
        setCommonSpecies(new SpeciesTiger(this));
    }

    @Override
    public List<Block> getRegisterableBlocks(List<Block> blockList) {
        blockList.add(getCommonSpecies().getDynamicSapling().getBlock());
        return super.getRegisterableBlocks(blockList);
    }
}
