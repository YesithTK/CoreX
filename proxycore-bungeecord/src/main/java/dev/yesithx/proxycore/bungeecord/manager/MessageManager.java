package dev.yesithx.proxycore.bungeecord.manager;

import dev.yesithx.proxycore.bungeecord.ProxyCoreBungee;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MessageManager {

    private final ProxyCoreBungee plugin;
    private final Map<UUID, UUID> lastMessaged = new HashMap<>();

    public MessageManager(ProxyCoreBungee plugin) {
        this.plugin = plugin;
    }

    public void setLastMessaged(UUID sender, UUID target) {
        lastMessaged.put(sender, target);
        lastMessaged.put(target, sender);
    }

    public ProxiedPlayer getLastMessaged(UUID uuid) {
        UUID targetUUID = lastMessaged.get(uuid);
        if (targetUUID == null) return null;
        return plugin.getProxy().getPlayer(targetUUID);
    }

    public void removePlayer(UUID uuid) {
        lastMessaged.remove(uuid);
    }
}
