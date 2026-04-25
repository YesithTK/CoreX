package dev.yesithx.proxycore.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import dev.yesithx.proxycore.velocity.ProxyCoreVelocity;
import dev.yesithx.proxycore.velocity.util.ColorUtil;

public final class MaintenanceCommand implements SimpleCommand {

    private final ProxyCoreVelocity plugin;

    public MaintenanceCommand(ProxyCoreVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation inv) {
        if (inv.arguments().length < 1) {
            inv.source().sendMessage(ColorUtil.color("&cUsage: /vmaintenance <on|off>"));
            return;
        }
        boolean enable = inv.arguments()[0].equalsIgnoreCase("on");
        plugin.getMaintenanceManager().setEnabled(enable);

        if (enable) {
            plugin.getServer().getAllPlayers().stream()
                    .filter(p -> !p.hasPermission("proxycore.maintenance.bypass"))
                    .forEach(p -> p.disconnect(ColorUtil.color(plugin.getMaintenanceManager().getKickMessage())));
        }

        plugin.getServer().getAllPlayers().forEach(p ->
                p.sendMessage(ColorUtil.color("&8[&6ProxyCore&8] &7Maintenance: " + (enable ? "&cEnabled" : "&aDisabled"))));
        inv.source().sendMessage(ColorUtil.color("&7Maintenance is now " + (enable ? "&cEnabled" : "&aDisabled") + "&7."));
    }

    @Override
    public boolean hasPermission(Invocation inv) {
        return inv.source().hasPermission("proxycore.maintenance");
    }
}
