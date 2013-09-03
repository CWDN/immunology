package piefarmer.immunology.disease;

import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EffectSneeze extends DiseaseEffect{

	World worldObj = null;
	public EffectSneeze(int id, int stgAct, int stgEnd, String name) {
		super(id, stgAct, stgEnd, name);
	}
	public void performEffect(Disease disease, EntityLivingBase living)
	{
		worldObj = living.worldObj;
		EntityPlayer player = null;
		EntityLiving entityliving = null;
		if(!worldObj.isRemote)
		{
			int rint = rand.nextInt(3000);
			if(living instanceof EntityPlayer)
			{
				player = (EntityPlayer)living;
			}
			else if(living instanceof EntityLiving)
			{
				entityliving = (EntityLiving)living;
			}
			if(rint < 3)
			{				
					if(player != null)
					{
						Float rfloat = rand.nextFloat();
						Float pitch = 0.9F + (rfloat / 8);
						worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "piefarmer.immunology.sneeze", 0.65F, pitch);
						this.sneezeMovement(living);
					}
					else if(entityliving != null)
					{
						this.sneezeSound(living);
					}
				}
		
		}
	}
	private void sneezeMovement(EntityLivingBase living)
	{
		//entityliving.rotationPitch = -200F;
	}
	private void sneezeSound(EntityLivingBase living)
	{
		Random rand = new Random();
		switch(living.entityId)
		{
			case 92://Cow
				//worldObj.playSound(entityliving.posX, entityliving.posY, entityliving.posZ, "piefarmer.immunology.sneeze", 10, 0.5F, false);
				break;
			case 90://Pig
				//worldObj.playSound(entityliving.posX, entityliving.posY, entityliving.posZ, "piefarmer.immunology.sneeze", 10, 0.6F, false);
				break;
			case 120://Villager
				Float rfloat = rand.nextFloat();
				Float pitch = 1.0F + (rfloat / 8);
				worldObj.playSoundEffect(living.posX, living.posY, living.posZ, "piefarmer.immunology.sneeze", 0.65F, pitch);
				break;
			case 65://Bat
				//worldObj.playSound(entityliving.posX, entityliving.posY, entityliving.posZ, "piefarmer.immunology.sneeze", 10, 1.2F, false);
				break;
			case 95://Wolf
				//worldObj.playSound(entityliving.posX, entityliving.posY, entityliving.posZ, "piefarmer.immunology.sneeze", 10, 0.9F, false);
				break;
		}
	}
}
