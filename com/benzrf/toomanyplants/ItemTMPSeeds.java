package com.benzrf.toomanyplants;

import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemTMPSeeds extends Item
{
	protected ItemTMPSeeds(int par1, Block b)
	{
		super(par1);
		blockType = b;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float par8, float par9, float par10)
	{
		if (world.getBlockId(i, j, k) != Block.snow.blockID)
		{
			if (l == 0)
			{
				--j;
			}

			if (l == 1)
			{
				++j;
			}

			if (l == 2)
			{
				--k;
			}

			if (l == 3)
			{
				++k;
			}

			if (l == 4)
			{
				--i;
			}

			if (l == 5)
			{
				++i;
			}

			if (!world.isAirBlock(i, j, k))
			{
				return false;
			}
		}

		if (!entityplayer.func_82247_a(i, j, k, l, itemstack))
		{
			return false;
		}
		else
		{
			if (Block.redstoneWire.canPlaceBlockAt(world, i, j, k))
			{
				--itemstack.stackSize;
				world.setBlockWithNotify(i, j, k, blockType.blockID);
			}

			return true;
		}
	}
	
	@Override
	public String getTextureFile()
	{
		return TooManyPlants.textureFile;
	}
	
	private final Block blockType;
}