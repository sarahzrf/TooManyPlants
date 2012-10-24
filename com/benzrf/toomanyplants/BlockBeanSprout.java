package com.benzrf.toomanyplants;

import java.util.Random;


import net.minecraft.src.Block;
import net.minecraft.src.BlockFlower;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;

public class BlockBeanSprout extends BlockFlower
{
	protected BlockBeanSprout(int i, int j)
	{
		super(i, j);
	}
	
	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int i)
	{
		return i == Block.dirt.blockID || i == Block.grass.blockID;
	}
	
	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving)
	{
		world.scheduleBlockUpdate(i, j, k, blockID, 60);
	}
	
	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{
		world.setBlockMetadata(i, j, k, world.getBlockMetadata(i, j, k) + 1);
		if (world.getBlockMetadata(i, j, k) == 10)
		{
			world.setBlockAndMetadataWithNotify(i, j, k, TooManyPlants.objs.blockbeanplant.blockID, 0);
		}
		else
		{
			world.scheduleBlockUpdate(i, j, k, blockID, 60);
		}
	}
	
	@Override
	public String getTextureFile()
	{
		return TooManyPlants.textureFile;
	}
}