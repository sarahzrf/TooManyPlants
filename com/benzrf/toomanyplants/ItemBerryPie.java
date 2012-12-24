package com.benzrf.toomanyplants;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemBerryPie extends ItemFood
{
	protected ItemBerryPie(int i, int t1, int t2, int t3)
	{
		super(i, 1, 0.3F, false);
		setMaxStackSize(1);
		i1 = t1;
		i2 = t2;
		i3 = t3;
	}
	
	@Override
	public ItemStack onFoodEaten(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if (itemstack.getItemDamage() > 1)
		{
			--itemstack.stackSize;
		}
		else
		{
			itemstack.setItemDamage(itemstack.getItemDamage() + 1);
		}
		entityplayer.getFoodStats().addStats(this);
		entityplayer.heal(2);
		entityplayer.addPotionEffect(new PotionEffect(1, 1800, 1));
		world.playSoundAtEntity(entityplayer, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		return itemstack;
	}
	
	@Override
	public int getIconFromDamage(int i)
	{
		if (i == 0)
		{
			return i1;
		}
		else if (i == 1)
		{
			return i2;
		}
		return i3;
	}
	
	@Override
	public String getTextureFile()
	{
		return TooManyPlants.textureFile;
	}
	
	int i1;
	int i2;
	int i3;
}
