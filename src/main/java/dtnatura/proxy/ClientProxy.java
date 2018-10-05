package dtnatura.proxy;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.client.ModelHelper;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicSapling;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.progwml6.natura.overworld.NaturaOverworld;
import dtnatura.DynamicTreesNatura;
import dtnatura.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nullable;

public class ClientProxy extends CommonProxy {


    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);
        registerColorHandlers();
    }

    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    public void registerColorHandlers() {

        for (BlockDynamicLeaves leaves: TreeHelper.getLeavesMapForModId(DynamicTreesNatura.MODID).values()) {
            ModelHelper.regColorHandler(leaves, new IBlockColor() {
                @Override
                public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
                    Block block = state.getBlock();

                    if (TreeHelper.isLeaves(block)) {
                        return ((BlockDynamicLeaves) block).getProperties(state).foliageColorMultiplier(state, worldIn, pos);
                    }

                    return 0xFFFFFF;
                }
            });
        }

        for (TreeFamily tree: ModBlocks.trees) {
            BlockDynamicSapling sapling = (BlockDynamicSapling) tree.getCommonSpecies().getDynamicSapling().getBlock();
            ModelHelper.regDynamicSaplingColorHandler(sapling);
        }
    }
}
