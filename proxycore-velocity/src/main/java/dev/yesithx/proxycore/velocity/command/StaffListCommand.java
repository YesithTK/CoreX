package dev.yesithx.proxycore.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import dev.yesithx.proxycore.velocity.ProxyCoreVelocity;
import dev.yesithx.proxycore.velocity.util.ColorUtil;

import java.util.List;
import java.util.stream.Collectors;

public final class StaffListCommand implements SimpleCommand {

    private final ProxyCoreVelocity plugin;

    public StaffListCommand(ProxyCoreVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation inv) {
        List<Player> staff = plugin.getServer().getAllPlayers().stream()
                .filter(p -> p.hasPermission("proxycore.staff"))
                .collect(Collectors.toList());

        inv.source().sendMessage(ColorUtil.color("&8&m----&r &c&lStaff Online &8&m----"));
        if (staff.isEmpty()) {
            inv.source().sendMessage(ColorUtil.color(" &7No staff members online."));
        } else {
            for (Player p : staff) {
                String server = p.getCurrentServer().map(s -> s.getServerInfo().getName()).orElse("?");
                inv.source().sendMessage(ColorUtil.color(" &c● &e" + p.getUsername() + " &7(" + server + ")"));
            }
        }
        inv.source().sendMessage(ColorUtil.color("&8&m--------------------"));
    }

    @Override
    public boolean hasPermission(Invocation inv) {
        return inv.source().hasPermission("proxycore.stafflist");
    }
}
