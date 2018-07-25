package fi.dy.masa.vanishmentpoint.config;

import java.io.File;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import fi.dy.masa.vanishmentpoint.reference.Reference;

@Mod.EventBusSubscriber(Side.CLIENT)
public class Configs
{
    public static double celestialAngleOffset;
    public static double vanishingBlockVanishingTime;
    public static double vanishingBlockVanishingChance;

    public static File configurationFile;
    public static Configuration config;

    public static final String CATEGORY_GENERIC = "Generic";

    @SubscribeEvent
    public static void onConfigChangedEvent(OnConfigChangedEvent event)
    {
        if (Reference.MOD_ID.equals(event.getModID()))
        {
            reLoadAllConfigs(false);
        }
    }

    public static void loadConfigsFromFile(File configFile)
    {
        configurationFile = configFile;
        config = new Configuration(configurationFile, null, true);
        reLoadAllConfigs(true);
    }

    public static void reLoadAllConfigs(boolean reloadFromFile)
    {
        if (reloadFromFile)
        {
            config.load();
        }

        loadConfigsGeneric(config);

        if (config.hasChanged())
        {
            config.save();
        }
    }

    private static void loadConfigsGeneric(Configuration conf)
    {
        Property prop;

        prop = conf.get(CATEGORY_GENERIC, "celestialAngleOffset", -0.25);
        prop.setComment("This is the offset of the \"actual\" celestial angle, which starts at 0.75 at sun rise.\n" +
                        "Use this to adjust the calculation such that you can get the block to stay at the time of day\n" +
                        "that you want it to stay (because it vanishes after the adjusted angle goes above the configured value).\n" +
                        "So for example using the default offset of 0.75, which makes the adjusted value to start at 0.0 at sun rise,\n" +
                        "you couldn't get the blocks to only stay at night, because the night would start at 0.5,\n" +
                        "and the blocks would need to vanish at >= 0.0, which would make them always vanish.\n" +
                        "So by subtracting 0.25, you would make the night be 0.0 .. 0.5 and the day be 0.5 .. 1.0 instead of 0.75 .. 1.0 -> 0.0 .. 0.25,\n" +
                        "which would allow you to set the blocks to vanish at 0.5, meaning at dawn." +
                        "You can use the command '/vanishmentpoint-get-current-time' to check the current values,\n" +
                        "and the command '/vanishmentpoint-load-configs' to reload this config at any time.\n" +
                        "Valid range: -1 ... 1");
        celestialAngleOffset = MathHelper.clamp(prop.getDouble(), -1.0, 1.0);

        prop = conf.get(CATEGORY_GENERIC, "vanishingBlockVanishingChance", 1.0);
        prop.setComment("This is the chance per each random tick that the block will vanish, if the time requirement for vanishing is met.");
        vanishingBlockVanishingChance = MathHelper.clamp(prop.getDouble(), 0.0, 1.0);

        prop = conf.get(CATEGORY_GENERIC, "vanishingBlockVanishingTime", 0.6);
        prop.setComment("This is the time of a Minecraft day the blocks will start to vanish at.\n" +
                        "Specified as the Celestial Angle value, which is length-of-day independent.\n" +
                        "Note that this is an adjusted value, see the celestialAngleOffset config. Range is 0.0 to 1.0.");
        vanishingBlockVanishingTime = MathHelper.clamp(prop.getDouble(), 0.0, 1.0);

        if (conf.hasChanged())
        {
            conf.save();
        }
    }
}
