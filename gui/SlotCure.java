package piefarmer.immunology.gui;

import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotCure extends Slot{
	
	final ContainerMedicalResearchTable container;

	SlotCure(ContainerMedicalResearchTable par1Container, IInventory par2IInventory, int par3, int par4, int par5)
    {
        super(par2IInventory, par3, par4, par5);
        this.container = par1Container;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return canHoldCure(par1ItemStack);
    }
    
    public static boolean canHoldCure(ItemStack par1ItemStack)
    {
    	return par1ItemStack != null && par1ItemStack.getItem().itemID == Item.potion.itemID;
    }

}
