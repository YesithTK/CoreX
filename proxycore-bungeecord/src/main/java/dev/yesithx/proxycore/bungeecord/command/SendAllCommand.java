package dev.yesithx.proxycore.bungeecord.command;

import dev.yesithx.proxycore.bungeecord.ProxyCoreBungee;
import dev.yesithx.proxycore.bungeecord.util.ColorUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class SendAllCommand extends Command {

    private final ProxyCoreBungee plugin;

    public SendAllCommand(ProxyCoreBungee plugin) {
        super("sendall", "proxycore.sendall");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ColorUtil.color("&cUsage: /sendall <server>"));
            return;
        }
        ServerInfo server = plugin.getProxy().getServerInfo(args[0]);
        if (server == null) {
            sender.sendMessage(ColorUtil.color("&cServer &e" + args[0] + " &cnot found."));
            return;
        }
        int count = 0;
        for (ProxiedPlayer player : plugin.getProxy().getPlayers()) {
            player.connect(server);
            player.sendMessage(ColorUtil.color("&7All players are being moved to &e" + server.getName() + "&7."));
            count++;
        }
        sender.sendMessage(ColorUtil.color("&aSent &e" + count + " &aplayers to &e" + server.getName() + "&a."));
    }
}
