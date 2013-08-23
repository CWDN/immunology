package piefarmer.immunology.item;

import piefarmer.immunology.common.Immunology;
import piefarmer.immunology.model.ModelGlider;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHangGlider extends ItemArmor{

	public ItemHangGlider(int par1) {
		super(par1, EnumArmorMaterial.CLOTH, 0, 1);
	}
	@Override
	public boolean isValidArmor(ItemStack stack, int armorType)
    {
		if(stack.getItem().itemID == Immunology.hangGlider.itemID && armorType == 1)
		{
			return true;
		}
		return false;
    }
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer)
    {
		return "/mods/Immunology/textures/models/glider.png";
		
    }
	@SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLiving entityLiving, ItemStack itemStack, int armorSlot)
    {
        return new ModelGlider();
    }
	@Override
	public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack)
    {
		if(player.fallDistance > 1)
		{
			player.setSprinting(false);
			player.moveEntityWithHeading(0, 1);
			if(player.isJumping)
			{
				player.motionY = 0;
			}
			else
			{
				player.motionY /= 4;	
			}
			

		}
    }

}
