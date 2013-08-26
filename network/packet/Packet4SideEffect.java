package piefarmer.immunology.network.packet;

import java.util.Iterator;

import piefarmer.immunology.common.Immunology;
import piefarmer.immunology.disease.Disease;
import piefarmer.immunology.disease.DiseaseEffect;
import piefarmer.immunology.entity.EntityDiseaseHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class Packet4SideEffect extends ImmunPacket{

	private int hash;
	private int diseaseeffectID;
	private int duration;
	private boolean load;
	public Packet4SideEffect(){}
	
	public Packet4SideEffect(DiseaseEffect par1Effect, int par2Hash)
	{
		this.hash = par2Hash;
		this.diseaseeffectID = par1Effect.getDiseaseEffectID();
		this.duration = par1Effect.getDuration();
		this.load = par1Effect.getLoad();
	}
	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(hash);
		out.writeInt(diseaseeffectID);
		out.writeInt(duration);
		out.writeBoolean(load);
	}

	@Override
	public void read(ByteArrayDataInput in) throws ProtocolException {
		this.hash = in.readInt();
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
		if(Immunology.loadedEntityList.containsKey(hash))
		{
			if(side.isServer())
			{
				
				EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(hash);
				hand.addSideEffect(effect);
			}
			else if(side.isClient())
			{
				EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(hash);
				hand.addSideEffectClient(effect);
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
						if(side.isClient())
						{
							EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(hash);
							if(hand != null)
							{
								hand.addSideEffectClient(effect);
							}
						}
						else
						{
							EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(hash);
							if(hand != null)
							{
								hand.addSideEffect(effect);
							}
						}
						break;
					
					}
				}
			}
		
		}
		
	}



}
