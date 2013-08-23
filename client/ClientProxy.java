package piefarmer.immunology.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import piefarmer.immunology.common.*;
import piefarmer.immunology.entity.EntityBadger;
import piefarmer.immunology.events.EventSounds;
import piefarmer.immunology.gui.ContainerMedicalResearchTable;
import piefarmer.immunology.gui.GuiDiagnosticTable;
import piefarmer.immunology.gui.GuiMedicalResearchTable;
import piefarmer.immunology.item.ItemDiagnosticTableRenderer;
import piefarmer.immunology.item.ItemHangGliderRenderer;
import piefarmer.immunology.item.ItemMedicalResearchTableRenderer;
import piefarmer.immunology.item.ItemRockRenderer;
import piefarmer.immunology.model.ModelBadger;
import piefarmer.immunology.model.RenderBadger;
import piefarmer.immunology.tileentity.TileEntityDiagnosticTable;
import piefarmer.immunology.tileentity.TileEntityDiagnosticTableRenderer;
import piefarmer.immunology.tileentity.TileEntityMedicalResearchTable;
import piefarmer.immunology.tileentity.TileEntityMedicalResearchTableRenderer;
import piefarmer.immunology.tileentity.TileEntityRock;
import piefarmer.immunology.tileentity.TileEntityRockSpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderThings() 
	 {
		 ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMedicalResearchTable.class, new TileEntityMedicalResearchTableRenderer());
		 MinecraftForgeClient.registerItemRenderer(Immunology.blockMedResearchTable.blockID, new ItemMedicalResearchTableRenderer());
		 ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDiagnosticTable.class, new TileEntityDiagnosticTableRenderer());
		 MinecraftForgeClient.registerItemRenderer(Immunology.blockDiagTable.blockID, new ItemDiagnosticTableRenderer());
		 ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRock.class, new TileEntityRockSpecialRenderer());
		 MinecraftForgeClient.registerItemRenderer(Immunology.blockRock.blockID, new ItemRockRenderer());
		 RenderingRegistry.registerEntityRenderingHandler(EntityBadger.class, new RenderBadger(new ModelBadger(), 0.3F));
		 
		 MinecraftForgeClient.registerItemRenderer(Immunology.hangGlider.itemID, (IItemRenderer) new ItemHangGliderRenderer());
	 }
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		
	
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity != null)
		{
			switch(ID)
			{
				case 0:
					return new GuiMedicalResearchTable(player.inventory, (TileEntityMedicalResearchTable)tileEntity);
				case 1:
					return new GuiDiagnosticTable(player, (TileEntityDiagnosticTable)tileEntity);
			}
		}
		return null;
	}
	public void preInit()
	{
		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new EventSounds());
	}
}
