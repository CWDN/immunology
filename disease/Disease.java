package piefarmer.immunology.disease;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import piefarmer.immunology.common.Immunology;
import piefarmer.immunology.entity.EntityDiseaseHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.potion.Potion;
import net.minecraft.src.ModLoader;
import net.minecraft.util.Icon;
import net.minecraft.util.StringUtils;

public class Disease {
	
	/** The array of disease types. */
    public static final Disease[] diseaseTypes = new Disease[32];
    public static final Disease commonCold = new DiseaseCommonCold(0, 168000, Arrays.asList(0,1,4,7), "Common Cold", true);
    public static final Disease chickenPox = new DiseaseChickenPox(1, 504000, Arrays.asList(2, 7), "Chicken Pox", true);
    public static final Disease influenza = new DiseaseInfluenza(2, 72000, 0, Arrays.asList(1,3,4,5,6,7), "Influenza", true);
    public static final Disease measles = new DiseaseMeasles(3, 72000, 0, Arrays.asList(1,7), "Measles", true);
    public List<Integer> DiseaseEffects = new ArrayList<Integer>();
	/** ID value of this disease*/
    private int diseaseID;
    /** The duration of the disease */
    private int duration;

    /** The stage of the disease */
    private int stage;
    
    /**The name of the disease */
    private String name = "";

	private int statusIconIndex; 
	
	private static int movingMatch = 0;
	/**
	 * Used to check when the entity has just loaded into the world. So that the disease can be loaded to both client and server sides. 
	 * Please contact Piefarmer if there is a better way to do this. 
	*/
	private boolean load = true;
    

    public Disease(int par1, int par2)
    {
    	this(par1, par2, 0, new ArrayList<Integer>(), "Unknown");
    }

    public Disease(int par1, int par2, int par3, List<Integer> list, String diseasename)
    {
        this.diseaseID = par1;
        this.duration = par2;
        this.stage = par3;
        this.statusIconIndex = par1;
        this.DiseaseEffects = list;
        this.name = diseasename;
    }
    public Disease(int par1, int par2, int par3, List<Integer> list, String diseasename, boolean write)
    {
    	this.diseaseID = par1;
        this.duration = par2;
        this.stage = par3;
        this.statusIconIndex = par1;
        this.DiseaseEffects = list;
        this.name = diseasename;
        diseaseTypes[par1] = this;
    }

    public Disease(Disease par1Disease)
    {
    	this.diseaseID = par1Disease.diseaseID;
    	this.duration = par1Disease.duration;
    	this.stage = par1Disease.stage;
    	this.statusIconIndex = par1Disease.diseaseID;
    	this.DiseaseEffects = par1Disease.DiseaseEffects;
    	this.name = par1Disease.name;
    }

    /**
     * merges the input Disease into this one if this.stage <= tomerge.stage. The duration in the supplied
     * disease effect is assumed to be greater.
     */
    public void combine(Disease par1Disease)
    {
        if (this.diseaseID != par1Disease.diseaseID)
        {
            System.err.println("This method should only be called for matching effects!");
        }

        if (par1Disease.stage > this.stage)
        {
            this.stage = par1Disease.stage;
            this.duration = par1Disease.duration;
        }
        else if (par1Disease.stage == this.stage && this.duration < par1Disease.duration)
        {
            this.duration = par1Disease.duration;
        }
    }

    /**
     * Set the name of the disease
     */
    public Disease setDiseaseName(String par1Str)
    {
        this.name = par1Str;
        return this;
    }
    public static Disease getDiseaseById(int id)
    {
		return diseaseTypes[id];
    }
    public int getdiseaseID()
    {
        return this.diseaseID;
    }

    public int getDuration()
    {
        return this.duration;
    }

    public int getStage()
    {
        return this.stage;
    }
    public String getName()
    {
        return this.name;
    }
    public void setStage(int par1)
    {
    	this.stage = par1;
    }
    public void setDuration(int par1)
    {
    	this.duration = par1;
    }
    @SideOnly(Side.CLIENT)

    /**
     * Returns true if the disease has a associated status icon to display in then inventory when active.
     */
    public boolean hasStatusIcon()
    {
        return this.statusIconIndex >= 0;
    }
    @SideOnly(Side.CLIENT)
    public void setStatusIcon(int index)
    {
    	this.statusIconIndex = index;
    }
    @SideOnly(Side.CLIENT)

