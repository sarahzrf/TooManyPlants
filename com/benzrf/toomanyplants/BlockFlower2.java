package com.benzrf.toomanyplants;

import net.minecraft.block.BlockFlower;

public class BlockFlower2 extends BlockFlower
{
	protected BlockFlower2(int par1, int par2)
	{
		super(par1, par2);
	}
	
	@Override
	public String getTextureFile()
	{
		return TooManyPlants.textureFile;
	}
}