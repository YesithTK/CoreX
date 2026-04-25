package dev.yesithx.proxycore.bungeecord.command;

import dev.yesithx.proxycore.bungeecord.ProxyCoreBungee;
import dev.yesithx.proxycore.bungeecord.util.ColorUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class FindCommand extends Command {

    private final ProxyCoreBungee plugin;

    public FindCommand(ProxyCoreBungee plugin) {
        super("find", "proxycore.find");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ColorUtil.color(plugin.getMessages().getString("find-usage", "&cUsage: /find <player>")));
            return;
        }
        ProxiedPlayer target = plugin.getProxy().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ColorUtil.color(plugin.getMessages().getString("player-not-found", "&cPlayer not found.").replace("%player%", args[0])));
            return;
        }
        String server = target.getServer().getInfo().getName();
        sender.sendMessage(ColorUtil.color(plugin.getMessages().getString("find-result",
                "&a%player% &7is on server &e%server%")
                .replace("%player%", target.getName())
                .replace("%server%", server)));
    }
}
