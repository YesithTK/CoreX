package dev.yesithx.proxycore.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import dev.yesithx.proxycore.velocity.ProxyCoreVelocity;
import dev.yesithx.proxycore.velocity.util.ColorUtil;

import java.util.Optional;

public final class SendCommand implements SimpleCommand {

    private final ProxyCoreVelocity plugin;

    public SendCommand(ProxyCoreVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation inv) {
        String[] args = inv.arguments();
        if (args.length < 2) {
            inv.source().sendMessage(ColorUtil.color("&cUsage: /vsend <player> <server>"));
            return;
        }
        Optional<Player> opt = plugin.getServer().getPlayer(args[0]);
        if (opt.isEmpty()) {
            inv.source().sendMessage(ColorUtil.color(plugin.getConfigManager().getMessage("player-not-found", "&cPlayer not found.").replace("%player%", args[0])));
            return;
        }
        Optional<RegisteredServer> serverOpt = plugin.getServer().getServer(args[1]);
        if (serverOpt.isEmpty()) {
            inv.source().sendMessage(ColorUtil.color("&cServer &e" + args[1] + " &cnot found."));
            return;
        }
        opt.get().createConnectionRequest(serverOpt.get()).fireAndForget();
        inv.source().sendMessage(ColorUtil.color("&aSent &e" + opt.get().getUsername() + " &ato &e" + args[1] + "&a."));
        opt.get().sendMessage(ColorUtil.color("&7You have been moved to &e" + args[1] + "&7."));
    }

    @Override
    public boolean hasPermission(Invocation inv) {
        return inv.source().hasPermission("proxycore.send");
    }
}
