package dev.yesithx.proxycore.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import dev.yesithx.proxycore.velocity.ProxyCoreVelocity;
import dev.yesithx.proxycore.velocity.util.ColorUtil;

import java.util.Arrays;
import java.util.Optional;

public final class SudoCommand implements SimpleCommand {

    private final ProxyCoreVelocity plugin;

    public SudoCommand(ProxyCoreVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation inv) {
        String[] args = inv.arguments();
        if (args.length < 2) {
            inv.source().sendMessage(ColorUtil.color("&cUsage: /vsudo <player> <command>"));
            return;
        }
        Optional<Player> opt = plugin.getServer().getPlayer(args[0]);
        if (opt.isEmpty()) {
            inv.source().sendMessage(ColorUtil.color(plugin.getConfigManager().getMessage("player-not-found", "&cPlayer not found.").replace("%player%", args[0])));
            return;
        }
        String cmd = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        plugin.getServer().getCommandManager().executeAsync(opt.get(), cmd);
        inv.source().sendMessage(ColorUtil.color("&aMade &e" + opt.get().getUsername() + " &arun: &f/" + cmd));
    }

    @Override
    public boolean hasPermission(Invocation inv) {
        return inv.source().hasPermission("proxycore.sudo");
    }
}
