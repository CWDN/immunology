package piefarmer.immunology.tileentity;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import piefarmer.immunology.model.ModelDiagnosticTable;

public class TileEntityDiagnosticTableRenderer extends TileEntitySpecialRenderer{
	
	private ModelDiagnosticTable  model;
	public static final ResourceLocation RESOURCE_TEXTURE = new ResourceLocation("immunology:textures/model/diagnostictable.png");
	public TileEntityDiagnosticTableRenderer()
	{
		model = new ModelDiagnosticTable();
	}
	
	
	public void renderAModelAt(TileEntityDiagnosticTable var1, double var2, double var4, double var6, float var8) {
		
		int rotation = 0;
		if(var1.worldObj != null)
		{
			rotation = var1.getBlockMetadata();
		}
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		this.func_110628_a(RESOURCE_TEXTURE);
		GL11.glPushMatrix();
		
		GL11.glTranslatef((float)var2 + 0.5F, (float)var4 + 1.5F, (float)var6 + 0.5F);
		if(rotation == 0 || rotation == 2)
		{
			GL11.glRotatef(rotation * 90, 0.0F, 1.0F, 0.0F);
		}
		else
		{
			GL11.glRotatef(rotation * 270, 0.0F, 1.0F, 0.0F);
		}
		GL11.glScalef(1.0F, -1F, -1F);
		model.renderAll();
		GL11.glPopMatrix();
		//GL11.glDisable(GL11.GL_BLEND);
	}

	public void renderTileEntityAt(TileEntity var1, double var2, double var4,
			double var6, float var8) {
		renderAModelAt((TileEntityDiagnosticTable)var1, var2, var4, var6, var8);
		
	}

}
