package dev.yesithx.proxycore.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import dev.yesithx.proxycore.velocity.ProxyCoreVelocity;
import dev.yesithx.proxycore.velocity.util.ColorUtil;

import java.lang.management.ManagementFactory;

public final class UptimeCommand implements SimpleCommand {

    private final ProxyCoreVelocity plugin;

    public UptimeCommand(ProxyCoreVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation inv) {
        long uptime = ManagementFactory.getRuntimeMXBean().getUptime() / 1000;
        long d = uptime / 86400;
        long h = (uptime % 86400) / 3600;
        long m = (uptime % 3600) / 60;
        long s = uptime % 60;
        inv.source().sendMessage(ColorUtil.color("&7Network uptime: &e" + d + "d " + h + "h " + m + "m " + s + "s"));
    }

    @Override
    public boolean hasPermission(Invocation inv) {
        return inv.source().hasPermission("proxycore.uptime");
    }
}
