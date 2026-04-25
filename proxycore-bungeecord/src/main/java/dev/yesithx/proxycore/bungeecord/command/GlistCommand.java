package dev.yesithx.proxycore.bungeecord.command;

import dev.yesithx.proxycore.bungeecord.ProxyCoreBungee;
import dev.yesithx.proxycore.bungeecord.util.ColorUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Command;

import java.util.Map;

public class GlistCommand extends Command {

    private final ProxyCoreBungee plugin;

    public GlistCommand(ProxyCoreBungee plugin) {
        super("glist", "proxycore.glist");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        int total = plugin.getProxy().getOnlineCount();
        sender.sendMessage(ColorUtil.color("&8&m-----&r &6&lNetwork List &8&m-----"));
        for (Map.Entry<String, ServerInfo> entry : plugin.getProxy().getServers().entrySet()) {
            String name = entry.getKey();
            int count = entry.getValue().getPlayers().size();
            String status = count > 0 ? "&a● " : "&c● ";
            sender.sendMessage(ColorUtil.color(" " + status + "&e" + name + " &7- &f" + count + " players"));
        }
        sender.sendMessage(ColorUtil.color("&7Total online: &f" + total + " players"));
        sender.sendMessage(ColorUtil.color("&8&m-------------------"));
    }
}
