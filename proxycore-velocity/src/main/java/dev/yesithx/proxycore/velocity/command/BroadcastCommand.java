package dev.yesithx.proxycore.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import dev.yesithx.proxycore.velocity.ProxyCoreVelocity;
import dev.yesithx.proxycore.velocity.util.ColorUtil;
import net.kyori.adventure.text.Component;

public final class BroadcastCommand implements SimpleCommand {

    private final ProxyCoreVelocity plugin;

    public BroadcastCommand(ProxyCoreVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation inv) {
        if (inv.arguments().length < 1) {
            inv.source().sendMessage(ColorUtil.color("&cUsage: /vbroadcast <message>"));
            return;
        }
        String prefix = plugin.getConfigManager().getMessage("broadcast-prefix", "&8[&4Broadcast&8] &r");
        Component msg = ColorUtil.color(prefix + String.join(" ", inv.arguments()));
        plugin.getServer().getAllPlayers().forEach(p -> p.sendMessage(msg));
    }

    @Override
    public boolean hasPermission(Invocation inv) {
        return inv.source().hasPermission("proxycore.broadcast");
    }
}
