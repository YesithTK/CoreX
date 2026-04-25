package dev.yesithx.proxycore.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import dev.yesithx.proxycore.velocity.ProxyCoreVelocity;
import dev.yesithx.proxycore.velocity.util.ColorUtil;

import java.util.Optional;

public final class IPCommand implements SimpleCommand {

    private final ProxyCoreVelocity plugin;

    public IPCommand(ProxyCoreVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation inv) {
        String[] args = inv.arguments();
        if (args.length < 1) {
            inv.source().sendMessage(ColorUtil.color("&cUsage: /vip <player>"));
            return;
        }
        Optional<Player> opt = plugin.getServer().getPlayer(args[0]);
        if (opt.isEmpty()) {
            inv.source().sendMessage(ColorUtil.color(plugin.getConfigManager().getMessage("player-not-found", "&cPlayer not found.").replace("%player%", args[0])));
            return;
        }
        String ip = opt.get().getRemoteAddress().getAddress().getHostAddress();
        inv.source().sendMessage(ColorUtil.color("&7" + opt.get().getUsername() + "'s IP: &e" + ip));
    }

    @Override
    public boolean hasPermission(Invocation inv) {
        return inv.source().hasPermission("proxycore.ip");
    }
}
