package piefarmer.immunology.entity;

import piefarmer.immunology.common.Immunology;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBadger extends EntityAnimal{

	double moveSpeed = 0.3F;
	public EntityBadger(World par1World) {
		super(par1World);
		this.setSize(0.3F, 0.5F);
        this.moveSpeed = 0.3F;
		this.getNavigator().setAvoidsWater(false);
		this.getNavigator().setCanSwim(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIWander(this, this.moveSpeed));
		this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(3, new EntityAIAttackOnCollide(this, this.moveSpeed, true));
		this.tasks.addTask(4, new EntityAIMate(this, this.moveSpeed));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.tasks.addTask(7, new EntityAIFollowParent(this, 0.28F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
	}
	public float getEyeHeight()
    {
        return this.height * 0.8F;
    }
	public boolean isBreedingItem(ItemStack par1ItemStack)
    {
        return par1ItemStack.itemID == Item.melon.itemID;
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
       return "mob.wolf.growl";
    }
	private float getHealth(EntityLivingBase entity)
	{
		return entity.func_110143_aJ();
	}

	private float getMaxHealth(EntityLivingBase entity)
	{
		return entity.func_110138_aP();
	}

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
    protected String getHurtSound()
    {
        return Immunology.modid + ":badgerhurt";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
    protected String getDeathSound()
    {
        return Immunology.modid + ":badgerdeath";
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
            //this.isChild() = compound.getBoolean("IsBaby");
        }
    }
    public EntityBadger spawnBabyAnimal(EntityAgeable par1EntityAgeable)
    {
        return new EntityBadger(this.worldObj);
    }

    public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
    {
        return this.spawnBabyAnimal(par1EntityAgeable);
    }
    protected float getSoundVolume()
    {
        return 0.4F;
    }
    public void setAttackTarget(EntityLiving par1EntityLiving)
    {
        super.setAttackTarget(par1EntityLiving);
    }
    public boolean attackEntityAsMob(Entity par1Entity)
    {
        return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), 4);
    }
    protected void attackEntity(Entity par1Entity, float par2)
    {
    	super.attackEntity(par1Entity, par2);
    }
    

}
