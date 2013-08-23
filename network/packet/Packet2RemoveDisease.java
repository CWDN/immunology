package piefarmer.immunology.network.packet;

import java.io.DataInputStream;
import java.io.IOException;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import piefarmer.immunology.common.Immunology;
import piefarmer.immunology.disease.Disease;
import piefarmer.immunology.entity.EntityDiseaseHandler;

public class Packet2RemoveDisease extends ImmunPacket{
	
	public Packet2RemoveDisease(){}
	
	private int diseaseid;
	
	public Packet2RemoveDisease(int par1DiseaseID)
	{
		this.diseaseid = par1DiseaseID;
	}
	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(diseaseid);
    }

    @Override
	public void read(ByteArrayDataInput in) throws ProtocolException {
    	this.diseaseid = in.readInt();
    }

    @Override
	public void execute(EntityPlayer player, Side side) throws ProtocolException {
    	if(side.isClient())
    	{
    		EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(player.entityId);
    		if(hand != null)
    		{
    			hand.removeDiseaseClient(diseaseid);
    		}
    		else
			{
				while(player.entityId >= Immunology.loadedEntityList.size())
				{
					Immunology.loadedEntityList.add(null);
				}
				Immunology.loadedEntityList.add(player.entityId, new EntityDiseaseHandler(player));
				EntityDiseaseHandler handler = (EntityDiseaseHandler) Immunology.loadedEntityList.get(player.entityId);
		        handler.readNBTData(player.getEntityData());
			}
    	}
	}

}