    /**
     * Returns the index for the icon to display when the disease is active.
     */
    public int getStatusIconIndex()
    {
        return this.statusIconIndex;
    }
    @SideOnly(Side.CLIENT)
    public static String getDurationString(Disease par0Disease)
    {
        int var1 = par0Disease.getDuration();
        return StringUtils.ticksToElapsedTime(var1);
    }

    public Disease getDiseasebyId(int id)
    {
		return diseaseTypes[id];
    	
    }
    /**
     * Called for when the disease has been caught and the disease effects will need to be applied. Called every tick.
     * @param par1EntityLiving
     */
    public void performEffect(EntityLiving par1EntityLiving) {
    	

    	for(int i = 0; i < Disease.diseaseTypes[this.diseaseID].DiseaseEffects.size(); i++)
    	{
    		DiseaseEffect effect = DiseaseEffect.diseaseEffects[Disease.diseaseTypes[this.diseaseID].DiseaseEffects.get(i)];
    		if(this.stage >= effect.getStageStart() && this.stage <= effect.getStageEnd())
    		{
    			effect.performEffect(this, par1EntityLiving);
    		}
    	}
    	
	}
    public boolean onUpdate(EntityLiving par1EntityLiving)
    {
    	Side side = FMLCommonHandler.instance().getEffectiveSide();
    	if(load && side.isServer())
        {
    		if(Immunology.loadedEntityList.containsKey(par1EntityLiving))
    		{
	    		EntityDiseaseHandler hand = (EntityDiseaseHandler) Immunology.loadedEntityList.get(par1EntityLiving);
	    		if(hand != null)
	    		{
	    			hand.onNewDisease(this);
	    		}
	        	load = false;
    		}
        }
    	else if(side.isClient())
    	{
    		if(load)
    		{
    			load = false;
    		}
    		
    	}
        if (this.duration > 0)
        {
            this.performEffect(par1EntityLiving);
            this.deincrementDuration();
        }
        
        return this.duration > 0;
    }

    private int deincrementDuration()
    {
        return --this.duration;
    }
    public boolean getLoad()
    {
    	return this.load;
    }
    public void setLoad(boolean par1)
    {
    	this.load = par1;
    }
    public int hashCode()
    {
        return this.diseaseID;
    }

   /*public String toString()
    {
        String var1 = "";

        if (this.getstage() > 0)
        {
            var1 = this.getName() + " x " + (this.getstage() + 1) + ", Duration: " + this.getDuration();
        }
        else
        {
            var1 = this.getName() + ", Duration: " + this.getDuration();
        }

        return disease.diseaseTypes[this.diseaseID].isUsable() ? "(" + var1 + ")" : var1;
    }*/

    /**
     * Write a custom disease to a disease item's NBT data.
     */
    public NBTTagCompound writeCustomDiseaseToNBT(NBTTagCompound par1NBTTagCompound)
    {
        par1NBTTagCompound.setByte("Id", (byte)this.getdiseaseID());
        par1NBTTagCompound.setByte("stage", (byte)this.getStage());
        par1NBTTagCompound.setInteger("Duration", this.getDuration());
        par1NBTTagCompound.setString("Name", this.getName());
        int[] effects = new int[this.DiseaseEffects.size()];
        if (!this.DiseaseEffects.isEmpty())
        {
            Iterator var7 = this.DiseaseEffects.iterator();
            int index = 0;
            while (var7.hasNext())
            {
            	Integer i = (Integer)var7.next();
            	effects[index] = i;
            	++index;
            }
        }
        par1NBTTagCompound.setIntArray("Effects", effects);
        return par1NBTTagCompound;
    }
    /**
     * Read a custom disease from a disease item's NBT data.
     */
    public static Disease readCustomDiseaseFromNBT(NBTTagCompound par0NBTTagCompound)
    {
        byte var1 = par0NBTTagCompound.getByte("Id");
        byte var2 = par0NBTTagCompound.getByte("stage");
        int var3 = par0NBTTagCompound.getInteger("Duration");
        String var4 = par0NBTTagCompound.getString("Name");
        int[] effects = par0NBTTagCompound.getIntArray("Effects");
        ArrayList<Integer> var5 = new ArrayList<Integer>();
        for(int index = 0; index < effects.length; index++)
        {
        	var5.add(effects[index]);
        }
        Disease disease = Disease.getInstancebyName(diseaseTypes[var1]);
        disease.setStage(var2);
        disease.setDuration(var3);
        disease.setLoad(true);
        return disease;
    }
    /**
     * Used so that when adding a disease to any method that is overriding the methods in Disease they are actually overridden.
     * @param par1Disease
     * @return
     */
    public static Disease getInstancebyName(Disease par1Disease)
    {
    	
    	Class<?> clazz = par1Disease.getClass();
    	try {
			return (Disease) clazz.getConstructor(Disease.class).newInstance(par1Disease);
			
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
    	} catch (SecurityException e) {
    		e.printStackTrace();
    	} catch (InstantiationException e) {
			e.printStackTrace();
    	} catch (IllegalAccessException e) {
			e.printStackTrace();
    	} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return new Disease(0,0);    	
    }
 
