package com.github.azulaloi.dtnatura;

import com.ferreusveritas.dynamictrees.ModItems;
import com.ferreusveritas.dynamictrees.ModRecipes;
import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.client.ModelHelper;
import com.ferreusveritas.dynamictrees.api.treedata.ILeavesProperties;
import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.ferreusveritas.dynamictrees.blocks.BlockRooty;
import com.ferreusveritas.dynamictrees.blocks.LeavesProperties;
import com.ferreusveritas.dynamictrees.items.DendroPotion;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.github.azulaloi.dtnatura.blocks.BlockRootyNetherrack;
import com.github.azulaloi.dtnatura.items.ItemMapleSeed;
import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.overworld.NaturaOverworld;
import com.github.azulaloi.dtnatura.blocks.BlockDynamicLeavesPotash;
import com.github.azulaloi.dtnatura.blocks.BlockRootyInverse;
import com.github.azulaloi.dtnatura.blocks.BlockRootyInverseNetherrack;
import com.github.azulaloi.dtnatura.items.ItemBloodSeed;
import com.github.azulaloi.dtnatura.trees.nether.TreeBlood;
import com.github.azulaloi.dtnatura.trees.nether.TreeDark;
import com.github.azulaloi.dtnatura.trees.nether.TreeFuse;
import com.github.azulaloi.dtnatura.trees.nether.TreeGhost;
import com.github.azulaloi.dtnatura.trees.overworld.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

@Mod.EventBusSubscriber(modid = DynamicTreesNatura.MODID)
public class ModBlocks {

    public static BlockRootyInverse blockRootyInverseNetherrack;
    public static BlockRootyNetherrack blockRootyNetherrack;

    public static BlockDynamicLeavesPotash potashLeaves;

    public static ILeavesProperties ghostwoodLeavesProperties;
    public static ILeavesProperties bloodwoodLeavesProperties;
    public static ILeavesProperties fusewoodLeavesProperties;
    public static ILeavesProperties darkwoodLeavesProperties;

    public static ILeavesProperties mapleLeavesProperties;
    public static ILeavesProperties silverbellLeavesProperties;
    public static ILeavesProperties amaranthLeavesProperties;
    public static ILeavesProperties tigerLeavesProperties;

    public static ILeavesProperties willowLeavesProperties;
    public static ILeavesProperties eucalyptusLeavesProperties;
    public static ILeavesProperties hopseedLeavesProperties;
    public static ILeavesProperties sakuraLeavesProperties;

    public static ILeavesProperties[] basicLeavesProperties;

    public static ArrayList<TreeFamily> trees = new ArrayList<TreeFamily>();

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();

        // ROOTS

        blockRootyInverseNetherrack = new BlockRootyInverseNetherrack("rootyinversenetherrack", false, Material.ROCK);
        blockRootyNetherrack = new BlockRootyNetherrack("rootynetherrack", false, Material.ROCK);

        // LEAF BLOCKS & ITEMBLOCKS

//        Block netherLeaves = NaturaNether.netherLeaves;
        Item netherLeavesItem = new ItemBlock(NaturaNether.netherLeaves);
        netherLeavesItem.setRegistryName(NaturaNether.netherLeaves.getRegistryName());

        Block darkwoodLeaves = NaturaNether.netherLeaves2;
        Item darkwoodLeavesItem = new ItemBlock(darkwoodLeaves);
        darkwoodLeavesItem.setRegistryName(darkwoodLeaves.getRegistryName());

        Item overworldLeavesAlephItem = new ItemBlock(NaturaOverworld.overworldLeaves);
        overworldLeavesAlephItem.setRegistryName(NaturaOverworld.overworldLeaves.getRegistryName());

        Item overworldLeavesBetItem = new ItemBlock(NaturaOverworld.overworldLeaves2);
        overworldLeavesBetItem.setRegistryName(NaturaOverworld.overworldLeaves2.getRegistryName());

        GameRegistry.findRegistry(Item.class).registerAll(
                netherLeavesItem,
                darkwoodLeavesItem,
                overworldLeavesAlephItem,
                overworldLeavesBetItem
        );

        potashLeaves = new BlockDynamicLeavesPotash();
        registry.register(potashLeaves);

        ////////
        //LEAVES PROPERTIES
        ////////

        //NETHER LEAVES PROPERTIES

        ghostwoodLeavesProperties = new LeavesProperties(
                NaturaNether.netherLeaves.getStateFromMeta(0),
                new ItemStack(netherLeavesItem, 1, 0),
                TreeRegistry.findCellKit("deciduous")) {

            @Override
            public int getLightRequirement() {
                return 0;
            }
        };

        bloodwoodLeavesProperties = new LeavesProperties(
                NaturaNether.netherLeaves.getStateFromMeta(1),
                new ItemStack(netherLeavesItem, 1, 1),
                TreeRegistry.findCellKit("darkoak")) {

            @Override
            public int getLightRequirement() {
                return 0;
            }

            @Override
            public int getSmotherLeavesMax() {
                return 6;
            }
        };

