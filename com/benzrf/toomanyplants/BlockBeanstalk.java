package com.benzrf.toomanyplants;

import java.util.Random;


import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockBeanstalk extends Block
{
	protected BlockBeanstalk(int i, int j)
	{
		super(i, j, Material.ground);
		float f = 0.375F;
		setBlockBounds(f, 0, f, 1 - f, 1, 1 - f);
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return 1;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		float f = 0.375F;
		return AxisAlignedBB.getBoundingBox((float)i + f, j, (float)k + f, (float)(i + 1) - f, (float)(j + 1) - f, (float)(k + 1) - f);
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
	{
		float f = 0.375F;
		return AxisAlignedBB.getBoundingBox((float)i + f, j, (float)k + f, (float)(i + 1) - f, (float)(j + 1) - f, (float)(k + 1) - f);
	}
	
	public void grow(World world, int i, int j, int k)
	{
		world.scheduleBlockUpdate(i, j, k, blockID, 5);
	}
	
	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{
		if (j < 253)
		{
			if (world.getBlockId(i, j + 1, k) == 0)
			{
				world.setBlockAndMetadataWithNotify(i, j + 1, k, blockID, world.getBlockMetadata(i, j, k));
				world.scheduleBlockUpdate(i, j + 1, k, blockID, 5);
			}
		}
		else if (world.getBlockMetadata(i, j, k) == 1)
		{
			world.setBlockMetadata(i, j, k, 0);
			world.setBlockAndMetadataWithNotify(i, j + 1, k, TooManyPlants.objs.blockpod.blockID, 1);
			world.setBlockWithNotify(i, j + 2, k, TooManyPlants.objs.blockpod.blockID);
		}
	}
	
	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
	{
		entity.onGround = true;
		entity.fallDistance = 0;
	}
	
//	public void onBlockRemoval(World world, int i, int j, int k)
//	{
//		world.markBlocksDirty(i, j, k, i, j, k);
//		if (world.getBlockId(i, j + 1, k) == blockID)
//		{
//			world.setBlock(i, j + 1, k, 0);
//		}
//	}
	
	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		int id = world.getBlockId(i, j - 1, k);
		if (id != blockID && id != Block.dirt.blockID && id != Block.grass.blockID && id != Block.tilledField.blockID)
		{
			world.setBlockWithNotify(i, j, k, 0);
		}
	}
	
	@Override
	public String getTextureFile()
	{
		return "/com/benzrf/toomanyplants/resources/plantssheet.png";
	}
}