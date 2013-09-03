package piefarmer.immunology.tileentity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import piefarmer.immunology.common.Immunology;
import piefarmer.immunology.disease.Disease;
import piefarmer.immunology.gui.MedicalResearchTableRecipes;
import piefarmer.immunology.item.ItemCure;
import piefarmer.immunology.item.ItemMedicalBook;
import piefarmer.immunology.item.Items;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ForgeDummyContainer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.brewing.PotionBrewedEvent;

public class TileEntityMedicalResearchTable extends TileEntity implements ISidedInventory{

	public ItemStack itemstacks[];
	private boolean isActive;
	public String entityname = ""; 
	public HashMap entityDiseases = new HashMap();
	public float[][] potioncolours = new float[4][3];
	public int[] brewTime = new int[4];
	private int[][] ingredientID = new int[4][2];
	
	
	public TileEntityMedicalResearchTable(){
		itemstacks = new ItemStack[13];
		this.potioncolours[0][0] = 1.0F;
		this.potioncolours[0][1] = 1.0F;
		this.potioncolours[0][2] = 1.0F;
		
		this.potioncolours[1][0] = 1.0F;
		
		this.potioncolours[2][1] = 1.0F;
		
		this.potioncolours[3][2] = 1.0F;
	}

	@Override
	public int getSizeInventory() {
		return itemstacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return itemstacks[var1];
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		 if (itemstacks[var1] != null)
         {
                 if (itemstacks[var1].stackSize <= var2)
                 {
                         ItemStack itemstack = itemstacks[var1];
                         itemstacks[var1] = null;
                         return itemstack;
                 }

                 ItemStack itemstack1 = itemstacks[var1].splitStack(var2);

                 if (itemstacks[var1].stackSize == 0)
                 {
                         itemstacks[var1] = null;
                 }

                 return itemstack1;
         }
         else
         {
                 return null;
         }

	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		
		 if (itemstacks[var1] != null)
         {
                 ItemStack itemstack = itemstacks[var1];
                 itemstacks[var1] = null;
                 return itemstack;
         }
         else
         {
                 return null;
         }

	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2) {
		itemstacks[var1] = var2;

        if (var2 != null && var2.stackSize > getInventoryStackLimit())
        {
                var2.stackSize = getInventoryStackLimit();
        }

		
	}

	@Override
	public String getInvName() {
		return "container.medicalresearchtable";
	}

	@Override
	public int getInventoryStackLimit() {
		
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && var1.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;

	}

