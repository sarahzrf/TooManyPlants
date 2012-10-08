package com.benzrf.toomanyplants;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import cpw.mods.fml.client.FMLTextureFX;

import net.minecraft.client.Minecraft;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.TextureFX;
import net.minecraftforge.client.ForgeHooksClient;

public class TextureDawnFlowerFX extends FMLTextureFX
{
	public TextureDawnFlowerFX(int par1, Minecraft minecraft)
	{
		super(par1);
		m = minecraft;
		try {
			BufferedImage o = ImageIO.read(getClass().getResource("/com/benzrf/toomanyplants/resources/DawnflowerOpen.png"));
			BufferedImage c = ImageIO.read(getClass().getResource("/com/benzrf/toomanyplants/resources/DawnflowerClosed.png"));
			int index;
			int orgb;
			int crgb;
			for (int x = 0; x <= 15; x++)
			{
				for (int y = 0; y <= 15; y++)
				{
					orgb = o.getRGB(x, y);
					crgb = c.getRGB(x, y);
					index = ((y * 16) + x) * 4;
					open[index] = (byte)((orgb>>16)&0xFF);
					closed[index] = (byte)((crgb>>16)&0xFF);
					open[index + 1] = (byte)((orgb>>8)&0xFF);
					closed[index + 1] = (byte)((crgb>>8)&0xFF);
					open[index + 2] = (byte)((orgb)&0xFF);
					closed[index + 2] = (byte)((crgb)&0xFF);
					open[index + 3] = (byte)((orgb>>24)&0xFF);
					closed[index + 3] = (byte)((crgb>>24)&0xFF);
				}
			}
			imageData = closed;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void bindImage(RenderEngine renderengine)
	{
		ForgeHooksClient.bindTexture("/com/benzrf/toomanyplants/resources/plantssheet.png", 0);
	}
	
	@Override
	public void onTick()
	{
		if (m.theWorld == null)
		{
			return;
		}
		if ((m.theWorld.getWorldInfo().getWorldTime()) % 24000 > 13000)
		{
			imageData = closed;
		}
		else
		{
			imageData = open;
		}
	}
	
	byte[] open = new byte[1024];
	byte[] closed = new byte[1024];
	Minecraft m;
}
