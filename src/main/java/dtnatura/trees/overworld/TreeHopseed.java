package dtnatura.trees.overworld;

import com.ferreusveritas.dynamictrees.blocks.BlockDynamicSapling;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.progwml6.natura.overworld.block.logs.BlockOverworldLog2;
import dtnatura.DynamicTreesNatura;
import dtnatura.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.List;

public class TreeHopseed extends TreeFamily {
    public class SpeciesHopseed extends Species {

        SpeciesHopseed (TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModBlocks.hopseedLeavesProperties);

            setBasicGrowingParameters(0.3f, 12.0f, upProbability, lowestBranchHeight, 8f);

            setDynamicSapling(new BlockDynamicSapling("hopseedsapling").getDefaultState());

            envFactor(BiomeDictionary.Type.PLAINS, 1.5f);

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
    public void createSpecies() {
        setCommonSpecies(new SpeciesHopseed(this));
    }

    @Override
    public List<Block> getRegisterableBlocks(List<Block> blockList) {
        blockList.add(getCommonSpecies().getDynamicSapling().getBlock());
        return super.getRegisterableBlocks(blockList);
    }
}
