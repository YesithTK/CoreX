package dev.yesithx.proxycore.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import dev.yesithx.proxycore.velocity.ProxyCoreVelocity;
import dev.yesithx.proxycore.velocity.util.ColorUtil;

import java.util.Optional;

public final class FindCommand implements SimpleCommand {

    private final ProxyCoreVelocity plugin;

    public FindCommand(ProxyCoreVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation inv) {
        String[] args = inv.arguments();
        if (args.length < 1) {
            inv.source().sendMessage(ColorUtil.color("&cUsage: /find <player>"));
            return;
        }
        Optional<Player> target = plugin.getServer().getPlayer(args[0]);
        if (target.isEmpty()) {
            inv.source().sendMessage(ColorUtil.color(plugin.getConfigManager().getMessage("player-not-found", "&cPlayer not found.").replace("%player%", args[0])));
            return;
        }
        String server = target.get().getCurrentServer().map(s -> s.getServerInfo().getName()).orElse("unknown");
        inv.source().sendMessage(ColorUtil.color(plugin.getConfigManager().getMessage("find-result", "&a%player% &7is on server &e%server%")
                .replace("%player%", target.get().getUsername())
                .replace("%server%", server)));
    }

    @Override
    public boolean hasPermission(Invocation inv) {
        return inv.source().hasPermission("proxycore.find");
    }
}
