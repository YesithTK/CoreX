package dev.yesithx.proxycore.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import dev.yesithx.proxycore.velocity.ProxyCoreVelocity;
import dev.yesithx.proxycore.velocity.util.ColorUtil;

import java.lang.management.ManagementFactory;

public final class NetworkCommand implements SimpleCommand {

    private final ProxyCoreVelocity plugin;

    public NetworkCommand(ProxyCoreVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation inv) {
        long uptime = ManagementFactory.getRuntimeMXBean().getUptime() / 1000;
        long h = uptime / 3600;
        long m = (uptime % 3600) / 60;
        inv.source().sendMessage(ColorUtil.color("&8&m-----&r &b&lNetwork Stats &8&m-----"));
        inv.source().sendMessage(ColorUtil.color(" &7Players: &f" + plugin.getServer().getPlayerCount()));
        inv.source().sendMessage(ColorUtil.color(" &7Servers: &f" + plugin.getServer().getAllServers().size()));
        inv.source().sendMessage(ColorUtil.color(" &7Uptime: &f" + h + "h " + m + "m"));
        inv.source().sendMessage(ColorUtil.color(" &7Maintenance: " + (plugin.getMaintenanceManager().isEnabled() ? "&cEnabled" : "&aDisabled")));
        inv.source().sendMessage(ColorUtil.color("&8&m---------------------"));
    }

    @Override
    public boolean hasPermission(Invocation inv) {
        return inv.source().hasPermission("proxycore.network");
    }
}
