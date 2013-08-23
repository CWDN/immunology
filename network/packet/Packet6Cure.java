package piefarmer.immunology.network.packet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import piefarmer.immunology.gui.ContainerMedicalResearchTable;
import piefarmer.immunology.item.ItemCure;
import piefarmer.immunology.tileentity.TileEntityMedicalResearchTable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class Packet6Cure extends ImmunPacket{

	private int xCoord = 0;
	private int yCoord = 0;
	private int zCoord = 0;
	private List<Integer> effects = new ArrayList<Integer>();
	private int slot;
	
	public Packet6Cure(){}
	
	public Packet6Cure(int x, int y, int z, List sideeffects, int slotnumber)
	{
		this.xCoord = x;
		this.yCoord = y;
		this.zCoord = z;
		this.effects = sideeffects;
		this.slot = slotnumber;
	}
	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(xCoord);
		out.writeInt(yCoord);
		out.writeInt(zCoord);
		out.writeInt(slot);
		out.writeInt(effects.size());
		Iterator iterator = effects.iterator();
		while(iterator.hasNext())
		{
			int effect = (Integer)iterator.next();
			out.writeInt(effect);
		}
	}

	@Override
	public void read(ByteArrayDataInput in) throws ProtocolException {
		this.xCoord = in.readInt();
		this.yCoord = in.readInt();
		this.zCoord = in.readInt();
		this.slot = in.readInt();
		int count = in.readInt();
		for(int i = 0; i < count; i++)
		{
			this.effects.add(in.readInt());
		}
	}

	@Override
	public void execute(EntityPlayer player, Side side)
			throws ProtocolException {
		TileEntity tile = player.worldObj.getBlockTileEntity(xCoord, yCoord, zCoord);
		if(tile instanceof TileEntityMedicalResearchTable)
		{
			TileEntityMedicalResearchTable tileentity = (TileEntityMedicalResearchTable)tile;
			tileentity.setItemStackSideEffects(effects, slot);
		}
	}

}
