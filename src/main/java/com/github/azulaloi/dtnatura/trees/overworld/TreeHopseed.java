package com.github.azulaloi.dtnatura.trees.overworld;

import com.ferreusveritas.dynamictrees.blocks.BlockDynamicSapling;
import com.ferreusveritas.dynamictrees.items.Seed;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.github.azulaloi.dtnatura.items.ItemBloodSeed;
import com.github.azulaloi.dtnatura.items.ItemMapleSeed;
import com.github.azulaloi.dtnatura.items.SeedInverse;
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

public class TreeHopseed extends TreeFamily {
    public class SpeciesHopseed extends Species {

        SpeciesHopseed (TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModBlocks.hopseedLeavesProperties);

            setBasicGrowingParameters(0.2f, 10.0f, upProbability, 4, 9f);

            setDynamicSapling(new BlockDynamicSapling("hopseedsapling").getDefaultState());

            envFactor(BiomeDictionary.Type.MOUNTAIN, 1.5f);

            generateSeed();
            setupStandardSeedDropping();
        }

        @Override
        public boolean isBiomePerfect(Biome biome) { return isOneOfBiomes(biome, Biomes.PLAINS); }

    }

    public TreeHopseed() {
        super(new ResourceLocation(DynamicTreesNatura.MODID, "hopseed"));

        IBlockState primLog = NaturaOverworld.overworldLog2.getDefaultState().withProperty(BlockOverworldLog2.TYPE, BlockOverworldLog2.LogType.HOPSEED);

        setPrimitiveLog(primLog);

        ModBlocks.hopseedLeavesProperties.setTree(this);
    }

    @Override
    public ItemStack getPrimitiveLogItemStack(int qty) {
        return new ItemStack(NaturaOverworld.overworldLog2, MathHelper.clamp(qty, 0, 64), 2);
    }

    @Override
    public void createSpecies() {
        setCommonSpecies(new SpeciesHopseed(this));
    }

    @Override
    public List<Block> getRegisterableBlocks(List<Block> blockList) {
        blockList.add(getCommonSpecies().getDynamicSapling().getBlock());
        return super.getRegisterableBlocks(blockList);
    }
}
