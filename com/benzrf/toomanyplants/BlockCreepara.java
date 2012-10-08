package com.benzrf.toomanyplants;

import java.util.Random;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityArrow;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockCreepara extends Block
{
	protected BlockCreepara(int i, int t1p, int t2p)
	{
		super(i, Material.pumpkin);
		t1 = t1p;
		t2 = t2p;
	}
	
	@Override
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		if (isCoveredWithWater(world, i, j, k))
		{
			return;
		}
		world.setBlockWithNotify(i, j, k, 0);
		world.createExplosion(null, i, j, k, 3);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
	{
		super.onEntityCollidedWithBlock(world, i, j, k, entity);
		if (!(entity instanceof EntityLiving) && !(entity instanceof EntityArrow))
		{
			return;
		}
		if (isCoveredWithWater(world, i, j, k))
		{
			return;
		}
		world.setBlockWithNotify(i, j, k, 0);
		world.createExplosion(null, i, j, k, 3);
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int l, float i2, float j2, float k2)
	{
		if (entityplayer.inventory.getCurrentItem() != null && entityplayer.inventory.getCurrentItem().getItem().equals(Item.gunpowder))
		{
			if (world.getBlockMetadata(i, j, k) <= 3)
			{
				world.setBlockMetadataWithNotify(i, j, k, world.getBlockMetadata(i, j, k) + 1);
				entityplayer.inventory.getCurrentItem().stackSize--;
				updateTick(world, i, j, k, world.rand);
				return true;
			}
			else
			{
				sparkle(world, i, j, k);
				entityplayer.inventory.getCurrentItem().stackSize--;
				world.setBlockMetadataWithNotify(i, j, k, 0);
				int count = 2;
				for (int x = -1; x <= 1; x++)
				{
					for (int y = -1; y <= 1; y++)
					{
						for (int z = -1; z <= 1; z++)
						{
							if (world.getBlockId(i + x, j + y, k + z) == Block.leaves.blockID && world.rand.nextInt(1) == 0 && canThisPlantGrowOnThisBlockID(world.getBlockId(i + x, j + y - 1, k + z)))
							{
								world.setBlockAndMetadataWithNotify(i + x, j + y, k + z, blockID, 0);
								sparkle(world, i + x, j + y, k + z);
								count--;
								if (count <= -1)
								{
									return true;
								}
							}
						}
					}
				}
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return super.canPlaceBlockAt(par1World, par2, par3, par4) && canThisPlantGrowOnThisBlockID(par1World.getBlockId(par2, par3 - 1, par4));
	}
	
	protected boolean canThisPlantGrowOnThisBlockID(int par1)
	{
		return par1 == Block.grass.blockID || par1 == Block.dirt.blockID || par1 == Block.tilledField.blockID;
	}
	
	@Override
	public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		return canThisPlantGrowOnThisBlockID(par1World.getBlockId(par2, par3 - 1, par4));
	}
	
	@Override
	public int getBlockTextureFromSide(int i)
	{
		if (i == 0 || i == 1)
		{
			return t1;
		}
		else
		{
			return t2;
		}
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		float s = 0.03F;
		float s2 = 0.97F;
		return AxisAlignedBB.getBoundingBox(i + s, j, k + s, i + s2, j + s2, k + s2);
	}
	
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		if (!canBlockStay(par1World, par2, par3, par4))
		{
			par1World.setBlockWithNotify(par2, par3, par4, 0);
			if (isCoveredWithWater(par1World, par2, par3, par4))
			{
				dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
				return;
			}
			par1World.createExplosion(null, par2, par3, par4, 3);
		}
	}
	
	@Override
	public void breakBlock(World world, int i, int j, int k, int l, int m)
	{
		if (isCoveredWithWater(world, i, j, k))
		{
			return;
		}
		world.setBlockWithNotify(i, j, k, 0);
		world.createExplosion(null, i, j, k, 3);
	}
	
	public boolean isCoveredWithWater(World world, int i, int j, int k)
	{
		int sideCount = 0;
		if (world.getBlockId(i + 1, j, k) == 8 || world.getBlockId(i + 1, j, k) == 9)
		{
			sideCount++;
		}
		if (world.getBlockId(i, j + 1, k) == 8 || world.getBlockId(i, j + 1, k) == 9)
		{
			sideCount++;
		}
		if (world.getBlockId(i, j, k + 1) == 8 || world.getBlockId(i, j, k + 1) == 9)
		{
			sideCount++;
		}
		if (world.getBlockId(i - 1, j, k) == 8 || world.getBlockId(i - 1, j, k) == 9)
		{
			sideCount++;
		}
		if (world.getBlockId(i, j, k - 1) == 8 || world.getBlockId(i, j, k - 1) == 9)
		{
			sideCount++;
		}
		return sideCount == 5;
	}
	
	private void sparkle(World par1World, int par2, int par3, int par4)
	{
		Random random = par1World.rand;
		double d = 0.0625D;

		for (int i = 0; i < 6; i++)
		{
			double d1 = (float)par2 + random.nextFloat();
			double d2 = (float)par3 + random.nextFloat();
			double d3 = (float)par4 + random.nextFloat();

			if (i == 0 && !par1World.isBlockOpaqueCube(par2, par3 + 1, par4))
			{
				d2 = (double)(par3 + 1) + d;
			}

			if (i == 1 && !par1World.isBlockOpaqueCube(par2, par3 - 1, par4))
			{
				d2 = (double)(par3 + 0) - d;
			}

			if (i == 2 && !par1World.isBlockOpaqueCube(par2, par3, par4 + 1))
			{
				d3 = (double)(par4 + 1) + d;
			}

			if (i == 3 && !par1World.isBlockOpaqueCube(par2, par3, par4 - 1))
			{
				d3 = (double)(par4 + 0) - d;
			}

			if (i == 4 && !par1World.isBlockOpaqueCube(par2 + 1, par3, par4))
			{
				d1 = (double)(par2 + 1) + d;
			}

			if (i == 5 && !par1World.isBlockOpaqueCube(par2 - 1, par3, par4))
			{
				d1 = (double)(par2 + 0) - d;
			}

			if (d1 < (double)par2 || d1 > (double)(par2 + 1) || d2 < 0.0D || d2 > (double)(par3 + 1) || d3 < (double)par4 || d3 > (double)(par4 + 1))
			{
				par1World.spawnParticle("reddust", d1, d2, d3, 0.0D, 10.0D, 0.0D);
			}
		}
	}
	
	@Override
	public String getTextureFile()
	{
		return "/com/benzrf/toomanyplants/resources/plantssheet.png";
	}
	
	int t1;
	int t2;
}