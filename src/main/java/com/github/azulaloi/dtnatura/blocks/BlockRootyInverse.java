package com.github.azulaloi.dtnatura.blocks;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.blocks.BlockBranch;
import com.ferreusveritas.dynamictrees.blocks.BlockRooty;
import com.ferreusveritas.dynamictrees.entities.EntityFallingTree;
import com.ferreusveritas.dynamictrees.util.BranchDestructionData;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;

public class BlockRootyInverse extends BlockRooty {

    public BlockRootyInverse(String name, boolean isTileEntity, Material material) {
        super(name, material, isTileEntity);

    }

    @Override
    public EnumFacing getTrunkDirection(IBlockAccess access, BlockPos rootPos) {
        return EnumFacing.DOWN;
    }

    @Override
    public void destroyTree(World world, BlockPos pos) {
        BlockBranch branch = TreeHelper.getBranch(world.getBlockState(pos.down()));
        if(branch != null) {
            BranchDestructionData data = branch.destroyBranchFromNode(world, pos.down(), EnumFacing.UP, true);
            EntityFallingTree.dropTree(world, data, new ArrayList<ItemStack>(0), EntityFallingTree.DestroyType.ROOT);
        }
    }
}
