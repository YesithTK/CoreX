package dev.yesithx.proxycore.api.event;

/**
 * Base class for all ProxyCore API events.
 * Extend this to create custom events that addons can listen to.
 *
 * @author YesithTK
 */
public abstract class ProxyCoreEvent {

    private final long timestamp;

    protected ProxyCoreEvent() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Returns the Unix timestamp (ms) when this event was created.
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the simple name of this event class.
     */
    public String getEventName() {
        return getClass().getSimpleName();
    }
}
