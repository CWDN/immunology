package piefarmer.immunology.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import piefarmer.immunology.common.Immunology;
import piefarmer.immunology.disease.Disease;
import piefarmer.immunology.disease.DiseaseEffect;
import piefarmer.immunology.item.ItemCure;
import piefarmer.immunology.item.ItemMedicalBook;
import piefarmer.immunology.xml.XMLReader;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public class GuiMedicalBook extends GuiScreen{
	
	protected static RenderItem itemRenderer = new RenderItem();
	int posX = 0;
	int posY = 0;
	int page = 0;
	List<Integer> diseasepagesFound = new ArrayList<Integer>();
	List<Integer> curepagesFound = new ArrayList<Integer>();
	public GuiMedicalBook(ItemStack is)
	{
		ItemMedicalBook iBook = (ItemMedicalBook) is.getItem();
		diseasepagesFound = iBook.getDiseasePages(is);
		curepagesFound = iBook.getCurePages(is);
	}
	public void initGui()
	{
		posX = this.width / 2;
		posY = this.height /2;
		this.buttonList.add(new GuiButtonNextBookPage(0, posX + 110, posY + 60, true));
		this.buttonList.add(new GuiButtonNextBookPage(1, posX - 140, posY + 60, false));
	}
	public void drawScreen(int par1, int par2, float par3)
    {
		this.drawDefaultBackground();
		posX = this.width / 2;
		posY = this.height /2;
		
		this.mc.renderEngine.bindTexture("/mods/Immunology/textures/gui/medicalbookright.png");
		this.drawTexturedModalRect(posX, posY - (176 /2), 0, 0, 161, 176);
		this.mc.renderEngine.bindTexture("/mods/Immunology/textures/gui/medicalbookleft.png");
		this.drawTexturedModalRect(posX - 168, posY - (176 /2), 0, 0, 168, 176);
		fontRenderer.drawString("Description", posX - 140, posY - 70, 0x111111);
		fontRenderer.drawString("Cure", posX + 40, posY - 70, 0x111111);
		fontRenderer.drawString("Ingredients:", posX + 30, posY - 60, 0x111111);
		if(this.diseasepagesFound.contains(page))
		{
			this.writeDescription(Integer.toString(page));
		}
		else
		{
			this.writeDescription(Integer.toString(-1));
		}
		if(this.curepagesFound.contains(page))
		{
			List<ItemStack> l = MedicalResearchTableRecipes.brewing().getIngredients(page + 1, false);
			
			if(l != null)
			{
				ItemStack is = l.get(0);
				ItemStack is2 = l.get(1);
				fontRenderer.drawString("- " + is.stackSize + " x     " + is.getDisplayName(), posX + 30, posY - 47, 0x111111);
				fontRenderer.drawString("- " + is2.stackSize + " x     " + is2.getDisplayName(), posX + 30, posY - 31, 0x111111);
				this.drawItemStack(is, posX + 58, posY - 52);
				this.drawItemStack(is2, posX + 58, posY - 36);
				
				is = MedicalResearchTableRecipes.brewing().getBrewingResult(l.get(0), l.get(1));
				fontRenderer.drawString("Side Effects", posX + 30, posY - 7, 0x111111);
				
				if(is != null)
				{
					ItemCure cure = (ItemCure) is.getItem();
					List li = cure.getEffects(is);
					if(li != null)
					{
						for(int i = 0; i < li.size(); i++)
						{
							DiseaseEffect eff = (DiseaseEffect) li.get(i);
							eff = DiseaseEffect.diseaseEffects[eff.getDiseaseEffectID()];
							fontRenderer.drawString("- " +eff.getName(), posX + 30, posY + 8 + (10 * i), 0x111111);
						}
					}
					else
					{
						fontRenderer.drawString("- None", posX + 30, posY + 8, 0x111111);
					}
				}
			}
			
		}
		else
		{
			fontRenderer.drawString("Not researched cure.", posX + 30, posY - 47, 0x111111);
		}
		
		
		super.drawScreen(par1, par2, par3);
	}
	public boolean doesGuiPauseGame()
    {
        return false;
    }
	public void writeDescription(String id)
	{
		int lineLength = 21;
		
		String description = XMLReader.getDescriptionByDiseaseID(id);
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
				fontRenderer.drawString(description.substring(0, endIndex + 1), posX - 137, posY - 55 + (line * fontRenderer.FONT_HEIGHT), 0x111111);
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
	@Override
	protected void keyTyped(char par1, int par2)
	{
		if (par2 == 1 || par2 == this.mc.gameSettings.keyBindInventory.keyCode)
		{
			this.mc.thePlayer.closeScreen();
		}
	}
	private void drawItemStack(ItemStack par1ItemStack, int par2, int par3)
    {
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRenderer.zLevel = 200.0F;
        FontRenderer font = par1ItemStack.getItem().getFontRenderer(par1ItemStack);
        if (font == null) font = fontRenderer;
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glEnable(GL11.GL_LIGHTING);
        itemRenderer.renderItemAndEffectIntoGUI(font, this.mc.renderEngine, par1ItemStack, par2, par3);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        this.zLevel = 0.0F;
        itemRenderer.zLevel = 0.0F;
    }
	public void actionPerformed(GuiButton but)
	{
		switch(but.id)
		{
			case 0:
				if(page + 1 < Disease.diseaseTypes.length)
				{
					page += 1;
				}
				break;
			case 1:
				if(page - 1 > -1)
				{
					page--;
				}
				break;
		}
	}
	
}
