package piefarmer.immunology.disease;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import piefarmer.immunology.client.particle.ParticleEffects;
import piefarmer.immunology.common.Immunology;
import piefarmer.immunology.entity.EntityDiseaseHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;

public class DiseaseChickenPox extends Disease{
	
	public World worldObj;
	Random rand = new Random();
	
	public DiseaseChickenPox(int par1, int par2, List<Integer> effects, String name, boolean write) {
		super(par1, par2, 0, effects, name, write);
	}
	public DiseaseChickenPox(Disease disease)
	{
		super(disease.getdiseaseID(), disease.getDuration(), 0, disease.DiseaseEffects, disease.getName());
	}
	public void performEffect(EntityLiving entityliving)
	{
		super.performEffect(entityliving);
		if(this.getDuration() < 240000)
		{
			int randint = this.getDuration();
			int rint = rand.nextInt(randint);
			if(rint <= 1)
			{
				this.setStage(1);
				this.setDuration(168000);
			}
		}
	}
	public static void entityUpdate(EntityLiving entityliving){
		if(entityliving instanceof EntityChicken)
		{
			Random rand = new Random();
			int i = rand.nextInt(200000);
			if(i == 1)
			{
				EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(entityliving.hashCode());
				hand.addDisease(chickenPox.getInstancebyName(chickenPox));
				System.out.println(entityliving.getEntityName() + " has caught ChickenPox at " + entityliving.posX + " "+ entityliving.posY + " " + entityliving.posZ);
			}
		}
	}

}
