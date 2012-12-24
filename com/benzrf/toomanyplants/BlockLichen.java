package com.benzrf.toomanyplants;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class BlockLichen extends BlockFlower2
{
	protected BlockLichen(int i, int j)
	{
		super(i, j);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
		setTickRandomly(TooManyPlants.objs.enableLichen);
		Block.setBurnProperties(blockID, 1000, 1000);
	}
	
	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{
		if (random.nextInt(15) != 0 || world.getBlockId(i, j, k) != blockID || world.getBlockMetadata(i, j, k) > 5)
		{
			return;
		}
		for (int i2 = i - 1; i2 <= i + 1; i2++)
		{
			for (int k2 = i - k; k2 <= k + 1; k2++)
			{
				if (canBlockStay(world, i2, j, k2) && world.isAirBlock(i2, j, k2) && random.nextInt(9) == 0)
				{
					world.setBlockAndMetadataWithNotify(i2, j, k2, blockID, world.getBlockMetadata(i, j, k) + 1);
					return;
				}
			}
		}
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public int getRenderType()
	{
		return 0;
	}
	
	@Override
	public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		return (par1World.getFullBlockLightValue(par2, par3, par4) >= 8 || par1World.canBlockSeeTheSky(par2, par3, par4)) && canThisPlantGrowOnThisBlockID(par1World.getBlockId(par2, par3 - 1, par4));
	}
	
	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int i)
	{
		return i == Block.stone.blockID || i == Block.cobblestone.blockID || i == Block.cobblestoneMossy.blockID;
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return TooManyPlants.objs.itemlichen.shiftedIndex;
	}
}