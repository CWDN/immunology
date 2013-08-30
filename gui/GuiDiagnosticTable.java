package piefarmer.immunology.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.PacketDispatcher;

import piefarmer.immunology.common.Immunology;
import piefarmer.immunology.disease.Disease;
import piefarmer.immunology.entity.EntityDiseaseHandler;
import piefarmer.immunology.item.ItemMedicalBook;
import piefarmer.immunology.network.packet.Packet7Book;
import piefarmer.immunology.tileentity.TileEntityDiagnosticTable;
import piefarmer.immunology.tileentity.TileEntityMedicalResearchTable;
import piefarmer.immunology.xml.XMLReader;

public class GuiDiagnosticTable extends GuiScreen{
	int posX = 0;
	int posY = 0;
	int ySize = 139;
	int xSize = 176;
	int currentPage = 1;
	int maxPages = 99;
	
	GuiButtonExpand btnExpand1 = null;
	GuiButtonExpand btnExpand2 = null;
	GuiButtonExpand btnExpand3 = null;
	EntityPlayer entityplayer = null;
	TileEntityDiagnosticTable tile = null;
	Disease selectedDisease = null;
	int selectedIndex = 0;
	Disease[] diseases = new Disease[3];
	List<Disease> Entitydiseases = new ArrayList<Disease>();
	
	public GuiDiagnosticTable(EntityPlayer player, TileEntityDiagnosticTable tileEntity)
	{
		entityplayer = player;
		tile = tileEntity;
		this.Entitydiseases = new ArrayList<Disease>(tile.entityDiseases.values());
		float size = this.Entitydiseases.size();
		this.pageSetup(size);
		this.getDiseases();
	} 
	public void initGui()
	{
		posX = (this.width - this.xSize) / 2;
        posY = (this.height - this.ySize) / 2;
		buttonList.clear();
		this.buttonList.add(new GuiButtonNextDisease(0, posX + 102, posY + 27, true));
		this.buttonList.add(new GuiButtonNextDisease(1, posX + 55, posY + 27, false));
		btnExpand1 = new GuiButtonExpand(2, posX + 141, posY + 47);
		btnExpand2 = new GuiButtonExpand(3, posX + 141, posY + 76);
		btnExpand3 = new GuiButtonExpand(4, posX + 141, posY + 105);
		this.buttonList.add(btnExpand1);
		this.buttonList.add(btnExpand2);		
		this.buttonList.add(btnExpand3);
		//this.buttonList.add(new GuiButtonAddDisease(5, posX + 119, posY + 23));
	}
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture("/mods/Immunology/textures/gui/DiagnosticTableGUI.png");
        drawTexturedModalRect(posX, posY, 0, 0, this.xSize, this.ySize);        
        if(this.btnExpand1.drawButton && this.btnExpand2.drawButton && this.btnExpand3.drawButton)
        {	
        	setup3Bars();
            this.setDiseasesDisplay();
        }
        else
        {
        	if(this.btnExpand1.drawButton)
        	{
        		drawTexturedModalRect(posX + 12, posY + 39, 0,139, 150, 86);
        	}
        	else if(this.btnExpand2.drawButton)
        	{
        		drawTexturedModalRect(posX + 12, posY + 39, 0,139, 150, 86);
        	}
        	else if(this.btnExpand3.drawButton)
        	{
        		drawTexturedModalRect(posX + 12, posY + 39, 0,139, 150, 86);
        	}
        	this.setTopDisease(selectedDisease, selectedIndex);
        }
        
