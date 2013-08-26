package piefarmer.immunology.network.packet;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Iterator;

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
	private int hash;
	public Packet2RemoveDisease(int par1DiseaseID, int par2Hash)
	{
		this.diseaseid = par1DiseaseID;
		this.hash = par2Hash;
	}
	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(diseaseid);
		out.writeInt(hash);
    }

    @Override
	public void read(ByteArrayDataInput in) throws ProtocolException {
    	this.diseaseid = in.readInt();
    	this.hash = in.readInt();
    }

    @Override
	public void execute(EntityPlayer player, Side side) throws ProtocolException {
    	if(side.isClient())
    	{
    		if(Immunology.loadedEntityList.containsKey(hash))
    		{
	    		EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(hash);
	    		if(hand != null)
	    		{
	    			hand.removeDiseaseClient(diseaseid);
	    		}
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
	    						hand.removeDiseaseClient(diseaseid);
	    					}
	    					break;
	    				}
    				}
    			}
    		
    		}
    		
    	}
	}

}
