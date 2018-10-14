package com.github.azulaloi.dtnatura.event;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.github.azulaloi.dtnatura.DynamicTreesNatura;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.overworld.NaturaOverworld;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = DynamicTreesNatura.MODID)
public class ReplaceSaplingEventHandler {

    @SubscribeEvent
    public static void onPlaceSapling(PlaceEvent event) {

        IBlockState state = event.getPlacedBlock();

        Species species = null;

        if (state.getBlock() == NaturaNether.netherSapling) {
            int type = (state.getBlock().getMetaFromState(state));

            switch (type) {
                case 0:
                    species = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "ghost"));
                    break;
                case 1:
                    species = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "fuse"));
                    break;
                case 2:
                    species = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "dark"));
                    break;
            }
        } else if (state.getBlock() == NaturaNether.netherSapling2) {
            species = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "blood"));
        } else if (state.getBlock() == NaturaOverworld.overworldSapling) {
            int type = (state.getBlock().getMetaFromState(state));

            switch (type) {
                case 0:
                    species = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "maple"));
                    break;
                case 1:
                    species = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "silverbell"));
                    break;
                case 2:
                    species = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "amaranth"));
                    break;
                case 3:
                    species = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "tiger"));
                    break;
            }
        } else if (state.getBlock() == NaturaOverworld.overworldSapling2) {
            int type = (state.getBlock().getMetaFromState(state));

            switch (type) {
                case 0:
                    species = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "willow"));
                    break;
                case 1:
                    species = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "eucalyptus"));
                    break;
                case 2:
                    species = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "hopseed"));
                    break;
                case 3:
                    species = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "sakura"));
                    break;
            }
        }

        if (species != null) {
            event.getWorld().setBlockToAir(event.getPos());
            if(!species.plantSapling(event.getWorld(), event.getPos())) {
                double x = event.getPos().getX() + 0.5;
                double y = event.getPos().getY() + 0.5;
                double z = event.getPos().getZ() + 0.5;

                EntityItem itemEntity = new EntityItem(event.getWorld(), x, y, z, species.getSeedStack(1));
                event.getWorld().spawnEntity(itemEntity);
            }
        }
    }
}
