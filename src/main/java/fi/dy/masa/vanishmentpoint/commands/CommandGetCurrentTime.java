package fi.dy.masa.vanishmentpoint.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import fi.dy.masa.vanishmentpoint.block.BlockVanishingBlockTime;

public class CommandGetCurrentTime extends CommandBase
{
    @Override
    public String getName()
    {
        return "vanishmentpoint-get-current-time";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "/" + this.getName();
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        World world = sender.getEntityWorld();
        float adjustedAngle = BlockVanishingBlockTime.getAdjustedAngle(world.getCelestialAngle(0));

        String str = String.format("%.2f", world.getCelestialAngle(0));
        String str2 = String.format("%.2f", adjustedAngle);
        notifyCommandListener(sender, this, "vanishmentpoint.commands.current_time.celestial_angle", str, str2);

        str = String.valueOf(world.getWorldTime());
        notifyCommandListener(sender, this, "vanishmentpoint.commands.current_time.day", str);

        str = String.valueOf(world.getTotalWorldTime());
        notifyCommandListener(sender, this, "vanishmentpoint.commands.current_time.total", str);
    }
}
