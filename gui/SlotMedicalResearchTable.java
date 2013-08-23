package piefarmer.immunology.gui;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

public class SlotMedicalResearchTable extends InventoryBasic{

	final ContainerMedicalResearchTable container;
	
	public SlotMedicalResearchTable(ContainerMedicalResearchTable cont, String par1Str, boolean par2, int par3) {
		super(par1Str, par2, par3);
		container = cont;
	}
	@Override
	public int getInventoryStackLimit()
	{
	    return 1;
	}
	@Override
	public void onInventoryChanged()
	{
	    super.onInventoryChanged();
	    this.container.onCraftMatrixChanged(this);
	}
	@Override
	public boolean isStackValidForSlot(int par1, ItemStack par2ItemStack)
	{
	    return true;
	}

}
