package dev.yesithx.proxycore.bungeecord.command;

import dev.yesithx.proxycore.bungeecord.ProxyCoreBungee;
import dev.yesithx.proxycore.bungeecord.util.ColorUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.List;

public class StaffListCommand extends Command {

    private final ProxyCoreBungee plugin;

    public StaffListCommand(ProxyCoreBungee plugin) {
        super("stafflist", "proxycore.stafflist", "sl");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        List<String> staff = new ArrayList<>();
        for (ProxiedPlayer player : plugin.getProxy().getPlayers()) {
            if (player.hasPermission("proxycore.staff")) {
                staff.add("&e" + player.getName() + " &7(" + player.getServer().getInfo().getName() + ")");
            }
        }
        sender.sendMessage(ColorUtil.color("&8&m----&r &c&lStaff Online &8&m----"));
        if (staff.isEmpty()) {
            sender.sendMessage(ColorUtil.color(" &7No staff members online."));
        } else {
            for (String s : staff) {
                sender.sendMessage(ColorUtil.color(" &c● " + s));
            }
        }
        sender.sendMessage(ColorUtil.color("&8&m--------------------"));
    }
}
