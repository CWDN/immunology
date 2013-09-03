package piefarmer.immunology.item;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.LanguageRegistry;
import piefarmer.immunology.lib.Ids;
import piefarmer.immunology.lib.Names;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;

public class Items {
	public static Item disease;
	public static ItemCure cure;
	public static Item effect;
	public static Item hangGlider;
	public static Item medicalBook;
	
	public static Item beefRaw; 
    public static Item chickenRaw;
    public static Item fishRaw;
    public static Item porkRaw;
	
	public static void init(){
		disease = new ItemDisease(Ids.itemdiseaseID_actual).setUnlocalizedName(Names.diseaseItem_unlocalizedName);
		cure = (ItemCure) new ItemCure(Ids.itemcureID_actual).setUnlocalizedName(Names.cureItem_unlocalizedName);
		effect = new ItemEffect(Ids.itemeffectID_actual).setUnlocalizedName(Names.effectItem_unlocalizedName);
		hangGlider = new ItemHangGlider(Ids.itemhanggliderID_actual).setUnlocalizedName(Names.hangItem_unlocalizedName);
		medicalBook = new ItemMedicalBook(Ids.itemmedicalbookID_actual).setUnlocalizedName(Names.medbookItem_unlocalizedName);
		porkRaw = (new ItemDecayingFood(63, 3, 0.3F, true)).setUnlocalizedName("porkchopRaw").func_111206_d("porkchop_raw");
		fishRaw = (new ItemDecayingFood(93, 2, 0.3F, false)).setUnlocalizedName("fishRaw").func_111206_d("fish_raw");;
		chickenRaw = (new ItemDecayingFood(109, 2, 0.3F, true)).setPotionEffect(Potion.hunger.id, 30, 0, 0.3F).setUnlocalizedName("chickenRaw").func_111206_d("chicken_raw");;
		beefRaw = (new ItemDecayingFood(107, 3, 0.3F, true)).setUnlocalizedName("beefRaw").func_111206_d("beef_raw");;
	}
	public static void addNames()
	{
		LanguageRegistry.addName(disease, Names.diseaseItem_name);
		LanguageRegistry.addName(cure, Names.cureItem_name);
		LanguageRegistry.addName(effect, Names.effectItem_name);
		LanguageRegistry.addName(hangGlider, Names.hangItem_name);
		LanguageRegistry.addName(medicalBook, Names.medbookItem_name);
	}

}
