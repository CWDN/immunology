package piefarmer.immunology.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;

import piefarmer.immunology.common.Immunology;
import piefarmer.immunology.disease.Disease;
import piefarmer.immunology.disease.DiseaseEffect;
import piefarmer.immunology.entity.EntityDiseaseHandler;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.src.ModLoader;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;

@SideOnly(Side.CLIENT)
public class InventoryDiseaseEffectRenderer extends GuiScreen
{
	FontRenderer fontRenderer = ModLoader.getMinecraftInstance().fontRenderer;
	GuiScreen gui;
	int guiLeft = 0;
	int guiTop = 0;
	private boolean hasDisease;
	public InventoryDiseaseEffectRenderer(GuiScreen guiscreen) 
	{
		super();
		gui = guiscreen;
		this.mc = ModLoader.getMinecraftInstance();
		this.guiLeft = (gui.width - 176) / 2;
        this.guiTop = (gui.height - 166) / 2;
	}
	public void initGui()
    {
        super.initGui();
        
        EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(this.mc.thePlayer.hashCode());
        if (!hand.getActiveDiseases().isEmpty())
        {
            this.hasDisease = true;
        }
    }
	public void drawScreen(int par1, int par2, float par3)
    {
        	this.displayDiseaseEffects();
    }
	private void displayDiseaseEffects()
	{
		int id = this.mc.thePlayer.entityId;
		if(Immunology.loadedEntityList.containsKey(this.mc.thePlayer.hashCode()))
		{
			EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(this.mc.thePlayer.hashCode());
			if(hand != null)
			{
				List var4 = new ArrayList();
			    int[] effects = hand.getDiseaseEffects();
			    for(int i = 0; i < effects.length; i++)
			    {
			    	if(effects[i] > 0 && effects[i] != 8)
			    	{
			    		var4.add(effects[i]);
			    	}
			    }
			    if(!hand.getActiveSideEffects().isEmpty())
		        {
			    	Iterator var20 = hand.getActiveSideEffects().iterator();
		        	while(var20.hasNext())
		        	{
		        		DiseaseEffect effect = (DiseaseEffect)var20.next();
		        		if(!var4.contains(effect.getDiseaseEffectID() +1 ))
		        		{
		        			var4.add(effect.getDiseaseEffectID() + 1);
		        		}
		        	}
		        }
			    int var1 = guiLeft;
			    int var2 = guiTop - 33;
			    
			    if (var4.size() > 0)
			    {
			        String var5 = "/mods/Immunology/textures/gui/Inventory.png";
			        
			        this.mc.renderEngine.bindTexture(var5);
			        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			        GL11.glDisable(GL11.GL_LIGHTING);
			        int var6 = 33;
			        Iterator var20 = var4.iterator();
			        while(var20.hasNext())
			        {
			        	int effectid = (Integer) var20.next();
			        	effectid--;
			        	if(effectid != 7)
			        	{
			        		DiseaseEffect var8 = DiseaseEffect.diseaseEffects[effectid];
			                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			                this.mc.renderEngine.bindTexture(var5);
			                this.drawTexturedModalRect(var1, var2, 0, 0, 32, 32);
			                String fileloc = "/mods/Immunology/textures/gui/effects/" + var8.getName() + ".png";
			                this.mc.renderEngine.bindTexture(fileloc);
			                GL11.glScalef(0.0625F, 0.0625F, 0.0625F);
				            this.drawTexturedModalRect((var1 + 7) * 16, (var2 + 5) * 16, 0, 0, 256, 256);
				            GL11.glScalef(16F, 16F, 16F);
			                GL11.glScalef(0.5F, 0.5F, 0.5F);
			                this.drawCenteredString(this.fontRenderer, var8.getName(), var1 * 2 + 31, var2 * 2 + 51, 0xFFFFFF);
			                GL11.glScalef(2F, 2F, 2F);
			                var1 += var6;
			                
			        	}
			        }
			    }
			}
		}
	}
}
