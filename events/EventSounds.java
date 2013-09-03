package piefarmer.immunology.events;

import java.io.File;
import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class EventSounds {

	@ForgeSubscribe
	public void onSound(SoundLoadEvent event)
	{
		try
		{
			Minecraft mc = Minecraft.getMinecraft();
			event.manager.addSound("immunology:sneeze.wav");
			event.manager.addSound("immunology:sniff.wav");
			event.manager.addSound("imnnuology:cough.wav");
			event.manager.addSound("immunology:badgerdeath.wav");
			event.manager.addSound("immunology:badgerhurt.wav");
		}
		catch(Exception e)
		{
			FMLLog.log(Level.SEVERE, e, "Immunology Mod failed to register one or more sounds.");
			FMLLog.severe(e.getMessage());
		}
	}

}
