package piefarmer.immunology.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import piefarmer.immunology.block.BlockCustomFlower;
import piefarmer.immunology.block.BlockCustomTorch;
import piefarmer.immunology.block.BlockDiagnosticTable;
import piefarmer.immunology.block.BlockMedicalResearchTable;
import piefarmer.immunology.block.BlockRock;
import piefarmer.immunology.client.ClientTickHandler;
import piefarmer.immunology.disease.Disease;
import piefarmer.immunology.entity.EntityBadger;
import piefarmer.immunology.entity.EntityDiseaseHandler;
import piefarmer.immunology.entity.EntityPacketHandler;
import piefarmer.immunology.events.EventHook;
import piefarmer.immunology.item.ItemCure;
import piefarmer.immunology.item.ItemDecayingFood;
import piefarmer.immunology.item.ItemDisease;
import piefarmer.immunology.item.ItemEffect;
import piefarmer.immunology.item.ItemHangGlider;
import piefarmer.immunology.item.ItemMedicalBook;
import piefarmer.immunology.model.ModelBadger;
import piefarmer.immunology.model.RenderBadger;
import piefarmer.immunology.network.packet.ImmunPacket;
import piefarmer.immunology.network.packet.PacketHandler;
import piefarmer.immunology.tileentity.TileEntityDiagnosticTable;
import piefarmer.immunology.tileentity.TileEntityMedicalResearchTable;
import piefarmer.immunology.tileentity.TileEntityRock;
import piefarmer.immunology.tileentity.TileEntityTorch;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.texturepacks.ITexturePack;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.src.ModLoader;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenForest;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Immunology.modid, name = "Immunology", version = "0.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = true, channels={ImmunPacket.CHANNEL}, packetHandler = PacketHandler.class)


public class Immunology {
	
	public final static String modid = "Immunology";
	
	@Instance ("Immunology")
	public static Immunology instance;
	
	@SidedProxy (clientSide = "piefarmer.immunology.client.ClientProxy", serverSide = "piefarmer.immunology.common.CommonProxy")
	public static CommonProxy proxy;
	
	public static int medrestblID = 192;
	public static int diatblID = 193;
	public static final int rockID = 195;
	public static int torchID = 194;
	public static int itemdiseaseID = 22943;
	public static int itemcureID = 22944;
	public static int itemeffectID = 22945;
	public static int itemhandglider = 22947;
	public static int itemmedicalbook = 22948;
	
	public static HashMap<Integer, EntityDiseaseHandler> loadedEntityList = new HashMap<Integer, EntityDiseaseHandler>();
	
