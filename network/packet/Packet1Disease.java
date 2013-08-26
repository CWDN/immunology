package piefarmer.immunology.network.packet;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import piefarmer.immunology.common.Immunology;
import piefarmer.immunology.disease.Disease;
import piefarmer.immunology.entity.EntityDiseaseHandler;

public class Packet1Disease extends ImmunPacket{

	private int diseaseid;
	private int stage;
	private int duration;
	private int length;
	private boolean load;
	List<Integer> effects = new ArrayList<Integer>();
    public Packet1Disease(Disease par1Disease) {
            this.diseaseid = par1Disease.getdiseaseID();
            this.stage = par1Disease.getStage();
            this.duration = par1Disease.getDuration();
            this.length = par1Disease.DiseaseEffects.size();
            this.effects = par1Disease.DiseaseEffects;
            this.load = par1Disease.getLoad();
    }

    public Packet1Disease() { }

    @Override
	public void write(ByteArrayDataOutput out) {
    	
            out.writeInt(diseaseid);
            out.writeInt(stage);
            out.writeInt(duration);
            out.writeBoolean(load);
            out.writeInt(length);
            for(int index = 0; index < length; index++)
			{
            	out.writeInt(effects.get(index));
			}
            
    }

    @Override
	public void read(ByteArrayDataInput in) throws ProtocolException {
    	
			diseaseid = in.readInt();
			stage = in.readInt();
			duration = in.readInt();
			load = in.readBoolean();
			length = in.readInt();
			for(int index = 0; index < length; index++)
			{
				effects.add(in.readInt());
			}
    }

    @Override
	public void execute(EntityPlayer player, Side side) throws ProtocolException {
    	
   		Disease disease = new Disease(Disease.diseaseTypes[this.diseaseid]);
		disease.setLoad(this.load);
		disease.setDuration(duration);
		disease.setStage(stage);
		disease.setDiseaseEffects(effects);
		if(Immunology.loadedEntityList.containsKey(player))
		{
			if(side.isClient())
			{
				EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(player);
				if(hand != null)
				{
					hand.addDiseaseClient(disease);
				}
			}
			else
			{
				EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(player);
				if(hand != null)
				{
					hand.addDiseaseServer(disease);
				}
			}
		}
		else
		{
			Immunology.loadedEntityList.put(player, new EntityDiseaseHandler(player));
			EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(player);
			if(hand != null)
			{
				hand.addDisease(disease);
			}
		}
		
   	}	
}
