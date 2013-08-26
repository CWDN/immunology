package piefarmer.immunology.network.packet;

import java.util.Iterator;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import piefarmer.immunology.common.Immunology;
import piefarmer.immunology.entity.EntityDiseaseHandler;
import piefarmer.immunology.network.packet.ImmunPacket.ProtocolException;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class Packet5RemoveSideEffect extends ImmunPacket{
	private int diseaseeffectID;
	private int hash;
	public Packet5RemoveSideEffect(){}
	public Packet5RemoveSideEffect(int id, int par2Hash)
	{
		this.diseaseeffectID = id;
		this.hash = par2Hash;
	}
	
	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(diseaseeffectID);
		out.writeInt(hash);
	}

	@Override
	public void read(ByteArrayDataInput in) throws ProtocolException {
		this.diseaseeffectID = in.readInt();
		this.hash = in.readInt();
	}

	@Override
	public void execute(EntityPlayer player, Side side)
			throws ProtocolException {
		if(side.isClient())
		{
			if(Immunology.loadedEntityList.containsKey(hash))
			{
				EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(hash);
				hand.removeSideEffectClient(this.diseaseeffectID);
			}
			else
    		{
    			Iterator i = player.worldObj.loadedEntityList.iterator();
    			while(i.hasNext())
    			{
    				Entity en = (Entity) i.next();
    				if(en instanceof EntityLiving)
    				{
    					EntityLiving living = (EntityLiving)en;
	    				if(hash == living.hashCode())
	    				{
	    					Immunology.loadedEntityList.put(hash, new EntityDiseaseHandler(living));
	    					EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(hash);
	    					if(hand != null)
	    					{
	    						hand.removeSideEffectClient(this.diseaseeffectID);
	    					}
	    					break;
	    				}
    				}
    			}
    		
    		}
		}
	}

}
