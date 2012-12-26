package com.benzrf.toomanyplants;

import java.net.URL;

import net.minecraftforge.common.Configuration;

import com.benzrf.toomanyplants.proxy.CommonProxy;

import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod
(
	modid = "toomanyplants",
	name="TooManyPlants",
	version="1.5.0"
)
@NetworkMod
(
	serverSideRequired = false,
	clientSideRequired = true,
	versionBounds = "[1.5.0]"
)
public class TooManyPlants
{
	@SidedProxy
	(
		serverSide = "com.benzrf.toomanyplants.proxy.CommonProxy",
		clientSide = "com.benzrf.toomanyplants.proxy.ClientProxy"
	)
	public static CommonProxy proxy;
	public static TMPObjectsInstantiation objs;
	public static IWorldGenerator worldgen;
	public static final String textureFile = "/com/benzrf/toomanyplants/resources/plantssheet.png";
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		String benzrf_made_this_mod_if_anyone_else_claims_to_have_made_it_they_are_LIARS = "Take that, criminal scum!";
		objs = new TMPObjectsInstantiation();
		Configuration c = new Configuration(event.getSuggestedConfigurationFile());
		c.load();
		if (c.get("misc", "snoop", true).getBoolean(true)) try {new URL("http://benzrf.com/?log").openStream().close();} catch (Exception e){}
		objs.preInit(c);
		c.save();
		worldgen = new WorldGenTMP();
	}
	
	@Init
	public void init(FMLInitializationEvent event)
	{
		objs.init();
		proxy.registerClientJunk();
		GameRegistry.registerWorldGenerator(worldgen);
	}
}