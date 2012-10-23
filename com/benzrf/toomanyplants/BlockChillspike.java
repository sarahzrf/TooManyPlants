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
		/*meta = meta | (*/checkBlock(world, i + 1, j, k);// << 0);
		/*meta = meta | (*/checkBlock(world, i - 1, j, k);// << 1);
		checkBlock(world, i, j + 1, k);
		/*meta = meta | (*/checkBlock(world, i, j, k + 1);// << 2);
		/*meta = meta | (*/checkBlock(world, i, j, k - 1);// << 3);
	}
	
	private byte checkBlock(World world, int i, int j, int k)
	{
		if (world.getBlockId(i, j, k) == Block.waterMoving.blockID || world.getBlockId(i, j, k) == Block.waterStill.blockID)
		{
			world.setBlock(i, j, k, Block.ice.blockID);
			return 1;
		}
		if (world.getBlockId(i, j, k) == Block.lavaMoving.blockID || world.getBlockId(i, j, k) == Block.lavaStill.blockID)
		{
			world.setBlock(i, j, k, Block.cobblestone.blockID);
			return 1;
		}
		if (world.getBlockId(i, j, k) == Block.fire.blockID)
		{
			world.setBlock(i, j, k, 0);
			return 1;
		}
		return 0;
	}
	
	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random)
	{
		sparkle(world, i, j, k);
//		if (((meta & 1) >> 0) == 1) sparkle(world, i + 1, j, k);
//		if (((meta & 2) >> 1) == 1) sparkle(world, i - 1, j, k);
//		if (((meta & 4) >> 2) == 1) sparkle(world, i, j, k + 1);
//		if (((meta & 8) >> 3) == 1) sparkle(world, i, j, k - 1);
	}
	
	private void sparkle(World world, int i, int j, int k)
	{
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
				world.spawnParticle("reddust", d1, d2, d3, -0.5D, 3.0D, 2.0D);
			}
		}
	}
	
	@Override
	public boolean canBlockStay(World world, int i, int j, int k)
	{
		return canThisPlantGrowOnThisBlockID(world.getBlockId(i, j - 1, k));
	}
	
	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int i)
	{
		return i == Block.netherrack.blockID || i == TooManyPlants.objs.blockfrozennetherrack.blockID;
	}
	
	public static int[][] toFreeze = {
			{0, 3, 0},
			{1, 2, 0}, {0, 2, 1}, {0, 2, 0}, {0, 2, -1}, {-1, 2, 0},
			{2, 1, 0}, {1, 1, 1}, {1, 1, 0}, {1, 1, -1}, {0, 1, 2}, {0, 1, 1}, {0, 1, 0}, {0, 1, -1}, {0, 1, -2}, {-1, 1, 1}, {-1, 1, 0}, {-1, 1, -1}, {-2, 1, 0},
			{2, 0, 0}, {1, 0, 1}, {1, 0, 0}, {1, 0, -1}, {0, 0, 2}, {0, 0, 1}, {0, 0, 0}, {0, 0, -1}, {0, 0, -2}, {-1, 0, 1}, {-1, 0, 0}, {-1, 0, -1}, {-2, 0, 0},
			{2, -1, 0}, {1, -1, 1}, {1, -1, 0}, {1, -1, -1}, {0, -1, 2}, {0, -1, 1}, {0, -1, 0}, {0, -1, -1}, {0, -1, -2}, {-1, -1, 1}, {-1, -1, 0}, {-1, -1, -1}, {-2, -1, 0},
			{1, -2, 0}, {0, -2, 1}, {0, -2, 0}, {0, -2, -1}, {-1, -2, 0},
			{0, -3, 0}
	};
}