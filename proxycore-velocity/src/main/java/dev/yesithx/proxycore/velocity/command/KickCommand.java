package dev.yesithx.proxycore.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import dev.yesithx.proxycore.velocity.ProxyCoreVelocity;
import dev.yesithx.proxycore.velocity.util.ColorUtil;

import java.util.Arrays;
import java.util.Optional;

public final class KickCommand implements SimpleCommand {

    private final ProxyCoreVelocity plugin;

    public KickCommand(ProxyCoreVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation inv) {
        String[] args = inv.arguments();
        if (args.length < 1) {
            inv.source().sendMessage(ColorUtil.color("&cUsage: /vkick <player> [reason]"));
            return;
        }
        Optional<Player> opt = plugin.getServer().getPlayer(args[0]);
        if (opt.isEmpty()) {
            inv.source().sendMessage(ColorUtil.color(plugin.getConfigManager().getMessage("player-not-found", "&cPlayer not found.").replace("%player%", args[0])));
            return;
        }
        String reason = args.length > 1
                ? String.join(" ", Arrays.copyOfRange(args, 1, args.length))
                : plugin.getConfigManager().getMessage("default-kick-reason", "You have been kicked.");

        Player target = opt.get();
        target.disconnect(ColorUtil.color(reason));
        inv.source().sendMessage(ColorUtil.color("&aKicked &e" + target.getUsername() + " &afor: &f" + reason));
        plugin.getServer().getAllPlayers().forEach(p ->
                p.sendMessage(ColorUtil.color("&8[&cKick&8] &e" + target.getUsername() + " &7has been kicked: &f" + reason)));
    }

    @Override
    public boolean hasPermission(Invocation inv) {
        return inv.source().hasPermission("proxycore.kick");
    }
}
