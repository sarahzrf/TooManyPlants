package com.benzrf.toomanyplants;

import java.util.Random;

import net.minecraft.src.BlockFlower;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

public class BlockAirFlower extends BlockFlower2
{
	protected BlockAirFlower(int i, int j)
	{
		super(i, j);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving)
	{
		if (entityliving instanceof EntityPlayer)
		{
			for (int x = -1; x <= 1; x++)
			{
				for (int y = -1; y <= 1; y++)
				{
					for (int z = -1; z <= 1; z++)
					{
						if (world.getBlockId(i + x, j + y, k + z) == 8 || world.getBlockId(i + x, j + y, k + z) == 9)
						{
							world.setBlock(i + x, j + y, k + z, 0);
						}
					}
				}
			}
		}
	}
	
	@Override
	public void breakBlock(World world, int i, int j, int k, int l, int m)
	{
		for (int x = -1; x <= 1; x++)
		{
			for (int y = -1; y <= 1; y++)
			{
				for (int z = -1; z <= 1; z++)
				{
					world.notifyBlocksOfNeighborChange(i + x, j + y, k + z, 0);
				}
			}
		}
	}
	
	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		super.onNeighborBlockChange(world, i, j, k, l);
		for (int x = -1; x <= 1; x++)
		{
			for (int y = 0; y <= 1; y++)
			{
				for (int z = -1; z <= 1; z++)
				{
					if (world.getBlockId(i + x, j + y, k + z) == 8 || world.getBlockId(i + x, j + y, k + z) == 9)
					{
						world.setBlock(i + x, j + y, k + z, 0);
					}
				}
			}
		}
	}
	
	@Override
	public boolean canBlockStay(World world, int i, int j, int k)
	{
		return canThisPlantGrowOnThisBlockID(world.getBlockId(i, j - 1, k));
	}
}
