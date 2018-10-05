package dtnatura.blocks;

import com.ferreusveritas.dynamictrees.api.treedata.ILeavesProperties;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.ferreusveritas.dynamictrees.blocks.LeavesProperties;
import com.ferreusveritas.dynamictrees.util.SafeChunkBounds;
import dtnatura.DynamicTreesNatura;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

import static com.ferreusveritas.dynamictrees.ModConfigs.worldGen;

public class BlockDynamicLeavesPotash extends BlockDynamicLeaves {

    public static final PropertyBool GROWN = PropertyBool.create("grown");
    public static final PropertyBool GROWING = PropertyBool.create("growing");
    public static final PropertyBool CAN_GROW = PropertyBool.create("can_grow");

    private ILeavesProperties properties = LeavesProperties.NULLPROPERTIES;

    public BlockDynamicLeavesPotash() {
        setDefaultState(this.blockState.getBaseState().withProperty(HYDRO, 4).withProperty(GROWN, false).withProperty(GROWING, false).withProperty(CAN_GROW, false));
        setRegistryName(DynamicTreesNatura.MODID, "leaves_potash");
        setUnlocalizedName("leaves_potash");
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {HYDRO, GROWN, GROWING, CAN_GROW, TREE});
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(GROWN, ((meta >> 2) & 1) > 0).withProperty(GROWING, ((meta >> 3) & 1) > 0).withProperty(CAN_GROW, ((meta >> 4) & 1) > 0).withProperty(HYDRO, (meta & 3) + 1);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(HYDRO) - 1) | (state.getValue(GROWN) ? 4 : 0) | (state.getValue(GROWING) ? 8 : 0) | (state.getValue(CAN_GROW) ? 12 : 0);
    }

    public void setProperties(int tree, ILeavesProperties properties) {
        this.properties = properties;
        for (int i = 0; i < 4; i++) super.setProperties(i, properties);
    }

    public ILeavesProperties getProperties (IBlockState blockState) {
        return properties;
    }

    @Override
    public int age(World world, BlockPos pos, IBlockState state, Random rand, SafeChunkBounds safeBounds) {
        ILeavesProperties leavesProperties = getProperties(state);
        int oldHydro = state.getValue(HYDRO);

        boolean worldGen = safeBounds != SafeChunkBounds.ANY;

        int newHydro = getHydrationLevelFromNeighbors(world, pos, leavesProperties);
        if (newHydro == 0 || (!worldGen && !hasAdequateLight(state, world, leavesProperties, pos))) {
            System.out.println(worldGen + " : " +  hasAdequateLight(state, world, leavesProperties, pos) + " : " + "Air");
            world.setBlockToAir(pos);
            return -1;
        } else {
            if (oldHydro != newHydro) {
                world.setBlockState(pos, state.withProperty(HYDRO, MathHelper.clamp(newHydro, 1, 4)), 4);
            }
        }


        //if can grow and not growing, goto growing
        if (canGrow(state) & !isGrowing(state)) {
            boolean grow = worldGen || world.getLight(pos) >= 8;
            if (isGrowing(state) != grow) {
                setGrowing(world, pos, grow, state.withProperty(HYDRO, MathHelper.clamp(newHydro, 1, 4)));
            }

            //if growing, goto grown
        } else if (isGrowing(state)) {
            boolean grow = worldGen || world.getLight(pos) >= 8;
            if (isGrown(state) != grow) {
                setGrown(world, pos, grow, state.withProperty(HYDRO, MathHelper.clamp(newHydro, 1, 4)));
            }
        }

        //if leaves, goto can grow
        for (EnumFacing dir : EnumFacing.VALUES) {
            if (newHydro > 1 || rand.nextInt(4) == 0) {
                BlockPos offPos = pos.offset(dir);
                if (safeBounds.inBounds(offPos, true) && isLocationSuitableForNewLeaves(world, leavesProperties, offPos)) {
                    int hydro = getHydrationLevelFromNeighbors(world, offPos, leavesProperties);
                    if (hydro > 0) {
                        boolean canGrow = world.rand.nextInt(4) == 0;
                        world.setBlockState(offPos, leavesProperties.getDynamicLeavesState(hydro).withProperty(CAN_GROW, canGrow).withProperty(GROWING, canGrow && world.getLight(pos) >= 8), 2);
                    }
                }
            }
        }

        //no state modification, hydro changes
        return newHydro;
    }

    public boolean isGrowing(IBlockState blockState) {
        return blockState.getValue(GROWING);
    }

    public boolean canGrow(IBlockState blockState) {
        return blockState.getValue(CAN_GROW);
    }

    public boolean isGrown(IBlockState blockState) {
        return blockState.getValue(GROWN);
    }

    public static void setGrowing(World world, BlockPos pos, boolean growing, IBlockState currentState) {
        world.setBlockState(pos, currentState.withProperty(GROWING, growing), 2);
    }

    public static void setGrown(World world, BlockPos pos, boolean grown, IBlockState currentState) {
        world.setBlockState(pos, currentState.withProperty(GROWN, grown), 2);
    }

    @Override
    public boolean growLeavesIfLocationIsSuitable(World world, ILeavesProperties leavesProperties, BlockPos pos, int hydro) {
        hydro = hydro == 0 ? leavesProperties.getCellKit().getDefaultHydration() : hydro;
        if (isLocationSuitableForNewLeaves(world, leavesProperties, pos)) {
            boolean canGrow = world.rand.nextInt(4) == 0;
            boolean grown = world.rand.nextInt(8) == 0;
            IBlockState state = this.getDefaultState().withProperty(CAN_GROW, canGrow).withProperty(GROWING, canGrow && world.getLight(pos) >= 8).withProperty(GROWN, grown).withProperty(HYDRO, hydro);
            world.setBlockState(pos, state, 2);
            return true;
        }

        return false;
    }




}
