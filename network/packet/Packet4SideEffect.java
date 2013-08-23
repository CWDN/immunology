package piefarmer.immunology.network.packet;

import piefarmer.immunology.common.Immunology;
import piefarmer.immunology.disease.Disease;
import piefarmer.immunology.disease.DiseaseEffect;
import piefarmer.immunology.entity.EntityDiseaseHandler;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class Packet4SideEffect extends ImmunPacket{

	private int diseaseeffectID;
	private int duration;
	private boolean load;
	public Packet4SideEffect(){}
	
	public Packet4SideEffect(DiseaseEffect par1Effect)
	{
		this.diseaseeffectID = par1Effect.getDiseaseEffectID();
		this.duration = par1Effect.getDuration();
		this.load = par1Effect.getLoad();
	}
	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(diseaseeffectID);
		out.writeInt(duration);
		out.writeBoolean(load);
	}

	@Override
	public void read(ByteArrayDataInput in) throws ProtocolException {
		this.diseaseeffectID = in.readInt();
		this.duration = in.readInt();
		this.load = in.readBoolean();
	}

	@Override
	public void execute(EntityPlayer player, Side side)
			throws ProtocolException {
		DiseaseEffect effect = new DiseaseEffect(DiseaseEffect.diseaseEffects[this.diseaseeffectID]);
		effect.setDuration(this.duration);
		effect.setLoad(this.load);
		if(Immunology.loadedEntityList.containsKey(player))
		{
			if(side.isServer())
			{
				
				EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(player.entityId);
				hand.addSideEffect(effect);
			}
			else if(side.isClient())
			{
				EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(player.entityId);
				hand.addSideEffectServer(effect);
			}
		}
		
	}



}
