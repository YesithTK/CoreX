package dev.yesithx.proxycore.bungeecord.command;

import dev.yesithx.proxycore.bungeecord.ProxyCoreBungee;
import dev.yesithx.proxycore.bungeecord.util.ColorUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class KickCommand extends Command {

    private final ProxyCoreBungee plugin;

    public KickCommand(ProxyCoreBungee plugin) {
        super("pkick", "proxycore.kick");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ColorUtil.color("&cUsage: /pkick <player> [reason]"));
            return;
        }
        ProxiedPlayer target = plugin.getProxy().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ColorUtil.color(plugin.getMessages().getString("player-not-found", "&cPlayer not found.").replace("%player%", args[0])));
            return;
        }
        String reason = args.length > 1
                ? String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length))
                : plugin.getMessages().getString("default-kick-reason", "You have been kicked.");

        target.disconnect(new TextComponent(ColorUtil.color(reason)));
        sender.sendMessage(ColorUtil.color("&aKicked &e" + target.getName() + " &afor: &f" + reason));
        plugin.getProxy().broadcast(ColorUtil.color("&8[&cKick&8] &e" + target.getName() + " &7has been kicked: &f" + reason));
    }
}
