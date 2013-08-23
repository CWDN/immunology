package piefarmer.immunology.disease;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.network.PacketDispatcher;

import piefarmer.immunology.client.particle.ParticleEffects;
import piefarmer.immunology.common.Immunology;
import piefarmer.immunology.entity.EntityDiseaseHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class DiseaseCommonCold extends Disease{

	static World worldObj;
	
	public DiseaseCommonCold(int par1, int par2, List<Integer> effects, String name, boolean b) {
		super(par1, par2, 0, effects, name, b);
		}
	public DiseaseCommonCold(Disease disease)
	{
		super(disease.getdiseaseID(), disease.getDuration(), 0, disease.DiseaseEffects, disease.getName());
	}
	
	public void performEffect(EntityLiving entityliving)
	{
		super.performEffect(entityliving);
		if(this.getDuration() > 0)
		{
			worldObj = entityliving.worldObj;
			if(worldObj != null)
			{
				if(!worldObj.isRemote)
				{
					//PacketDispatcher.sendPacketToAllPlayers(getParticlePacket(1, entityliving.posX, entityliving.posY + 5, entityliving.posZ));
				}
			}
			EntityPlayer player = null;
		}
		else
		{
			EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(entityliving.entityId);
			hand.removeDisease(this.getdiseaseID());
		}
	}
	
	public static void entityUpdate(EntityLiving entityliving)
	{
		if(DiseaseCommonCold.getBiomeTemperature(entityliving) < 4000)
		{
			Random rand = new Random();
			int i = rand.nextInt(200000);
			if(i == 1)
			{
				EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(entityliving.entityId);
				if(hand != null)
				{
					hand.addDisease(Disease.getInstancebyName(commonCold));
					System.out.println(entityliving.getEntityName() + " has caught Common Cold at " + entityliving.posX + " "+ entityliving.posY + " " + entityliving.posZ);
				}
				else
				{
					while(entityliving.entityId >= Immunology.loadedEntityList.size())
					{
						Immunology.loadedEntityList.add(null);
					}
					Immunology.loadedEntityList.add(entityliving.entityId, new EntityDiseaseHandler(entityliving));
					EntityDiseaseHandler handler = (EntityDiseaseHandler) Immunology.loadedEntityList.get(entityliving.entityId);
			        handler.readNBTData(entityliving.getEntityData());
			        handler.addDisease(Disease.getInstancebyName(commonCold));
				}
			}
		}
	}
	public static int getBiomeTemperature(EntityLiving entityliving)
	{
		worldObj = entityliving.worldObj;
		if(worldObj != null)
		{
			BiomeGenBase biome = worldObj.getBiomeGenForCoords((int)entityliving.posX, (int)entityliving.posZ);
			return biome.getIntTemperature();
		}
		return 0;
	}

}
