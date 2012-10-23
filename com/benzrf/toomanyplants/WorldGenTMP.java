package com.benzrf.toomanyplants;

import java.util.Random;

import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenMinable;
import net.minecraft.src.WorldType;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenTMP implements IWorldGenerator
{
	@Override
	public void generate(Random random, int x, int z, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		TMPPlantGen gen = new TMPPlantGen();
		
		x *= 16;
		z *= 16;
		
		if (world.getWorldInfo().getTerrainType().equals(WorldType.FLAT))
		{
			return;
		}
		
		if (world.getBiomeGenForCoords(x, z).equals(BiomeGenBase.hell))
		{
			gen.plantBlockId = TooManyPlants.objs.blockbonefinger.blockID;
			gen.limit = 1;
			gen.metadata = 4;
			gen.generate(world, random, x + 8, random.nextInt(120), z + 8);
			
			if (random.nextInt(4) == 0)
			{
				gen.plantBlockId = TooManyPlants.objs.blockchillspike.blockID;
				gen.limit = 1;
				gen.metadata = 0;
				gen.run = new GenRunnable(){
					@Override
					public void run(World world, int i, int j, int k)
					{
						for (int[] t : BlockChillspike.toFreeze)
						{
							BlockChillspike.setIfNetherrack(world, i + t[0], j + t[1], k + t[2]);
						}
					}
				};
				gen.generate(world, random, x + 8, random.nextInt(120), z + 8);
			}
			return;
		}
		
		gen.plantBlockId = TooManyPlants.objs.blockpreglowflower.blockID;
		gen.limit = 3;
		for (int i = 0; i <= 6; i++)
		{
			gen.generate(world, random, x + 8, random.nextInt(255), z + 8);
		}
		
		if (!world.getBiomeGenForCoords(x, z).equals(BiomeGenBase.iceMountains) && !world.getBiomeGenForCoords(x, z).equals(BiomeGenBase.taigaHills))
		{
			gen.plantBlockId = TooManyPlants.objs.blockdawnflower.blockID;
			gen.limit = 1;
			for (int i = 0; i <= 2; i++)
			{
				gen.generate(world, random, x + 8, 90 + random.nextInt(90), z + 8);
			}
		}
		
		if (world.getBiomeGenForCoords(x, z).equals(BiomeGenBase.iceMountains) || world.getBiomeGenForCoords(x, z).equals(BiomeGenBase.taigaHills) || world.getBiomeGenForCoords(x, z).equals(BiomeGenBase.taigaHills))
		{
			gen.plantBlockId = TooManyPlants.objs.blocklotus.blockID;
			gen.limit = 1;
			gen.generate(world, random, x + 8, 90 + random.nextInt(90), z + 8);
		}
		
		if (random.nextInt(1) == 0)
		{
			gen.plantBlockId = TooManyPlants.objs.blockbeanplant.blockID;
			gen.generate(world, random, x + 8, random.nextInt(255), z + 8);
		}
		
		if ((world.getBiomeGenForCoords(x, z).equals(BiomeGenBase.desert) || world.getBiomeGenForCoords(x, z).equals(BiomeGenBase.desertHills)) && random.nextInt(4) == 0)
		{
			gen.plantBlockId = TooManyPlants.objs.blockevilflower.blockID;
			gen.limit = 1;
			gen.generate(world, random, x + 8, random.nextInt(255), z + 8);
		}
		
		if ((world.getBiomeGenForCoords(x, z).equals(BiomeGenBase.taiga) || world.getBiomeGenForCoords(x, z).equals(BiomeGenBase.taigaHills) || world.getBiomeGenForCoords(x, z).equals(BiomeGenBase.icePlains) || world.getBiomeGenForCoords(x, z).equals(BiomeGenBase.iceMountains)) && random.nextInt(1) == 0)
		{
			gen.plantBlockId = TooManyPlants.objs.blocklily.blockID;
			gen.limit = 1;
			gen.generate(world, random, x + 8, random.nextInt(255), z + 8);
		}
		
		if (world.getBiomeGenForCoords(x, z).equals(BiomeGenBase.swampland) && random.nextInt(2) == 0)
		{
			gen.plantBlockId = TooManyPlants.objs.blockpitcherplant.blockID;
			gen.limit = 1;
			gen.generate(world, random, x + 8, random.nextInt(255), z + 8);
		}
		
		if (world.getBiomeGenForCoords(x, z).equals(BiomeGenBase.forest) || world.getBiomeGenForCoords(x, z).equals(BiomeGenBase.forestHills))
		{
			for (int i = 0; i <= 2; i++)
			{
				gen.plantBlockId = TooManyPlants.objs.blockberrybush.blockID;
				gen.limit = 3;
				gen.generate(world, random, x + 8, random.nextInt(255), z + 8);
			}
		}
		
		if (world.getBiomeGenForCoords(x, z).equals(BiomeGenBase.swampland))
		{
			gen.plantBlockId = TooManyPlants.objs.blockfireflower.blockID;
			gen.limit = 3;
			gen.generate(world, random, x + 8, random.nextInt(255), z + 8);
		}
		
		gen.plantBlockId = TooManyPlants.objs.blockairflower.blockID;
		gen.limit = 4;
		if (random.nextInt(2) == 0)
		{
			gen.generate(world, random, x + 8, random.nextInt(215) + 40, z + 8);
		}
		
		gen.plantBlockId = TooManyPlants.objs.blocklichen.blockID;
		gen.limit = 5;
		for (int i = 0; i <= 10; i++)
		{
			gen.generate(world, random, x + 8, 13 * i + 5, z + 8);
		}
		
		if (world.getBiomeGenForCoords(x, z).equals(BiomeGenBase.desert) || world.getBiomeGenForCoords(x, z).equals(BiomeGenBase.desertHills))
		{
			gen.plantBlockId = TooManyPlants.objs.blockpricklypear.blockID;
			gen.limit = 1;
			for (int i = 0; i <= 2; i++)
			{
				gen.generate(world, random, x + 8, random.nextInt(170), z + 8);
			}
		}
		
		if (world.getBiomeGenForCoords(x, z).equals(BiomeGenBase.jungle) || world.getBiomeGenForCoords(x, z).equals(BiomeGenBase.jungleHills))
		{
			gen.plantBlockId = TooManyPlants.objs.blockcreepara.blockID;
			gen.limit = 1;
			gen.replace = Block.leaves.blockID;
			for (int i = 0; i <= 10; i++)
			{
				gen.generate(world, random, x + 8, random.nextInt(170), z + 8);
			}
		}
		
		if (world.getSeed() == 40507131 && !world.getBiomeGenForCoords(x, z).equals(BiomeGenBase.ocean))
		{
			WorldGenMinable w = new WorldGenMinable(Block.oreDiamond.blockID, 25);
			for (int i = 0; i <= 10; i++)
			{
				w.generate(world, random, x + random.nextInt(16), random.nextInt(25) + 30, z + random.nextInt(16));
			}
		}
	}
	
	class TMPPlantGen
	{
		public void generate(World world, Random random, int i, int j, int k)
		{
			if (replace != 0)
			{
				generateR(world, random, i, j, k);
				return;
			}
			int count = 0;
			for (int l = 0; l < 64; l++)
			{
				if (count >= limit)
				{
					return;
				}
				int i1 = (i + random.nextInt(8)) - random.nextInt(8);
				int j1 = (j + random.nextInt(4)) - random.nextInt(4);
				int k1 = (k + random.nextInt(8)) - random.nextInt(8);
				if ((world.isAirBlock(i1, j1, k1) || (world.getBlockId(i1, j1, k1) == Block.snow.blockID)) && (Block.blocksList[plantBlockId]).canBlockStay(world, i1, j1, k1))
				{
					world.setBlockAndMetadata(i1, j1, k1, plantBlockId, metadata);
					if (run != null)
					{
						run.run(world, i1, j1, k1);
					}
					count++;
//					System.out.println("Generated a " + Block.blocksList[plantBlockId].getBlockName() + " at " + i1 + ", " + j1 + ", " + k1);
				}
			}
		}
		
		private void generateR(World world, Random random, int i, int j, int k)
		{
			int count = 0;
			for (int l = 0; l < 64; l++)
			{
				if (count > limit)
				{
					return;
				}
				int i1 = (i + random.nextInt(8)) - random.nextInt(8);
				int j1 = (j + random.nextInt(4)) - random.nextInt(4);
				int k1 = (k + random.nextInt(8)) - random.nextInt(8);
				if (world.getBlockId(i1, j1, k1) == replace && world.getBlockId(i1, j1 + 1, k1) == replace && (Block.blocksList[plantBlockId]).canBlockStay(world, i1, j1, k1))
				{
					world.setBlockAndMetadata(i1, j1, k1, plantBlockId, metadata);
					count++;
				}
			}
		}

		public int limit = 0;
		public int metadata = 0;
		public int replace = 0;
		public GenRunnable run;
		public int plantBlockId = 0;
	}
}
class GenRunnable
{
	public void run(World world, int i, int j, int k)
	{
	}
}