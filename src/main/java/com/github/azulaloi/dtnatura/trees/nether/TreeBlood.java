package com.github.azulaloi.dtnatura.trees.nether;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.blocks.BlockBranch;
import com.ferreusveritas.dynamictrees.blocks.BlockRooty;
import com.ferreusveritas.dynamictrees.systems.GrowSignal;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.github.azulaloi.dtnatura.items.SeedInverse;
import com.progwml6.natura.nether.NaturaNether;
import com.github.azulaloi.dtnatura.DynamicTreesNatura;
import com.github.azulaloi.dtnatura.ModBlocks;
import com.github.azulaloi.dtnatura.blocks.BlockDynamicSaplingInverse;
import com.github.azulaloi.dtnatura.items.ItemBloodSeed;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.List;

public class TreeBlood extends TreeFamily{

    public class SpeciesBlood extends Species {

        SpeciesBlood (TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModBlocks.bloodwoodLeavesProperties);

            setBasicGrowingParameters(0.3f, 18.0f, 5, 5, 8f);

            setDynamicSapling(new BlockDynamicSaplingInverse("bloodsapling").getDefaultState());

            envFactor(BiomeDictionary.Type.NETHER, 1.5f);

            generateSeed();
            setupStandardSeedDropping();

            clearAcceptableSoils();
            addAcceptableSoil(Blocks.NETHERRACK);
        }

        @Override
        public boolean isBiomePerfect(Biome biome) { return isOneOfBiomes(biome, Biomes.HELL); }

        @Override
        public BlockRooty getRootyBlock(){
            return ModBlocks.blockRootyInverseNetherrack;
        }

        public ItemStack generateSeed() {
            SeedInverse seed = new ItemBloodSeed(getRegistryName().getResourcePath() + "seed");
            return setSeedStack(new ItemStack(seed));
        }

        public boolean plantSapling(World world, BlockPos pos) {

            if(world.getBlockState(pos).getBlock().isReplaceable(world, pos) && BlockDynamicSaplingInverse.canInverseSaplingStay(world, this, pos)) {
                world.setBlockState(pos, getDynamicSapling());
                //System.out.println("plant inverse : " + pos);
                return true;
            }

            //System.out.println("failed inverse : " + pos);
            return false;
        }

        @Override
        public EnumFacing selectNewDirection(World world, BlockPos pos, BlockBranch branch, GrowSignal signal) {
            EnumFacing originDir = signal.dir.getOpposite();

            //prevent branches on the ground
            if(signal.numSteps + 1 <= getLowestBranchHeight(world, signal.rootPos)) {
                return EnumFacing.DOWN;
            }

            int probMap[] = new int[6];//6 directions possible DUNSWE

            //Probability taking direction into account
            probMap[EnumFacing.DOWN.ordinal()] = signal.dir != EnumFacing.DOWN ? getUpProbability(): 0;//Favor up
            probMap[signal.dir.ordinal()] += getReinfTravel(); //Favor current direction

            //Create probability map for direction change
            for(EnumFacing dir: EnumFacing.VALUES) {
                if(!dir.equals(originDir)) {
                    BlockPos deltaPos = pos.offset(dir);
                    //Check probability for surrounding blocks
                    //Typically Air:1, Leaves:2, Branches: 2+r
                    IBlockState deltaBlockState = world.getBlockState(deltaPos);
                    probMap[dir.getIndex()] += TreeHelper.getTreePart(deltaBlockState).probabilityForBlock(deltaBlockState, world, deltaPos, branch);
                }
            }

            //Do custom stuff or override probability map for various species
            probMap = customDirectionManipulation(world, pos, branch.getRadius(world.getBlockState(pos)), signal, probMap);

            //Select a direction from the probability map
            int choice = com.ferreusveritas.dynamictrees.util.MathHelper.selectRandomFromDistribution(signal.rand, probMap);//Select a direction from the probability map
            return newDirectionSelected(EnumFacing.getFront(choice != -1 ? choice : 0), signal);//Default to down if things are screwy
        }


        @Override
        protected int[] customDirectionManipulation(World world, BlockPos pos, int radius, GrowSignal signal, int probMap[]) {
            EnumFacing originDir = signal.dir.getOpposite();

            //Alter probability map for direction change
            //probMap[0] = signal.isInTrunk() ? getUpProbability() : probMap[0]; //
            //probMap[1] = 0;
            //probMap[2] = probMap[3] = probMap[4] = probMap[5] =
             //       !signal.isInTrunk() || (signal.isInTrunk() && signal.numSteps % 2 == 1 && radius > 1) ? 2 : 0;

            //if (signal.dir != EnumFacing.DOWN) probMap[signal.dir.ordinal()] = 0;//Disable the current direction, unless that direction is up
            probMap[originDir.ordinal()] = 0;//Disable the direction we came from
            probMap[signal.dir.ordinal()] += signal.isInTrunk() ? 0 : signal.numTurns == 1 ? 2 : 1; //Favor current dir over new dir
            return probMap;
        }

        @Override
        protected EnumFacing newDirectionSelected(EnumFacing newDir, GrowSignal signal) {
            if(signal.isInTrunk() && newDir != EnumFacing.DOWN){
                signal.energy *= 1.5f;
            }
            return newDir;
        }
    }

    public TreeBlood() {
        super(new ResourceLocation(DynamicTreesNatura.MODID, "blood"));

        IBlockState primLog = NaturaNether.netherLog2.getDefaultState();

        setPrimitiveLog(primLog);

        ModBlocks.bloodwoodLeavesProperties.setTree(this);
    }

    @Override
    public ItemStack getPrimitiveLogItemStack(int qty) {
        return new ItemStack(NaturaNether.netherLog2, MathHelper.clamp(qty, 0, 64), 0);
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
