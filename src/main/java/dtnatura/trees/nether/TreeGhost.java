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
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.List;

public class TreeGhost extends TreeFamily {

    public class SpeciesGhost extends Species {

        SpeciesGhost (TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModBlocks.ghostwoodLeavesProperties);

            setBasicGrowingParameters(0.3f, 12.0f, 1, lowestBranchHeight, 8.8f);

            setDynamicSapling(new BlockDynamicSapling("ghostsapling").getDefaultState());

            envFactor(BiomeDictionary.Type.NETHER, 1.5f);

            generateSeed();
            setupStandardSeedDropping();
        }

        @Override
        public boolean isBiomePerfect(Biome biome) {
            return isOneOfBiomes(biome, Biomes.HELL);
        }
    }

    public TreeGhost() {
        super(new ResourceLocation(DynamicTreesNatura.MODID, "ghost"));

        IBlockState primLog = NaturaNether.netherLog.getDefaultState().withProperty(BlockNetherLog.TYPE, BlockNetherLog.LogType.GHOSTWOOD);

        setPrimitiveLog(primLog);

        ModBlocks.ghostwoodLeavesProperties.setTree(this);
    }

    @Override
    public ItemStack getPrimitiveLogItemStack(int qty) {
        return new ItemStack(NaturaNether.netherLog, MathHelper.clamp(qty, 0, 64), 0);
    }

    @Override
    public void createSpecies() {
        setCommonSpecies(new SpeciesGhost(this));
    }

    @Override
    public List<Block> getRegisterableBlocks(List<Block> blockList) {
        blockList.add(getCommonSpecies().getDynamicSapling().getBlock());
        return super.getRegisterableBlocks(blockList);
    }

}
