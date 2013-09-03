package piefarmer.immunology.lib;

import piefarmer.immunology.block.Blocks;
import piefarmer.immunology.item.Items;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes {
	
	public static void init()
	{
		CraftingManager.getInstance().addRecipe(new ItemStack(Blocks.blockMedResearchTable, 1), new Object[] {
			"GGG",
			"MMM",
			"MBM", 
			'G',
			Item.glassBottle, 
			'M',
			Block.blockNetherQuartz, 
			'B',
			Item.book});
		CraftingManager.getInstance().addRecipe(new ItemStack(Blocks.blockDiagTable, 1), new Object[] {
			"GBG",
			"MMM",
			"M M", 
			'G',
			Item.glassBottle, 
			'M',
			Block.blockNetherQuartz, 
			'B',
			Item.book});
		CraftingManager.getInstance().addRecipe(new ItemStack(Items.hangGlider, 1), new Object[]{
			" S ",
			"SLS",
			"SLS",
			'S', Item.stick,
			'L', Item.leather});
		CraftingManager.getInstance().addRecipe(new ItemStack(Items.medicalBook, 1), new Object[]{
			"LP ",
			"PG ",
			"   ",
			'P', Item.paper,
			'L', Item.leather,
			'G', Item.glassBottle});
	}

}
