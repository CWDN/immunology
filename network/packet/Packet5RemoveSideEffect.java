package piefarmer.immunology.network.packet;

import net.minecraft.entity.player.EntityPlayer;
import piefarmer.immunology.common.Immunology;
import piefarmer.immunology.entity.EntityDiseaseHandler;
import piefarmer.immunology.network.packet.ImmunPacket.ProtocolException;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class Packet5RemoveSideEffect extends ImmunPacket{
	private int diseaseeffectID;
	public Packet5RemoveSideEffect(){}
	public Packet5RemoveSideEffect(int id)
	{
		this.diseaseeffectID = id;
	}
	
	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(diseaseeffectID);
	}

	@Override
	public void read(ByteArrayDataInput in) throws ProtocolException {
		this.diseaseeffectID = in.readInt();
	}

	@Override
	public void execute(EntityPlayer player, Side side)
			throws ProtocolException {
		if(side.isClient())
		{
			if(Immunology.loadedEntityList.containsKey(player))
			{
				EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(player);
				hand.removeSideEffectClient(this.diseaseeffectID);
			}
		}
	}

}
