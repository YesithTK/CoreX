package dev.yesithx.proxycore.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import dev.yesithx.proxycore.velocity.ProxyCoreVelocity;
import dev.yesithx.proxycore.velocity.util.ColorUtil;

public final class ProxyReloadCommand implements SimpleCommand {

    private final ProxyCoreVelocity plugin;

    public ProxyReloadCommand(ProxyCoreVelocity plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Invocation inv) {
        inv.source().sendMessage(ColorUtil.color("&7Reloading ProxyCore..."));
        plugin.reload();
        inv.source().sendMessage(ColorUtil.color("&aProxyCore reloaded successfully!"));
    }

    @Override
    public boolean hasPermission(Invocation inv) {
        return inv.source().hasPermission("proxycore.reload");
    }
}
