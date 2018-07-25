package fi.dy.masa.vanishmentpoint.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import fi.dy.masa.vanishmentpoint.config.Configs;

public class CommandLoadConfigs extends CommandBase
{
    @Override
    public String getName()
    {
        return "vanishmentpoint-load-configs";
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
        Configs.reLoadAllConfigs(true);
        notifyCommandListener(sender, this, "vanishmentpoint.commands.loadconfig.success", new Object[0]);
    }
}
