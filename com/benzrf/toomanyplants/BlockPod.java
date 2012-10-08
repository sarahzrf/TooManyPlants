package com.benzrf.toomanyplants;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockPod extends Block
{
	protected BlockPod(int i, int j)
	{
		super(i, j, Material.plants);
	}
	
	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int l)
	{
//		dropItems(world, i, j, k);
		if (world.getBlockMetadata(i, j - 1, k) == 0)
		{
			world.setBlock(i, j + 1, k, 0);
//			dropItems(world, i, j + 1, k);
		}
		else
		{
			world.setBlock(i, j - 1, k, 0);
//			dropItems(world, i, j - 1, k);
		}
	}
	
	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int i, int j, int k, int l, int fortune)
	{
		ArrayList<ItemStack> a = new ArrayList<ItemStack>();
		Random r = new Random();
		int limit = r.nextInt(7) + 4;
		for (int i1 = 0; i1 <= limit; i1++)
		{
			a.add(new ItemStack(Item.ingotGold, 1));
		}
		limit = r.nextInt(7) + 4;
		for (int i1 = 0; i1 <= limit; i1++)
		{
			a.add(new ItemStack(Item.ingotIron, 1));
		}
		limit = r.nextInt(7) + 4;
		for (int i1 = 0; i1 <= limit; i1++)
		{
			a.add(new ItemStack(TooManyPlants.objs.itembean, 1));
		}
		return a;
	}
	
	@Override
	public String getTextureFile()
	{
		return "/com/benzrf/toomanyplants/resources/plantssheet.png";
	}
}
