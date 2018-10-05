package dtnatura.trees.nether;

import com.ferreusveritas.dynamictrees.blocks.BlockDynamicSapling;
import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreator;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.progwml6.natura.library.NaturaRegistry;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.nether.block.leaves.BlockNetherLeaves2;
import com.progwml6.natura.nether.block.logs.BlockNetherLog;
import com.progwml6.natura.shared.NaturaCommons;
import com.progwml6.natura.tools.NaturaTools;
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

public class TreeDark extends TreeFamily {

    public class SpeciesDark extends Species {

        SpeciesDark(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModBlocks.darkwoodLeavesProperties);

            setBasicGrowingParameters(0.3f, 12.0f, upProbability, lowestBranchHeight, 8.0f);

            setDynamicSapling(new BlockDynamicSapling("darksapling").getDefaultState());

            envFactor(BiomeDictionary.Type.NETHER, 1.5f);

            generateSeed();
            setupStandardSeedDropping();

            //addDropCreator(new DropCreator(NaturaCommons.potashApple));
        }

        @Override
        public boolean isBiomePerfect(Biome biome) {
            return isOneOfBiomes(biome, Biomes.HELL);
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
