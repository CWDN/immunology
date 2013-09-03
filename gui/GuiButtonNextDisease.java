package piefarmer.immunology.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class GuiButtonNextDisease extends GuiButton{


	private final boolean nextPage;
	public static final ResourceLocation RESOURCE_BUTTON = new ResourceLocation("immunology:textures/gui/DiagnosticTableGUI.png");
	private static TextureManager textureManager;
    public GuiButtonNextDisease(int par1, int par2, int par3, boolean par4)
    {
        super(par1, par2, par3, 12, 9, "");
        this.nextPage = par4;
    }
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.drawButton)
        {
            boolean var4 = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft mc = par1Minecraft;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            int var5 = 198;
            int var6 = 9;
            textureManager = mc.func_110434_K();
            textureManager.func_110577_a(RESOURCE_BUTTON);
            if (var4)
            {
                var5 += 12;
            }

            if (!this.nextPage)
            {
                var6 += 9;
            }

            this.drawTexturedModalRect(this.xPosition, this.yPosition, var5, var6, 12, 9);
        }
    }

}
