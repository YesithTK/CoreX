package dev.yesithx.proxycore.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import dev.yesithx.proxycore.velocity.ProxyCoreVelocity;
import dev.yesithx.proxycore.velocity.util.ColorUtil;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public final class PListCommand implements SimpleCommand {

    private final ProxyCoreVelocity plugin;

    public PListCommand(ProxyCoreVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation inv) {
        if (inv.arguments().length < 1) {
            inv.source().sendMessage(ColorUtil.color("&cUsage: /vplist <server>"));
            return;
        }
        Optional<RegisteredServer> opt = plugin.getServer().getServer(inv.arguments()[0]);
        if (opt.isEmpty()) {
            inv.source().sendMessage(ColorUtil.color("&cServer &e" + inv.arguments()[0] + " &cnot found."));
            return;
        }
        RegisteredServer rs = opt.get();
        Collection<Player> players = rs.getPlayersConnected();
        inv.source().sendMessage(ColorUtil.color("&8&m--&r &e" + rs.getServerInfo().getName() + " &7(&f" + players.size() + "&7) &8&m--"));
        if (players.isEmpty()) {
            inv.source().sendMessage(ColorUtil.color(" &7No players online."));
        } else {
            String names = players.stream().map(Player::getUsername).collect(Collectors.joining("&7, &f"));
            inv.source().sendMessage(ColorUtil.color(" &f" + names));
        }
    }

    @Override
    public boolean hasPermission(Invocation inv) {
        return inv.source().hasPermission("proxycore.plist");
    }
}
