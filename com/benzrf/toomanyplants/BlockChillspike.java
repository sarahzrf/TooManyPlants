package com.benzrf.toomanyplants;

import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

public class BlockChillspike extends BlockFlower2
{
	protected BlockChillspike(int par1, int par2)
	{
		super(par1, par2);
		setTickRandomly(true);
	}
	
	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{
		if (random.nextInt(3) == 0)
		{
			int[] t = toFreeze[random.nextInt(toFreeze.length)];
			setIfNetherrack(world, i + t[0], j + t[1], k + t[2]);
		}
	}
	
	@Override
	public void onBlockAdded(World world, int i, int j, int k)
	{
		super.onBlockAdded(world, i, j, k);
		checkBlock(world, i + 1, j, k);
		checkBlock(world, i - 1, j, k);
		checkBlock(world, i, j + 1, k);
		checkBlock(world, i, j, k + 1);
		checkBlock(world, i, j, k - 1);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		float f = 0.2F;
		return AxisAlignedBB.getBoundingBox((float)par2 + f, par3, (float)par4 + f, (float)(par2 + 1) - f, (float)(par3 + 1) - 0.2F, (float)(par4 + 1) - f);
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
	}
	
	@Override
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		if (entityplayer.inventory.getCurrentItem() != null && entityplayer.inventory.getCurrentItem().getItem().shiftedIndex == TooManyPlants.objs.itemgoldenshears.shiftedIndex)
		{
			return;
		}
		entityplayer.knockBack(null, 1, i - entityplayer.posX, k - entityplayer.posZ);
		entityplayer.attackEntityFrom(DamageSource.cactus, 10);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
	{
		if (entity instanceof EntityLiving)
		{
			entity.attackEntityFrom(DamageSource.cactus, 1);
			((EntityLiving) entity).knockBack(null, 3, i - entity.posX, k - entity.posZ);
		}
	}
	
	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		super.onNeighborBlockChange(world, i, j, k, l);
		checkBlock(world, i + 1, j, k);
		checkBlock(world, i - 1, j, k);
		checkBlock(world, i, j + 1, k);
		checkBlock(world, i, j, k + 1);
		checkBlock(world, i, j, k - 1);
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
	
	public static void setIfNetherrack(World world, int i, int j, int k)
	{
		if (world.getBlockId(i, j, k) == Block.netherrack.blockID)
		{
			world.setBlock(i, j, k, TooManyPlants.objs.blockfrozennetherrack.blockID);
		}
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