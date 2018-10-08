package com.github.azulaloi.dtnatura.items;

import com.ferreusveritas.dynamictrees.ModConfigs;
import com.ferreusveritas.dynamictrees.items.Seed;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.util.SafeChunkBounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SeedInverse extends Seed {

    public SeedInverse(String name) {
        super(name);
    }

    @Override
    public boolean shouldPlant(World world, BlockPos pos, ItemStack seedStack) {

        if(hasForcePlant(seedStack)) {
            return true;
        }

        float plantChance = getSpecies(seedStack).biomeSuitability(world, pos) * ModConfigs.seedPlantRate;
        float accum = 1.0f;
        int count = seedStack.getCount();
        while(count-- > 0) {
            accum *= 1.0f - plantChance;
        }
        plantChance = 1.0f - accum;

        return plantChance > world.rand.nextFloat();
    }

    @Override
    public EnumActionResult onItemUsePlantSeed(EntityPlayer player, World world, BlockPos pos, EnumHand hand, ItemStack seedStack, EnumFacing facing, float hitX, float hitY, float hitZ) {

        if (facing == EnumFacing.DOWN) {//Ensure this seed is only used on the top side of a block
            if (player.canPlayerEdit(pos, facing, seedStack) && player.canPlayerEdit(pos.up(), facing, seedStack)) {//Ensure permissions to edit block
                if(doPlanting(world, pos.down(), player, seedStack)) {
                    seedStack.shrink(1);
                    System.out.println("InvSeed planted : " + pos.down());
                    return EnumActionResult.SUCCESS;
                }
            }
        }

        System.out.println("InvSeed failed : " + pos.down());
        return EnumActionResult.PASS;
    }

    @Override
    public boolean doPlanting(World world, BlockPos pos, EntityPlayer planter, ItemStack seedStack) {
        Species species = getSpecies(seedStack);
        if(species.plantSapling(world, pos)) {//Do the planting
            String joCode = getCode(seedStack);
            if(!joCode.isEmpty()) {
                world.setBlockToAir(pos);//Remove the newly created dynamic sapling
                species.getJoCode(joCode).setCareful(true).generate(world, species, pos.down(), world.getBiome(pos), planter != null ? planter.getHorizontalFacing() : EnumFacing.NORTH, 8, SafeChunkBounds.ANY);
            }
            return true;
        }
        return false;
    }
}
