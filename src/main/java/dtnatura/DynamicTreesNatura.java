package dtnatura;

import com.progwml6.natura.nether.NaturaNether;
import dtnatura.proxy.CommonProxy;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid=DynamicTreesNatura.MODID, name=DynamicTreesNatura.NAME, version=DynamicTreesNatura.VERSION, dependencies=DynamicTreesNatura.DEPENDENCIES)
public class DynamicTreesNatura {

    public static final String MODID = "dtnatura";
    public static final String NAME = "Dynamic Trees Natura";
    public static final String VERSION = "@VERSION@";
    public static final String DEPENDENCIES = "required-after:dynamictrees@[1.12.2-0.8.1i,);required-after:natura";

    @Mod.Instance
    public static DynamicTreesNatura instance;

    @SidedProxy(clientSide = "dtnatura.proxy.ClientProxy", serverSide = "dttraverse.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
