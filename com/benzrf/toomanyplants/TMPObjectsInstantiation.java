package com.benzrf.toomanyplants;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class TMPObjectsInstantiation extends TMPObjectsDeclaration
{
	public void preInit(Configuration c)
	{
		initGlowFlower(c);
		initEvilFlower(c);
		initAirFlower(c);
		initBeanstalk(c);
		initBeanPlant(c);
		initPitcherPlant(c);
		initBoneFinger(c);
		initLichen(c);
		initCreepara(c);
		initPricklyPear(c);
		initFireFlower(c);
		initDawnFlower(c);
		initBerryBush(c);
		initLotus(c);
		initLily(c);
		initExtraShears(c);
		initChillspike(c);
	}
	
	void initGlowFlower(Configuration c)
	{
		glowTexture = 0;
		preGlowFlowerId = c.getBlock("preGlowFlowerId", 1255).getInt();
		blockpreglowflower = new BlockFlower2(preGlowFlowerId, glowTexture) {
			@Override
			public int idDropped(int i, Random random, int j)
			{
				return glowFlowerId;
			}
		}.setBlockName("Non-Glowing Glow Flower");
		glowFlowerId = c.getBlock("glowFlowerId", 1254).getInt();
		blockglowflower = new BlockFlower2(glowFlowerId, glowTexture){
			@Override
			public boolean canBlockStay(World world, int i, int j, int k)
			{
				return canThisPlantGrowOnThisBlockID(world.getBlockId(i, j - 1, k));
			}
		}.setLightValue(0.5F).setBlockName("Glow Flower");
	}
	
	void initEvilFlower(Configuration c)
	{
		evilFlowerTexture = 1;
		evilFlowerId = c.getBlock("evilFlowerId", 1253).getInt();
		blockevilflower = new BlockEvilFlower(evilFlowerId, evilFlowerTexture).setBlockName("Evil Flower");
		evilFlowerPetalTexture = 2;
		evilFlowerPetalId = c.getItem("evilFlowerPetalId", 413).getInt();
		itemevilflowerpetal = new Item(evilFlowerPetalId){
			@Override
			public String getTextureFile()
			{
				return TooManyPlants.textureFile;
			}
		}.setIconIndex(evilFlowerPetalTexture).setItemName("Evil Flower Petal").setCreativeTab(CreativeTabs.tabMaterials);
		animationEssenceTexture = 3;
		animationEssenceId = c.getItem("animationEssenceId", 612).getInt();
		itemmagicpowder = new Item(animationEssenceId){
			@Override
			public String getTextureFile()
			{
				return TooManyPlants.textureFile;
			}
		}.setIconIndex(animationEssenceTexture).setItemName("Magic Powder").setCreativeTab(CreativeTabs.tabMaterials);
	}
	
	void initAirFlower(Configuration c)
	{
		airFlowerTexture = 4;
		airFlowerId = c.getBlock("airFlowerId", 1252).getInt();
		blockairflower = new BlockAirFlower(airFlowerId, airFlowerTexture).setBlockName("Air Flower");
		rainstickTexture = 5;
		rainstickId = c.getItem("rainstickId", 6875).getInt();
		itemrainstick = new Item(rainstickId){
			{
				setMaxStackSize(1);
				setMaxDamage(2);
			}
			@Override
			public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
			{
				if (world.isRaining())
				{
					world.getWorldInfo().setRainTime(0);
					world.getWorldInfo().setRaining(false);
					world.getWorldInfo().setThunderTime(0);
					world.getWorldInfo().setThundering(false);
				}
				else
				{
					world.getWorldInfo().setRaining(true);
				}
				entityplayer.swingItem();
				itemstack.damageItem(1, entityplayer);
				return itemstack;
			}
			@Override
			public String getTextureFile()
			{
				return TooManyPlants.textureFile;
			}
		}.setIconIndex(rainstickTexture).setItemName("Rainstick").setCreativeTab(CreativeTabs.tabTools);
	}
	
	void initBeanstalk(Configuration c)
	{
		beanstalkTexture = 6;
		beanstalkId = c.getBlock("beanstalkId", 1251).getInt();
		blockbeanstalk = new BlockBeanstalk(beanstalkId, beanstalkTexture).setHardness(0.5F);
		magicBeanTexture = 7;
		magicBeanId = c.getItem("magicBeanId", 1230).getInt();
		itemmagicbean = new ItemSeeds(magicBeanId, blockbeanstalk.blockID, Block.dirt.blockID){
			@Override
			public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float i2, float j2, float k2)
			{
				boolean toReturn = super.onItemUse(itemstack, entityplayer, world, i, j, k, l, i2, j2, k2);
				if (toReturn)
				{
					world.setBlockMetadata(i, j + 1, k, 1);
					((BlockBeanstalk) blockbeanstalk).grow(world, i, j + 1, k);
				}
				return toReturn;
			}
			@Override
			public String getTextureFile()
			{
				return TooManyPlants.textureFile;
			}
		}.setIconIndex(magicBeanTexture).setItemName("Magic Bean");
		lesserMagicBeanTexture = 8;
		lesserMagicBeanId = c.getItem("lesserMagicBeanId", 975).getInt();
		itemlessermagicbean = new ItemSeeds(lesserMagicBeanId, blockbeanstalk.blockID, Block.dirt.blockID){
			@Override
			public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float i2, float j2, float k2)
			{
				boolean toReturn = super.onItemUse(itemstack, entityplayer, world, i, j, k, l, i2, j2, k2);
				if (toReturn)
				{
					((BlockBeanstalk) blockbeanstalk).grow(world, i, j + 1, k);
				}
				return toReturn;
			}
			@Override
			public String getTextureFile()
			{
				return TooManyPlants.textureFile;
			}
		}.setIconIndex(lesserMagicBeanTexture).setItemName("Lesser Magic Bean");
		podTexture = 9;
		podId = c.getBlock("podId", 1250).getInt();
		blockpod = new BlockPod(podId, podTexture);
	}
	
	void initBeanPlant(Configuration c)
	{
		beanPlantTexture = 10;
		beanPlantId = c.getBlock("beanPlantId", 1249).getInt();
		blockbeanplant = new BlockFlower2(beanPlantId, beanPlantTexture){
			@Override
			public int idDropped(int i, Random random, int j)
			{
				return itembean.shiftedIndex;
			}
			@Override
			public int quantityDropped(Random random)
			{
				return random.nextInt(2) + 1;
			}
		}.setBlockName("Bean Plant");
		beanSproutTexture = 11;
		beanSproutId = c.getBlock("beanSproutId", 1248).getInt();
		blockbeansprout = new BlockBeanSprout(beanSproutId, beanSproutTexture).setBlockName("Bean Sprout");
		beanTexture = 12;
		beanId = c.getItem("beanId", 142).getInt();
		itembean = new ItemTMPSeeds(beanId, blockbeansprout, Block.dirt).setIconIndex(beanTexture).setItemName("Bean");
		beanStewTexture = 13;
		beanStewId = c.getItem("beanStewId", 4444).getInt();
		itembeanstew = new ItemSoup(beanStewId, 20){
			@Override
			public ItemStack onFoodEaten(ItemStack itemstack, World world, EntityPlayer entityplayer)
			{
				entityplayer.heal(10);
				return super.onFoodEaten(itemstack, world, entityplayer);
			}
			@Override
			public String getTextureFile()
			{
				return TooManyPlants.textureFile;
			}
		}.setIconIndex(beanStewTexture).setItemName("Bean Stew");
	}
	
	void initPitcherPlant(Configuration c)
	{
		pitcherPlantTexture = 14;
		pitcherPlantId = c.getBlock("pitcherPlantId", 1247).getInt();
		blockpitcherplant = new BlockFlower2(pitcherPlantId, pitcherPlantTexture){
			@Override
			public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int l, float i2, float j2, float k2)
			{
				world.setBlockMetadata(i, j, k, 3);
				return Block.cauldron.onBlockActivated(world, i, j, k, entityplayer, l, i2, j2, k2);
			}
		}.setBlockName("Pitcher Plant");
	}
	
	void initBoneFinger(Configuration c)
	{
		bonefingerTexture1 = 15;
		bonefingerTexture2 = 16;
		bonefingerTexture3 = 17;
		bonefingerId = c.getBlock("bonefingerId", 1246).getInt();
		blockbonefinger = new BlockBoneFinger(bonefingerId, bonefingerTexture1, bonefingerTexture2, bonefingerTexture3).setBlockName("Bonefinger");
		boneseedTexture = 18;
		boneseedId = c.getItem("boneseedId", 777).getInt();
		itemboneseed = new ItemTMPSeeds(boneseedId, blockbonefinger, Block.netherrack).setIconIndex(boneseedTexture).setItemName("Boneseed");
	}
	
	void initLichen(Configuration c)
	{
		enableLichen = c.get("misc", "enableLichen", true).getBoolean(true);
		lichenBlockTexture = 19;
		lichenId = c.getBlock("lichenId", 1245).getInt();
		blocklichen = new BlockLichen(lichenId, lichenBlockTexture).setBlockName("Lichen");
		lichenItemTexture = 20;
		lichenItemId = c.getItem("lichenItemId", 314).getInt();
		itemlichen = new ItemTMPSeeds(lichenItemId, blocklichen).setIconIndex(lichenItemTexture).setItemName("Lichen");
		roastedLichenTexture = 21;
		roastedLichenId = c.getItem("roastedLichenId", 4130).getInt();
		itemroastedlichen = new ItemRoastedLichen(roastedLichenId).setIconIndex(roastedLichenTexture).setItemName("Roasted Lichen").setCreativeTab(CreativeTabs.tabFood);
	}
	
	void initCreepara(Configuration c)
	{
		creeparaTBTexture = 22;
		creeparaSideTexture = 23;
		creeparaId = c.getBlock("creeparaId", 1244).getInt();
		blockcreepara = new BlockCreepara(creeparaId, creeparaTBTexture, creeparaSideTexture).setBlockName("Creepara").setHardness(0.2F).setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	void initPricklyPear(Configuration c)
	{
		pricklyPearTexture = 24;
		pricklyPearId = c.getBlock("pricklyPearId", 1243).getInt();
		blockpricklypear = new BlockPricklyPear(pricklyPearId, pricklyPearTexture, Material.cactus).setBlockName("Prickly Pear").setHardness(0.4F).setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	void initFireFlower(Configuration c)
	{
		fireflowerTexture = 25;
		fireflowerId = c.getBlock("fireflowerId", 1242).getInt();
		blockfireflower = new BlockFlower2(fireflowerId, fireflowerTexture) {
			@Override
			public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
			{
				double d = (float)par2 + 0.5F;
				double d1 = (float)par3 + 1F;
				double d2 = (float)par4 + 0.5F;
				double d3 = 0.2199999988079071D;
				double d4 = 0.27000001072883606D;
				par1World.spawnParticle("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
			}
		}.setBlockName("Fire Flower");
		fireLauncherTexture = 26;
		fireLauncherId = c.getItem("fireLauncherId", 783).getInt();
		itemfirelauncher = new ItemFireLauncher(fireflowerId).setIconIndex(fireLauncherTexture).setItemName("Firelauncher").setCreativeTab(CreativeTabs.tabCombat); 
	}
	
	void initDawnFlower(Configuration c)
	{
		dawnflowerTexture = 39;
		dawnflowerId = c.getBlock("dawnflowerId", 1241).getInt();
		blockdawnflower = new BlockFlower2(dawnflowerId, dawnflowerTexture).setBlockName("Dawnflower");
	}
	
	void initBerryBush(Configuration c)
	{
		berryBushTexture = 27;
		berryBushId = c.getBlock("berryBushId", 1240).getInt();
		blockberrybush = new BlockFlower2(berryBushId, berryBushTexture){
			@Override
			public int idDropped(int i, Random random, int j)
			{
				return itemberry.shiftedIndex;
			}
			@Override
			public int quantityDropped(Random random)
			{
				return random.nextInt(4) + 1;
			}
			@Override
			public int getRenderType()
			{
				return 6;
			}
			@Override
			public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
			{
				float f = 0.2F;
				return AxisAlignedBB.getBoundingBox((float)par2 + f, par3, (float)par4 + f, (float)(par2 + 1) - f, (float)(par3 + 1) - 0.05F, (float)(par4 + 1) - f);
			}
			@Override
			public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
			{
				return getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
			}
		}.setBlockName("Berry Bush");
		berryTexture = 28;
		berryId = c.getItem("berryId", 9203).getInt();
		itemberry = new ItemFood(berryId, 0, 0, false){
			@Override
			public int getMaxItemUseDuration(ItemStack par1ItemStack)
			{
				return 10;
			}
			@Override
			public ItemStack onFoodEaten(ItemStack itemstack, World world, EntityPlayer entityplayer)
			{
				entityplayer.heal(1);
				entityplayer.addPotionEffect(new PotionEffect(1, 1200, 1));
				return super.onFoodEaten(itemstack, world, entityplayer);
			}
			@Override
			public String getTextureFile()
			{
				return TooManyPlants.textureFile;
			}
		}.setAlwaysEdible().setItemName("Berry").setIconIndex(berryTexture);
		berryPieTexture1 = 29;
		berryPieTexture2 = 30;
		berryPieTexture3 = 31;
		berryPieId = c.getItem("berryPieId", 7209).getInt();
		itemberrypie = new ItemBerryPie(berryPieId, berryPieTexture1, berryPieTexture2, berryPieTexture3).setItemName("Berry Pie");
	}
	
	void initLotus(Configuration c)
	{
		lotusTexture = 32;
		lotusId = c.getBlock("lotusId", 1239).getInt();
		blocklotus = new BlockFlower2(lotusId, lotusTexture){
			@Override
			public int idDropped(int i, Random random, int j)
			{
				return itemlotusblossom.shiftedIndex;
			}
		}.setBlockName("Lotus");
		lotusBlossomTexture = 33;
		lotusBlossomId = c.getItem("lotusBlossomId", 2390).getInt();
		itemlotusblossom = new ItemFood(lotusBlossomId, 0, 0, false){
			@Override
			public int getMaxItemUseDuration(ItemStack par1ItemStack)
			{
				return 10;
			}
			@Override
			public ItemStack onFoodEaten(ItemStack itemstack, World world, EntityPlayer entityplayer)
			{
				world.playSoundAtEntity(entityplayer, "random.orb", 0.1F, 0.5F * ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.8F));
				entityplayer.addExperience(15 + world.rand.nextInt(16));
				return super.onFoodEaten(itemstack, world, entityplayer);
			}
			@Override
			public String getTextureFile()
			{
				return TooManyPlants.textureFile;
			}
		}.setAlwaysEdible().setIconIndex(lotusBlossomTexture).setItemName("Lotus Blossom");
	}
	
	void initLily(Configuration c)
	{
		lilyTexture = 34;
		lilyId = c.getBlock("lilyId", 1238).getInt();
		blocklily = new BlockFlower2(lilyId, lilyTexture){
			@Override
			public int idDropped(int i, Random random, int j)
			{
				return itemlilyseeds.shiftedIndex;
			}
		}.setBlockName("Lily");
		lilySeedsTexture = 35;
		lilySeedsId = c.getItem("lilySeedsId", 2380).getInt();
		itemlilyseeds = new ItemTMPSeeds(lilySeedsId, blocklily).setIconIndex(lilySeedsTexture).setItemName("Lily Seeds").setCreativeTab(CreativeTabs.tabMaterials);
		gildedlilyTexture = 37;
		gildedlilyId = c.getBlock("gildedlilyId", 1237).getInt();
		blockgildedlily = new BlockFlower2(gildedlilyId, gildedlilyTexture){
			@Override
			public int idDropped(int i, Random random, int j)
			{
				return 0;
			}
			@Override
			public ArrayList<ItemStack> getBlockDropped(World world, int i, int j, int k, int l, int fortune)
			{
				ArrayList<ItemStack> a = new ArrayList<ItemStack>();
				a.add(new ItemStack(Item.ingotGold));
				a.add(new ItemStack(itemlilyseeds));
				return a;
			}
		}.setBlockName("Gilded Lily");
		smallGildedlilyTexture = 38;
		smallGildedlilyId = c.getBlock("smallGildedlilyId", 1236).getInt();
		blocksmallgildedlily = new BlockFlower2(smallGildedlilyId, smallGildedlilyTexture){
			{
				setTickRandomly(true);
			}
			@Override
			public void updateTick(World world, int i, int j, int k, Random random)
			{
				if (random.nextInt(10) == 0)
				{
					world.setBlock(i, j, k, gildedlilyId);
				}
			}
		}.setBlockName("Small Gilded Lily");
		gildedlilySeedsTexture = 36;
		gildedlilySeedsId = c.getItem("gildedlilySeedsId", 2393).getInt();
		itemgildedlilyseeds = new ItemTMPSeeds(gildedlilySeedsId, blocksmallgildedlily){
			@Override
			public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float i2, float j2, float k2)
			{
				if (world.isMaterialInBB(AxisAlignedBB.getBoundingBox(i - 5, j - 5, k - 5, i + 5, j + 5, k + 5), Material.plants))
				{
					return false;
				}
				return super.onItemUse(itemstack, entityplayer, world, i, j, k, l, i2, j2, k2);
			}
		}.setIconIndex(gildedlilySeedsTexture).setItemName("Gilded Lily Seeds").setCreativeTab(CreativeTabs.tabMaterials);
	}
	
	void initExtraShears(Configuration c)
	{
		goldenShearsTexture = 42;
		goldenShearsId = c.getItem("goldenShearsId", 1025).getInt();
		itemgoldenshears = new ItemShears(goldenShearsId){
			@Override
			public String getTextureFile()
			{
				return TooManyPlants.textureFile;
			}
		}.setIconIndex(goldenShearsTexture).setItemName("Golden Shears").setMaxDamage(119).setCreativeTab(CreativeTabs.tabTools);
		
		/* bountyShearsTexture = 43;
		bountyShearsId = c.getItem("bountyShearsId", 3140).getInt();
		itembountyshears = new ItemShears(bountyShearsId){
			@Override
			public String getTextureFile()
			{
				return TooManyPlants.textureFile;
			}
		}.setIconIndex(bountyShearsTexture).setItemName("Shears of Bounty").setMaxDamage(119).setCreativeTab(CreativeTabs.tabTools);*/
		
		autocraftingShearsTexture = 44;
		autocraftingShearsId = c.getItem("autocraftingShearsId", 3141).getInt();
		itemautocraftingshears = new ItemAutocraftingShears(autocraftingShearsId).setIconIndex(autocraftingShearsTexture).setItemName("Autocrafting Shears").setMaxDamage(119).setCreativeTab(CreativeTabs.tabTools);
	}
	
	void initChillspike(Configuration c)
	{
		frozenNetherrackTexture = 40;
		frozenNetherrackId = c.getBlock("frozenNetherrackId", 1235).getInt();
		blockfrozennetherrack = new BlockNetherrack(frozenNetherrackId, frozenNetherrackTexture){
			@Override
			public void onBlockAdded(World world, int i, int j, int k)
			{
				if (world.getBlockId(i, j + 1, k) == Block.fire.blockID)
				{
					world.setBlock(i, j + 1, k, 0);
				}
				if (world.getBlockId(i, j + 1, k) == Block.lavaStill.blockID || world.getBlockId(i, j + 1, k) == Block.lavaMoving.blockID)
				{
					world.setBlock(i, j, k, Block.netherrack.blockID);
					world.playAuxSFX(1004, i, j, k, 0);
				}
			}
			@Override
			public void onNeighborBlockChange(World world, int i, int j, int k, int l)
			{
				if (world.getBlockId(i, j + 1, k) == Block.fire.blockID)
				{
					world.setBlock(i, j + 1, k, 0);
				}
				if (world.getBlockId(i, j + 1, k) == Block.lavaStill.blockID || world.getBlockId(i, j + 1, k) == Block.lavaMoving.blockID)
				{
					world.setBlock(i, j, k, Block.netherrack.blockID);
					world.playAuxSFX(1004, i, j, k, 0);
				}
			}
			@Override
			public String getTextureFile()
			{
				return TooManyPlants.textureFile;
			}
		}.setHardness(0.8F).setBlockName("Frozen Netherrack");
		chillspikeTexture = 41;
		chillspikeId = c.getBlock("chillspikeId", 1234).getInt();
		blockchillspike = new BlockChillspike(chillspikeId, chillspikeTexture).setHardness(0.5F).setBlockName("Chillspike").setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	public void init()
	{
		registerBlocks();
		addNames();
		addRecipes();
	}
	
	void registerBlocks()
	{
		GameRegistry.registerBlock(blockglowflower);
		GameRegistry.registerBlock(blockpreglowflower);
		GameRegistry.registerBlock(blockevilflower);
		GameRegistry.registerBlock(blockairflower);
		GameRegistry.registerBlock(blockbeanstalk);
		GameRegistry.registerBlock(blockpod);
		GameRegistry.registerBlock(blockbeanplant);
		GameRegistry.registerBlock(blockbeansprout);
		GameRegistry.registerBlock(blockpitcherplant);
		GameRegistry.registerBlock(blockbonefinger);
		GameRegistry.registerBlock(blocklichen);
		GameRegistry.registerBlock(blockcreepara);
		GameRegistry.registerBlock(blockpricklypear);
		GameRegistry.registerBlock(blockfireflower);
		GameRegistry.registerBlock(blockdawnflower);
		GameRegistry.registerBlock(blockberrybush);
		GameRegistry.registerBlock(blocklotus);
		GameRegistry.registerBlock(blocklily);
		GameRegistry.registerBlock(blockgildedlily);
		GameRegistry.registerBlock(blocksmallgildedlily);
		GameRegistry.registerBlock(blockfrozennetherrack);
		GameRegistry.registerBlock(blockchillspike);
	}
	
	void addNames()
	{
		LanguageRegistry.addName(blockglowflower, "Glow Flower");
		LanguageRegistry.addName(blockpreglowflower, "Non-Glowing Glow Flower");
		LanguageRegistry.addName(blockairflower, "Air Flower");
		LanguageRegistry.addName(blockpitcherplant, "Pitcher Plant");
		LanguageRegistry.addName(blockcreepara, "Creepara");
		LanguageRegistry.addName(blockpricklypear, "Prickly Pear");
		LanguageRegistry.addName(blockfireflower, "Fire Flower");
		LanguageRegistry.addName(blockdawnflower, "Dawnflower");
		LanguageRegistry.addName(itemlichen, "Lichen");
		LanguageRegistry.addName(itemroastedlichen, "Roasted Lichen");
		LanguageRegistry.addName(itemmagicbean, "Magic Bean");
		LanguageRegistry.addName(itemlessermagicbean, "Lesser Magic Bean");
		LanguageRegistry.addName(itemevilflowerpetal, "Evil Flower Petal");
		LanguageRegistry.addName(itemmagicpowder, "Magic Powder");
		LanguageRegistry.addName(itembean, "Bean");
		LanguageRegistry.addName(itembeanstew, "Bean Stew");
		LanguageRegistry.addName(itemboneseed, "Boneseed");
		LanguageRegistry.addName(itemfirelauncher, "Firelauncher");
		LanguageRegistry.addName(itemrainstick, "Rainstick");
		LanguageRegistry.addName(itemberry, "Berry");
		LanguageRegistry.addName(itemlotusblossom, "Lotus Blossom");
		LanguageRegistry.addName(itemberrypie, "Berry Pie");
		LanguageRegistry.addName(itemlilyseeds, "Lily Seeds");
		LanguageRegistry.addName(itemgildedlilyseeds, "Gilded Lily Seeds");
		LanguageRegistry.addName(blockevilflower, "Evil Flower");
		LanguageRegistry.addName(blockbeanplant, "Bean Plant");
		LanguageRegistry.addName(blockbeansprout, "Bean Sprout");
		LanguageRegistry.addName(blockbonefinger, "Bonefinger");
		LanguageRegistry.addName(blocklichen, "Lichen");
		LanguageRegistry.addName(blocklily, "Lily");
		LanguageRegistry.addName(blockgildedlily, "Gilded Lily");
		LanguageRegistry.addName(blocksmallgildedlily, "Small Gilded Lily");
		LanguageRegistry.addName(blocklotus, "Lotus");
		LanguageRegistry.addName(blockberrybush, "Berry Bush");
		LanguageRegistry.addName(itemgoldenshears, "Golden Shears");
//		LanguageRegistry.addName(itembountyshears, "Shears of Bounty");
		LanguageRegistry.addName(blockfrozennetherrack, "Frozen Netherrack");
		LanguageRegistry.addName(blockchillspike, "Chillspike");
		LanguageRegistry.addName(itemautocraftingshears, "Autocrafting Shears");
	}
	
	void addRecipes()
	{
		GameRegistry.addRecipe(new ItemStack(itembeanstew, 1), new Object[] {" O ", "&&&", " # ", ('#'), Item.bowlSoup, ('O'), Item.wheat, ('&'), itembean});
		GameRegistry.addRecipe(new ItemStack(itembeanstew, 1), new Object[] {"&&&", " O ", " # ", ('#'), Item.bowlSoup,  ('O'), Item.wheat, ('&'), itembean});
		GameRegistry.addRecipe(new ItemStack(itemberrypie, 1), new Object[] {"&&&", "XOX", " # ", ('#'), Item.bowlEmpty, ('X'), itemberry, ('O'), Item.sugar, ('&'), Item.wheat});
		GameRegistry.addRecipe(new ItemStack(itemmagicpowder, 1), new Object[] {"###", "###", "###", ('#'), itemevilflowerpetal});
		GameRegistry.addRecipe(new ItemStack(itemlessermagicbean, 1), new Object[] {"###", "#O#", "###", ('#'), Item.goldNugget, ('O'), itembean});
		GameRegistry.addRecipe(new ItemStack(itemgildedlilyseeds, 1), new Object[] {"###", "#O#", "###", ('#'), Item.goldNugget, ('O'), itemlilyseeds});
		GameRegistry.addRecipe(new ItemStack(itemfirelauncher, 1), new Object[] {"#", "O", ('O'), Item.stick, ('#'), blockfireflower});
		GameRegistry.addRecipe(new ItemStack(itemrainstick, 1), new Object[] {"#", "O", ('O'), Item.stick, ('#'), blockairflower});
		GameRegistry.addRecipe(new ItemStack(itemgoldenshears, 1), new Object[] {" #", "# ", ('#'), Item.ingotGold});
		GameRegistry.addShapelessRecipe(new ItemStack(itemmagicbean, 1), new Object[] {itemmagicpowder, itembean});
		GameRegistry.addShapelessRecipe(new ItemStack(Item.dyePowder, 3, 5), new Object[] {blockdawnflower});
		GameRegistry.addShapelessRecipe(new ItemStack(Item.bucketWater, 1), new Object[] {blockpricklypear, Item.bucketEmpty});
		GameRegistry.addShapelessRecipe(new ItemStack(itemautocraftingshears, 1), new Object[] {itemgoldenshears, itemmagicpowder, Item.redstone});
		GameRegistry.addSmelting(itemlichen.shiftedIndex, new ItemStack(itemroastedlichen, 1), 0.1F);
	}
}