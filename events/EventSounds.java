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
			event.manager.soundPoolSounds.addSound("piefarmer/immunology/sneeze.wav", new File(mc.mcDataDir, "resources/immunology/sneeze.wav"));
			event.manager.soundPoolSounds.addSound("piefarmer/immunology/sniff.wav", new File(mc.mcDataDir, "resources/immunology/sniff.wav"));
			event.manager.soundPoolSounds.addSound("piefarmer/immunology/cough.wav", new File(mc.mcDataDir, "resources/immunology/cough.wav"));
			event.manager.soundPoolSounds.addSound("piefarmer/immunology/badgerdeath.wav", new File(mc.mcDataDir, "resources/immunology/badgerdeath.wav"));
			event.manager.soundPoolSounds.addSound("piefarmer/immunology/badgerhurt.wav", new File(mc.mcDataDir, "resources/immunology/badgerhurt.wav"));
			event.manager.soundPoolSounds.addSound("piefarmer/immunology/badgerliving.wav", new File(mc.mcDataDir, "resources/immunology/badgerliving.wav"));
		}
		catch(Exception e)
		{
			FMLLog.log(Level.SEVERE, e, "Immunology Mod failed to register one or more sounds.");
			FMLLog.severe(e.getMessage());
		}
	}

}
