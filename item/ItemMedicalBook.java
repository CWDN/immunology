package piefarmer.immunology.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import piefarmer.immunology.common.Immunology;
import piefarmer.immunology.disease.DiseaseEffect;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class ItemMedicalBook extends Item{

	public ItemMedicalBook(int par1) {
		super(par1);
	}
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		par3EntityPlayer.openGui(Immunology.instance, 2, par2World, (int)par3EntityPlayer.posX, (int)par3EntityPlayer.posY, (int)par3EntityPlayer.posZ);
		return par1ItemStack;
		
    }
	public void registerIcons(IconRegister par1)
	{
		this.itemIcon = par1.registerIcon(Immunology.modid + ":" + this.getUnlocalizedName());
	}
	public ItemStack setDiseasePages(ItemStack is, int id)
	{
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		NBTTagList nbtlist = new NBTTagList();
		List pages = this.getDiseasePages(is);
		Iterator iterator = pages.iterator();
		while(iterator.hasNext())
		{
			int var1 = (Integer)iterator.next();
			nbtTagCompound.setInteger("page", var1);
	        nbtlist.appendTag(nbtTagCompound);
	        nbtTagCompound = new NBTTagCompound();
		}
		if(!pages.contains(id))
		{
			nbtTagCompound.setInteger("page", id);
	        nbtlist.appendTag(nbtTagCompound);
	        nbtTagCompound = new NBTTagCompound();
		}
		if(nbtlist.tagCount() > 0)
		{
			is.setTagInfo("DiseasePages", nbtlist);
		}
		return is;
	}
	public List getDiseasePages(ItemStack par1ItemStack)
    {
        if (par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().hasKey("DiseasePages"))
        {
            ArrayList var6 = new ArrayList();
            NBTTagList var3 = par1ItemStack.getTagCompound().getTagList("DiseasePages");

            for (int var4 = 0; var4 < var3.tagCount(); ++var4)
            {
                NBTTagCompound var5 = (NBTTagCompound)var3.tagAt(var4);
                var6.add(var5.getInteger("page"));
            }

            return var6;
        }
        else
        {
        	return new ArrayList();
        }
    }
	public ItemStack setCurePages(ItemStack is, int id)
	{
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		NBTTagList nbtlist = new NBTTagList();
		Iterator iterator = this.getCurePages(is).iterator();
		while(iterator.hasNext())
		{
			int var1 = (Integer)iterator.next();
			nbtTagCompound.setInteger("page", var1);
	        nbtlist.appendTag(nbtTagCompound);
	        nbtTagCompound = new NBTTagCompound();
		}
		nbtTagCompound.setInteger("page", id);
        nbtlist.appendTag(nbtTagCompound);
        nbtTagCompound = new NBTTagCompound();
		if(nbtlist.tagCount() > 0)
		{
			is.setTagInfo("CurePages", nbtlist);
		}
		return is;
	}
	public List getCurePages(ItemStack par1ItemStack)
    {
        if (par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().hasKey("CurePages"))
        {
            ArrayList var6 = new ArrayList();
            NBTTagList var3 = par1ItemStack.getTagCompound().getTagList("CurePages");

            for (int var4 = 0; var4 < var3.tagCount(); ++var4)
            {
                NBTTagCompound var5 = (NBTTagCompound)var3.tagAt(var4);
                var6.add(var5.getInteger("page"));
            }

            return var6;
        }
        else
        {
        	return new ArrayList();
        }
    }
	public ItemStack setSidePages(ItemStack is, int id)
	{
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		NBTTagList nbtlist = new NBTTagList();
		Iterator iterator = this.getSidePages(is).iterator();
		while(iterator.hasNext())
		{
			int var1 = (Integer)iterator.next();
			nbtTagCompound.setInteger("page", var1);
	        nbtlist.appendTag(nbtTagCompound);
	        nbtTagCompound = new NBTTagCompound();
		}
		nbtTagCompound.setInteger("page", id);
        nbtlist.appendTag(nbtTagCompound);
        nbtTagCompound = new NBTTagCompound();
		if(nbtlist.tagCount() > 0)
		{
			is.setTagInfo("SidePages", nbtlist);
		}
		return is;
	}
	public List getSidePages(ItemStack par1ItemStack)
    {
        if (par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().hasKey("SidePages"))
        {
            ArrayList var6 = new ArrayList();
            NBTTagList var3 = par1ItemStack.getTagCompound().getTagList("SidePages");

            for (int var4 = 0; var4 < var3.tagCount(); ++var4)
            {
                NBTTagCompound var5 = (NBTTagCompound)var3.tagAt(var4);
                var6.add(var5.getInteger("page"));
            }

            return var6;
        }
        else
        {
        	return new ArrayList();
        }
    }

}
