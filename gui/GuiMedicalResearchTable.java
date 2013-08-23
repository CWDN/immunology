package piefarmer.immunology.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bouncycastle.jcajce.provider.symmetric.Grain128;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

import piefarmer.immunology.disease.Disease;
import piefarmer.immunology.network.packet.Packet6Cure;
import piefarmer.immunology.tileentity.TileEntityMedicalResearchTable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.StatCollector;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiMedicalResearchTable extends GuiContainer{

	int posX = 0;
	int posY = 0;
	int currentPage = 1;
	int maxPages = 99;
	private EntityPlayer entityplayer;
	private ImmunologyFont immunfont;
	private TileEntityMedicalResearchTable tile;
	private ContainerMedicalResearchTable container; 
	float count;
	float count2;
	float count3;
	float count4;
	
	public GuiMedicalResearchTable(InventoryPlayer player, TileEntityMedicalResearchTable tileentity)
	{
		super(new ContainerMedicalResearchTable(player, tileentity));
		this.container = (ContainerMedicalResearchTable)this.inventorySlots;
		entityplayer = player.player;
		this.ySize = 220;
		tile = tileentity;
	}
	@Override  
	public void initGui()
	{
		super.initGui();
		posX = (this.width - this.xSize) / 2;
        posY = (this.height - this.ySize) / 2;
		this.buttonList.add(new GuiButtonNextDisease(0, posX + 150, posY + 70, true));
		this.buttonList.add(new GuiButtonNextDisease(1, posX + 88, posY + 70, false));

		
		
	}
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
		super.drawGuiContainerForegroundLayer(par1, par2);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.medicalresearchtable"), 9, 5, 4210752);
        for(int i = 0; i < 4; i++)
        {
        	if(this.isPointInRegion(14 + (i * 18), 25, 10, 22, par1, par2))
        	{
        		String cure = "Cure";
        		if(tile.getItemStack(i) != null && tile.getItemStack(i).hasDisplayName())
        		{
        			cure = tile.getItemStack(i).getDisplayName();
        		}
        		int time = 100 - tile.brewTime[i] / 10;
        		if(time == 100)
        		{
        			time = 0;
        		}
        		this.drawHoveringText(Arrays.asList(cure + " " + (i + 1) + " : " + time + "/100"), par1 - this.posX, par2 - this.posY, fontRenderer);
        	}
        }
    }
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {
		this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        mc.renderEngine.bindTexture("/mods/Immunology/textures/gui/MedicalResearchTableGUI.png");
        drawTexturedModalRect(posX, posY, 0, 0, this.xSize, this.ySize);
        this.drawpotioncolours();
        this.drawbrewing();
        mc.renderEngine.bindTexture("/mods/Immunology/textures/gui/DiagnosticTableGUI.png");
        drawTexturedModalRect(posX + 85, posY + 80, 0, 139, 70, 33);
		drawTexturedModalRect(posX + 85, posY + 113, 0, 211, 70, 14);
		drawTexturedModalRect(posX + 155, posY + 113, 140, 211, 10, 14);
		drawTexturedModalRect(posX + 155, posY + 80, 140, 139, 55, 33);
        
		this.drawCenteredString(fontRenderer, currentPage + "/" + maxPages, posX + 125, posY + 70, 0xffffff);
        immunfont = new ImmunologyFont(mc.gameSettings, "/font/default.png", mc.renderEngine, false);
        GL11.glScalef(0.5F, 0.5F, 1F);
        posX *= 2;
        posY *= 2;
        immunfont.drawString("Total Known", posX + 259, posY + 52, 0x444444);
        immunfont.drawString("Diseases: " + posX, posX + 259, posY + 62, 0x444444);
        immunfont.drawString("Total Found", posX + 259, posY + 96, 0x444444);
        immunfont.drawString("Cures: " + posY, posX + 259, posY + 106, 0x444444);
        posX /= 2;
        posY /= 2;
        GL11.glScalef(2F, 2F, 1F);
        

	}
	private void drawbrewing() {
		if(tile.brewTime[0] > 0)
		{
			count += 0.3F;
        	int intcount = (int)count;
        	drawTexturedModalRect(posX + 11, posY + 94 - intcount, xSize + 1, 82 - intcount, 16, intcount);
        	if(intcount == 23)
        	{
        		count = 0;
        	}
		}
		if(tile.brewTime[1] > 0)
		{
			count2 += 0.3F;
        	int intcount2 = (int)count2;
			drawTexturedModalRect(posX + 29, posY + 94 - intcount2, xSize + 19, 82 - intcount2, 16, intcount2);
			if(intcount2 == 23)
        	{
        		count2 = 0;
        	}
		}
		if(tile.brewTime[2] > 0)
		{
			count3 += 0.3F;
        	int intcount3 = (int)count3;
        	drawTexturedModalRect(posX + 47, posY + 94 - intcount3, xSize + 37, 82 - intcount3, 16, intcount3);
        	if(intcount3 == 23)
        	{
        		count3 = 0;
        	}
		}
		if(tile.brewTime[3] > 0)
		{
			count4 += 0.3F;
        	int intcount4 = (int)count4;
        	drawTexturedModalRect(posX + 65, posY + 94 - intcount4, xSize + 55, 82 - intcount4, 16, intcount4);
        	if(intcount4 == 23)
        	{
        		count4 = 0;
        	}
		}
	}
	public void actionPerformed(GuiButton button)
	{
		switch(button.id)
		{
		case 0:
			if(this.currentPage + 1 <= this.maxPages)
				this.currentPage++;
				
				Side side = FMLCommonHandler.instance().getEffectiveSide();
				tile.setItemStackSideEffects(Arrays.asList(0,1,1), 0);
				PacketDispatcher.sendPacketToServer(new Packet6Cure(tile.xCoord, tile.yCoord, tile.zCoord, Arrays.asList(0,1,1), 0).makePacket());
			
			break;
		case 1:
			if(this.currentPage - 1 > 0)
			{
				this.currentPage--;
			}
			break;
			
		}
		
	}
	public void drawpotioncolours()
	{
		for(int i = 0; i < 4; i++)
		{
			int potionsize = 22 - ((tile.brewTime[i] / 10) / 4);
			if(potionsize > 21)
			{
				potionsize = 0;
			}
		    GL11.glColor4f(tile.potioncolours[i][0], tile.potioncolours[i][1], tile.potioncolours[i][2], 0.3F);
		    drawTexturedModalRect(posX + 16 + (18 * i), posY + 25 + (22 - potionsize), xSize, 22 - potionsize, 6, potionsize);
		    drawTexturedModalRect(posX + 11 + (18 * i), posY + 50, xSize, 23, 16, 16);
		    drawTexturedModalRect(posX + 10 + (18 * i), posY + 70, xSize + 18, 0, 18, 58);
		    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        drawTexturedModalRect(posX + 13 + (i * 18), posY + 25, xSize + 6, 0, 12, 23);
		}
		
	}
	protected void mouseClicked(int par1, int par2, int par3)
    {
		super.mouseClicked(par1, par2, par3);
		/*for(int i = 0; i < 4; i++)
        {
        	if(this.isPointInRegion(14 + (i * 18), 25, 10, 22, par1, par2))
        	{
        		this.changeColour(i);
        	}
        }*/
    }
	private void changeColour(int potion)
	{
		if(tile.potioncolours[potion][0] == 1F && tile.potioncolours[potion][1] == 1F && tile.potioncolours[potion][2] == 1F)
		{
			tile.potioncolours[potion][0] = 1.0F;
			tile.potioncolours[potion][1] = 0.0F;
			tile.potioncolours[potion][2] = 0.0F;
		}
		else if(tile.potioncolours[potion][0] == 1F)
		{
			tile.potioncolours[potion][0] = 0.0F;
			tile.potioncolours[potion][1] = 1.0F;
			tile.potioncolours[potion][2] = 0.0F;
		}
		else if(tile.potioncolours[potion][1] == 1F)
		{
			tile.potioncolours[potion][0] = 0.0F;
			tile.potioncolours[potion][1] = 0.0F;
			tile.potioncolours[potion][2] = 1.0F;
		}
		else if(tile.potioncolours[potion][2] == 1F)
		{
			tile.potioncolours[potion][0] = 1.0F;
			tile.potioncolours[potion][1] = 1.0F;
			tile.potioncolours[potion][2] = 1.0F;
		}
	}



}
