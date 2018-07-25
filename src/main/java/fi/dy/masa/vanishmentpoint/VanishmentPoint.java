package fi.dy.masa.vanishmentpoint;

import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import fi.dy.masa.vanishmentpoint.commands.CommandGetCurrentTime;
import fi.dy.masa.vanishmentpoint.commands.CommandLoadConfigs;
import fi.dy.masa.vanishmentpoint.config.Configs;
import fi.dy.masa.vanishmentpoint.reference.Reference;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, certificateFingerprint = Reference.FINGERPRINT,
    guiFactory = "fi.dy.masa.vanishmentpoint.config.VanishmentPointGuiFactory",
    updateJSON = "https://raw.githubusercontent.com/maruohon/vanishmentpoint/master/update.json",
    acceptedMinecraftVersions = "1.12")
public class VanishmentPoint
{
    @Mod.Instance(Reference.MOD_ID)
    public static VanishmentPoint instance;

    public static final Logger logger = LogManager.getLogger(Reference.MOD_ID);
    public static final Random RAND = new Random(System.currentTimeMillis());

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Configs.loadConfigsFromFile(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event)
    {
        // Should these be moved to the ServerAboutToStartEvent?
        Configs.reLoadAllConfigs(true);

        event.registerServerCommand(new CommandGetCurrentTime());
        event.registerServerCommand(new CommandLoadConfigs());
    }

    @Mod.EventHandler
    public void onFingerPrintViolation(FMLFingerprintViolationEvent event)
    {
        // Not running in a dev environment
        if (event.isDirectory() == false)
        {
            logger.warn("*********************************************************************************************");
            logger.warn("*****                                    WARNING                                        *****");
            logger.warn("*****                                                                                   *****");
            logger.warn("*****   The signature of the mod file '{}' does not match the expected fingerprint!     *****", event.getSource().getName());
            logger.warn("*****   This might mean that the mod file has been tampered with!                       *****");
            logger.warn("*****   If you did not download the mod {} directly from Curse/CurseForge,       *****", Reference.MOD_NAME);
            logger.warn("*****   or using one of the well known launchers, and you did not                       *****");
            logger.warn("*****   modify the mod file at all yourself, then it's possible,                        *****");
            logger.warn("*****   that it may contain malware or other unwanted things!                           *****");
            logger.warn("*********************************************************************************************");
        }
    }
}