        this.drawCenteredString(fontRenderer, currentPage + "/" + maxPages, posX + 84, posY + 27, 0xffffff);
        this.drawCenteredString(fontRenderer, this.tile.entityname, posX + 85, posY + 17, 0xffffff);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.diagnostictable"), posX + 10, posY + 5, 0x333333);
        super.drawScreen(par1, par2, par3);
	}
	private void setup3Bars()
	{
		//drawTexturedModalRect(posX + 13, posY + 40, 0,0, 150, 86);
		drawTexturedModalRect(posX + 12, posY + 39, 0, 139, 150, 24);
		drawTexturedModalRect(posX + 12, posY + 63, 0, 221, 150, 4);
		
		drawTexturedModalRect(posX + 12, posY + 68, 0, 139, 150, 24);
		drawTexturedModalRect(posX + 12, posY + 92, 0, 221, 150, 4);
		
		drawTexturedModalRect(posX + 12, posY + 97, 0, 139, 150, 24);
		drawTexturedModalRect(posX + 12, posY + 121, 0, 221, 150, 4);
	}
	public void actionPerformed(GuiButton button)
	{
		switch(button.id)
		{
		case 0: 
			if((currentPage + 1) <= maxPages)
			{
				currentPage++;
			}
			break;
		case 1:
			if((currentPage - 1) > 0)
			{
				currentPage--;
			}
			break;
		case 2:
			if(this.btnExpand2.drawButton && this.btnExpand3.drawButton)
			{
				this.btnExpand2.drawButton = false;
				this.btnExpand3.drawButton = false;
				this.btnExpand1.setExpaned(true);
				this.selectedDisease = this.diseases[(button.id - 2)];
				this.selectedIndex = button.id - 2;
			}
			else if(!this.btnExpand2.drawButton && !this.btnExpand3.drawButton)
			{
				this.btnExpand2.drawButton = true;
				this.btnExpand3.drawButton = true;
				this.btnExpand1.setExpaned(false);
			}
			break;
		case 3:
			if(this.btnExpand1.drawButton && this.btnExpand3.drawButton)
			{
				this.btnExpand2.drawButton = false;
				this.btnExpand3.drawButton = false;
				this.btnExpand1.setExpaned(true);
				this.selectedDisease = this.diseases[button.id - 2];
				this.selectedIndex = button.id - 2;
			}
			else if(!this.btnExpand1.drawButton && !this.btnExpand3.drawButton)
			{
				this.btnExpand1.drawButton = true;
				this.btnExpand3.drawButton = true;
				this.btnExpand2.setExpaned(false);
			}
			break;
		case 4:
			if(this.btnExpand1.drawButton && this.btnExpand2.drawButton)
			{
				this.btnExpand2.drawButton = false;
				this.btnExpand3.drawButton = false;
				this.btnExpand1.setExpaned(true);
				this.selectedDisease = this.diseases[button.id - 2];
				this.selectedIndex = button.id - 2;
			}
			else if(!this.btnExpand1.drawButton && !this.btnExpand2.drawButton)
			{
				this.btnExpand1.drawButton = true;
				this.btnExpand2.drawButton = true;
				this.btnExpand3.setExpaned(false);
			}
			break;
			
		}
		
	}
	private void setDiseasesDisplay()
	{
		int count = 0;
		if(currentPage > 0)
		{
			int end = 3 * currentPage;
			int start = ((this.currentPage - 1) * 3);
			for(int i = start; i < end; i++)
			{
				if(i < this.Entitydiseases.size())
				{
					Disease var2 = this.Entitydiseases.get(i);
					Disease disease = Disease.diseaseTypes[var2.getdiseaseID()];
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					GL11.glEnable(GL11.GL_BLEND);
	        		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					mc.renderEngine.bindTexture("/mods/Immunology/textures/gui/diseases/"+ disease.getName()+ ".png");
					GL11.glScalef(0.0625F, 0.0625F, 0.0625F);
		            this.drawTexturedModalRect((posX + 22) * 16, (posY + 44 + (29 * count)) * 16, 0, 0, 256, 256);
		            GL11.glScalef(16F, 16F, 16F);
					int colour = 0xffffff;
					colour = this.getColour(disease.getdiseaseID());
    				this.drawCenteredString(fontRenderer, disease.getName(), posX + 85, posY + 49 + (29 * count), colour);
    				this.diseases[count] = disease;
				}
				else
				{
					this.diseases[count] = null;
				}
				count++;
			}
		}
	}
	private void setTopDisease(Disease disease, int index)
	{
		if(disease != null)
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(GL11.GL_BLEND);
    		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			mc.renderEngine.bindTexture("/mods/Immunology/textures/gui/diseases/"+ disease.getName()+ ".png");
			GL11.glScalef(0.0625F, 0.0625F, 0.0625F);
            this.drawTexturedModalRect((posX + 22) * 16, (posY + 44) * 16, 0, 0, 256, 256);
            GL11.glScalef(16F, 16F, 16F);
			this.drawCenteredString(fontRenderer, disease.getName(), posX + 85, posY + 49, this.getColour(disease.getdiseaseID()));
			int lineLength = 26;
			
			String description = XMLReader.getDescriptionByDiseaseID(Integer.toString(disease.getdiseaseID()));
			String[] stringstoDraw = new String[(description.length() / 26) + 1];
			if(description.length() > lineLength)
			{
				int endIndex = 0;
				int line = 0;
				while(endIndex != description.length())
				{
					if(description.length() > lineLength)
					{
						char ch = 0;
						int backtrack = 0;
						while(ch != ' ')
						{
							int charend = lineLength;
							if(charend > description.length())
							{
								charend = description.length() - 1;
							}
							ch = description.charAt(charend - backtrack);
							backtrack++;
						}
						endIndex = lineLength - backtrack;
						if(endIndex > description.length())
						{
							endIndex = description.length() -1;
						}
						
					}
					else
					{
						endIndex = description.length() -1;
					}
					this.drawString(fontRenderer, description.substring(0, endIndex + 1), posX + 21, posY + 64 + (line * fontRenderer.FONT_HEIGHT), 0xaaaaaa);
					if(description.length() > lineLength)
					{
						description = description.substring(endIndex + 2, description.length());
					}else{
						endIndex = description.length();
					}
					line++;
				}
			}
			else
			{
				this.drawString(fontRenderer, description, posX + 21, posY + 66, 0xaaaaaa);
			}
		}
	}
	public int getColour(int index)
	{
		return Immunology.cure.getColorFromDamage(index + 1);
	}
	public void getDiseases()
	{
		if(Immunology.loadedEntityList.containsKey(entityplayer.hashCode()))
		{
			EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(this.entityplayer.hashCode());
			if(hand != null)
			{
				int[] effects = hand.getDiseaseEffects();
				this.Entitydiseases = Disease.getDiseasesByEffects(effects);
				float size = this.Entitydiseases.size();
				this.tile.setEntityName(entityplayer.username, entityplayer, this.Entitydiseases);
				this.pageSetup(size);
				Iterator iter = this.Entitydiseases.iterator();
				if(this.entityplayer.inventory.hasItem(Immunology.medicalBook.itemID))
				{
					int slot = 0;
					ItemStack[] is = this.entityplayer.inventory.mainInventory;
					for(slot = 0; slot < is.length; slot++)
					{
						ItemStack it = this.entityplayer.inventory.getStackInSlot(slot);
						if(it != null && it.getItem() instanceof ItemMedicalBook)
						{
							ItemMedicalBook book = (ItemMedicalBook) it.getItem();
							List<Integer> ids = new ArrayList<Integer>();
							while(iter.hasNext())
							{
								Disease di = (Disease) iter.next();
								book.setDiseasePages(it, di.getdiseaseID());
								ids.add(di.getdiseaseID());
							}
							this.entityplayer.inventory.setInventorySlotContents(slot, it);
							PacketDispatcher.sendPacketToServer(new Packet7Book(slot, ids).makePacket());
						}
					}
				}
			}
		}
	}
	public void pageSetup(float size)
	{
		maxPages = (int) Math.ceil(size / 3);
		currentPage = 1;
		if(maxPages == 0)
		{
			currentPage = 0;
		}
	}
	@Override
	protected void keyTyped(char par1, int par2)
	{
		if (par2 == 1 || par2 == this.mc.gameSettings.keyBindInventory.keyCode)
		{
			this.mc.thePlayer.closeScreen();
		}
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}


}
