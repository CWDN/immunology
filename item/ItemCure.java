package piefarmer.immunology.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import piefarmer.immunology.disease.Disease;
import piefarmer.immunology.disease.DiseaseEffect;
import piefarmer.immunology.entity.EntityDiseaseHandler;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import piefarmer.immunology.common.*;

public class ItemCure extends Item{

	private List sideEffects;
	private Icon curecontentsicon;
	public ItemCure(int par1) {
		super(par1);
		this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(Immunology.tabImmunology);
	}
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            --par1ItemStack.stackSize;
        }
		if (!par2World.isRemote)
	    {
			List list = this.getEffects(par1ItemStack);
			 if(Immunology.loadedEntityList.containsKey(par3EntityPlayer.hashCode()))
             {
	            EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(par3EntityPlayer.hashCode());
	            if (list != null)
	            {
	                Iterator iterator = list.iterator();
	               
	                while (iterator.hasNext())
	                {
	                    DiseaseEffect effect = (DiseaseEffect)iterator.next();
	                    hand.addSideEffect(effect);
	                }
	            }
	            
	            hand.removeDisease(par1ItemStack.getItemDamage() - 1);
             }
	        
	    }
		if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            if (par1ItemStack.stackSize <= 0)
            {
                return new ItemStack(Item.glassBottle);
            }

            par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
        }
		return par1ItemStack;
    
    }
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
		
    }
	public static ItemStack setSideEffects(ItemStack is, List effects)
	{
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		NBTTagList nbtlist = new NBTTagList();
		Iterator iterator = effects.iterator();
		while(iterator.hasNext())
		{
			int var1 = (Integer)iterator.next();
			nbtTagCompound.setByte("Id", (byte)var1);
	        nbtTagCompound.setInteger("Duration", DiseaseEffect.diseaseEffects[var1].getDuration());
	        nbtlist.appendTag(nbtTagCompound);
	        nbtTagCompound = new NBTTagCompound();
		}
		if(nbtlist.tagCount() > 0)
		{
			is.setTagInfo("CustomSideEffects", nbtlist);
		}
		return is;
	}
	public void registerIcons(IconRegister par1)
	{
		this.itemIcon = par1.registerIcon(Immunology.modid + ":" + this.getUnlocalizedName());
		this.curecontentsicon = par1.registerIcon("potion_contents");
	}
	public Icon getIconFromDamageForRenderPass(int par1, int par2)
    {
        return par2 == 0 ? this.curecontentsicon : super.getIconFromDamageForRenderPass(par1, par2);
    }
	public List getEffects(ItemStack par1ItemStack)
    {
        if (par1ItemStack.hasTagCompound() && par1ItemStack.getTagCompound().hasKey("CustomSideEffects"))
        {
            ArrayList var6 = new ArrayList();
            NBTTagList var3 = par1ItemStack.getTagCompound().getTagList("CustomSideEffects");

            for (int var4 = 0; var4 < var3.tagCount(); ++var4)
            {
                NBTTagCompound var5 = (NBTTagCompound)var3.tagAt(var4);
                var6.add(DiseaseEffect.readCustomSideEffectFromNBT(var5));
            }

            return var6;
        }
        else
        {
        	return null;
        }
    }
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
	   return 32;
	}
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
	   return EnumAction.drink;
	}
	@SideOnly(Side.CLIENT)
    public int getColorFromDamage(int par1)
    {
        return PotionHelper.func_77915_a(par1, false);
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        return par2 > 0 ? 16777215 : this.getColorFromDamage(par1ItemStack.getItemDamage());
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        if (par1ItemStack.getItemDamage() != 0)
        {
             String var6 = "Side Effects: ";
             par3List.add("\u00a77" + var6);
             sideEffects = this.getEffects(par1ItemStack);
             if(sideEffects != null && !sideEffects.isEmpty())
             {
            	 Iterator iterator = sideEffects.iterator();
            	 while(iterator.hasNext())
            	 {
            		 DiseaseEffect var1 = (DiseaseEffect) iterator.next();
            		 par3List.add("\u00a7c"+ DiseaseEffect.diseaseEffects[var1.getDiseaseEffectID()].getName() + " (" + StringUtils.ticksToElapsedTime(var1.getDuration()) + ")");
            	 }
             }
             else
             {
            	 par3List.add("\u00a77None");
             }
        }

    }
	@SideOnly(Side.CLIENT)
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        super.getSubItems(par1, par2CreativeTabs, par3List);

        for(int i = 0; i < Disease.diseaseTypes.length; i++)
        {
        	Disease disease = Disease.diseaseTypes[i];
        	if(disease != null)
        	{
        		int var7 = (Integer)disease.getdiseaseID();
        		par3List.add(new ItemStack(par1, 1, var7 + 1));
        	}
            
        }
        par3List.remove(3);
    }
	public String getItemDisplayName(ItemStack par1ItemStack)
    {
        if (par1ItemStack.getItemDamage() == 0)
        {
            return StatCollector.translateToLocal("item.emptyCure.name").trim();
        }
        else
        {
            return Disease.diseaseTypes[par1ItemStack.getItemDamage() - 1].getName() + " Cure";
        }
    }

}
