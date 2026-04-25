package dev.yesithx.proxycore.bungeecord.manager;

import dev.yesithx.proxycore.bungeecord.ProxyCoreBungee;

public class MaintenanceManager {

    private final ProxyCoreBungee plugin;
    private boolean enabled = false;

    public MaintenanceManager(ProxyCoreBungee plugin) {
        this.plugin = plugin;
        this.enabled = plugin.getConfig().getBoolean("maintenance.enabled", false);
    }

    public boolean isEnabled() { return enabled; }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getKickMessage() {
        return plugin.getMessages().getString("maintenance-kick",
                "&cThe server is under maintenance. Please try again later.");
    }
}
