package piefarmer.immunology.item;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import piefarmer.immunology.model.ModelGlider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

@SideOnly(Side.CLIENT)
public class ItemHangGliderRenderer implements IItemRenderer{

	ModelGlider model = new ModelGlider();
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType var2) {
		  return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... var3) {
		
		if(type == ItemRenderType.EQUIPPED)
		{
			if(var3.length > 1)
			{
				GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture("/mods/Immunology/textures/models/glider.png");
				if (var3[1] != null && var3[1] instanceof EntityPlayer)
				{
					float var5 = 0.5F;
					GL11.glTranslatef(0.6F, 0.2F, 0.3F);
					GL11.glScalef(var5, var5, var5);
					GL11.glRotatef(190.0F, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(100.0F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(-5.0F, 1.0F, 0.0F, 0.0F);
				}				
			this.model.render((Entity)var3[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
			}
			
		}
		else if(type == ItemRenderType.INVENTORY)
		{
			GL11.glPushMatrix();
			Minecraft.getMinecraft().renderEngine.bindTexture("/mods/Immunology/textures/models/glider.png");
			GL11.glTranslatef(5.5F, 10.5F, 0.5F);
			GL11.glScalef(6F, 6F, 6F);
			GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(120.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-25.0F, 1.0F, 0.0F, 0.0F);
			this.model.renderAll();
			GL11.glPopMatrix();
		}
	}	
}
