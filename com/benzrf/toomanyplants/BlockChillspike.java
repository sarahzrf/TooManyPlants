package com.benzrf.toomanyplants;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.World;

public class BlockChillspike extends BlockFlower2
{
	protected BlockChillspike(int par1, int par2)
	{
		super(par1, par2);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		super.onNeighborBlockChange(world, i, j, k, l);
		sparkle(world, i, j, k);
		sparkle(world, i, j, k);
		checkBlock(world, i + 1, j, k);
		checkBlock(world, i - 1, j, k);
		checkBlock(world, i, j + 1, k);
		checkBlock(world, i, j, k + 1);
		checkBlock(world, i, j, k - 1);
	}
	
	private void checkBlock(World world, int i, int j, int k)
	{
		if (world.getBlockId(i, j, k) == Block.waterMoving.blockID || world.getBlockId(i, j, k) == Block.waterStill.blockID)
		{
			world.setBlock(i, j, k, Block.ice.blockID);
			sparkle(world, i, j, k);
			sparkle(world, i, j, k);
			sparkle(world, i, j, k);
			sparkle(world, i, j, k);
		}
		if (world.getBlockId(i, j, k) == Block.lavaMoving.blockID || world.getBlockId(i, j, k) == Block.lavaStill.blockID)
		{
			world.setBlock(i, j, k, Block.cobblestone.blockID);
			sparkle(world, i, j, k);
			sparkle(world, i, j, k);
			sparkle(world, i, j, k);
			sparkle(world, i, j, k);
		}
		if (world.getBlockId(i, j, k) == Block.fire.blockID)
		{
			world.setBlock(i, j, k, 0);
			sparkle(world, i, j, k);
			sparkle(world, i, j, k);
			sparkle(world, i, j, k);
			sparkle(world, i, j, k);
		}
	}
	
	private void sparkle(World world, int i, int j, int k)
	{
		System.out.println("spahklin");
		Random random = world.rand;
		double d = 0.0625D;

		for (int i2 = 0; i2 < 6; i2++)
		{
			double d1 = (float)i + random.nextFloat();
			double d2 = (float)j + random.nextFloat();
			double d3 = (float)k + random.nextFloat();

			if (i2 == 0 && !world.isBlockOpaqueCube(i, j + 1, k))
			{
				d2 = (double)(j + 1) + d;
			}

			if (i2 == 1 && !world.isBlockOpaqueCube(i, j - 1, k))
			{
				d2 = (double)(j + 0) - d;
			}

			if (i2 == 2 && !world.isBlockOpaqueCube(i, j, k + 1))
			{
				d3 = (double)(k + 1) + d;
			}

			if (i2 == 3 && !world.isBlockOpaqueCube(i, j, k - 1))
			{
				d3 = (double)(k + 0) - d;
			}

			if (i2 == 4 && !world.isBlockOpaqueCube(i + 1, j, k))
			{
				d1 = (double)(i + 1) + d;
			}

			if (i2 == 5 && !world.isBlockOpaqueCube(i - 1, j, k))
			{
				d1 = (double)(i + 0) - d;
			}

			if (d1 < (double)i || d1 > (double)(i + 1) || d2 < 0.0D || d2 > (double)(j + 1) || d3 < (double)k || d3 > (double)(k + 1))
			{
				System.out.println("spakle");
				world.spawnParticle("reddust", d1, d2, d3, 0.0D, 10.0D, 0.0D);
			}
		}
	}
	
	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int i)
	{
		return i == Block.netherrack.blockID|| i == TooManyPlants.objs.blockfrozennetherrack.blockID;
	}
}