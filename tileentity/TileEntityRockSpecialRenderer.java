package piefarmer.immunology.tileentity;

import org.lwjgl.opengl.GL11;

import piefarmer.immunology.model.ModelMedicalResearchTable;
import piefarmer.immunology.model.ModelRock;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class TileEntityRockSpecialRenderer extends TileEntitySpecialRenderer {

	private ModelRock  model;
	public TileEntityRockSpecialRenderer()
	{
		model = new ModelRock();
	}
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1,
			double d2, float f) {
		renderModelAt((TileEntityRock)tileentity, d0, d1, d2, f);
		

	}
	public void renderModelAt(TileEntityRock var1, double var2, double var4, double var6, float var8) {

		int rotation = 0;
		if(var1.worldObj != null)
		{
			rotation = var1.getBlockMetadata();
		}
		bindTextureByName("/mods/Immunology/textures/models/rock.png");
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
}
