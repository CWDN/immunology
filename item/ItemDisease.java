package piefarmer.immunology.item;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Iterator;
import java.util.Random;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import piefarmer.immunology.common.Immunology;
import piefarmer.immunology.disease.DiseaseChickenPox;
import piefarmer.immunology.disease.DiseaseCommonCold;
import piefarmer.immunology.disease.Disease;
import piefarmer.immunology.entity.EntityDiseaseHandler;
import piefarmer.immunology.xml.XMLReader;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemDisease extends Item{

	public ItemDisease(int par1) {
		super(par1);
	}
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if (!par2World.isRemote)
        {
			int count = 0;
			Iterator i = Immunology.loadedEntityList.values().iterator();
			while(i.hasNext())
			{
				EntityDiseaseHandler hand = (EntityDiseaseHandler) i.next();
				count++;
				if(hand != null && hand.living instanceof EntityPlayer)
				{
					System.out.println(hand.living.entityId );
				}
			}
			if(Immunology.loadedEntityList.containsKey(par3EntityPlayer))
			{
				EntityDiseaseHandler hand = (EntityDiseaseHandler)Immunology.loadedEntityList.get(par3EntityPlayer);
				hand.clearActiveDiseases();
				hand.addDisease(Disease.commonCold);
				hand.addDisease(Disease.chickenPox);
				hand.addDisease(Disease.influenza);
				hand.addDisease(Disease.measles);
			}
        }
		return par1ItemStack;
    }
	public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(Immunology.modid + ":" + this.getUnlocalizedName());
    }

}
