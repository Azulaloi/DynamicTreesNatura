package dtnatura.blocks;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicSapling;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.sun.istack.internal.NotNull;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockDynamicSaplingInverse extends BlockDynamicSapling {

    public BlockDynamicSaplingInverse(String name) {
        super(name);
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        return canInverseSaplingStay(world, this.getSpecies(world, pos, state), pos);
    }

    public static boolean canInverseSaplingStay(World world, Species species, BlockPos pos) {
        for(EnumFacing dir: EnumFacing.HORIZONTALS) {
            IBlockState blockState = world.getBlockState(pos.offset(dir));
            Block block = blockState.getBlock();
            if(TreeHelper.isBranch(block) || block instanceof BlockDynamicSapling) {
                return false;
            }
        }

        return world.isAirBlock(pos.down()) && species.isAcceptableSoil(world, pos.up(), world.getBlockState(pos.up()));
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
        Species species = getSpecies(world, pos, state);
        if(canBlockStay(world, pos, state)) {
            TreeFamily family = species.getFamily();
            if(world.isAirBlock(pos.down()) && species.isAcceptableSoil(world, pos.up(), world.getBlockState(pos.up()))) {
                family.getDynamicBranch().setRadius(world, pos, (int)family.getPrimaryThickness(), null);
                world.setBlockState(pos.down(), species.getLeavesProperties().getDynamicLeavesState());
                species.placeRootyDirtBlock(world, pos.up(), 15);
            }

        } else {
            dropBlock(world, species, state, pos);
        }
    }

}
