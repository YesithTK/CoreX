package dev.yesithx.proxycore.bungeecord.command;

import dev.yesithx.proxycore.bungeecord.ProxyCoreBungee;
import dev.yesithx.proxycore.bungeecord.util.ColorUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ProxyReloadCommand extends Command {

    private final ProxyCoreBungee plugin;

    public ProxyReloadCommand(ProxyCoreBungee plugin) {
        super("proxyreload", "proxycore.reload", "pcreload");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(ColorUtil.color("&7Reloading ProxyCore..."));
        plugin.reloadConfigs();
        sender.sendMessage(ColorUtil.color("&aProxyCore reloaded successfully!"));
    }
}
