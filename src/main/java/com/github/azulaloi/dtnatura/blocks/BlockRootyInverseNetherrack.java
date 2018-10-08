package com.github.azulaloi.dtnatura.blocks;


import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockRootyInverseNetherrack extends BlockRootyInverse {

    public BlockRootyInverseNetherrack(String name, boolean isTileEntity, Material material) {
        super(name, isTileEntity, material);
    }

    @Override
    public IBlockState getDecayBlockState(IBlockAccess access, BlockPos pos) {
        return Blocks.NETHERRACK.getDefaultState();
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int Fortune) {
        return Item.getItemFromBlock(Blocks.NETHERRACK);
    }

    @Override
    public IBlockState getMimic(IBlockAccess access, BlockPos pos) {
        return MimicPropertyNatura.getNetherrackMimic(access, pos);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
}
