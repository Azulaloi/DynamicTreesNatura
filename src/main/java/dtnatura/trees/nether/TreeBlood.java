package dtnatura.trees.nether;

import com.ferreusveritas.dynamictrees.blocks.BlockDynamicSapling;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.nether.block.logs.BlockNetherLog;
import dtnatura.DynamicTreesNatura;
import dtnatura.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.Resource;
import java.util.List;

public class TreeBlood extends TreeFamily{

    public class SpeciesBlood extends Species {

        SpeciesBlood (TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModBlocks.bloodwoodLeavesProperties);

            setBasicGrowingParameters(0.3f, 12.0f, upProbability, lowestBranchHeight, 8f);

            setDynamicSapling(new BlockDynamicSapling("bloodsapling").getDefaultState());

            envFactor(BiomeDictionary.Type.NETHER, 1.5f);

            generateSeed();
            setupStandardSeedDropping();
        }

        @Override
        public boolean isBiomePerfect(Biome biome) { return isOneOfBiomes(biome, Biomes.HELL); }
    }

    public TreeBlood() {
        super(new ResourceLocation(DynamicTreesNatura.MODID, "blood"));

        IBlockState primLog = NaturaNether.netherLog2.getDefaultState();

        setPrimitiveLog(primLog);

        ModBlocks.bloodwoodLeavesProperties.setTree(this);
    }

    @Override
    public void createSpecies() {
        setCommonSpecies(new SpeciesBlood(this));
    }

    @Override
    public List<Block> getRegisterableBlocks(List<Block> blockList) {
        blockList.add(getCommonSpecies().getDynamicSapling().getBlock());
        return super.getRegisterableBlocks(blockList);
    }
}
