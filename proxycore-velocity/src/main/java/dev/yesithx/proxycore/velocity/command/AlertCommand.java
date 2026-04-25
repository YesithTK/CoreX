package dev.yesithx.proxycore.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import dev.yesithx.proxycore.velocity.ProxyCoreVelocity;
import dev.yesithx.proxycore.velocity.util.ColorUtil;

public final class AlertCommand implements SimpleCommand {

    private final ProxyCoreVelocity plugin;

    public AlertCommand(ProxyCoreVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation inv) {
        if (inv.arguments().length < 1) {
            inv.source().sendMessage(ColorUtil.color("&cUsage: /valert <message>"));
            return;
        }
        String msg = String.join(" ", inv.arguments());
        String line = "&8&m" + "=".repeat(40);
        plugin.getServer().getAllPlayers().forEach(p -> {
            p.sendMessage(ColorUtil.color(line));
            p.sendMessage(ColorUtil.color("    &c&l⚠ ALERT &c&l⚠"));
            p.sendMessage(ColorUtil.color("    &f" + msg));
            p.sendMessage(ColorUtil.color(line));
        });
    }

    @Override
    public boolean hasPermission(Invocation inv) {
        return inv.source().hasPermission("proxycore.alert");
    }
}
