package dtnatura.trees.nether;

import com.ferreusveritas.dynamictrees.blocks.BlockDynamicSapling;
import com.ferreusveritas.dynamictrees.blocks.BlockRooty;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.nether.block.logs.BlockNetherLog;
import dtnatura.DynamicTreesNatura;
import dtnatura.ModBlocks;
import dtnatura.blocks.BlockDynamicSaplingInverse;
import dtnatura.items.SeedInverse;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.Resource;
import java.util.List;

public class TreeBlood extends TreeFamily{

    public class SpeciesBlood extends Species {

        SpeciesBlood (TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModBlocks.bloodwoodLeavesProperties);

            setBasicGrowingParameters(0.3f, 12.0f, -2, -16, 8f);

            setDynamicSapling(new BlockDynamicSaplingInverse("bloodsapling").getDefaultState());

            envFactor(BiomeDictionary.Type.NETHER, 1.5f);

            generateSeed();
            setupStandardSeedDropping();
        }

        @Override
        public boolean isBiomePerfect(Biome biome) { return isOneOfBiomes(biome, Biomes.HELL); }

        @Override
        public BlockRooty getRootyBlock(){
            return ModBlocks.blockRootyInverse;
        }

        public ItemStack generateSeed() {
            SeedInverse seed = new SeedInverse(getRegistryName().getResourcePath() + "seed");
            return setSeedStack(new ItemStack(seed));
        }

        public boolean plantSapling(World world, BlockPos pos) {

            if(world.getBlockState(pos).getBlock().isReplaceable(world, pos) && BlockDynamicSaplingInverse.canInverseSaplingStay(world, this, pos)) {
                world.setBlockState(pos, getDynamicSapling());
                System.out.println("plant inverse : " + pos);
                return true;
            }

            System.out.println("failed inverse : " + pos);
            return false;
        }
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
