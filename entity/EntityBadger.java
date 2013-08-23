package piefarmer.immunology.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBadger extends EntityMob{

	public EntityBadger(World par1World) {
		super(par1World);
		texture = "/mods/Immunology/textures/models/badger.png";
		this.moveSpeed = 0.3F;
		this.getNavigator().setAvoidsWater(false);
		this.getNavigator().setCanSwim(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(6, new EntityAIWander(this, this.moveSpeed));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityVillager.class, this.moveSpeed, true));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
	}

	@Override
	public int getMaxHealth() {
		return 10;
	}
	@Override
	protected boolean isAIEnabled()
    {
        return true;
    }
	/**
     * Returns the sound this mob makes while it's alive.
     */
	@Override
    protected String getLivingSound()
    {
		return null;
       // return "piefarmer.immunology.badgerliving";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    protected String getHurtSound()
    {
        return "piefarmer.immunology.badgerhurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
    protected String getDeathSound()
    {
        return "piefarmer.immunology.badgerdeath";
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    @Override
    protected void playStepSound(int par1, int par2, int par3, int par4)
    {
    	this.playSound("mob.sheep.step", 0.15F, 1.0F);
    }
    @Override
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    }
    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
    	super.writeEntityToNBT(compound);
    	if (this.isChild())
        {
            compound.setBoolean("IsBaby", true);
        }
    }
    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
    	super.readEntityFromNBT(compound);
    	if (compound.getBoolean("IsBaby"))
        {
            
        }
    }
    @Override
    protected void attackEntity(Entity par1Entity, float par2)
    {
        float f1 = this.getBrightness(1.0F);

        if (f1 > 0.5F && this.rand.nextInt(100) == 0)
        {
            this.entityToAttack = null;
        }
        else
        {
            if (par2 > 2.0F && par2 < 6.0F && this.rand.nextInt(10) == 0)
            {
                if (this.onGround)
                {
                    double d0 = par1Entity.posX - this.posX;
                    double d1 = par1Entity.posZ - this.posZ;
                    float f2 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
                    this.motionX = d0 / (double)f2 * 0.5D * 0.800000011920929D + this.motionX * 0.20000000298023224D;
                    this.motionZ = d1 / (double)f2 * 0.5D * 0.800000011920929D + this.motionZ * 0.20000000298023224D;
                    this.motionY = 0.4000000059604645D;
                }
            }
            else
            {
                super.attackEntity(par1Entity, par2);
            }
        }
    }
    

}
