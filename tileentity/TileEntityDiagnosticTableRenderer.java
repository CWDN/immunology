package piefarmer.immunology.tileentity;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import piefarmer.immunology.model.ModelDiagnosticTable;

public class TileEntityDiagnosticTableRenderer extends TileEntitySpecialRenderer{
	
	private ModelDiagnosticTable  model;
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

		bindTextureByName("/mods/Immunology/textures/models/diagnostictable.png");
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
	}

	public void renderTileEntityAt(TileEntity var1, double var2, double var4,
			double var6, float var8) {
		renderAModelAt((TileEntityDiagnosticTable)var1, var2, var4, var6, var8);
		
	}

}
