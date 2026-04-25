package dev.yesithx.proxycore.api;

import dev.yesithx.proxycore.api.manager.IAnnounceManager;
import dev.yesithx.proxycore.api.manager.IMaintenanceManager;
import dev.yesithx.proxycore.api.manager.IMessageManager;

/**
 * Base interface for ProxyCore plugin.
 * Implemented by both BungeeCord and Velocity versions.
 *
 * @author YesithTK
 */
public interface IProxyCorePlugin {

    /**
     * Returns the message manager handling private messages between players.
     */
    IMessageManager getMessageManager();

    /**
     * Returns the maintenance manager handling server maintenance mode.
     */
    IMaintenanceManager getMaintenanceManager();

    /**
     * Returns the announce manager handling automatic announcements.
     */
    IAnnounceManager getAnnounceManager();

    /**
     * Reloads the plugin configuration and all managers.
     */
    void reload();

    /**
     * Returns the plugin version string.
     */
    default String getVersion() {
        return "1.0.0";
    }
}
