package piefarmer.immunology.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import piefarmer.immunology.disease.DiseaseEffect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class ItemDecayingFood extends ItemFood{

	
	public ItemDecayingFood(int par1, int par2, float par3, boolean par4) {
		super(par1, par2, par3, par4);
	}
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		int decay = this.getDecay(par1ItemStack);
		if(decay == -1)
		{
			Random rand = new Random();
			this.setDecay(par1ItemStack, 6000 + rand.nextInt(6000));
		}
		else
		{
			decay--;
			this.setDecay(par1ItemStack, decay);
			if(decay == 0)
			{
				if(par3Entity instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer)par3Entity;
					player.inventory.mainInventory[par4] = new ItemStack(Item.rottenFlesh, par1ItemStack.stackSize);
				}
				 
			}
		}
		
		
	}
	public ItemStack setDecay(ItemStack is, int decay)
	{
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		NBTTagList nbtlist = new NBTTagList();
		nbtTagCompound.setInteger("decay", decay);
	    nbtlist.appendTag(nbtTagCompound);
		if(nbtlist.tagCount() > 0)
		{
			is.setTagInfo("Decay", nbtlist);
		}
		return is;
	}
	public int getDecay(ItemStack par1ItemStack)
    {
        if (par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().hasKey("Decay"))
        {
            NBTTagList var3 = par1ItemStack.getTagCompound().getTagList("Decay");

            for (int var4 = 0; var4 < var3.tagCount(); ++var4)
            {
                NBTTagCompound var5 = (NBTTagCompound)var3.tagAt(var4);
                if(var5.hasKey("decay"))
                {
                	return var5.getInteger("decay");
                }
            }
        }
		return -1;
    }

}
