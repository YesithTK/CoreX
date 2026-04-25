package dev.yesithx.proxycore.bungeecord.listener;

import dev.yesithx.proxycore.bungeecord.ProxyCoreBungee;
import dev.yesithx.proxycore.bungeecord.util.ColorUtil;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerListener implements Listener {

    private final ProxyCoreBungee plugin;

    public PlayerListener(ProxyCoreBungee plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogin(LoginEvent event) {
        if (!plugin.getMaintenanceManager().isEnabled()) return;
        if (event.getConnection().getName() != null) {
            var player = plugin.getProxy().getPlayer(event.getConnection().getName());
            if (player != null && player.hasPermission("proxycore.maintenance.bypass")) return;
        }
        event.setCancelled(true);
        event.setCancelReason(ColorUtil.color(plugin.getMaintenanceManager().getKickMessage()));
    }

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent event) {
        plugin.getMessageManager().removePlayer(event.getPlayer().getUniqueId());
    }
}
