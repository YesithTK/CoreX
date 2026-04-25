package dev.yesithx.proxycore.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import dev.yesithx.proxycore.velocity.ProxyCoreVelocity;
import dev.yesithx.proxycore.velocity.util.ColorUtil;

public final class AnnounceCommand implements SimpleCommand {

    private final ProxyCoreVelocity plugin;

    public AnnounceCommand(ProxyCoreVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation inv) {
        if (inv.arguments().length < 1) {
            inv.source().sendMessage(ColorUtil.color("&cUsage: /vannounce <message>"));
            return;
        }
        String msg = String.join(" ", inv.arguments());
        String prefix = plugin.getConfigManager().getMessage("announce-prefix", "&8[&6Announce&8] &r");
        plugin.getServer().getAllPlayers().forEach(p -> p.sendMessage(ColorUtil.color(prefix + msg)));
    }

    @Override
    public boolean hasPermission(Invocation inv) {
        return inv.source().hasPermission("proxycore.announce");
    }
}
