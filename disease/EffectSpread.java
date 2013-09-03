package piefarmer.immunology.disease;

import java.util.Random;

import piefarmer.immunology.common.Immunology;
import piefarmer.immunology.entity.EntityDiseaseHandler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EffectSpread extends DiseaseEffect {
	
	private World worldObj;
	public EffectSpread(int id, int stgAct, int stgEnd, String par1name) {
		super(id, stgAct, stgEnd, par1name);
	}
	public void performEffect(Disease disease, EntityLivingBase living){
		worldObj = living.worldObj;
		double par7 = 5.0D;
		if(worldObj != null && !worldObj.isRemote)
		{
			for (int var12 = 0; var12 < worldObj.loadedEntityList.size(); ++var12)
			{
				if(worldObj.loadedEntityList.size() > var12)
				{
					Entity var13 = (Entity)worldObj.loadedEntityList.get(var12);
					double var14 = var13.getDistanceSq(living.posX, living.posY, living.posZ);
            
					if ((par7 < 0.0D || var14 < par7 * par7))
					{
							Random rand = new Random();
							int i = rand.nextInt(20000);
							EntityPlayer player = null;
							if(var13 instanceof EntityLivingBase)
							{
								EntityLivingBase entityliving = (EntityLivingBase)var13;
								if(i <= 10 && entityliving.entityId != living.entityId)
								{
									if(disease.getName() == "Chicken Pox")
									{
										if(entityliving instanceof EntityChicken || entityliving instanceof EntityPlayer)
										{
											EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(var13.hashCode());
											if(hand != null)
											{
												hand.addDisease(Disease.diseaseTypes[disease.getdiseaseID()]);
												System.out.println(entityliving.getEntityName() + " has caught " + disease.getName() + " at " + 
														entityliving.posX + " "+ entityliving.posY + " " + entityliving.posZ + " by proxy of " + living.getEntityName());
											}
											else
											{
												Immunology.loadedEntityList.put(entityliving.hashCode(), new EntityDiseaseHandler(entityliving));
												hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(entityliving.hashCode());
												if(hand != null)
												{
													hand.addDisease(Disease.diseaseTypes[disease.getdiseaseID()]);
													System.out.println(entityliving.getEntityName() + " has caught " + disease.getName() + " at " + 
															entityliving.posX + " "+ entityliving.posY + " " + entityliving.posZ + " by proxy of " + living.getEntityName());
												}
											}
										}
									}
									else
									{
										if(Immunology.loadedEntityList.containsKey(entityliving.hashCode()))
										{
											EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(entityliving.hashCode());
											if(hand != null)
											{
												hand.addDisease(Disease.diseaseTypes[disease.getdiseaseID()]);
												System.out.println(entityliving.getEntityName() + " has caught " + disease.getName() + " at " + 
													entityliving.posX + " "+ entityliving.posY + " " + entityliving.posZ + " by proxy of " + living.getEntityName());
											}
										}
									}
								}
							}
					}
				}
        
			}
		}
	}

}
