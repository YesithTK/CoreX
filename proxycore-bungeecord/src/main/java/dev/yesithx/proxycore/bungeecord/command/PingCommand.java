package dev.yesithx.proxycore.bungeecord.command;

import dev.yesithx.proxycore.bungeecord.ProxyCoreBungee;
import dev.yesithx.proxycore.bungeecord.util.ColorUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PingCommand extends Command {

    private final ProxyCoreBungee plugin;

    public PingCommand(ProxyCoreBungee plugin) {
        super("pping", "proxycore.ping");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer target;
        if (args.length >= 1) {
            if (!sender.hasPermission("proxycore.ping.others")) {
                sender.sendMessage(ColorUtil.color(plugin.getMessages().getString("no-permission", "&cNo permission.")));
                return;
            }
            target = plugin.getProxy().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ColorUtil.color(plugin.getMessages().getString("player-not-found", "&cPlayer not found.").replace("%player%", args[0])));
                return;
            }
        } else {
            if (!(sender instanceof ProxiedPlayer)) {
                sender.sendMessage(ColorUtil.color("&cOnly players can use /pping without arguments."));
                return;
            }
            target = (ProxiedPlayer) sender;
        }

        int ping = target.getPing();
        String quality;
        if (ping < 50) quality = "&a✅ Excellent";
        else if (ping < 100) quality = "&2✅ Good";
        else if (ping < 200) quality = "&e⚡ Average";
        else if (ping < 350) quality = "&6⚠ Poor";
        else quality = "&c💀 Very Poor";

        sender.sendMessage(ColorUtil.color("&7" + target.getName() + "'s ping: " + quality + " &7(" + ping + "ms)"));
    }
}
