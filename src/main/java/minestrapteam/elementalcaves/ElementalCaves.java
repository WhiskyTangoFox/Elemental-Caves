package minestrapteam.elementalcaves;

import cpw.mods.fml.common.*;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.Loader;
import minestrapteam.elementalcaves.common.ECCommonProxy;
import minestrapteam.elementalcaves.common.ECEventHandler;
import minestrapteam.elementalcaves.creativetab.ECCreativeTabBlocks;
import minestrapteam.elementalcaves.creativetab.ECCreativeTabItems;
import minestrapteam.elementalcaves.entity.projectile.*;
import minestrapteam.elementalcaves.lib.*;
import minestrapteam.elementalcaves.world.gen.ECWorldGenerator;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import me.dawars.CraftingPillars.api.baubles.Baubles;

@Mod(modid = ECReference.MODID, name = ECReference.NAME, version = ECReference.VERSION)
public class ElementalCaves
{
	@Instance(ECReference.MODID)
	public static ElementalCaves	instance;
	
	@SidedProxy(clientSide = "minestrapteam.elementalcaves.client.ECClientProxy", serverSide = "minestrapteam.elementalcaves.common.ECCommonProxy")
	public static ECCommonProxy		proxy;
	
	public static ECEventHandler	eventHandler		= new ECEventHandler();
	
	public static CreativeTabs		tabBlocks			= new ECCreativeTabBlocks("ec_blocks");
	public static CreativeTabs		tabItems			= new ECCreativeTabItems("ec_items");
	
	public static Fluid				iceFloe				= new Fluid("ice_floe").setLuminosity(6).setViscosity(2000);
	
	public static boolean			minestrappolation	= minestrappolationInstalled();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ECAddons.loadAddons();
		
		FluidRegistry.registerFluid(iceFloe);
		
		ECConfig.init(new Configuration(event.getSuggestedConfigurationFile()));
		ECBlocks.init();
		ECItems.init();
		ECRecipes.init();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		ECBlocks.addHarvestLevels();
		
		FMLCommonHandler.instance().bus().register(eventHandler);
		MinecraftForge.EVENT_BUS.register(eventHandler);
		
		ECCaves.addCaveTypes();
		GameRegistry.registerWorldGenerator(new ECWorldGenerator(), 0);
		
		EntityRegistry.registerModEntity(EntityIceShard.class, "entity_ice_shard", 2, ElementalCaves.instance, 40, 3, true);
		EntityRegistry.registerModEntity(EntityFrostGem.class, "entity_frost_gem", 3, ElementalCaves.instance, 40, 3, true);
		EntityRegistry.registerModEntity(EntityFireShard.class, "entity_fire_shard", 4, ElementalCaves.instance, 40, 3, true);
		EntityRegistry.registerModEntity(EntityMagmaGem.class, "entity_magma_gem", 5, ElementalCaves.instance, 40, 3, true);
		EntityRegistry.registerModEntity(EntityForestGem.class, "forest_gem", 6, ElementalCaves.instance, 40, 3, true);
		
		proxy.registerRenders();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		if(Loader.isModLoaded("craftingpillars"))
		{
			Baubles.addModifier(ECItems.gems, new GemMod());
		}
	}
	
	private static boolean minestrappolationInstalled()
	{
		try
		{
			Class.forName("minestrapteam.minestrappolation.Minestrappolation", false, ClassLoader.getSystemClassLoader());
			return true;
		}
		catch (ClassNotFoundException ex)
		{
			return false;
		}
	}
}
