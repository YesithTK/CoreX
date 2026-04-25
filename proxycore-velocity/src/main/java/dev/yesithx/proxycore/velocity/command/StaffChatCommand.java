package dev.yesithx.proxycore.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import dev.yesithx.proxycore.velocity.ProxyCoreVelocity;
import dev.yesithx.proxycore.velocity.util.ColorUtil;
import net.kyori.adventure.text.Component;

public final class StaffChatCommand implements SimpleCommand {

    private final ProxyCoreVelocity plugin;

    public StaffChatCommand(ProxyCoreVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation inv) {
        if (inv.arguments().length < 1) {
            inv.source().sendMessage(ColorUtil.color("&cUsage: /vstaffchat <message>"));
            return;
        }
        String name = inv.source() instanceof Player ? ((Player) inv.source()).getUsername() : "Console";
        String msg = String.join(" ", inv.arguments());
        Component formatted = ColorUtil.color("&8[&cStaff&8] &7" + name + ": &f" + msg);
        plugin.getServer().getAllPlayers().stream()
                .filter(p -> p.hasPermission("proxycore.staffchat"))
                .forEach(p -> p.sendMessage(formatted));
        plugin.getLogger().info("[StaffChat] " + name + ": " + msg);
    }

    @Override
    public boolean hasPermission(Invocation inv) {
        return inv.source().hasPermission("proxycore.staffchat");
    }
}
