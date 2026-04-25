package dev.yesithx.proxycore.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import dev.yesithx.proxycore.velocity.ProxyCoreVelocity;
import dev.yesithx.proxycore.velocity.util.ColorUtil;

import java.util.Optional;

public final class PingCommand implements SimpleCommand {

    private final ProxyCoreVelocity plugin;

    public PingCommand(ProxyCoreVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation inv) {
        String[] args = inv.arguments();
        Player target;

        if (args.length >= 1) {
            if (!inv.source().hasPermission("proxycore.ping.others")) {
                inv.source().sendMessage(ColorUtil.color(plugin.getConfigManager().getMessage("no-permission", "&cNo permission.")));
                return;
            }
            Optional<Player> opt = plugin.getServer().getPlayer(args[0]);
            if (opt.isEmpty()) {
                inv.source().sendMessage(ColorUtil.color(plugin.getConfigManager().getMessage("player-not-found", "&cPlayer not found.").replace("%player%", args[0])));
                return;
            }
            target = opt.get();
        } else {
            if (!(inv.source() instanceof Player)) {
                inv.source().sendMessage(ColorUtil.color("&cOnly players can use this without arguments."));
                return;
            }
            target = (Player) inv.source();
        }

        long ping = target.getPing();
        String quality;
        if (ping < 50) quality = "&a✅ Excellent";
        else if (ping < 100) quality = "&2✅ Good";
        else if (ping < 200) quality = "&e⚡ Average";
        else if (ping < 350) quality = "&6⚠ Poor";
        else quality = "&c💀 Very Poor";

        inv.source().sendMessage(ColorUtil.color("&7" + target.getUsername() + "'s ping: " + quality + " &7(" + ping + "ms)"));
    }

    @Override
    public boolean hasPermission(Invocation inv) {
        return inv.source().hasPermission("proxycore.ping");
    }
}