        fusewoodLeavesProperties = new LeavesProperties(
                NaturaNether.netherLeaves.getStateFromMeta(2),
                new ItemStack(netherLeavesItem, 1, 2),
                TreeRegistry.findCellKit("deciduous")) {

            @Override
            public int getLightRequirement() {
                return 0;
            }
        };

        darkwoodLeavesProperties = new LeavesProperties(
                darkwoodLeaves.getDefaultState(),
                new ItemStack(darkwoodLeavesItem)) {

            @Override
            public int getLightRequirement() {
                return 0;
            }

            Random rand = new Random();

            @Override
            public IBlockState getDynamicLeavesState(int hydro) {
                if (rand.nextInt(4) == 0) {
                    return super.getDynamicLeavesState(hydro).withProperty(BlockDynamicLeavesPotash.CAN_GROW, true).withProperty(BlockDynamicLeavesPotash.GROWING, true).withProperty(BlockDynamicLeavesPotash.GROWN, true);
                }

                return super.getDynamicLeavesState(hydro);
            }
        };

        darkwoodLeavesProperties.setDynamicLeavesState(potashLeaves.getDefaultState());
        potashLeaves.setProperties(0, darkwoodLeavesProperties);

        //OVERWORLD LEAVES PROPERTIES 1

        mapleLeavesProperties = new LeavesProperties(
                NaturaOverworld.overworldLeaves.getStateFromMeta(0),
                new ItemStack(overworldLeavesAlephItem, 1, 0),
                TreeRegistry.findCellKit("deciduous")
        );

        silverbellLeavesProperties = new LeavesProperties(
                NaturaOverworld.overworldLeaves.getStateFromMeta(1),
                new ItemStack(overworldLeavesAlephItem, 1, 1),
                TreeRegistry.findCellKit("deciduous")
        );

        amaranthLeavesProperties = new LeavesProperties(
                NaturaOverworld.overworldLeaves.getStateFromMeta(2),
                new ItemStack(overworldLeavesAlephItem, 1, 2),
                TreeRegistry.findCellKit("deciduous")
        );


        tigerLeavesProperties = new LeavesProperties(
                NaturaOverworld.overworldLeaves.getStateFromMeta(3),
                new ItemStack(overworldLeavesAlephItem, 1, 3),
                TreeRegistry.findCellKit("deciduous")
        );

        //OVERWORLD LEAVES PROPERTIES 2

        willowLeavesProperties = new LeavesProperties(
                NaturaOverworld.overworldLeaves2.getStateFromMeta(0),
                new ItemStack(overworldLeavesBetItem, 1, 0),
                TreeRegistry.findCellKit("deciduous")
        );

        eucalyptusLeavesProperties = new LeavesProperties(
                NaturaOverworld.overworldLeaves2.getStateFromMeta(1),
                new ItemStack(overworldLeavesBetItem, 1, 1),
                TreeRegistry.findCellKit("acacia")) {
            @Override
            public int getSmotherLeavesMax() {
                return 3;
            }
        };


        hopseedLeavesProperties = new LeavesProperties(
                NaturaOverworld.overworldLeaves2.getStateFromMeta(2),
                new ItemStack(overworldLeavesBetItem, 1, 2),
                TreeRegistry.findCellKit("deciduous")
        );

        sakuraLeavesProperties = new LeavesProperties(
                NaturaOverworld.overworldLeaves2.getStateFromMeta(3),
                new ItemStack(overworldLeavesBetItem, 1, 3),
                TreeRegistry.findCellKit("deciduous")
        );

        ////////
        //REGISTER LEAVES PROPERTIES
        ////////

        basicLeavesProperties = new ILeavesProperties[] {
                ghostwoodLeavesProperties,
                bloodwoodLeavesProperties,
                fusewoodLeavesProperties,
                LeavesProperties.NULLPROPERTIES, //Darkwood Null
                mapleLeavesProperties,
                silverbellLeavesProperties,
                amaranthLeavesProperties,
                tigerLeavesProperties,
                willowLeavesProperties,
                eucalyptusLeavesProperties,
                hopseedLeavesProperties,
                sakuraLeavesProperties
        };

        int seq = 0;
        for (ILeavesProperties lp : basicLeavesProperties) {
            TreeHelper.getLeavesBlockForSequence(DynamicTreesNatura.MODID, seq++, lp);
        }


        ////////
        //REGISTER TREES
        ////////

        //NETHER
        TreeFamily ghostTree = new TreeGhost();
        TreeFamily fuseTree = new TreeFuse();
        TreeFamily bloodTree = new TreeBlood();
        TreeFamily darkTree = new TreeDark();

        //OVERWORLD 1
        TreeFamily mapleTree = new TreeMaple();
        TreeFamily silverbellTree = new TreeSilverbell();
        TreeFamily amaranthTree = new TreeAmaranth();
        TreeFamily tigerTree = new TreeTiger();

