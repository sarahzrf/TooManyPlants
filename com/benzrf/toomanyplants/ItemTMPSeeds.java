package com.benzrf.toomanyplants;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTMPSeeds extends Item
{
	protected ItemTMPSeeds(int par1, Block b)
	{
		super(par1);
		blockType = b;
		soilBlockType = null;
	}
	
	protected ItemTMPSeeds(int par1, Block b, Block b2)
	{
		super(par1);
		blockType = b;
		soilBlockType = b2;
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

		if (!entityplayer.canPlayerEdit(i, j, k, l, itemstack))
		{
			return false;
		}
		else
		{
			if (blockType.canPlaceBlockAt(world, i, j, k))
			{
				if ((soilBlockType == null) || ((soilBlockType != null) && soilBlockType.blockID == world.getBlockId(i, j - 1, k)))
				{
					--itemstack.stackSize;
					world.setBlockWithNotify(i, j, k, blockType.blockID);
				}
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
	private final Block soilBlockType;
}
