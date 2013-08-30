package piefarmer.immunology.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import piefarmer.immunology.common.Immunology;
import piefarmer.immunology.disease.DiseaseEffect;
import piefarmer.immunology.item.ItemCure;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MedicalResearchTableRecipes {
	
	 private static final MedicalResearchTableRecipes brewingBase = new MedicalResearchTableRecipes();

	    /** The list of brewing results. */
	    private Map brewingList = new HashMap();
	    private Map experienceList = new HashMap();
	    private HashMap<List<Integer>, List<ItemStack>> metaBrewingList = new HashMap<List<Integer>, List<ItemStack>>();
	    private HashMap<List<Integer>, Float> metaExperience = new HashMap<List<Integer>, Float>();
	    private HashMap<List<Integer>, Integer> sideEffectsList = new HashMap<List<Integer>, Integer>();

	    /**
	     * Used to call methods addBrewing and getBrewingResult.
	     */
	    public static final MedicalResearchTableRecipes brewing()
	    {
	        return brewingBase;
	    }

	    private MedicalResearchTableRecipes()
	    {
	    	
	        this.addBrewing(Item.snowball.itemID, Arrays.asList(new ItemStack(Immunology.cure, 1, 1), new ItemStack(Item.slimeBall, 1)), 1.0F);
	        this.addBrewing(Item.egg.itemID, Arrays.asList(ItemCure.setSideEffects(new ItemStack(Immunology.cure, 1, 2), Arrays.asList(2,5)), new ItemStack(Item.feather, 1)), 1.5F);
	        this.addBrewing(Item.slimeBall.itemID, Arrays.asList(ItemCure.setSideEffects(new ItemStack(Immunology.cure, 1, 3), Arrays.asList(3, 5)), new ItemStack(Item.netherStalkSeeds, 1)), 3.0F);
	        this.sideEffectsList.put(Arrays.asList(Item.paper.itemID, 0), 0);
	        this.sideEffectsList.put(Arrays.asList(Item.slimeBall.itemID, 0), 1);
	        this.sideEffectsList.put(Arrays.asList(Item.netherStalkSeeds.itemID, 0), 2);
	        this.sideEffectsList.put(Arrays.asList(Item.blazePowder.itemID, 0), 3);
	        this.sideEffectsList.put(Arrays.asList(Item.magmaCream.itemID, 0), 4);
	        this.sideEffectsList.put(Arrays.asList(Item.bone.itemID, 0), 5);
	        this.sideEffectsList.put(Arrays.asList(Item.leather.itemID, 0), 6);
	    }
	    
	    
	    /**
	     * Adds a brewing recipe.
	     */
	    public void addBrewing(int par1, List<ItemStack> par2ItemStack, float par3)
	    {
	        this.brewingList.put(Integer.valueOf(par1), par2ItemStack);
	        
	        this.experienceList.put(Integer.valueOf(par2ItemStack.get(0).itemID), Float.valueOf(par3));
	    }
	    public Map getBrewingList()
	    {
	        return this.brewingList;
	    }

	    /**
	     * A metadata sensitive version of adding a brewing recipe.
	     */
	    public void addBrewing(int itemID, int metadata, List<ItemStack> itemstack, float experience)
	    {
	        metaBrewingList.put(Arrays.asList(itemID, metadata), itemstack);
	        metaExperience.put(Arrays.asList(itemID, metadata), experience);
	    }

	    /**
	     * Used to get the resulting ItemStacks form a source ItemStack
	     * @param item The Source ItemStack, item2 The secondary ItemStack to mix
	     * @return The result ItemStack
	     */
	    public ItemStack getBrewingResult(ItemStack item, ItemStack item2) 
	    {
	        if (item == null)
	        {
	            return null;
	        }
	        if(item.itemID != Immunology.cure.itemID)
	        {
		        List<ItemStack> ret = (List<ItemStack>)metaBrewingList.get(Arrays.asList(item.itemID, item.getItemDamage()));
		        if (ret != null) 
		        {
		        	if(item2.itemID == ret.get(1).itemID)
		        	{
		        		return ret.get(0);
		        	}
		        }
		        else
		        {
			        ret = (List<ItemStack>)brewingList.get(Integer.valueOf(item.itemID));
			        ItemStack i = ret.get(1);
			        if(item2 != null && item2.itemID == i.itemID)
			        {
			        	return ret.get(0);
			        }
			        else
			        {
			        	return null;
			        }
		        }
	        }
	        else
	        {
	        	ItemCure cure = (ItemCure) item.getItem();
	        	List effects = cure.getEffects(item);
	        	if(item2 != null)
	        	{
		        	Integer is = sideEffectsList.get(Arrays.asList(item2.itemID, item2.getItemDamage()));
		        	for(int i = 0; i < effects.size(); i++)
		        	{
		        		DiseaseEffect eff = (DiseaseEffect) effects.get(i);
		        		if(is == eff.getDiseaseEffectID())
		        		{
		        			effects.remove(i);
		        			List<Integer> list = new ArrayList<Integer>();
		        			Iterator iter = effects.iterator();
		        			while(iter.hasNext())
		        			{
		        				DiseaseEffect effect = (DiseaseEffect)iter.next();
		        				list.add(effect.getDiseaseEffectID());
		        			}
		        			return ItemCure.setSideEffects(new ItemStack(Immunology.cure.itemID, 1, item.getItemDamage()), list);
		        		}
		        	}
		        	if(is != null)
		        	{
		        		effects.add(new DiseaseEffect(DiseaseEffect.diseaseEffects[is]));
		        		List<Integer> list = new ArrayList<Integer>();
	        			Iterator iter = effects.iterator();
	        			while(iter.hasNext())
	        			{
	        				DiseaseEffect effect = (DiseaseEffect)iter.next();
	        				list.add(effect.getDiseaseEffectID());
	        			}
		        		return ItemCure.setSideEffects(new ItemStack(Immunology.cure.itemID, 1, item.getItemDamage()), list);
		        	}
	        	}
	        	
	        }
	        return null;
	    }

	    /**
	     * Grabs the amount of base experience for this item to give when pulled from the furnace slot.
	     */
	    public float getExperience(ItemStack item)
	    {
	        if (item == null || item.getItem() == null)
	        {
	            return 0;
	        }
	        float ret = item.getItem().getSmeltingExperience(item);
	        if (ret < 0 && metaExperience.containsKey(Arrays.asList(item.itemID, item.getItemDamage())))
	        {
	            ret = metaExperience.get(Arrays.asList(item.itemID, item.getItemDamage()));
	        }
	        if (ret < 0 && experienceList.containsKey(item.itemID))
	        {
	            ret = ((Float)experienceList.get(item.itemID)).floatValue();
	        }
	        return (ret < 0 ? 0 : ret);
	    }

	    public Map<List<Integer>, List<ItemStack>> getMetaBrewingList()
	    {
	        return metaBrewingList;
	    }
	    public boolean isCureIngredient(ItemStack item)
	    {
	    	if(metaBrewingList.get(Arrays.asList(item.itemID, item.getItemDamage())) != null)
    		{
    			return true;
    		}
	    	if(brewingList.get(Integer.valueOf(item.itemID)) != null)
    		{
    			return true;
    		}
	    	if(item.itemID == Immunology.cure.itemID)
	    	{
	    		return true;
	    	}
	    	return false;
	    }
	    public List<ItemStack> getIngredients(int id, boolean isCure)
	    {
	    	if(!isCure)
	    	{
		    	Iterator iter = this.brewingList.keySet().iterator();
		    	while(iter.hasNext())
		    	{
		    		Integer i = (Integer) iter.next();
		    		List<ItemStack> l = (List<ItemStack>) this.brewingList.get(i);
		    		if(l.get(0).getItemDamage() == id)
		    		{
		    			ItemStack is = new ItemStack(Item.itemsList[i]);
		    			return Arrays.asList(is, l.get(1));
		    		}
		    	}
		    	iter = this.metaBrewingList.keySet().iterator();
		    	while(iter.hasNext())
		    	{
		    		List<Integer> i = (List<Integer>) iter.next();
		    		List<ItemStack> l = (List<ItemStack>) this.brewingList.get(i);
		    		if(l.get(0).getItemDamage() == id)
		    		{
		    			ItemStack is = new ItemStack(Item.itemsList[i.get(0)], i.get(1));
		    			return Arrays.asList(is, l.get(1));
		    		}
		    	}
	    	}
	    	else
	    	{
	    		Iterator iter = this.sideEffectsList.keySet().iterator();
	    		while(iter.hasNext())
	    		{
	    			List<Integer> l = (List<Integer>)iter.next();
	    			ItemStack is = new ItemStack(Item.itemsList[l.get(0)], 1, l.get(1));
	    			Integer i = this.sideEffectsList.get(l);
	    			if(i == id - 1)
	    			{
	    				return Arrays.asList(new ItemStack(Immunology.cure, 1, id), is);
	    			}
	    		}
	    	}
	    	return null;
	    }

}
