package dev.yesithx.proxycore.bungeecord.command;

import dev.yesithx.proxycore.bungeecord.ProxyCoreBungee;
import dev.yesithx.proxycore.bungeecord.util.ColorUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MaintenanceCommand extends Command {

    private final ProxyCoreBungee plugin;

    public MaintenanceCommand(ProxyCoreBungee plugin) {
        super("maintenance", "proxycore.maintenance", "maint");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ColorUtil.color("&cUsage: /maintenance <on|off>"));
            return;
        }
        boolean enable = args[0].equalsIgnoreCase("on");
        plugin.getMaintenanceManager().setEnabled(enable);

        if (enable) {
            sender.sendMessage(ColorUtil.color("&cMaintenance mode &lenabled&r&c. Non-staff players will be kicked."));
            for (ProxiedPlayer player : plugin.getProxy().getPlayers()) {
                if (!player.hasPermission("proxycore.maintenance.bypass")) {
                    player.disconnect(new TextComponent(ColorUtil.color(plugin.getMaintenanceManager().getKickMessage())));
                }
            }
        } else {
            sender.sendMessage(ColorUtil.color("&aMaintenance mode &ldisabled&r&a. Server is open."));
        }
        plugin.getProxy().broadcast(ColorUtil.color("&8[&6ProxyCore&8] &7Maintenance: " + (enable ? "&cEnabled" : "&aDisabled")));
    }
}
