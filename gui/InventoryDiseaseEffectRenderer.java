package piefarmer.immunology.gui;

import java.util.Collection;
import java.util.Iterator;

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
        
        EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(this.mc.thePlayer);
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
		if(Immunology.loadedEntityList.containsKey(this.mc.thePlayer))
		{
			EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(this.mc.thePlayer);
			if(hand != null)
			{
			    Collection var4 = hand.getActiveDiseases();
			    int var1 = guiLeft;
			    int var2 = guiTop - 33;
			    
			    if (!var4.isEmpty())
			    {
			        String var5 = "/mods/Immunology/textures/gui/Inventory.png";
			        this.mc.renderEngine.bindTexture(var5);
			        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			        GL11.glDisable(GL11.GL_LIGHTING);
			        int var6 = 33;
			        this.drawTexturedModalRect(var1, var2, 0, 0, 140, 32);
		
			        if (var4.size() > 5)
			        {
			           var6 = 132 / (var4.size() - 1);
			        }
			        for (Iterator var7 = hand.getActiveDiseases().iterator(); var7.hasNext(); var1 += var6)
			        {
			        	Disease var8 = (Disease)var7.next();
		                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		                this.mc.renderEngine.bindTexture(var5);
		                this.drawTexturedModalRect(var1, var2, 0, 0, 32, 32);
		                this.mc.renderEngine.bindTexture("/mods/Immunology/textures/gui/diseases.png");
		                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		    	        GL11.glDisable(GL11.GL_LIGHTING);
		               if (var8.hasStatusIcon())
		                {
		                    int var10 = var8.getStatusIconIndex();
		                    this.drawTexturedModalRect(var1 + 7, var2 + 4, 16 * var10, 0, 16, 16);
		                }
		               
		                String var12 = "I";
		
		                if (var8.getStage() == 1)
		                {
		                    var12 = var12 + " II";
		                }
		                else if (var8.getStage() == 2)
		                {
		                    var12 = var12 + " III";
		                }
		                else if (var8.getStage() == 3)
		                {
		                    var12 = var12 + " IV";
		                }
		                String var11 = Disease.getDurationString(var8);
		                this.fontRenderer.drawStringWithShadow(var11, (var1 + var11.length()) - 3, var2 - 8, 16777215);
		                
		                this.fontRenderer.drawStringWithShadow(var12, var1 + 13 - var8.getStage(), var2 + 22, 0xFFFFFF);
		
		               
			        }
			    }
		        int var21 = 0;
		        int var22 = 10;
			    if(!hand.getActiveSideEffects().isEmpty())
		        {
		        	for (Iterator var20 = hand.getActiveSideEffects().iterator(); var20.hasNext(); var21 += var22)
		        	{
		        		DiseaseEffect effect = (DiseaseEffect)var20.next();
		        		this.fontRenderer.drawStringWithShadow(effect.diseaseEffects[effect.getDiseaseEffectID()].getClass().getName(), 50, var21, 0xffffff);
		        		this.fontRenderer.drawStringWithShadow(StringUtils.ticksToElapsedTime(effect.getDuration()) + "", 300, var21, 0xffffff);
		        	}
		        }
			}
		}
	}
}
