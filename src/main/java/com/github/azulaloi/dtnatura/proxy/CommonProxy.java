package com.github.azulaloi.dtnatura.proxy;

import com.ferreusveritas.dynamictrees.api.WorldGenRegistry;
import com.github.azulaloi.dtnatura.worldgen.BiomeDataBasePopulator;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {

    }

    public void init(FMLInitializationEvent event) {

        WorldGenRegistry.registerBiomeDataBasePopulator(new BiomeDataBasePopulator());
    }

    public void postInit(FMLPostInitializationEvent event) {

    }


}
