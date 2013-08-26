package piefarmer.immunology.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import piefarmer.immunology.disease.Disease;
import piefarmer.immunology.disease.DiseaseEffect;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class EntityDiseaseHandler {
	
	protected HashMap activeDiseasesMap = new HashMap();
	protected HashMap activeSideEffectsMap = new HashMap();
	public EntityLiving living;
	public World worldObj;
	public EntityDiseaseHandler(EntityLiving par1Living)
	{
		living = par1Living;
		if(par1Living != null)
		{
			worldObj = par1Living.worldObj;
			this.readNBTData(living.getEntityData());
		}
	}
	public void entityUpdate()
	{
		this.updateDiseases(living);
        this.updateSideEffects(living);
	}
	public void saveNBTData(NBTTagCompound par1NBTTagCompound)
	{
		NBTTagList nbttaglist1;
		if(!this.activeDiseasesMap.isEmpty())
        {
        	nbttaglist1 = new NBTTagList();
        	Iterator var8 = this.activeDiseasesMap.values().iterator();
        	while (var8.hasNext())
        	{
        		Disease var5 = (Disease)var8.next();
        		nbttaglist1.appendTag(var5.writeCustomDiseaseToNBT(new NBTTagCompound()));
        	}
        	par1NBTTagCompound.setTag("ActiveDiseases", nbttaglist1);
        }
        
        if(!this.activeSideEffectsMap.isEmpty())
        {
        	nbttaglist1 = new NBTTagList();
        	Iterator var8 = this.activeSideEffectsMap.values().iterator();
        	while (var8.hasNext())
        	{
        		DiseaseEffect var5 = (DiseaseEffect)var8.next();
        		nbttaglist1.appendTag(var5.writeCustomSideEffectToNBT(new NBTTagCompound()));
        	}
        	par1NBTTagCompound.setTag("ActiveSideEffects", nbttaglist1);
        }
	}
	public void readNBTData(NBTTagCompound par1NBTTagCompound)
	{
		NBTTagList nbttaglist;
		int i = 0;
		if(par1NBTTagCompound.hasKey("ActiveDiseases"))
        {
        	nbttaglist = par1NBTTagCompound.getTagList("ActiveDiseases");
        	for(i = 0; i < nbttaglist.tagCount(); ++i)
        	{
        		NBTTagCompound var4 = (NBTTagCompound)nbttaglist.tagAt(i);
        		Disease var5 = Disease.readCustomDiseaseFromNBT(var4);
        		this.activeDiseasesMap.put(Integer.valueOf(var5.getdiseaseID()), var5);
        	}
        }
        if(par1NBTTagCompound.hasKey("ActiveSideEffects"))
        {
        	nbttaglist = par1NBTTagCompound.getTagList("ActiveSideEffects");
        	for(i = 0; i < nbttaglist.tagCount(); ++i)
        	{
        		NBTTagCompound var4 = (NBTTagCompound)nbttaglist.tagAt(i);
        		DiseaseEffect var5 = DiseaseEffect.readCustomSideEffectFromNBT(var4);
        		this.activeSideEffectsMap.put(Integer.valueOf(var5.getDiseaseEffectID()), var5);
        	}
        }
	}
	//
    //DISEASE START
    //
    
    /**
     * Add disease to entity living
     */
    public void addDisease(Disease par1Disease)
    {
    	Disease disease = par1Disease.getInstancebyName(par1Disease);
        if (this.activeDiseasesMap.containsKey(Integer.valueOf(disease.getdiseaseID())))
        {
            ((Disease)this.activeDiseasesMap.get(Integer.valueOf(disease.getdiseaseID()))).combine(disease);
                this.onChangedDisease((Disease)this.activeDiseasesMap.get(Integer.valueOf(disease.getdiseaseID())));
        }
        else
        {
        	
            this.activeDiseasesMap.put(Integer.valueOf(disease.getdiseaseID()), disease);
            this.onNewDisease(disease);
        }
    }
    @SideOnly(Side.CLIENT)
    public void addDiseaseClient(Disease par1Disease)
    {
    	Disease disease = par1Disease.getInstancebyName(par1Disease);
        if (this.activeDiseasesMap.containsKey(Integer.valueOf(disease.getdiseaseID())))
        {
            ((Disease)this.activeDiseasesMap.get(Integer.valueOf(disease.getdiseaseID()))).combine(disease);
        }
        else
        {
        	this.activeDiseasesMap.put(Integer.valueOf(disease.getdiseaseID()), disease);
        }
    }
    public void addDiseaseServer(Disease par1Disease)
    {
    	Disease disease = par1Disease.getInstancebyName(par1Disease);
        if (this.activeDiseasesMap.containsKey(Integer.valueOf(disease.getdiseaseID())))
        {
            ((Disease)this.activeDiseasesMap.get(Integer.valueOf(disease.getdiseaseID()))).combine(disease);
        }
        else
        {
        	this.activeDiseasesMap.put(Integer.valueOf(disease.getdiseaseID()), disease);
        }
    }
    /**
     * When disease is changed/combined update is set to true
     */
    public void onChangedDisease(Disease par1Disease)
    {
    	Side side = FMLCommonHandler.instance().getEffectiveSide();
    	//System.out.println("Disease Changed " + side.name() + " " + this.living.getEntityName() + " " + this.living.entityId);
    	if(living instanceof EntityPlayer)
    	{
    		Player player = (Player)this.living;
    		EntityPacketHandler.onChangedDisease(par1Disease, player);
    	}        
    }
    /**
     * When new disease is added set a variable to update
     * @param par1Disease
     */
    public void onNewDisease(Disease par1Disease)
    {
    	Side side = FMLCommonHandler.instance().getEffectiveSide();
    	//System.out.println("New Disease " + side.name() + " " + this.living.getEntityName() + " " + this.living.entityId);
    	if(living instanceof EntityPlayer)
    	{
    		Player player = (Player)this.living;
    		EntityPacketHandler.onNewDisease(par1Disease, player);
    	}
    }
    
    public void addDiseasesOnLoad()
    {
    	Iterator var1 = this.activeDiseasesMap.keySet().iterator();
    	while(var1.hasNext())
    	{
    		Integer var2 = (Integer)var1.next();
            Disease var3 = (Disease)this.activeDiseasesMap.get(var2);
            this.onNewDisease(var3);
    	}
    }
    /**
     * returns whether param is active
     * @param par1Disease
     * @return
     */
    public Disease getActiveDiseases(Disease par1Disease)
    {
        return (Disease)this.activeDiseasesMap.get(Integer.valueOf(par1Disease.getdiseaseID()));
    }
    /**
     * set disease update
     * @param par1Disease
     */
    protected void onFinishedDisease(Disease par1Disease)
    {
    	//System.out.println("Disease Finished");
    	if(living instanceof Player)
    	{
    		Player player = (Player)this.living;
    		EntityPacketHandler.onFinishedDisease(par1Disease, player);
    	}
    }
    /**
     * returns all diseases active from hash map
     * @return
     */
    public Collection getActiveDiseases()
    {
        return this.activeDiseasesMap.values();
    }
    /**
     * clears all active diseases in hash map
     */
    public void clearActiveDiseases()
    {
        Iterator var1 = this.activeDiseasesMap.keySet().iterator();

        while (var1.hasNext())
        {
            Integer var2 = (Integer)var1.next();
            Disease var3 = (Disease) this.activeDiseasesMap.get(var2);

            if (!this.worldObj.isRemote)
            {
                var1.remove();
                this.onFinishedDisease(var3);
            }
        }
    }
    protected void updateDiseases(EntityLiving living)
    {
        Iterator var1 = this.activeDiseasesMap.keySet().iterator();
        while (var1.hasNext())
        {
            Integer var2 = (Integer)var1.next();
            Disease var3 = (Disease)this.activeDiseasesMap.get(var2);

            if (!var3.onUpdate(living))
            {
                if (!this.worldObj.isRemote)
                {
                    var1.remove();
                    this.onFinishedDisease(var3);
                }
            }
            else if (var3.getDuration() % 600 == 0)
            {
                this.onChangedDisease(var3);
            }
        }
    }
    /**
     * Remove the speified disease from this entity.
     */
    public void removeDiseaseClient(int par1)
    {
        this.activeDiseasesMap.remove(Integer.valueOf(par1));
    }

    /**
     * Remove the specified disease from this entity.
     */
    public void removeDisease(int par1)
    {
        Disease var2 = (Disease)this.activeDiseasesMap.remove(Integer.valueOf(par1));

        if (var2 != null)
        {
            this.onFinishedDisease(var2);
        }
    }
    public boolean isDiseaseActive(Disease par1Disease)
    {
        return this.activeDiseasesMap.containsKey(Integer.valueOf(par1Disease.getdiseaseID()));
    }
    public int[] getDiseaseEffects()
    {
    	List<Integer> listeffects = new ArrayList<Integer>();
    	Iterator var1 = this.activeDiseasesMap.keySet().iterator();
    	int biggestID = 0;
    	while(var1.hasNext())
    	{
    		Integer diseaseid = (Integer)var1.next();
    		Disease disease = (Disease) this.activeDiseasesMap.get(diseaseid);
    		for(int i = 0; i < disease.DiseaseEffects.size(); i++)
    		{
    			if(biggestID < disease.DiseaseEffects.get(i))
    			{
    				biggestID = disease.DiseaseEffects.get(i);
    			}
    			listeffects.add(disease.DiseaseEffects.get(i));
    		}
    	}
    	int[] effects = new int[biggestID + 1];
    	for(int j = 0; j < listeffects.size(); j++)
    	{
    		effects[listeffects.get(j)] = listeffects.get(j) + 1;
    	}
    	if(listeffects.size() < 1)
    	{
    		effects[0] = -1;
    	}
		return effects;
    	
    }
    
    //
    //Side Effects
    //
    private void updateSideEffects(EntityLiving living) {
		
    	Iterator var1 = this.activeSideEffectsMap.keySet().iterator();
        while (var1.hasNext())
        {

	            Integer var2 = (Integer)var1.next();
	            DiseaseEffect var3 = (DiseaseEffect)this.activeSideEffectsMap.get(var2);
	
	            if (!var3.performSideEffect(living))
	            {
	                if (!this.worldObj.isRemote)
	                {
	                	var1.remove();
	                    this.onFinishedSideEffect(var3);
	                }
	                else
	                {
	                	if(var3.getDuration() == 0)
	                	{
	                		var1.remove();
	                	}
	                }
	            }
	            else if (var3.getDuration() % 600 == 0)
	            {
	                this.onChangedSideEffect(var3);
	            }
        }
	}
    private void onFinishedSideEffect(DiseaseEffect effect)
    {
    	System.out.println(effect.getClass().getName() + " Removed");
    	if(living instanceof Player)
    	{
    		Player player = (Player)this.living;
    		EntityPacketHandler.onFinishedSideEffect(effect, player);
    	}
    }
    private void onChangedSideEffect(DiseaseEffect effect)
    {
    	System.out.println(effect.getClass().getName() + " Changed");
    	if(living instanceof EntityPlayer)
    	{
    		Player player = (Player)this.living;
    		EntityPacketHandler.onChangedSideEffect(effect, player);
    	}
    }
    private void onNewSideEffect(DiseaseEffect effect)
    {
    	System.out.println("New " + effect.getClass().getName());
    	if(living instanceof EntityPlayer)
    	{
    		Player player = (Player)this.living;
    		EntityPacketHandler.onNewSideEffect(effect, player);
    	}
    }
    public void removeSideEffectClient(int par1)
    {
        this.activeSideEffectsMap.remove(Integer.valueOf(par1));
    }
    public void removeSideEffect(int par1)
    {
        DiseaseEffect var2 = (DiseaseEffect)this.activeSideEffectsMap.remove(Integer.valueOf(par1));

        if (var2 != null)
        {
            this.onFinishedSideEffect(var2);
        }
    }
    public void addSideEffect(DiseaseEffect par1Effect)
    {
    	DiseaseEffect effect = new DiseaseEffect(DiseaseEffect.diseaseEffects[par1Effect.getDiseaseEffectID()]);
        if (this.activeSideEffectsMap.containsKey(Integer.valueOf(par1Effect.getDiseaseEffectID())))
        {
            ((DiseaseEffect)this.activeSideEffectsMap.get(Integer.valueOf(par1Effect.getDiseaseEffectID()))).combine(par1Effect);
                this.onChangedSideEffect((DiseaseEffect)this.activeSideEffectsMap.get(Integer.valueOf(par1Effect.getDiseaseEffectID())));
        }
        else
        {
        	
            this.activeSideEffectsMap.put(Integer.valueOf(par1Effect.getDiseaseEffectID()), effect);
            this.onNewSideEffect(effect);
        }
    }
    public void addSideEffectClient(DiseaseEffect par1Effect)
    {
        if (this.activeSideEffectsMap.containsKey(Integer.valueOf(par1Effect.getDiseaseEffectID())))
        {
            ((DiseaseEffect)this.activeSideEffectsMap.get(Integer.valueOf(par1Effect.getDiseaseEffectID()))).combine(par1Effect);
        }
        else
        {
        	
            this.activeSideEffectsMap.put(Integer.valueOf(par1Effect.getDiseaseEffectID()), par1Effect);
        }
    }
    public Collection getActiveSideEffects()
    {
        return this.activeSideEffectsMap.values();
    }
	public void addSideEffectsOnLoad() {
		Iterator var1 = this.activeSideEffectsMap.keySet().iterator();
    	while(var1.hasNext())
    	{
    		Integer var2 = (Integer)var1.next();
            DiseaseEffect var3 = (DiseaseEffect)this.activeSideEffectsMap.get(var2);
            var3.setLoad(false);
            this.onNewSideEffect(var3);
    	}
		
	}

}
