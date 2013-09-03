package piefarmer.immunology.lib;

import java.io.File;

import net.minecraftforge.common.Configuration;

public class ConfigHandler {
	
	public static void init(File configFile)
	{
		Configuration config = new Configuration(configFile);
		
		config.load();
		Ids.blockcustomflowerID_actual = config.getBlock(Names.bluebellBlock_name, Ids.blockcustomflowerID_default).getInt();
		Ids.blockdiatblID_actual = config.getBlock(Names.diagBlock_name, Ids.blockdiatblID_default).getInt();
		Ids.blockmedrestblID_actual = config.getBlock(Names.medreBlock_name, Ids.blockmedrestblID_default).getInt();
		Ids.blockrockID_actual = config.getBlock(Names.rockBlock_name, Ids.blockrockID_default).getInt();
		Ids.blocktorchID_actual = config.getBlock(Names.unlittorchBlock_name, Ids.blocktorchID_default).getInt();
		
		Ids.itemcureID_actual = config.getItem(Names.cureItem_name, Ids.itemcureID_default).getInt() - 256;
		Ids.itemdiseaseID_actual = config.getItem(Names.diseaseItem_name, Ids.itemdiseaseID_default).getInt() - 256;
		Ids.itemeffectID_actual = config.getItem(Names.effectItem_name, Ids.itemeffectID_default).getInt() - 256;
		Ids.itemhanggliderID_actual = config.getItem(Names.hangItem_name, Ids.itemhanggliderID_default).getInt() - 256;
		Ids.itemmedicalbookID_actual = config.getItem(Names.medbookItem_name, Ids.itemmedicalbookID_default).getInt() - 256;
		config.save();
	}

}
