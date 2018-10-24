package com.github.azulaloi.dtnatura.worldgen;

import com.ferreusveritas.dynamictrees.DynamicTrees;
import com.ferreusveritas.dynamictrees.ModConstants;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.worldgen.BiomePropertySelectors;
import com.ferreusveritas.dynamictrees.api.worldgen.IBiomeDataBasePopulator;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.worldgen.BiomeDataBase;
import com.github.azulaloi.dtnatura.DynamicTreesNatura;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Optional;

public class BiomeDataBasePopulator implements IBiomeDataBasePopulator {

    protected final BiomeDataBase dbase;

    private static Species ghost, fuse, dark, blood,
    maple, silverbell, amaranth, tiger,
    willow, eucalyptus, hopseed, sakura,
    oak;

    public BiomeDataBasePopulator(BiomeDataBase dataBase) {
        this.dbase = dataBase;
    }

    public void populate(BiomeDataBase dbase) {
        ghost = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "ghost"));
        fuse = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "fuse"));
        dark = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "dark"));
        blood = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "blood"));

        maple = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "maple"));
        silverbell = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "silverbell"));
        amaranth = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "amaranth"));
        tiger = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "tiger"));

        willow = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "willow"));
        eucalyptus = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "eucalyptus"));
        hopseed = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "hopseed"));
        sakura = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, "sakura"));

        oak = TreeRegistry.findSpecies(new ResourceLocation(ModConstants.MODID, "oak"));

        Biome.REGISTRY.forEach( biome -> {
            boolean bool = false;

            BiomePropertySelectors.RandomSpeciesSelector selector = new BiomePropertySelectors.RandomSpeciesSelector().add(100);

            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST)) {
                selector.add(sakura, 2); //1 in 50
                selector.add(silverbell, 1); //1 in 70 (rounded down)
                selector.add(tiger, 3); //1 in 30
                selector.add(maple, 10); //1 in 10
                selector.add(eucalyptus, 1); //1 in 25
                bool = true;
            }

            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.PLAINS)) {
                // redwood
                selector.add(eucalyptus, 3); //1 in 37.5 (rounded up)
                bool = true;
            }

            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SWAMP)) {
                selector.add(willow, 10); //1 in 10
                bool = true;
            }

            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.MOUNTAIN) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.HILLS)) {
                selector.add(hopseed, 10); //1 in 10
                selector.add(eucalyptus, 3); //10 in 25 (rounded up)
                bool = true;
            }

            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.RIVER)) {
                selector.add(sakura, 10); //1 in 10
                selector.add(willow, 1); //1 in 10
                bool = true;
            }

            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.JUNGLE)) {
                selector.add(amaranth, 50); //1 in 2
                bool = true;
            }


                if (bool) {
                dbase.setSpeciesSelector(biome, selector, BiomeDataBase.Operation.SPLICE_BEFORE);
            }

        });

        // disable natura generation somehow

        addSpeciesSelector(Biomes.HELL, new BiomePropertySelectors.RandomSpeciesSelector().add(ghost, 1).add(fuse, 1).add(dark, 1));
        addDensitySelector(Biomes.HELL, scale(0.05));

        // somehow generate blood trees upside-down

    }

//    private void addSpeciesSelector(BiomeDataBase dbase, Optional<Biome> biome, BiomePropertySelectors.ISpeciesSelector selector) {
//        if(biome.isPresent()) {
//            addSpeciesSelector(dbase, biome.get(), selector);
//        }
//    }

    private void addSpeciesSelector(Biome biome, BiomePropertySelectors.ISpeciesSelector selector) {
        dbase.setSpeciesSelector(biome, selector, BiomeDataBase.Operation.REPLACE);
    }

    private void addDensitySelector(Biome biome, BiomePropertySelectors.IDensitySelector selector) {
        dbase.setDensitySelector(biome, selector, BiomeDataBase.Operation.REPLACE);
    }

    private BiomePropertySelectors.IDensitySelector scale(double factor1) {
        return (rnd, nd) -> nd * factor1;
    }
}
