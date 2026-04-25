package dev.yesithx.proxycore.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.LoginEvent;
import dev.yesithx.proxycore.velocity.ProxyCoreVelocity;
import dev.yesithx.proxycore.velocity.util.ColorUtil;
import net.kyori.adventure.text.Component;

public class PlayerListener {

    private final ProxyCoreVelocity plugin;

    public PlayerListener(ProxyCoreVelocity plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void onLogin(LoginEvent event) {
        if (!plugin.getMaintenanceManager().isEnabled()) return;
        if (event.getPlayer().hasPermission("proxycore.maintenance.bypass")) return;
        event.setResult(com.velocitypowered.api.event.ResultedEvent.ComponentResult.denied(
                ColorUtil.color(plugin.getMaintenanceManager().getKickMessage())
        ));
    }

    @Subscribe
    public void onDisconnect(DisconnectEvent event) {
        plugin.getMessageManager().removePlayer(event.getPlayer().getUniqueId());
    }
}
