package com.benzrf.toomanyplants;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFireLauncher extends Item
{
	protected ItemFireLauncher(int i)
	{
		super(i);
		setMaxStackSize(1);
		setMaxDamage(10);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if (entityplayer.inventory.hasItem(Item.fireballCharge.shiftedIndex))
		{
			int yAdd;
			if (world.isRemote)
			{
				yAdd = 0;
			}
			else
			{
				yAdd = 1;
			}
			EntitySmallFireball esf = new EntitySmallFireball(world);
			esf.setPositionAndRotation(entityplayer.posX, entityplayer.posY + yAdd, entityplayer.posZ, entityplayer.rotationYaw, entityplayer.rotationPitch);
			esf.shootingEntity = entityplayer;
			world.spawnEntityInWorld(esf);
			esf.motionX = entityplayer.getLookVec().xCoord;
			esf.motionY = entityplayer.getLookVec().yCoord;
			esf.motionZ = entityplayer.getLookVec().zCoord;
			esf.accelerationX = esf.motionX * 0.1D;
			esf.accelerationY = esf.motionY * 0.1D;
			esf.accelerationZ = esf.motionZ * 0.1D;
			world.playAuxSFX(1009, (int) entityplayer.posX, (int) entityplayer.posY, (int) entityplayer.posZ, 0);
			entityplayer.inventory.consumeInventoryItem(Item.fireballCharge.shiftedIndex);
			itemstack.damageItem(1, entityplayer);
		}
		return itemstack;
	}
	
	@Override
	public String getTextureFile()
	{
		return TooManyPlants.textureFile;
	}
}
