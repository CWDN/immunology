package piefarmer.immunology.network.packet;

import java.io.DataInputStream;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

public abstract class ImmunPacket {
	
	public static final String CHANNEL = "immunology";
	
	private static final BiMap<Integer, Class<? extends ImmunPacket>> idMap;
    
    static {
            ImmutableBiMap.Builder<Integer, Class<? extends ImmunPacket>> builder = ImmutableBiMap.builder();
           
            builder.put(Integer.valueOf(0), Packet1Disease.class);
            builder.put(Integer.valueOf(1), Packet2RemoveDisease.class);
            builder.put(Integer.valueOf(2), Packet3Particle.class);
            builder.put(Integer.valueOf(3), Packet4SideEffect.class);
            builder.put(Integer.valueOf(4), Packet5RemoveSideEffect.class);
            builder.put(Integer.valueOf(5), Packet6Cure.class);
            idMap = builder.build();
    }
    public static ImmunPacket constructPacket(int packetId) throws ProtocolException, ReflectiveOperationException {
        Class<? extends ImmunPacket> clazz = idMap.get(Integer.valueOf(packetId));
        if (clazz == null) {
                throw new ProtocolException("Unknown Packet Id!");
        } else {
                return clazz.newInstance();
        }
    }	

    public static class ProtocolException extends Exception {

        public ProtocolException() {
        }

        public ProtocolException(String message, Throwable cause) {
                super(message, cause);
        }

        public ProtocolException(String message) {
                super(message);
        }

        public ProtocolException(Throwable cause) {
                super(cause);
        }
    }
    
    public final int getPacketId() {
        if (idMap.inverse().containsKey(getClass())) {
                return idMap.inverse().get(getClass()).intValue();
        } else {
                throw new RuntimeException("Packet " + getClass().getSimpleName() + " is missing a mapping!");
        }
    }

    public final Packet makePacket() {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeByte(getPacketId());
        write(out);
        return PacketDispatcher.getPacket(CHANNEL, out.toByteArray());
    }
    public abstract void write(ByteArrayDataOutput out);
    
    public abstract void read(ByteArrayDataInput in) throws ProtocolException;
   
    public abstract void execute(EntityPlayer player, Side side) throws ProtocolException;

}
