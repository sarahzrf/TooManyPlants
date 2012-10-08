package com.benzrf.toomanyplants.proxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.MinecraftForgeClient;

import com.benzrf.toomanyplants.TextureDawnFlowerFX;
import com.benzrf.toomanyplants.TooManyPlants;

import cpw.mods.fml.client.FMLClientHandler;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerClientJunk()
	{
		MinecraftForgeClient.preloadTexture("/com/benzrf/toomanyplants/resources/plantssheet.png");
		FMLClientHandler.instance().getClient().renderEngine.registerTextureFX(new TextureDawnFlowerFX(TooManyPlants.objs.blockdawnflower.blockIndexInTexture, Minecraft.getMinecraft()));
	}
}