	public static BlockCustomFlower plantBlueBell = (BlockCustomFlower) new BlockCustomFlower(504).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("bluebell");
	public static Block blockMedResearchTable = (new BlockMedicalResearchTable(medrestblID)).setHardness(0.1F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("medicalresearchtable");
	public static Block blockDiagTable = (new BlockDiagnosticTable(diatblID)).setHardness(0.1F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("diagnostictable");
	public static Block blockRock = (new BlockRock(rockID, Material.rock)).setHardness(1.5F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("rock");
	
	public static Item disease = new ItemDisease(itemdiseaseID).setUnlocalizedName("disease");
	public static ItemCure cure = (ItemCure) new ItemCure(itemcureID).setUnlocalizedName("cure");
	public static Item effect = new ItemEffect(itemeffectID).setUnlocalizedName("effect");
	public static Item hangGlider = new ItemHangGlider(itemhandglider).setUnlocalizedName("handglider");
	public static Item medicalBook = new ItemMedicalBook(itemmedicalbook).setUnlocalizedName("medicalbook");
	
	public static Item beefRaw; 
    public static Item chickenRaw;
    public static Item fishRaw;
    public static Item porkRaw;
    
    public static Block torchWoodLit;
    public static Block torchWoodNotLit = (new BlockCustomTorch(torchID, false)).setHardness(0.0F).setLightValue(0).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("unlittorch");
    
	@PreInit
	public void preInit(FMLPreInitializationEvent evt) {
		proxy.registerRenderThings();
		proxy.preInit();
		Block.blocksList[50] = null;
		torchWoodLit = (new BlockCustomTorch(50, true)).setHardness(0.0F).setLightValue(0.9375F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("torch");
		Item.itemsList[63] = null;
		Item.itemsList[93] = null;
		Item.itemsList[107] = null;
		Item.itemsList[109] = null;
		porkRaw = (new ItemDecayingFood(63, 3, 0.3F, true)).setUnlocalizedName("porkchopRaw");
		fishRaw = (new ItemDecayingFood(93, 2, 0.3F, false)).setUnlocalizedName("fishRaw");
		chickenRaw = (new ItemDecayingFood(109, 2, 0.3F, true)).setPotionEffect(Potion.hunger.id, 30, 0, 0.3F).setUnlocalizedName("chickenRaw");
		beefRaw = (new ItemDecayingFood(107, 3, 0.3F, true)).setUnlocalizedName("beefRaw");
	}

	@Init
	public void load(FMLInitializationEvent evt) {
			
		GameRegistry.registerWorldGenerator(new ImmunologyWorldGenerator());
		MinecraftForge.EVENT_BUS.register(new EventHook());
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
		//KeyBindingRegistry.registerKeyBinding(new ImmunKeyHandler());
		GameRegistry.registerTileEntity(TileEntityMedicalResearchTable.class, "ImmunologyMedicalResearchTable");
		GameRegistry.registerTileEntity(TileEntityDiagnosticTable.class, "ImmunologyDiagnosticTable");
		GameRegistry.registerTileEntity(TileEntityTorch.class, "ImmunologyTorch");
		GameRegistry.registerTileEntity(TileEntityRock.class, "ImmunologyRock");
		
		//
		//Entity Registry
		//
		EntityRegistry.registerGlobalEntityID(EntityBadger.class, "Badger", EntityRegistry.findGlobalUniqueEntityId(), 0xFFFFFF, 0x000000);
		EntityRegistry.registerModEntity(EntityBadger.class, "Badger", 1, this, 80, 3, true);
		EntityRegistry.addSpawn(piefarmer.immunology.entity.EntityBadger.class, 12, 2, 4, EnumCreatureType.creature, BiomeGenBase.forest, BiomeGenBase.forestHills);
		//
		//CREATIVE TABS
		//
		blockMedResearchTable.setCreativeTab(tabImmunology);
		disease.setCreativeTab(tabImmunology);
		blockDiagTable.setCreativeTab(tabImmunology);
		cure.setCreativeTab(tabImmunology);
		effect.setCreativeTab(tabImmunology);
		medicalBook.setCreativeTab(tabImmunology);
		torchWoodLit.setCreativeTab(CreativeTabs.tabDecorations);
		blockRock.setCreativeTab(CreativeTabs.tabDecorations);
		
		hangGlider.setCreativeTab(CreativeTabs.tabTransport);
		
		beefRaw.setCreativeTab(CreativeTabs.tabFood);
		chickenRaw.setCreativeTab(CreativeTabs.tabFood);
		fishRaw.setCreativeTab(CreativeTabs.tabFood);
		//
		//ITEM REGISTRY
		//
		GameRegistry.registerItem(beefRaw, "Beef Raw", this.modid);
		GameRegistry.registerItem(chickenRaw, "Chicken Raw", this.modid);
		GameRegistry.registerItem(fishRaw, "Fish Raw", this.modid);
		
		//
		//BLOCK REGISTRY
		//
		GameRegistry.registerBlock(plantBlueBell, "immunologybluebell");
		GameRegistry.registerBlock(blockMedResearchTable, "medicalresearchtable");
		GameRegistry.registerBlock(blockDiagTable, "diagnostictable");
		GameRegistry.registerBlock(torchWoodLit, "Lit Torch");
		GameRegistry.registerBlock(torchWoodNotLit, "Unlit Torch");
		GameRegistry.registerBlock(blockRock, "Rock");
		//
		//RECIPE REGISTRY
		//
		GameRegistry.addRecipe(new ItemStack(this.blockMedResearchTable, 1), new Object[] {
			"GGG",
			"MMM",
			"MBM", 
			'G',
			Item.glassBottle, 
			'M',
			Block.blockNetherQuartz, 
			'B',
			Item.book});
		GameRegistry.addRecipe(new ItemStack(this.blockDiagTable, 1), new Object[] {
			"GBG",
			"MMM",
			"M M", 
			'G',
			Item.glassBottle, 
			'M',
			Block.blockNetherQuartz, 
			'B',
			Item.book});
		GameRegistry.addRecipe(new ItemStack(this.hangGlider, 1), new Object[]{
			" S ",
			"SLS",
			"SLS",
			'S', Item.stick,
			'L', Item.leather});
		GameRegistry.addRecipe(new ItemStack(this.medicalBook, 1), new Object[]{
			"LP ",
			"PG ",
			"   ",
			'P', Item.paper,
			'L', Item.leather,
			'G', Item.glassBottle});
		//
		//LANGUAGE REGISTRY
		//
		LanguageRegistry.addName(plantBlueBell, "Blue Bell");
		LanguageRegistry.addName(disease, "Disease Item");
		LanguageRegistry.addName(blockMedResearchTable, "Medical Research Table");
		LanguageRegistry.addName(blockDiagTable, "Diagnostic Table");
		LanguageRegistry.addName(cure, "Disease Cure");
		LanguageRegistry.addName(effect, "Effect Item");
		LanguageRegistry.addName(hangGlider, "Hang Glider");
		LanguageRegistry.addName(medicalBook, "Medical Book");
		LanguageRegistry.addName(blockRock, "Rock");
		LanguageRegistry.addName(torchWoodNotLit, "Torch (Burnt out)");
		LanguageRegistry.instance().addStringLocalization("itemGroup.tabImmunology", "en_US", "Immunology");
		LanguageRegistry.instance().addStringLocalization("container.medicalresearchtable", "en_US", "Medical Research Table");
		LanguageRegistry.instance().addStringLocalization("container.diagnostictable", "en_US", "Diagnostic Table");
		LanguageRegistry.instance().addStringLocalization("item.emptyCure.name", "en_US", "Empty Cure");
		LanguageRegistry.instance().addStringLocalization("entity.Badger.name", "en_US", "Badger");
	}
	@PostInit
    public void modsLoaded(FMLPostInitializationEvent evt)
    {
		/*Collection<Item> removeSet = new HashSet();
		Collections.addAll(removeSet, new Item[] { Item.bucketEmpty, Item.bow});
		Iterator<IRecipe> iterator = CraftingManager.getInstance().getRecipeList().iterator();
		while (iterator.hasNext())
		{
			IRecipe recipe = iterator.next();
			if(recipe == null)
				continue;
			ItemStack output = recipe.getRecipeOutput();
			if(output != null && output.getItem() != null && removeSet.contains(output.getItem()))
				iterator.remove();
		}*/
		
    }
	
	public static CreativeTabs tabImmunology = new CreativeTabs("tabImmunology") {
        public ItemStack getIconItemStack() {
                return new ItemStack(blockMedResearchTable, 1, 0);
        }};	
    @ServerStarting
    public void onServerStarting(FMLServerStartingEvent event) {
        GameRegistry.registerPlayerTracker(new PlayerTracker());
        
    }
	
	public class PlayerTracker implements IPlayerTracker
	{
		@Override
		public void onPlayerLogin(EntityPlayer player) {
			Immunology.loadedEntityList.put(player.hashCode(), new EntityDiseaseHandler(player));
	         EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(player.hashCode());
	         if(hand != null)
	         {
	         	hand.readNBTData(player.getEntityData());
	         }
			
		}
		@Override
		public void onPlayerLogout(EntityPlayer player) {
			Immunology.loadedEntityList.clear();
	        
		}

		public void onPlayerChangedDimension(EntityPlayer player) {}
		@Override
		public void onPlayerRespawn(EntityPlayer player) {
			EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(player.hashCode());
			if(hand != null)
			{
				hand.clearActiveDiseases();
			}
		}
	}
	

}