	public void openChest() {
		
	}
	public void closeChest() {
		
	}
	@Override
	public void readFromNBT(NBTTagCompound tagCompound){
		super.readFromNBT(tagCompound);

		NBTTagList tagList = tagCompound.getTagList("Inventory");

		for(int i = 0; i < tagList.tagCount(); i++){
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);

			byte slot = tag.getByte("Slot");

			if(slot >= 0 && slot < itemstacks.length){
				itemstacks[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
		this.entityname = tagCompound.getString("EntityName");
		NBTTagList var2;
		int var3;
		if(tagCompound.hasKey("ActiveDiseases"))
        {
        	var2 = tagCompound.getTagList("ActiveDiseases");
        	for(var3 = 0; var3 < var2.tagCount(); ++var3)
        	{
        		NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
        		Disease var5 = Disease.readCustomDiseaseFromNBT(var4);
        		this.entityDiseases.put(Integer.valueOf(var3), var5);
        	}
        }
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound){
		super.writeToNBT(tagCompound);

		NBTTagList itemList = new NBTTagList();

		for(int i = 0; i < itemstacks.length; i++){
			ItemStack stack = itemstacks[i];

			if(stack != null){
				NBTTagCompound tag = new NBTTagCompound();

				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		NBTTagCompound tag = new NBTTagCompound();
		tagCompound.setTag("Inventory", itemList);
		tagCompound.setString("EntityName", this.entityname);
		NBTTagList var6;
		if(!this.entityDiseases.isEmpty())
        {
        	var6 = new NBTTagList();
        	Iterator var8 = this.entityDiseases.values().iterator();
        	while (var8.hasNext())
        	{
        		Disease var5 = (Disease)var8.next();
        		var6.appendTag(var5.writeCustomDiseaseToNBT(new NBTTagCompound()));
        	}
        	tagCompound.setTag("ActiveDiseases", var6);
        }
	}


	public void updateEntity()
	{
		for(int i = 0; i < 4; i++)
		{
			if(brewTime[i] > 0)
			{
				--this.brewTime[i];
				if (this.brewTime[i] == 0)
		        {
					this.brewCures(i);
					this.onInventoryChanged();
		        }
				else if (!this.canBrew(i))
				{
					this.brewTime[i] = 0;
					this.onInventoryChanged();
				}
				else if (this.ingredientID[i][0] != this.itemstacks[i + 4].itemID)
				{
					this.brewTime[i] = 0;
					this.onInventoryChanged();
		        }
			}
			
			else if (this.canBrew(i))
	        {
	            this.brewTime[i] = 1000;
	            this.ingredientID[i][0] = this.itemstacks[i + 4].itemID;
	            this.ingredientID[i][1] = this.itemstacks[i + 8].itemID;
	        }
		} 
		super.updateEntity();
	}
	private boolean canBrew(int col) {
		
		if (this.itemstacks[col + 4] != null && this.itemstacks[col + 4].stackSize > 0)
        {
            ItemStack itemstack = this.itemstacks[col + 4];
            ItemStack itemstack2 = this.itemstacks[col + 8];
            if (!MedicalResearchTableRecipes.brewing().isCureIngredient(itemstack))
            {
                return false;
            }
            else
            {
                boolean flag = false;
                if (this.itemstacks[col] != null && this.itemstacks[col].getItem() instanceof ItemPotion)
                {
                	ItemStack is = MedicalResearchTableRecipes.brewing().getBrewingResult(itemstack, itemstack2);
                	if(is != null)
                	{
                		flag = true;
                	}
                    
                }

                return flag;
            }
        }
        else
        {
            return false;
        }
	}
	private void brewCures(int i) {
		if (this.canBrew(i))
        {
			ItemStack itemstack = this.itemstacks[i + 4];
            ItemStack itemstack2 = this.itemstacks[i + 8];
            if (this.itemstacks[i] != null && this.itemstacks[i].getItem() instanceof ItemPotion)
            {
            	ItemStack is = MedicalResearchTableRecipes.brewing().getBrewingResult(itemstack, itemstack2);
            	if(is != null)
            	{
            		if(is.stackSize == 0)
            		{
            			is.stackSize++;
            		}
            		this.itemstacks[i] = is;
            		if(this.itemstacks[12].getItem() instanceof ItemMedicalBook)
            		{
            			ItemMedicalBook book;
            			if(itemstack.itemID != Items.cure.itemID)
            			{
            				book = (ItemMedicalBook)this.itemstacks[12].getItem();
            				book.setCurePages(this.itemstacks[12], is.getItemDamage() - 1);
            			}
            			else if(itemstack.itemID == Items.cure.itemID)
            			{
            				book = (ItemMedicalBook)this.itemstacks[12].getItem();
            				if(!itemstack.equals(is))
            				{	
            					Integer effectid = MedicalResearchTableRecipes.brewing().getDiseaseEffect(itemstack2);
            					book.setSidePages(this.itemstacks[12], effectid);
            				}
            				
            			}
            		}
            		
            	}
                    
            }
            if (Item.itemsList[itemstack.itemID].hasContainerItem())
    		{
    			this.itemstacks[i + 4] = Item.itemsList[itemstack.itemID].getContainerItemStack(itemstacks[3]);
    		}
    		else
    		{
    			--this.itemstacks[i + 4].stackSize;

    			if (this.itemstacks[i + 4].stackSize <= 0)
    			{
    				this.itemstacks[i + 4] = null;
    			}
            }
            if (Item.itemsList[itemstack2.itemID].hasContainerItem())
    		{
    			this.itemstacks[i + 8] = Item.itemsList[itemstack2.itemID].getContainerItemStack(itemstacks[3]);
    		}
    		else
    		{
    			--this.itemstacks[i + 8].stackSize;

    			if (this.itemstacks[i + 8].stackSize <= 0)
    			{
    				this.itemstacks[i + 8] = null;
    			}
            }
            if(this.itemstacks[i] != null)
            {
            	
            }
        }
    }
	public void setEntityName(String name, EntityPlayer player, List list)
	{
		this.entityname = name;
    	for(int i = 0; i < list.size(); i++)
    	{
    		Disease var2 = (Disease)list.get(i);
    		entityDiseases.put(i, var2);
    	}
	}
	public boolean isActive()
	{
		return this.isActive;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}
	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return null;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}
	public void setItemStackSideEffects(List<Integer> asList, int slot) {
		ItemStack is = (ItemStack) this.itemstacks[slot];
		if(is != null)
		{
			is = new ItemStack(Items.cure.itemID , 1, is.getItemDamage());
			ItemCure.setSideEffects(is, asList);
			this.itemstacks[slot] = is;
		}
	}
	public ItemStack getItemStack(int index)
	{
		return this.itemstacks[index];
	}
	public void setItemStack(int index, ItemStack is)
	{
		this.itemstacks[index] = is;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}
}
