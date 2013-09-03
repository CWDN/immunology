package piefarmer.immunology.lib;

import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;

import piefarmer.immunology.common.Immunology;

public class LogHelper {
	private static Logger logger = Logger.getLogger(Immunology.modid);
	public static void init()
	{
		logger.setParent(FMLLog.getLogger());
	}
	public static void log(Level logLevel, String message)
	{
		logger.log(logLevel, message);
	}
}
