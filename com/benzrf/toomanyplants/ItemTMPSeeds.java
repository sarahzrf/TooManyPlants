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
	public boolean tryPlaceIntoWorld(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float i2, float j2, float k2)
	{
		if (par3World.getBlockId(par4, par5, par6) != Block.snow.blockID)
		{
			if (par7 == 0)
			{
				par5--;
			}

			if (par7 == 1)
			{
				par5++;
			}

			if (par7 == 2)
			{
				par6--;
			}

			if (par7 == 3)
			{
				par6++;
			}

			if (par7 == 4)
			{
				par4--;
			}

			if (par7 == 5)
			{
				par4++;
			}

			if (!par3World.isAirBlock(par4, par5, par6))
			{
				return false;
			}
		}

		if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6))
		{
			return false;
		}

		if (blockType.canPlaceBlockAt(par3World, par4, par5, par6))
		{
			par1ItemStack.stackSize--;
			par3World.setBlockWithNotify(par4, par5, par6, blockType.blockID);
		}

		return true;
	}
	
	@Override
	public String getTextureFile()
	{
		return "/com/benzrf/toomanyplants/resources/plantssheet.png";
	}
	
	private final Block blockType;
}