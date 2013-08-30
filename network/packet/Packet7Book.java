package piefarmer.immunology.network.packet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import piefarmer.immunology.item.ItemMedicalBook;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class Packet7Book extends ImmunPacket{

	int slotnum;
	List<Integer> pages = new ArrayList<Integer>();
	public Packet7Book(){}
	public Packet7Book(int slot, List<Integer> diseases)
	{
		slotnum = slot;
		pages = diseases;
	}
	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(slotnum);
		out.writeInt(pages.size());
		Iterator iter = pages.iterator();
		while(iter.hasNext())
		{
			Integer i = (Integer) iter.next();
			out.writeInt(i);
		}
	}

	@Override
	public void read(ByteArrayDataInput in) throws ProtocolException {
		slotnum = in.readInt();
		int count = in.readInt();
		for(int i = 0; i < count; i++)
		{
			pages.add(in.readInt());
		}
		
	}

	@Override
	public void execute(EntityPlayer player, Side side)
			throws ProtocolException {
		ItemStack is = player.inventory.getStackInSlot(slotnum);
		if(is != null && is.getItem() instanceof ItemMedicalBook)
		{
			ItemMedicalBook i = (ItemMedicalBook) is.getItem();
			Iterator iter = this.pages.iterator();
			while(iter.hasNext())
			{
				Integer in = (Integer)iter.next();
				i.setDiseasePages(is, in);
			}
		}
		
	}

}