        //OVERWORLD 2
        TreeFamily willowTree = new TreeWillow();
        TreeFamily eucalyptusTree = new TreeEucalyptus();
        TreeFamily hopseedTree = new TreeHopseed();
        TreeFamily sakuraTree = new TreeSakura();

        Collections.addAll(trees,
                ghostTree, fuseTree, bloodTree, darkTree,
                mapleTree, silverbellTree, amaranthTree, tigerTree,
                willowTree, eucalyptusTree, hopseedTree, sakuraTree
        );

        trees.forEach(tree -> tree.registerSpecies(Species.REGISTRY));

        ArrayList<Block> treeBlocks = new ArrayList<>();
        trees.forEach(tree -> tree.getRegisterableBlocks(treeBlocks));
        treeBlocks.addAll(TreeHelper.getLeavesMapForModId(DynamicTreesNatura.MODID).values());
        registry.registerAll(treeBlocks.toArray(new Block[treeBlocks.size()]));

        registry.registerAll(blockRootyNetherrack, blockRootyInverseNetherrack);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        ArrayList<Item> treeItems = new ArrayList<>();
        trees.forEach(tree -> tree.getRegisterableItems(treeItems));
        registry.registerAll(treeItems.toArray(new Item[treeItems.size()]));
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent<IRecipe> event) {
        setupRecipes("ghost", "ghost_sapling");
        setupRecipes("fuse", "fuse_sapling");
        setupRecipes("blood", "blood_sapling");
        setupRecipes("dark", "dark_sapling");

        setupRecipes("maple", "maple_sapling");
        setupRecipes("silverbell", "silverbell_sapling");
        setupRecipes("amaranth", "amaranth_sapling");
        setupRecipes("tiger", "tiger_sapling");

        setupRecipes("willow", "willow_sapling");
        setupRecipes("eucalyptus", "eucalyptus_sapling");
        setupRecipes("hopseed", "hopseed_sapling");
        setupRecipes("sakura", "sakura_sapling");
    }

    public static void setupRecipes(String species, String sapling) {
        Species specie = TreeRegistry.findSpecies(new ResourceLocation(DynamicTreesNatura.MODID, species));
        ItemStack seed = specie.getSeedStack(1);
        ItemStack transformationPotion = ModItems.dendroPotion.setTargetTree(new ItemStack(ModItems.dendroPotion, 1, DendroPotion.DendroPotionType.TRANSFORM.getIndex()), specie.getFamily());
        BrewingRecipeRegistry.addRecipe(new ItemStack(ModItems.dendroPotion, 1, DendroPotion.DendroPotionType.TRANSFORM.getIndex()), seed, transformationPotion);

        ModRecipes.createDirtBucketExchangeRecipes(new ItemStack(NaturaNether.netherSapling, 1, 0), seed, true);
        ModRecipes.createDirtBucketExchangeRecipes(new ItemStack(NaturaNether.netherSapling2, 1, 0), seed, true);
        ModRecipes.createDirtBucketExchangeRecipes(new ItemStack(NaturaOverworld.overworldSapling, 1, 0), seed, true);
        ModRecipes.createDirtBucketExchangeRecipes(new ItemStack(NaturaOverworld.overworldSapling2, 1, 0), seed, true);


    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (TreeFamily tree : ModBlocks.trees) {
            ModelHelper.regModel(tree.getDynamicBranch());
            ModelHelper.regModel(tree.getCommonSpecies().getSeed());
            ModelHelper.regModel(tree);
        }

        TreeHelper.getLeavesMapForModId(DynamicTreesNatura.MODID).forEach((key,leaves) -> ModelLoader.setCustomStateMapper(leaves, new StateMap.Builder().ignore(BlockLeaves.DECAYABLE).build()));

        ModelLoader.setCustomStateMapper(ModBlocks.darkwoodLeavesProperties.getDynamicLeavesState().getBlock(), new StateMap.Builder().ignore(BlockDynamicLeaves.TREE).ignore(BlockDynamicLeavesPotash.HYDRO).build());

        ModelLoader.setCustomStateMapper(ModBlocks.blockRootyInverseNetherrack, new StateMap.Builder().ignore(BlockRooty.LIFE).build());
        ModelLoader.setCustomStateMapper(ModBlocks.blockRootyNetherrack, new StateMap.Builder().ignore(BlockRooty.LIFE).build());
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
        int id = 0;

        EntityRegistry.registerModEntity(new ResourceLocation(DynamicTreesNatura.MODID, "blood_seed"), ItemBloodSeed.EntityItemBloodSeed.class, "blood_seed", id++, DynamicTreesNatura.MODID, 32, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(DynamicTreesNatura.MODID, "maple_seed"), ItemMapleSeed.EntityItemMapleSeed.class, "maple_seed", id++, DynamicTreesNatura.MODID, 32, 1, true);
    }
}
