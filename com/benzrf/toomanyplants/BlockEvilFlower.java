package com.benzrf.toomanyplants;

import java.util.Random;


import net.minecraft.src.Block;
import net.minecraft.src.BlockFlower;
import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.World;

public class BlockEvilFlower extends BlockFlower
{
	protected BlockEvilFlower(int i, int j)
	{
		super(i, j);
	}
	
	@Override
	public int idDropped(int i, Random random, int j)
	{
		return TooManyPlants.objs.itemevilflowerpetal.shiftedIndex;
	}
	
	@Override
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		if (entityplayer.inventory.getCurrentItem() != null && entityplayer.inventory.getCurrentItem().getItem().equals(Item.shears))
		{
			entityplayer.attackEntityFrom(DamageSource.magic, 10);
		}
		else
		{
			entityplayer.attackEntityFrom(DamageSource.magic, 20);
		}
	}
	
	@Override
	protected boolean canThisPlantGrowOnThisBlockID(int i)
	{
		return i == Block.sand.blockID || i == Block.stone.blockID;
	}
	
	@Override
	public String getTextureFile()
	{
		return TooManyPlants.textureFile;
	}
}