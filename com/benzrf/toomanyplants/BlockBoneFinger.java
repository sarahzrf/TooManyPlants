package com.benzrf.toomanyplants;

import java.util.Random;


import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.BlockFlower;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class BlockBoneFinger extends BlockFlower
{
	protected BlockBoneFinger(int i, int t1p, int t2p, int t3p)
	{
		super(i, t1p);
		setTickRandomly(true);
		float f = 0.5F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
		t1 = t1p;
		t2 = t2p;
		t3 = t3p;
	}

	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int i)
	{
		return i == Block.netherrack.blockID;
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{
		int l = world.getBlockMetadata(i, j, k);
		if (l < 3)
		{
			if ((!world.getBiomeGenForCoords(i, k).equals(BiomeGenBase.hell) && random.nextInt(7) == 0) || (world.getBiomeGenForCoords(i, k).equals(BiomeGenBase.hell) && random.nextInt(4) == 0))
			{
				l++;
				world.setBlockMetadataWithNotify(i, j, k, l);
				world.markBlocksDirty(i, j, k, i, j, k);
			}
		}
		super.updateTick(world, i, j, k, random);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		if (j >= 3)
		{
			return t3;
		}
		if (j > 0)
		{
			return t2;
		}
		else
		{
			return t1;
		}
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int i, int j, int k, int l, float f, int i1)
	{
		dropBlockAsItem_do(world, i, j, k, new ItemStack(TooManyPlants.objs.itemboneseed));
		int j1 = l == 3 ? world.rand.nextInt(4) : 0;
		for (int k1 = 0; k1 < j1; k1++)
		{
			dropBlockAsItem_do(world, i, j, k, new ItemStack(Item.bone));
		}
	}
	
	@Override
	public boolean canBlockStay(World world, int i, int j, int k)
	{
		return world.getFullBlockLightValue(i, j, k) <= 12 && canThisPlantGrowOnThisBlockID(world.getBlockId(i, j - 1, k));
	}
	
	@Override
	public String getTextureFile()
	{
		return TooManyPlants.textureFile;
	}
	
	int t1 = 1;
	int t2 = 1;
	int t3 = 1;
}