package piefarmer.immunology.disease;

import java.util.List;
import java.util.Random;

import piefarmer.immunology.common.Immunology;
import piefarmer.immunology.entity.EntityDiseaseHandler;

import cpw.mods.fml.common.network.PacketDispatcher;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;

public class DiseaseInfluenza extends Disease{
	
	public World worldObj; 
	public DiseaseInfluenza(int par1, int par2, int par3,
			List<Integer> list, String name, boolean b) {
		super(par1, par2, par3, list, name, b);
	}
	public DiseaseInfluenza(Disease disease)
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
					PacketDispatcher.sendPacketToAllPlayers(getParticlePacket(1, entityliving.posX, entityliving.posY + 5, entityliving.posZ));
				}
			}
		}
		else
		{
			EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(entityliving.hashCode());
			hand.removeDisease(this.getdiseaseID());
		}
	}
	public static void entityUpdate(EntityLiving entityliving)
	{
		if(DiseaseCommonCold.getBiomeTemperature(entityliving) > 50000)
		{
			Random rand = new Random();
			int i = rand.nextInt(200000);
			if(i == 1)
			{
				EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(entityliving.hashCode());
				hand.addDisease(new Disease(influenza));
				System.out.println(entityliving.getEntityName() + " has caught Influenza at " + entityliving.posX + " "+ entityliving.posY + " " + entityliving.posZ);
			}
		}
	}
}