    public static void entityUpdateHook(EntityLiving entityliving){
    	for(int i = 0; i < diseaseTypes.length; ++i)
    	{
    		if(diseaseTypes[i] != null)
    		{
    			Class<?> clazz = diseaseTypes[i].getClass();
    			Class[] methodParameters = new Class[]{EntityLiving.class};
    			Object[] params = new Object[]{entityliving};
    			
    			Method entityUpdateMethod;
    			try {
    				entityUpdateMethod = clazz.getDeclaredMethod("entityUpdate", methodParameters);
    				entityUpdateMethod.setAccessible(true);
    				entityUpdateMethod.invoke(clazz, params);
    			} catch (NoSuchMethodException e) {
    				//e.printStackTrace();
    			} catch (SecurityException e) {
    				e.printStackTrace();
    			} catch (IllegalAccessException e) {
    				e.printStackTrace();
    			} catch (IllegalArgumentException e) {
    				e.printStackTrace();
    			} catch (InvocationTargetException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    }
	public Packet250CustomPayload getParticlePacket(int i, double x, double y, double z)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		try {
			dos.writeInt(i);
			dos.writeDouble(x);
			dos.writeDouble(y);
			dos.writeDouble(z);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new Packet250CustomPayload("ImmunParticle", bos.toByteArray());
	}
	public void setDiseaseEffects(List<Integer> effects)
	{
		this.DiseaseEffects = effects;
	}
	public static List<Disease> getDiseasesByEffects(int[] effects)
	{
		List<Integer> matches = new ArrayList<Integer>();
		List<Disease> diseases = new ArrayList<Disease>();
		for(int i = 0; i < Disease.diseaseTypes.length; i++)
		{
			int effectmatches = 0;
			Disease disease = Disease.diseaseTypes[i];
			if(disease != null)
			{
				for(int j = 0; j < disease.DiseaseEffects.size(); j++)
				{
					for(int k = 0; k < effects.length; k++)
					{
						if(disease.DiseaseEffects.get(j) == effects[k] - 1)
						{
							effectmatches++;
						}
					}
				
				}
			
				if(effectmatches > 0)
				{
					diseases.add(disease);
					matches.add(effectmatches);
				}
			}
		}
		diseases = sortMatches(matches, diseases);
		return diseases;
		
	}
	public static List<Disease> sortMatches(List<Integer> matches, List<Disease> disease)
	{
		int indextomove = 0;
		Disease currentdisease = null;
		int origIndex = 0;
		for(int index = 0; index < matches.size(); index++)
		{
			movingMatch = matches.get(index);
			currentdisease = disease.get(index);
			origIndex = index;
			indextomove = 0;
			 for(int i = (index + 1); i < matches.size(); i++)
			 {
				 
				 if(movingMatch < matches.get(i))
				 {
					 indextomove = i;
					 if(indextomove > 0)
					 {
						 index = -1;
					 }
				 }
				 else if(movingMatch > matches.get(i))
				 {
					 if(index == 0)
					 {
						 indextomove = i - 2;
					 }
					 else
					 {
						 indextomove = i - 1;
					 }
					 i = matches.size() + 1;
					 
				 }
			 }
			 if(indextomove > 0)
			 {
				 matches.add(indextomove + 1, movingMatch);
				 disease.add(indextomove + 1, currentdisease);
				 matches.remove(origIndex);
				 disease.remove(origIndex);
			 }
		}
		return disease;
		
	}
}


