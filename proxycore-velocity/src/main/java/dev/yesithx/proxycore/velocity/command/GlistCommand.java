package dev.yesithx.proxycore.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import dev.yesithx.proxycore.velocity.ProxyCoreVelocity;
import dev.yesithx.proxycore.velocity.util.ColorUtil;

public final class GlistCommand implements SimpleCommand {

    private final ProxyCoreVelocity plugin;

    public GlistCommand(ProxyCoreVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation inv) {
        inv.source().sendMessage(ColorUtil.color("&8&m-----&r &6&lNetwork List &8&m-----"));
        for (RegisteredServer rs : plugin.getServer().getAllServers()) {
            int count = rs.getPlayersConnected().size();
            String status = count > 0 ? "&a● " : "&c● ";
            inv.source().sendMessage(ColorUtil.color(" " + status + "&e" + rs.getServerInfo().getName() + " &7- &f" + count + " players"));
        }
        inv.source().sendMessage(ColorUtil.color("&7Total: &f" + plugin.getServer().getPlayerCount() + " players"));
        inv.source().sendMessage(ColorUtil.color("&8&m-------------------"));
    }

    @Override
    public boolean hasPermission(Invocation inv) {
        return inv.source().hasPermission("proxycore.glist");
    }
}
