package piefarmer.immunology.gui;

import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import piefarmer.immunology.item.ItemCure;
import piefarmer.immunology.tileentity.TileEntityMedicalResearchTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMedicalResearchTable extends Container{

	private TileEntityMedicalResearchTable medrestbl;
	private IInventory testSlot = new SlotMedicalResearchTable(this, "Cure", true, 1);
	private boolean isExpanded = false;
	public ContainerMedicalResearchTable(InventoryPlayer par1InventoryPlayer, TileEntityMedicalResearchTable par2TileEntityMedResTbl)
	{
		this.medrestbl = par2TileEntityMedResTbl;
		
		
		//Cures
		addSlotToContainer(new SlotCure(this, par2TileEntityMedResTbl, 0, 11, 50));
		addSlotToContainer(new SlotCure(this, par2TileEntityMedResTbl, 1, 29, 50));
		addSlotToContainer(new SlotCure(this, par2TileEntityMedResTbl, 2, 47, 50));
		addSlotToContainer(new SlotCure(this, par2TileEntityMedResTbl, 3, 65, 50));
				
		//Row 2
		addSlotToContainer(new Slot(par2TileEntityMedResTbl, 4, 11, 94));
		addSlotToContainer(new Slot(par2TileEntityMedResTbl, 5, 29, 94));
		addSlotToContainer(new Slot(par2TileEntityMedResTbl, 6, 47, 94));
		addSlotToContainer(new Slot(par2TileEntityMedResTbl, 7, 65, 94));
		
		//Row 2
		addSlotToContainer(new Slot(par2TileEntityMedResTbl, 8, 11, 111));
		addSlotToContainer(new Slot(par2TileEntityMedResTbl, 9, 29, 111));
		addSlotToContainer(new Slot(par2TileEntityMedResTbl, 10, 47, 111));
		addSlotToContainer(new Slot(par2TileEntityMedResTbl, 11, 65, 111));
		
		
		//Book
		addSlotToContainer(new Slot(par2TileEntityMedResTbl, 12, 96, 34));
		
		bindPlayerInventory(par1InventoryPlayer);
	
	}
	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return medrestbl.isUseableByPlayer(var1);
	}
	 protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
         for (int i = 0; i < 3; i++) {
                 for (int j = 0; j < 9; j++) {
                         addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                                         8 + j * 18, 137 + i * 18));
                 }
         }

         for (int i = 0; i < 9; i++) {
                 addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 195));
         }
	 }
	 @Override
     public ItemStack transferStackInSlot(EntityPlayer player, int par2)
     {
		 ItemStack itemstack = null;
		 Slot slot = (Slot)inventorySlots.get(par2);
		 if (slot != null && slot.getHasStack())
	        {
	            ItemStack itemstack1 = slot.getStack();
	            itemstack = itemstack1.copy();

	            if ((par2 < 0 || par2 > 2) && par2 != 3)
	            {
	                if (SlotCure.canHoldCure(itemstack))
	                {
	                    if (!this.mergeItemStack(itemstack1, 0, 4, false))
	                    {
	                        return null;
	                    }
	                }
	                else if (par2 >= 4 && par2 < 31)
	                {
	                    if (!this.mergeItemStack(itemstack1, 31, 40, false))
	                    {
	                        return null;
	                    }
	                }
	                else if (par2 >= 31 && par2 < 40)
	                {
	                    if (!this.mergeItemStack(itemstack1, 4, 31, false))
	                    {
	                        return null;
	                    }
	                }
	                else if (!this.mergeItemStack(itemstack1, 4, 40, false))
	                {
	                    return null;
	                }
	            }
	            else
	            {
	                if (!this.mergeItemStack(itemstack1, 4, 40, true))
	                {
	                    return null;
	                }

	                slot.onSlotChange(itemstack1, itemstack);
	            }

	            if (itemstack1.stackSize == 0)
	            {
	                slot.putStack((ItemStack)null);
	            }
	            else
	            {
	                slot.onSlotChanged();
	            }

	            if (itemstack1.stackSize == itemstack.stackSize)
	            {
	                return null;
	            }

	            slot.onPickupFromSlot(player, itemstack1);
	        }
            
         return itemstack;
     }
		


}
