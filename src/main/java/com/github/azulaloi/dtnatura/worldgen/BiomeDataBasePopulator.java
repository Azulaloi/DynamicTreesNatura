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

import java.util.Optional;

public class BiomeDataBasePopulator implements IBiomeDataBasePopulator {

    //protected final BiomeDataBase dbase;

    private static Species ghost, fuse, dark, blood,
    maple, silverbell, amaranth, tiger,
    willow, eucalyptus, hopseed, sakura,
    oak;

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
            int biomeId = Biome.getIdForBiome(biome);



        });

        //sakura 1 in 50
        //maple 1 in 10
        //silverbell 1 in 70
        //tiger 1 in 30
        addSpeciesSelector(dbase, Biomes.FOREST, new BiomePropertySelectors.RandomSpeciesSelector().add(oak, 100).add(sakura, 2).add(maple, 10).add(silverbell, 3).add(tiger, 2));

        //how do I make this compatible with other biome mods
        //how do I disable natura generation
        //how do I derive weights of vanilla biomes
        //how am I gonna generate upside down blood trees

    }

    private void addSpeciesSelector(BiomeDataBase dbase, Optional<Biome> biome, BiomePropertySelectors.ISpeciesSelector selector) {
        if(biome.isPresent()) {
            addSpeciesSelector(dbase, biome.get(), selector);
        }
    }

    private void addSpeciesSelector(BiomeDataBase dbase, Biome biome, BiomePropertySelectors.ISpeciesSelector selector) {
        dbase.setSpeciesSelector(biome, selector, BiomeDataBase.Operation.REPLACE);
    }
}
