package com.benzrf.toomanyplants;

import net.minecraft.src.BlockFlower;

public class BlockDawnflower extends BlockFlower 
{
	protected BlockDawnflower(int par1, int par2)
	{
		super(par1, par2);
	}
	
	@Override
	public String getTextureFile()
	{
		return "/com/benzrf/toomanyplants/resources/plantssheet.png";
	}
}