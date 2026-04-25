package dev.yesithx.proxycore.api;

/**
 * Static entry point for ProxyCore's API.
 * Addons and external plugins can use this to access ProxyCore's managers.
 *
 * <p>Example usage:</p>
 * <pre>
 *     IProxyCorePlugin api = ProxyCoreAPI.get();
 *     boolean maintenance = api.getMaintenanceManager().isEnabled();
 * </pre>
 *
 * @author YesithTK
 */
public final class ProxyCoreAPI {

    private static IProxyCorePlugin instance;

    private ProxyCoreAPI() {}

    /**
     * Returns the active ProxyCore plugin instance.
     *
     * @return the plugin instance
     * @throws IllegalStateException if ProxyCore is not loaded
     */
    public static IProxyCorePlugin get() {
        if (instance == null) {
            throw new IllegalStateException("ProxyCore is not loaded or has not registered its API yet.");
        }
        return instance;
    }

    /**
     * Returns whether ProxyCore is currently loaded and available.
     */
    public static boolean isAvailable() {
        return instance != null;
    }

    /**
     * Registers the plugin instance. Called internally by ProxyCore on enable.
     * Do NOT call this from external plugins.
     *
     * @param plugin the plugin instance to register
     */
    public static void register(IProxyCorePlugin plugin) {
        if (instance != null) {
            throw new IllegalStateException("ProxyCore API is already registered.");
        }
        instance = plugin;
    }

    /**
     * Unregisters the plugin instance. Called internally on disable.
     */
    public static void unregister() {
        instance = null;
    }
}
