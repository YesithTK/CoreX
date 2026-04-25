package dev.yesithx.proxycore.api.event;

/**
 * Fired when maintenance mode is toggled on or off.
 * Addons can listen to this to react to maintenance state changes.
 *
 * @author YesithTK
 */
public class MaintenanceToggleEvent extends ProxyCoreEvent {

    private final boolean enabled;
    private final String toggledBy;

    /**
     * @param enabled   true if maintenance was just enabled, false if disabled
     * @param toggledBy name of the player or "Console" who toggled it
     */
    public MaintenanceToggleEvent(boolean enabled, String toggledBy) {
        super();
        this.enabled = enabled;
        this.toggledBy = toggledBy;
    }

    /**
     * Returns true if maintenance mode was just turned ON.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Returns the name of the player or "Console" who triggered the toggle.
     */
    public String getToggledBy() {
        return toggledBy;
    }
}
