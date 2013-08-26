package piefarmer.immunology.entity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;

import piefarmer.immunology.disease.Disease;
import piefarmer.immunology.disease.DiseaseEffect;
import piefarmer.immunology.network.packet.Packet1Disease;
import piefarmer.immunology.network.packet.Packet2RemoveDisease;
import piefarmer.immunology.network.packet.Packet4SideEffect;
import piefarmer.immunology.network.packet.Packet5RemoveSideEffect;

public class EntityPacketHandler {

    public static void onFinishedDisease(Disease par1Disease, Player player)
    {
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if(side == Side.SERVER)
		{
			PacketDispatcher.sendPacketToAllPlayers(new Packet2RemoveDisease(par1Disease.getdiseaseID()).makePacket());
		}
    }
    public static void onChangedDisease(Disease par1Disease, Player player)
    {
    	Side side = FMLCommonHandler.instance().getEffectiveSide();
    	if(side == Side.SERVER)
		{
    		PacketDispatcher.sendPacketToAllPlayers(new Packet1Disease(par1Disease).makePacket());
		}
		else if(side == Side.CLIENT)
		{
			PacketDispatcher.sendPacketToServer(new Packet1Disease(par1Disease).makePacket());
		}
    }
    public static void onNewDisease(Disease par1Disease, Player player)
    {
    	Side side = FMLCommonHandler.instance().getEffectiveSide();
    	if(side == Side.SERVER)
    	{
    		PacketDispatcher.sendPacketToAllPlayers(new Packet1Disease(par1Disease).makePacket());
		}
		else if(side == Side.CLIENT)
		{
			PacketDispatcher.sendPacketToServer(new Packet1Disease(par1Disease).makePacket());
		}
    }
    public static void onFinishedSideEffect(DiseaseEffect effect, Player player)
    {
    	Side side = FMLCommonHandler.instance().getEffectiveSide();
    	if(side.isServer())
    	{
    		PacketDispatcher.sendPacketToAllPlayers(new Packet5RemoveSideEffect(effect.getDiseaseEffectID()).makePacket());
    	}
    	else if(side.isClient())
    	{
    		
    	}
    }
	public static void onChangedSideEffect(DiseaseEffect effect, Player player) {
		Side side = FMLCommonHandler.instance().getEffectiveSide();
    	if(side == Side.SERVER)
		{
    		PacketDispatcher.sendPacketToAllPlayers(new Packet4SideEffect(effect).makePacket());
		}
		else if(side == Side.CLIENT)
		{
			PacketDispatcher.sendPacketToServer(new Packet4SideEffect(effect).makePacket());
		}
	}
	public static void onNewSideEffect(DiseaseEffect effect, Player player) {
		Side side = FMLCommonHandler.instance().getEffectiveSide();
    	if(side == Side.SERVER)
		{
    		PacketDispatcher.sendPacketToAllPlayers(new Packet4SideEffect(effect).makePacket());
		}
		else if(side == Side.CLIENT)
		{
			PacketDispatcher.sendPacketToServer(new Packet4SideEffect(effect).makePacket());
		}
		
	}

}
