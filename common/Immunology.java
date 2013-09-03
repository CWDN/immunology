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
import java.util.logging.Level;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
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
import piefarmer.immunology.block.Blocks;
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
import piefarmer.immunology.item.Items;
import piefarmer.immunology.lib.ConfigHandler;
import piefarmer.immunology.lib.LogHelper;
import piefarmer.immunology.lib.Recipes;
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
import net.minecraftforge.event.ForgeSubscribe;

@Mod(modid = Immunology.modid, name = "Immunology", version = "0.0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = true, channels={ImmunPacket.CHANNEL}, packetHandler = PacketHandler.class)


public class Immunology {
	
	public final static String modid = "immunology";
	
	@Instance ("Immunology")
	public static Immunology instance;
	
	@SidedProxy (clientSide = "piefarmer.immunology.client.ClientProxy", serverSide = "piefarmer.immunology.common.CommonProxy")
	public static CommonProxy proxy;
	
	
	
	public static HashMap<Integer, EntityDiseaseHandler> loadedEntityList = new HashMap<Integer, EntityDiseaseHandler>();
    
    
    
	@EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		LogHelper.init();
		ConfigHandler.init(evt.getSuggestedConfigurationFile());
		proxy.initRenderers();
		proxy.preInit();
		LogHelper.log(Level.INFO, "Preparing items");
		Items.init();
		
		Items.addNames();
		LogHelper.log(Level.INFO, "Items Loaded");
		
		LogHelper.log(Level.INFO, "Preparing blocks");
		Block.blocksList[50] = null;
		Blocks.init();
		Blocks.registerBlocks();
		Blocks.addNames();
		LogHelper.log(Level.INFO, "Blocks Loaded");
		
		
		
	}

	@EventHandler
	public void load(FMLInitializationEvent evt) {
			
		Immunology.instance = this;
		GameRegistry.registerWorldGenerator(new ImmunologyWorldGenerator());
		MinecraftForge.EVENT_BUS.register(new EventHook());
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
		//KeyBindingRegistry.registerKeyBinding(new ImmunKeyHandler());
		
		
		LogHelper.log(Level.INFO, "Preparing Tile Entities");
		GameRegistry.registerTileEntity(TileEntityMedicalResearchTable.class, "ImmunologyMedicalResearchTable");
		GameRegistry.registerTileEntity(TileEntityDiagnosticTable.class, "ImmunologyDiagnosticTable");
		GameRegistry.registerTileEntity(TileEntityTorch.class, "ImmunologyTorch");
		GameRegistry.registerTileEntity(TileEntityRock.class, "ImmunologyRock");
		LogHelper.log(Level.INFO, "Tile Entities Loaded");
		
		
		LogHelper.log(Level.INFO, "Preparing recipes");
		Recipes.init();
		LogHelper.log(Level.INFO, "Recipes Loaded");
		
		
		EntityRegistry.registerGlobalEntityID(EntityBadger.class, "Badger", EntityRegistry.findGlobalUniqueEntityId(), 0xFFFFFF, 0x000000);
		EntityRegistry.registerModEntity(EntityBadger.class, "Badger", 1, this, 80, 3, true);
		EntityRegistry.addSpawn(piefarmer.immunology.entity.EntityBadger.class, 12, 2, 4, EnumCreatureType.creature, BiomeGenBase.forest, BiomeGenBase.forestHills);
		
		LanguageRegistry.instance().addStringLocalization("itemGroup.tabImmunology", "en_US", "Immunology");
		LanguageRegistry.instance().addStringLocalization("container.medicalresearchtable", "en_US", "Medical Research Table");
		LanguageRegistry.instance().addStringLocalization("container.diagnostictable", "en_US", "Diagnostic Table");
		LanguageRegistry.instance().addStringLocalization("item.emptyCure.name", "en_US", "Empty Cure");
		LanguageRegistry.instance().addStringLocalization("entity.Badger.name", "en_US", "Badger");
	}
	@EventHandler
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
                return new ItemStack(Blocks.blockMedResearchTable, 1, 0);
        }};	
    @EventHandler
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