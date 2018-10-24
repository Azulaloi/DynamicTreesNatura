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

public class TreeSilverbell extends TreeFamily {
    public class SpeciesSilverbell extends Species {

        SpeciesSilverbell (TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModBlocks.silverbellLeavesProperties);

            setBasicGrowingParameters(0.5f, 7.0f, upProbability, lowestBranchHeight, 3f);

            setDynamicSapling(new BlockDynamicSapling("silverbellsapling").getDefaultState());

            envFactor(BiomeDictionary.Type.FOREST, 1.8f);

            generateSeed();
            setupStandardSeedDropping();
        }

        @Override
        public boolean isBiomePerfect(Biome biome) { return isOneOfBiomes(biome, Biomes.PLAINS); }
    }

    public TreeSilverbell() {
        super(new ResourceLocation(DynamicTreesNatura.MODID, "silverbell"));

        IBlockState primLog = NaturaOverworld.overworldLog.getDefaultState().withProperty(BlockOverworldLog.TYPE, BlockOverworldLog.LogType.SILVERBELL);

        setPrimitiveLog(primLog);

        ModBlocks.silverbellLeavesProperties.setTree(this);
    }

    @Override
    public ItemStack getPrimitiveLogItemStack(int qty) {
        return new ItemStack(NaturaOverworld.overworldLog, MathHelper.clamp(qty, 0, 64), 1);
    }

    @Override
    public void createSpecies() {
        setCommonSpecies(new SpeciesSilverbell(this));
    }

    @Override
    public List<Block> getRegisterableBlocks(List<Block> blockList) {
        blockList.add(getCommonSpecies().getDynamicSapling().getBlock());
        return super.getRegisterableBlocks(blockList);
    }
}
