package com.benzrf.toomanyplants;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySmallFireball;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemFireLauncher extends Item
{
	protected ItemFireLauncher(int i)
	{
		super(i);
		setMaxStackSize(1);
		setMaxDamage(10);
	}
	
//	@Override
//	public boolean tryPlaceIntoWorld(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
//	{
//		par2EntityPlayer.sendChatToPlayer(Integer.toString(par3World.getBlockId(par4, par5, par6)));
//		return false;
//	}
	
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
		return "/com/benzrf/toomanyplants/resources/plantssheet.png";
	}
}
