package piefarmer.immunology.block;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import piefarmer.immunology.lib.Ids;
import piefarmer.immunology.lib.Names;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class Blocks {
	
	public static BlockCustomFlower plantBlueBell;
	public static Block blockMedResearchTable;
	public static Block blockDiagTable;
	public static Block blockRock;
	public static Block torchWoodLit;
    public static Block torchWoodNotLit;
	
	public static void init()
	{
		plantBlueBell = (BlockCustomFlower) new BlockCustomFlower(Ids.blockcustomflowerID_actual).setHardness(0.0F).setStepSound(Block.soundGrassFootstep)
				.setUnlocalizedName(Names.bluebellBlock_unlocalizedName);
		blockMedResearchTable = (new BlockMedicalResearchTable(Ids.blockmedrestblID_actual)).setHardness(0.1F).setStepSound(Block.soundMetalFootstep)
				.setUnlocalizedName(Names.medresBlock_unlocalizedName);
		blockDiagTable = (new BlockDiagnosticTable(Ids.blockdiatblID_actual)).setHardness(0.1F).setStepSound(Block.soundMetalFootstep)
				.setUnlocalizedName(Names.diagBlock_unlocalizedName);
		blockRock = (new BlockRock(Ids.blockrockID_actual, Material.rock)).setHardness(1.5F).setStepSound(Block.soundStoneFootstep)
				.setUnlocalizedName(Names.rockBlock_unlocalizedName);
		torchWoodNotLit = (new BlockCustomTorch(Ids.blocktorchID_actual, false)).setHardness(0.0F).setLightValue(0).setStepSound(Block.soundWoodFootstep)
				.setUnlocalizedName(Names.unlittorchBlock_unlocalizedName);
		torchWoodLit = (new BlockCustomTorch(50, true)).setHardness(0.0F).setLightValue(0.9375F).setStepSound(Block.soundWoodFootstep)
				.setUnlocalizedName("torch");
		
	}
	public static void addNames()
	{
		LanguageRegistry.addName(plantBlueBell, Names.bluebellBlock_name);
		LanguageRegistry.addName(blockMedResearchTable, Names.medreBlock_name);
		LanguageRegistry.addName(blockDiagTable, Names.diagBlock_name);
		LanguageRegistry.addName(blockRock, Names.rockBlock_name);
		LanguageRegistry.addName(torchWoodNotLit, Names.unlittorchBlock_name);
	}
	public static void registerBlocks()
	{
		GameRegistry.registerBlock(plantBlueBell, "immunologybluebell");
		GameRegistry.registerBlock(blockMedResearchTable, "medicalresearchtable");
		GameRegistry.registerBlock(blockDiagTable, "diagnostictable");
		GameRegistry.registerBlock(torchWoodLit, "Lit Torch");
		GameRegistry.registerBlock(torchWoodNotLit, "Unlit Torch");
		GameRegistry.registerBlock(blockRock, "Rock");
	}

}
