package piefarmer.immunology.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import piefarmer.immunology.entity.EntityBadger;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

@SideOnly(Side.CLIENT)
public class RenderBadger extends RenderLiving{

	protected ModelBadger model;
	public RenderBadger(ModelBase par1ModelBase, float par2) {
		super(par1ModelBase, par2);
		model = (ModelBadger) par1ModelBase;
	}
	public void renderBadger(EntityBadger entity, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRenderLiving(entity, par2, par4, par6, par8, par9);	
	}
	@Override
	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		renderBadger((EntityBadger)par1EntityLiving, par2, par4, par6, par8, par9);
	}
	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderBadger((EntityBadger)par1Entity, par2, par4, par6, par8, par9);
	}
}
