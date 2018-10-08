package dtnatura.blocks;

import com.ferreusveritas.dynamictrees.blocks.MimicProperty;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class MimicPropertyNatura extends MimicProperty {

    public MimicPropertyNatura(String name) {
        super(name);
    }

    public static IBlockState getNetherrackMimic(IBlockAccess access, BlockPos pos) {
        final int dMap[] = {0, -1, 1};

        IBlockState mimic = Blocks.NETHERRACK.getDefaultState(); // Default to sand

        for (int depth : dMap) {
            for (EnumFacing dir : EnumFacing.HORIZONTALS) {
                IBlockState ground = access.getBlockState(pos.offset(dir).down(depth));
                if (ground.getMaterial() == Material.ROCK) {
                    // Appear as adjacent block as long as it is stone.
                    // TODO: Add config option to enter more acceptable soils (intended for implicit NetherEx compat)

                    // Planting on netherrack next to stone would cause netherrack to transform to stone...
                    // TODO: Make this check for "netherrack" in the name. Easier than a config option anyway
                    return ground;
                }
            }
        }
        return mimic;
    }
}
