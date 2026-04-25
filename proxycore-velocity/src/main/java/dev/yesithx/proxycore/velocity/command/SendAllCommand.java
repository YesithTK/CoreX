package dev.yesithx.proxycore.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import dev.yesithx.proxycore.velocity.ProxyCoreVelocity;
import dev.yesithx.proxycore.velocity.util.ColorUtil;

import java.util.Optional;

public final class SendAllCommand implements SimpleCommand {

    private final ProxyCoreVelocity plugin;

    public SendAllCommand(ProxyCoreVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation inv) {
        if (inv.arguments().length < 1) {
            inv.source().sendMessage(ColorUtil.color("&cUsage: /vsendall <server>"));
            return;
        }
        Optional<RegisteredServer> serverOpt = plugin.getServer().getServer(inv.arguments()[0]);
        if (serverOpt.isEmpty()) {
            inv.source().sendMessage(ColorUtil.color("&cServer &e" + inv.arguments()[0] + " &cnot found."));
            return;
        }
        RegisteredServer rs = serverOpt.get();
        plugin.getServer().getAllPlayers().forEach(p -> {
            p.sendMessage(ColorUtil.color("&7All players are being moved to &e" + rs.getServerInfo().getName() + "&7."));
            p.createConnectionRequest(rs).fireAndForget();
        });
        inv.source().sendMessage(ColorUtil.color("&aSent all players to &e" + rs.getServerInfo().getName() + "&a."));
    }

    @Override
    public boolean hasPermission(Invocation inv) {
        return inv.source().hasPermission("proxycore.sendall");
    }
}
