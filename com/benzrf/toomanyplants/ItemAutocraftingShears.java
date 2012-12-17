package com.benzrf.toomanyplants;

import java.util.*;
import java.util.Map.Entry;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.ChunkPosition;
import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityList;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemShears;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraftforge.common.ForgeChunkManager;

//import com.benzrf.toomanyplants.

public class ItemAutocraftingShears extends ItemShears
{
	public ItemAutocraftingShears(int par1)
	{
		super(par1);
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world, int id, int i, int j, int k, EntityLiving entityliving)
	{
		if (entityliving instanceof EntityPlayer)
		{
			EntityPlayer p = (EntityPlayer) entityliving;
			ItemStack item = p.inventory.getStackInSlot(p.inventory.currentItem - 1);
			if (item != null && AutocraftingManager.getArray(item.itemID) != null)
			{
				Set<ChunkPosition> blocks = new HashSet<ChunkPosition>();
				AutocraftingEntry[] e = AutocraftingManager.getArray(p.inventory.getStackInSlot(p.inventory.currentItem - 1).itemID);
				for (AutocraftingEntry a : e)
				{
					if (!hasIngredient(a, p, world, i, j, k)) return super.onBlockDestroyed(itemstack, world, id, i, j, k, entityliving);
				}
				for (AutocraftingEntry a : e)
				{
					if (a.remove) removeIngredient(a, p, world, i, j, k);
				}
				p.dropItem(item.itemID, 1);
			}
		}
		
		return super.onBlockDestroyed(itemstack, world, id, i, j, k, entityliving);
	}
	
	private void removeIngredient(AutocraftingEntry a, EntityPlayer p, World w, int i, int j, int k)
	{
		switch (a.type)
		{
		case INVENTORY_ITEM:
			p.inventory.consumeInventoryItem(a.id);
			break;
		case ENTITY:
			List l = w.getEntitiesWithinAABB((Class) EntityList.IDtoClassMapping.get(a.id), AxisAlignedBB.getBoundingBox(i - 5, j - 5, k - 5, i + 5, j + 5, k + 5));
			for (int i2 = 0; i < Math.min(a.quantity, l.size()); i++)
			{
				((EntityLiving) l.get(i2)).attackEntityFrom(DamageSource.magic, 999999);
			}
			break;
		case BLOCK:
			int count = a.quantity;
			for (int xAdd = 1; xAdd >= -1; xAdd--)
			{
				for (int yAdd = 1; yAdd >= -1; yAdd--)
				{
					for (int zAdd = 1; zAdd >= -1; zAdd--)
					{
						if (w.getBlockId(i + xAdd, j + yAdd, k + zAdd) == a.id)
						{
							if (a.metadata == -1 || w.getBlockMetadata(i + xAdd, j + yAdd, k + zAdd) == a.metadata)
							{
								w.setBlockWithNotify(i + xAdd, j + yAdd, k + zAdd, 0);
								if (--count <= 0) return;
							}
						}
					}
				}
			}
			if (count < a.quantity)
			{
				return;
			}
		}
	}
	
	private boolean hasIngredient(AutocraftingEntry a, EntityPlayer p, World w, int i, int j, int k)
	{
		switch (a.type)
		{
		case INVENTORY_ITEM:
			if (!p.inventory.hasItemStack(new ItemStack(a.id, 1, a.metadata)))
			{
				return false;
			}
			break;
		case ENTITY:
			if (w.getEntitiesWithinAABB((Class) EntityList.IDtoClassMapping.get(a.id), AxisAlignedBB.getBoundingBox(i - 5, j - 5, k - 5, i + 5, j + 5, k + 5)).size() < a.quantity)
			{
				return false;
			}
			break;
		case BLOCK:
			int count = 0;
			for (int xAdd = -1; xAdd <= 1; xAdd++)
			{
				for (int yAdd = -1; yAdd <= 1; yAdd++)
				{
					for (int zAdd = -1; zAdd <= 1; zAdd++)
					{
						if (w.getBlockId(i + xAdd, j + yAdd, k + zAdd) == a.id)
						{
							if (a.metadata == -1 || w.getBlockMetadata(i + xAdd, j + yAdd, k + zAdd) == a.metadata)
							{
								count++;
							}
						}
					}
				}
			}
			if (count < a.quantity)
			{
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String getTextureFile()
	{
		return TooManyPlants.textureFile;
	}
}
class AutocraftingManager
{
	public static AutocraftingEntry[] getArray(int id)
	{
		return autocraftingRecipes.get(id) == null ? null : autocraftingRecipes.get(id);
	}
	
	private static Map<Integer, AutocraftingEntry[]> autocraftingRecipes = new HashMap<Integer, AutocraftingEntry[]>();
	
	static
	{
		// bread
		autocraftingRecipes.put(Item.bread.shiftedIndex, new AutocraftingEntry[] {
			new AutocraftingEntry(AET.INVENTORY_ITEM, Block.workbench.blockID, 0, 1, false),
			new AutocraftingEntry(AET.BLOCK, Block.crops.blockID, 7, 3, true)
		});
		// cake
		autocraftingRecipes.put(Item.cake.shiftedIndex, new AutocraftingEntry[] {
			new AutocraftingEntry(AET.INVENTORY_ITEM, Block.workbench.blockID, 0, 0, false),
			new AutocraftingEntry(AET.INVENTORY_ITEM, Item.bucketEmpty.shiftedIndex, 0, 0, false),
			new AutocraftingEntry(AET.ENTITY, 92, 0, 1, false), // 92 = cow
			new AutocraftingEntry(AET.BLOCK, Block.reed.blockID, -1, 2, true),
			new AutocraftingEntry(AET.INVENTORY_ITEM, Item.egg.shiftedIndex, 0, 0, true),
			new AutocraftingEntry(AET.BLOCK, Block.crops.blockID, 7, 3, true)
		});
	}
}

enum AET {INVENTORY_ITEM, ENTITY, BLOCK;} // short for AutocraftingEntryType

class AutocraftingEntry
{
	public AutocraftingEntry(AET type, int id, int metadata, int quantity, boolean remove)
	{
		this.type = type;
		this.id = id;
		this.metadata = metadata;
		this.quantity = quantity;
		this.remove = remove;
	}
	
	public AET type;
	public int id;
	public int metadata;
	public int quantity;
	public boolean remove;
}
