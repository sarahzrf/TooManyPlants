package com.benzrf.toomanyplants;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import com.benzrf.toomanyplants.proxy.CommonProxy;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.TextureFXManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.BlockFlower;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.Item;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemSeeds;
import net.minecraft.src.ItemSoup;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.PotionEffect;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenMinable;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.Configuration;

@Mod
(
	modid = "toomanyplants",
	name="TooManyPlants",
	version="1.2.0"
)
@NetworkMod
(
	serverSideRequired = false,
	clientSideRequired = true,
	versionBounds = "[1.2]"
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
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		String benzrf_made_this_mod_if_anyone_else_claims_to_have_made_it_they_are_LIARS = "Take that, criminal scum!";
		objs = new TMPObjectsInstantiation();
		Configuration c = new Configuration(event.getSuggestedConfigurationFile());
		c.load();
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