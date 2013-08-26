package piefarmer.immunology.item;

import piefarmer.immunology.common.Immunology;
import piefarmer.immunology.disease.Disease;
import piefarmer.immunology.disease.DiseaseEffect;
import piefarmer.immunology.entity.EntityDiseaseHandler;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEffect extends Item{

	public ItemEffect(int par1) {
		super(par1);
	}
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if (par2World.isRemote)
        {
			EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(par3EntityPlayer.hashCode());
			hand.addSideEffect(DiseaseEffect.sneeze);
			hand.addSideEffect(DiseaseEffect.chills);
        }
		return par1ItemStack;
    }
	public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(Immunology.modid + ":" + this.getUnlocalizedName());
    }

}
