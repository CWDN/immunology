package piefarmer.immunology.tileentity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import piefarmer.immunology.disease.Disease;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

public class TileEntityDiagnosticTable extends TileEntity{
	
	public String entityname = ""; 
	public HashMap entityDiseases = new HashMap();
	
	public TileEntityDiagnosticTable(){
	}
	@Override
	public void readFromNBT(NBTTagCompound tagCompound){
		super.readFromNBT(tagCompound);
		this.entityname = tagCompound.getString("EntityName");
		NBTTagList var2;
		int var3;
		if(tagCompound.hasKey("ActiveDiseases"))
        {
        	var2 = tagCompound.getTagList("ActiveDiseases");
        	for(var3 = 0; var3 < var2.tagCount(); ++var3)
        	{
        		NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
        		Disease var5 = Disease.readCustomDiseaseFromNBT(var4);
        		this.entityDiseases.put(Integer.valueOf(var3), var5);
        	}
        }
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound){
		super.writeToNBT(tagCompound);

		tagCompound.setString("EntityName", this.entityname);
		NBTTagList var6;
		if(!this.entityDiseases.isEmpty())
        {
        	var6 = new NBTTagList();
        	Iterator var8 = this.entityDiseases.values().iterator();
        	while (var8.hasNext())
        	{
        		Disease var5 = (Disease)var8.next();
        		var6.appendTag(var5.writeCustomDiseaseToNBT(new NBTTagCompound()));
        	}
        	tagCompound.setTag("ActiveDiseases", var6);
        }
	}


	public void updateEntity()
	{
		
	}
	public void setEntityName(String name, EntityPlayer player, List list)
	{
		this.entityname = name;
    	for(int i = 0; i < list.size(); i++)
    	{
    		Disease var2 = (Disease)list.get(i);
    		entityDiseases.put(i, var2);
    	}
	}
}
