package dev.yesithx.proxycore.bungeecord.command;

import dev.yesithx.proxycore.bungeecord.ProxyCoreBungee;
import dev.yesithx.proxycore.bungeecord.util.ColorUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class SendCommand extends Command {

    private final ProxyCoreBungee plugin;

    public SendCommand(ProxyCoreBungee plugin) {
        super("psend", "proxycore.send");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ColorUtil.color("&cUsage: /psend <player> <server>"));
            return;
        }
        ProxiedPlayer target = plugin.getProxy().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ColorUtil.color(plugin.getMessages().getString("player-not-found", "&cPlayer not found.").replace("%player%", args[0])));
            return;
        }
        ServerInfo server = plugin.getProxy().getServerInfo(args[1]);
        if (server == null) {
            sender.sendMessage(ColorUtil.color("&cServer &e" + args[1] + " &cnot found."));
            return;
        }
        target.connect(server);
        sender.sendMessage(ColorUtil.color("&aSent &e" + target.getName() + " &ato &e" + server.getName() + "&a."));
        target.sendMessage(ColorUtil.color("&7You have been moved to &e" + server.getName() + "&7."));
    }
}
