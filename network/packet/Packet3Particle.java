package piefarmer.immunology.network.packet;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

import piefarmer.immunology.client.particle.ParticleEffects;
import piefarmer.immunology.disease.Disease;

import net.minecraft.entity.player.EntityPlayer;

public class Packet3Particle extends ImmunPacket{

	int particle;
	double posX;
	double posY;
	double posZ;
	public Packet3Particle(){}
	
	public Packet3Particle(int par1Particle, double par2PosX, double par3PosY, double par4PosZ)
	{
		this.particle = par1Particle;
		this.posX = par2PosX;
		this.posY = par3PosY;
		this.posZ = par4PosZ;
	}
	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(particle);
		out.writeDouble(posX);
		out.writeDouble(posY);
		out.writeDouble(posZ);
    }

    @Override
	public void read(ByteArrayDataInput in) throws ProtocolException {
    	this.particle = in.readInt();
    	this.posX = in.readDouble();
    	this.posY = in.readDouble();
    	this.posZ = in.readDouble();
    }

    @Override
	public void execute(EntityPlayer player, Side side) throws ProtocolException {
		switch(particle)
		{
			case 0:
				ParticleEffects.spawnParticle("chickenPox", posX, posY, posZ, 0, 0, 0);
				break;
			case 1:
				ParticleEffects.spawnParticle("test2", posX, posY, posZ, 0, 0, 0);
				break;
		}

		
	}

}
