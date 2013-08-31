package piefarmer.immunology.disease;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

import piefarmer.immunology.common.Immunology;
import piefarmer.immunology.entity.EntityDiseaseHandler;
public class DiseaseEffect {
	
	private int DiseaseEffectID;
	private int stageActivate;
	private int stageEnd;
	private int duration = 3000;
	private String name;
	public static final DiseaseEffect[] diseaseEffects = new DiseaseEffect[8];
	public static DiseaseEffect sneeze = new EffectSneeze(0, 0, 0, "Sneeze");
	public static DiseaseEffect sniff = new EffectSniff(1, 0, 0, "Sniff");
	public static DiseaseEffect chickenSpots = new EffectSpots(2, 1, 1, "Spots");
	public static DiseaseEffect cough = new EffectCough(3, 0, 0, "Cough");
	public static DiseaseEffect fever = new EffectFever(4, 0, 0, "Fever");
	public static DiseaseEffect headache = new EffectHeadache(5, 0, 0, "Headache");
	public static DiseaseEffect chills = new EffectChills(6, 0, 0, "Chills");
	public static DiseaseEffect spread = new EffectSpread(7, 0, 0, "Spread");
	
	protected Random rand = new Random();
	private boolean load = true;
	
	public DiseaseEffect(int id, int stgAct, int stgEnd, String par1name)
	{
		this.DiseaseEffectID = id;
		this.stageActivate = stgAct;
		this.stageEnd = stgEnd;
		this.name = par1name;
		diseaseEffects[id] = this;
	}
	public DiseaseEffect(int par1)
    {
    	this(par1, 0, 0, "Unknown");
    }
	public DiseaseEffect(DiseaseEffect effect)
	{
		this.DiseaseEffectID = effect.getDiseaseEffectID();
		this.stageActivate = effect.getStageStart();
		this.stageEnd = effect.getStageEnd();
	}
	public DiseaseEffect(DiseaseEffect effect, int par1Duration)
	{
		this.DiseaseEffectID = effect.getDiseaseEffectID();
		this.duration = par1Duration;
	}
	public DiseaseEffect(DiseaseEffect diseaseEffect, int var2, boolean var3) {
		this.DiseaseEffectID = diseaseEffect.getDiseaseEffectID();
		this.duration = var2;
		this.load = var3;
	}
	public void performEffect(Disease disease, EntityLiving living){
		
	}
	public int getDiseaseEffectID()
	{
		return this.DiseaseEffectID;
	}
	public int getStageStart()
	{
		return this.stageActivate;
	}
	public int getStageEnd()
	{
		return this.stageEnd;
	}
	public boolean performSideEffect(EntityLiving entityliving) {
		Side side = FMLCommonHandler.instance().getEffectiveSide();
    	if(side.isServer())
        {
    		if(load)
    		{
	    		if(Immunology.loadedEntityList.containsKey(entityliving.hashCode()))
	    		{
		    		EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(entityliving.hashCode());
		    		if(hand != null)
		    		{
		    			hand.addSideEffectsOnLoad();
		    		}
		        	load = false;
	    		}
    		}
    		if (this.duration > 0)
    		{
	            this.deincrementDuration();
	        }
        }
    	else if(side.isClient())
    	{
    		if(load)
    		{
    			load = false;
    		}
    		if(entityliving instanceof EntityClientPlayerMP || entityliving instanceof EntityCreature)
    		{
    	        if (this.duration > 0)
    	        {
    	            this.deincrementDuration();
    	        }
    		}
    		
    	}
        return this.duration > 0;
	}
	private void deincrementDuration() {
		this.duration--;
	}
	public int getDuration() {
		return this.duration;
	}
	public void combine(DiseaseEffect effect) {
		if (this.DiseaseEffectID != effect.DiseaseEffectID)
        {
            System.err.println("This method should only be called for matching effects!");
        }
		if (this.duration < effect.duration)
        {
            this.duration = effect.duration;
        }
		
	}
	public void setDuration(int par1Duration) {
		this.duration = par1Duration;
		
	}
	public NBTTagCompound writeCustomSideEffectToNBT(NBTTagCompound nbtTagCompound) {
		nbtTagCompound.setByte("Id", (byte)this.getDiseaseEffectID());
        nbtTagCompound.setInteger("Duration", this.getDuration());
        nbtTagCompound.setBoolean("Load", this.getLoad());
        return nbtTagCompound;
	}
    public static DiseaseEffect readCustomSideEffectFromNBT(NBTTagCompound par0NBTTagCompound)
    {
        byte var1 = par0NBTTagCompound.getByte("Id");
        int var2 = par0NBTTagCompound.getInteger("Duration");
        boolean var3 = par0NBTTagCompound.getBoolean("Load");
        DiseaseEffect effect = new DiseaseEffect(DiseaseEffect.diseaseEffects[var1], var2, var3);
        return effect;
    }
    public boolean getLoad()
    {
    	return this.load;
    }
	public void setLoad(boolean load2) {
		this.load = load2;
		
	}
	public String getName() {
		return this.name;
	}
    
	
}
